/**
 * 
 */
package org.opennms.netmgt.dao.jdbc.event;

import javax.sql.DataSource;


public class FindAll extends EventMappingQuery {

    public FindAll(DataSource ds) {
        super(ds, "FROM events as events, nodes as nodes, ");
        compile();
    }
    
}