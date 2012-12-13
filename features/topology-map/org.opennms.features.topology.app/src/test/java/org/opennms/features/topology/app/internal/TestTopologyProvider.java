/*******************************************************************************
 * This file is part of OpenNMS(R).
 *
 * Copyright (C) 2012 The OpenNMS Group, Inc.
 * OpenNMS(R) is Copyright (C) 1999-2012 The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is a registered trademark of The OpenNMS Group, Inc.
 *
 * OpenNMS(R) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * OpenNMS(R) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenNMS(R).  If not, see:
 *      http://www.gnu.org/licenses/
 *
 * For more information contact:
 *     OpenNMS(R) Licensing <license@opennms.org>
 *     http://www.opennms.org/
 *     http://www.opennms.com/
 *******************************************************************************/

package org.opennms.features.topology.app.internal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opennms.features.topology.api.EditableGraphProvider;
import org.opennms.features.topology.api.VertexContainer;
import org.opennms.features.topology.api.topo.Vertex;
import org.opennms.features.topology.api.topo.VertexRef;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;

public class TestTopologyProvider implements EditableGraphProvider {
	private final String m_namespace;
    private TestVertexContainer m_vertexContainer;
    private BeanContainer<String, TestEdge> m_edgeContainer;
    private int m_vertexCounter = 0;
    private int m_edgeCounter = 0;
    private int m_groupCounter = 0;
    
    public TestTopologyProvider(String namespace) {
    	m_namespace = namespace;
        m_vertexContainer = new TestVertexContainer();
        m_edgeContainer = new BeanContainer<String, TestEdge>(TestEdge.class);
        m_edgeContainer.setBeanIdProperty("id");
        
        String vId1 = getNextVertexId();
        TestVertex v1 = new TestLeafVertex(vId1, 0, 0);
        v1.setLabel("a leaf");
        
        Item beanItem = m_vertexContainer.addBean(v1);
        
        String vId2 = getNextVertexId();
        TestVertex v2 = new TestLeafVertex(vId2, 0, 0);
        v2.setLabel("another leaf");
        Item beanItem2 = m_vertexContainer.addBean(v2);
        
        String edgeId = getNextEdgeId();
        TestEdge edge = new TestEdge(edgeId, v1, v2);
        m_edgeContainer.addBean(edge);
        
    }
    
    @Override
    public Vertex addVertex(int x, int y) {
        String id = getNextVertexId();
        TestVertex vert = new TestLeafVertex(id, x, y);
        vert.setLabel("a vertex");
        BeanItem<Vertex> item = m_vertexContainer.addBean(vert);
        return item.getBean();
        
    }
    
    private String getNextEdgeId() {
        return "e" + m_edgeCounter++;
    }

    private String getNextVertexId() {
        return "v" + m_vertexCounter++;
    }

    @Override
    public void setParent(VertexRef vertexId, VertexRef parentId) {
        assertVertex(vertexId);
        assertGroup(parentId);
        
        m_vertexContainer.setParent(vertexId, parentId);
    }

    @Override
    public Vertex addGroup(String groupLabel, String groupIcon) {
        String nextGroupId = getNextGroupId();
        return addGroup(nextGroupId, groupIcon, groupLabel);
    }

    private Vertex addGroup(String groupId, String groupIcon, String groupLabel) {
        if(m_vertexContainer.containsId(groupId)) {
            throw new IllegalArgumentException("A vertex or group with id " + groupId + " already exists!");
        }
        TestVertex vertex = new TestGroup(groupId);
        vertex.setLabel(groupLabel);
        m_vertexContainer.addBean(vertex);
        return vertex;
    }

    private String getNextGroupId() {
        return "g" + m_groupCounter++;
    }

    @Override
    public void save(String filename) {
        // Do nothing
    }

    @Override
    public void load(String filename) {

        m_edgeContainer.removeAllItems();
        m_vertexContainer.removeAllItems();
        
        List<TestVertex> vertices = new ArrayList<TestVertex>();
        List<TestEdge> edges = new ArrayList<TestEdge>();
        
        String vId1 = getNextVertexId();
        TestVertex v1 = new TestLeafVertex(vId1, 0, 0);
        v1.setLabel("a leaf vertex");
        
        vertices.add(v1);
        //Item beanItem = m_vertexContainer.addBean(v1);
        
        String vId2 = getNextVertexId();
        TestVertex v2 = new TestLeafVertex(vId2, 0, 0);
        v2.setLabel("another leaf");
        vertices.add(v2);
        //Item beanItem2 = m_vertexContainer.addBean(v2);
        
        String edgeId = getNextEdgeId();
        TestEdge edge = new TestEdge(edgeId, v1, v2);
        edges.add(edge);
        //m_edgeContainer.addBean(edge);
                 
        m_vertexContainer.addAll(vertices);
        m_edgeContainer.addAll(edges);
    }

    @Override
    public VertexContainer getVertexContainer() {
        return m_vertexContainer;
    }

    @Override
    public BeanContainer<String,TestEdge> getEdgeContainer() {
        return m_edgeContainer;
    }

    private TestEdge getRequiredEdge(Object edgeId) {
        return getEdge(edgeId, true);
    }

    private TestEdge getEdge(Object edgeId, boolean required) {
        BeanItem<TestEdge> item = m_edgeContainer.getItem(edgeId);
        if(required && item == null) {
            throw new IllegalArgumentException("required edge " + edgeId + " not found");
        }
        return item == null ? null : item.getBean();
    }

    private Vertex getRequiresVertex(Object vertexId) {
        return getVertex(vertexId, true);
    }

    private Vertex getVertex(Object vertexId, boolean required) {
        BeanItem<Vertex> item = m_vertexContainer.getItem(vertexId);
        if(required && item == null) {
            throw new IllegalArgumentException("required vertex " + vertexId + " not found");
        }
        return item == null ? null : item.getBean();
    }
    
    private void assertGroup(Object parentId) {
        assertTrue(m_vertexContainer.containsId(parentId));
        Vertex parentItem = m_vertexContainer.getItem(parentId).getBean();
        assertFalse(parentItem.isLeaf());
    }

    private void assertVertex(Object vertexId) {
        assertTrue(m_vertexContainer.containsId(vertexId));
    }

    private void assertEdge(Object edgeId) {
        assertTrue(m_edgeContainer.containsId(edgeId));
    }

	@Override
	public String getNamespace() {
		return m_namespace;
	}
}
