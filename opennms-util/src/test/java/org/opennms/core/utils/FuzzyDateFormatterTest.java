/*******************************************************************************
 * This file is part of the OpenNMS(R) Application.
 *
 * Copyright (C) 2007-2008 The OpenNMS Group, Inc.  All rights reserved.
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

import java.util.Date;

import junit.framework.TestCase;

/**
 * 
 * @author <a href="mailto:ranger@opennms.org">Benjamin Reed</a>
 */
public class FuzzyDateFormatterTest extends TestCase {
    protected Date now = new Date();

    public void testFuzzy() throws Exception {
        assertEquals("1 second",     FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - 1000L                       ), now));
        assertEquals("30 seconds",   FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - 30000L                      ), now));
        assertEquals("30 seconds",   FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - 30005L                      ), now));
        assertEquals("30 seconds",   FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - 30100L                      ), now));
        assertEquals("1 minute",     FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - 60000L                      ), now));
        assertEquals("2 minutes",    FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 90)                ), now));
        assertEquals("25 minutes",   FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 25)           ), now));
        assertEquals("1 hour",       FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60)           ), now));
        assertEquals("1 hour",       FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 72)           ), now));
        assertEquals("2 hours",      FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 120)          ), now));
        assertEquals("1 day",        FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24)      ), now));
        assertEquals("2 days",       FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 36)      ), now));
        assertEquals("2 weeks",      FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24 * 14) ), now));
        assertEquals("2 weeks",      FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24 * 17) ), now));
        assertEquals("1 month",      FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24 * 30) ), now));
        
        Double realMonth = (1000 * 60 * 60 * 24 * 365.0 / 12.0);
        
        assertEquals("2 months",     FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (realMonth.longValue() * 2) ), now));
        assertEquals("3 months",     FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (new Double(realMonth * 2.5)).longValue() ), now));
        
        // why is this not 8.0?
        assertEquals("8 months",     FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (new Double(realMonth * 8)).longValue() ), now));
        
        assertEquals("1 year",       FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24 * 365) ), now));
        assertEquals("2 years",      FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24 * 548) ), now));
        assertEquals("2 years",      FuzzyDateFormatter.calculateDifference(new Date(now.getTime() - (1000L * 60 * 60 * 24 * 730) ), now));
    }
}
