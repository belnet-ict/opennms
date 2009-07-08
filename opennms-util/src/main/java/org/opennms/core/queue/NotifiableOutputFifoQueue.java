/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2002-2004, 2006, 2008 The OpenNMS Group, Inc.  All rights reserved.
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
 * This interface is implemented by FIFO queue implementations that can notify
 * interested listener when elements are removed from the queue. This is useful
 * for listeners that may block or preform other work while a queue is not
 * empty, instead of using polling.
 * </p>
 * 
 * @author <a href="mailto:weave@oculan.com">Brian Weaver </a>
 * @author <a href="http://www.opennms.org">OpenNMS </a>
 * 
 */
public interface NotifiableOutputFifoQueue extends FifoQueue {
    /**
     * Adds a new listener to the notifiable queue. If the listener already
     * exists then it is up to the implementor to determine behavior. When a new
     * element is removed from the queue the listener will have its
     * {@link OutputFifoQueueListener#onQueueOutput callback}method invoked.
     * 
     * @param listener
     *            The instance to be notified on queue removals.
     */
    public void addOutputListener(OutputFifoQueueListener listener);

    /**
     * Removes an already registered listener. If the listener was not already
     * registered then no action is performed.
     * 
     * @param listener
     *            The listener to remove from the queue.
     */
    public void removeOutputListener(OutputFifoQueueListener listener);
}
