//
// This file is part of the OpenNMS(R) Application.
//
// OpenNMS(R) is Copyright (C) 2006 The OpenNMS Group, Inc.  All rights reserved.
// OpenNMS(R) is a derivative work, containing both original code, included code and modified
// code that was published under the GNU General Public License. Copyrights for modified
// and included code are below.
//
// OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
//
// Modifications:
// 
// Created: November 9th, 2009 jonathan@opennms.org
//
// Copyright (C) 2009 The OpenNMS Group, Inc.  All rights reserved.
//
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
//
// For more information contact:
//      OpenNMS Licensing       <license@opennms.org>
//      http://www.opennms.org/
//      http://www.opennms.com/
//
package org.opennms.web.report.database.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class DatabaseReportCriteria implements Serializable {

    private static final long serialVersionUID = -3848794546173077375L;
    protected String m_reportId;
    protected String m_displayName;
    protected List <DatabaseReportDateParm> m_dateParms;
    protected List <DatabaseReportStringParm> m_stringParms;
    protected List <DatabaseReportIntParm> m_intParms;
    private HashMap <String,Object> m_reportParms;

    public DatabaseReportCriteria() {
        super();
        m_reportParms = new HashMap<String, Object>();
    }

    public List<DatabaseReportDateParm> getDateParms() {
        return m_dateParms;
    }

    public void setDateParms(List<DatabaseReportDateParm> dateParms) {
        m_dateParms = dateParms;
    }
    
    public List<DatabaseReportStringParm> getStringParms() {
        return m_stringParms;
    }

    public void setStringParms(List<DatabaseReportStringParm> strings) {
        m_stringParms = strings;
    }
    
    public List<DatabaseReportIntParm> getIntParms() {
        return m_intParms;
    }

    public void setIntParms(List<DatabaseReportIntParm> ints) {
        m_intParms = ints;
    }

    public void setReportId(String reportId) {
        m_reportId = reportId;
    }

    public String getReportId() {
        return m_reportId;
    }

    public void setDisplayName(String displayName) {
        m_displayName = displayName;
    }

    public String getDisplayName() {
        return m_displayName;
    }
    
    public HashMap<String, Object> getReportParms() {
        
        HashMap <String,Object>parmMap = new HashMap<String, Object>();
        
        // Add all the strings from the report
        if (m_stringParms != null ) {
            Iterator<DatabaseReportStringParm>stringIter = m_stringParms.iterator();
            while (stringIter.hasNext()) {
                DatabaseReportStringParm parm = stringIter.next();
                parmMap.put(parm.getName(), parm.getValue());
            }
        }
        // Add all the dates from the report
        if (m_dateParms != null) {
            Iterator<DatabaseReportDateParm>dateIter = m_dateParms.iterator();
            while (dateIter.hasNext()) {
                DatabaseReportDateParm parm = dateIter.next();
                if (parm.getUseAbsoluteDate()) {
                    // use the absolute date set when the report was scheduled.
                    parmMap.put(parm.getName(), parm.getValue());
                } else {
                    // use the offset date set when the report was scheduled
                    Calendar cal = Calendar.getInstance();
                    int amount = 0 - parm.getCount();
                    if (parm.getInterval().equals("year")) {
                        cal.add(Calendar.YEAR, amount);
                    } else { 
                        if (parm.getInterval().equals("month")) {
                            cal.add(Calendar.MONTH, amount);
                        } else {
                            cal.add(Calendar.DATE, amount);
                        }
                    }   
                    parmMap.put(parm.getName(),cal.getTime());
                }
            }
        }
        
        // Add all the integers from the report
        if (m_intParms != null) {
            Iterator<DatabaseReportIntParm>intIter = m_intParms.iterator();
            while (intIter.hasNext()) {
                DatabaseReportIntParm parm = intIter.next();
                parmMap.put(parm.getName(), parm.getValue());
            }
        }
        
        return parmMap;
    }

}