/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2002-2004, 2006-2008 The OpenNMS Group, Inc.  All rights reserved.
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


package org.opennms.core.queue;

/**
 * <p>
 * This interface defines a FIFO queue that can be open and closed to control
 * the addition of elements to the queue. When the queue is opened it is
 * possible to add new elements to the queue. When the queue is closed, it is
 * should not be possible to add elements to the queue. It should always be
 * possible to read elements from the queue, so long as it is not empty.
 * </p>
 * 
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 * @author <a href="http://www.opennms.org/">OpenNMS </a>
 * 
 */
public interface ClosableFifoQueue<T> extends FifoQueue<T> {
    /**
     * Returns true if the queue is currently open.
     * 
     * @return True if the queue is open.
     */
    public boolean isOpen();

    /**
     * Returns true if the queue is currently closed.
     * 
     * @return True if the queue is closed.
     */
    public boolean isClosed();

    /**
     * Closes a currently open queue. When a queue is closed is should still
     * allow elements already in the queue to be removed, but new elements
     * should not be added.
     * 
     * @exception org.opennms.core.queue.FifoQueueException
     *                Thrown if an error occurs closing the queue.
     */
    public void close() throws FifoQueueException;

    /**
     * Ensures that the queue is open and new elements can be added to the
     * queue.
     * 
     * @exception org.opennms.core.queue.FifoQueueException
     *                Thrown if an error occurs opening the queue.
     */
    public void open() throws FifoQueueException;
}
