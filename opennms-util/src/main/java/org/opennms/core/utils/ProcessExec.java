/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2004-2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc.:
 *
 *      51 Franklin Street
 *      5th Floor
 *      Boston, MA 02110-1301
 *      USA
 *
 * For more information contact:
 *
 *      OpenNMS Licensing <license@opennms.org>
 *      http://www.opennms.org/
 *      http://www.opennms.com/
 *
 *******************************************************************************/


package org.opennms.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class ProcessExec {
    PrintStream m_out = null;

    PrintStream m_err = null;

    public ProcessExec(PrintStream out, PrintStream err) {
        m_out = out;
        m_err = err;
    }

    public int exec(String[] cmd) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(cmd);

        p.getOutputStream().close();
        PrintInputStream out = new PrintInputStream(p.getInputStream(), m_out);
        PrintInputStream err = new PrintInputStream(p.getErrorStream(), m_err);

        Thread t1 = new Thread(out);
        Thread t2 = new Thread(err);
        t1.start();
        t2.start();

        int exitVal = p.waitFor();

        t1.join();
        t2.join();

        return exitVal;
    }

    public class PrintInputStream implements Runnable {
        private InputStream m_inputStream;

        private PrintStream m_printStream;

        public PrintInputStream(InputStream inputStream, PrintStream printStream) {
            m_inputStream = inputStream;
            m_printStream = printStream;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(m_inputStream));
                String line;

                while ((line = in.readLine()) != null) {
                    m_printStream.println(line);
                }

                m_inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    m_inputStream.close();
                } catch (IOException e2) {
                } // do nothing
            }
        }

    }
}
