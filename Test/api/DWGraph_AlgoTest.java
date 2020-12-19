package api;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.List;

class DWGraph_AlgoTest {
    public static directed_weighted_graph graphCreator(){
        directed_weighted_graph g = new DWGraph_DS();
        node_data a = new NodeData(0);
        node_data b = new NodeData(1);
        node_data c = new NodeData(2);
        node_data d = new NodeData(3);
        node_data e = new NodeData(4);
        node_data f = new NodeData(5);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
        g.addNode(e);
        g.addNode(f);
        g.connect(a.getKey(),b.getKey(),2);
        g.connect(a.getKey(),c.getKey(),4);
        g.connect(b.getKey(),d.getKey(),6);
        g.connect(c.getKey(),b.getKey(),1);
        g.connect(d.getKey(),b.getKey(),9);
        g.connect(d.getKey(),a.getKey(),12);
        g.connect(b.getKey(),a.getKey(),1);
        g.connect(d.getKey(),e.getKey(),8);
        g.connect(e.getKey(),f.getKey(),12);
        g.connect(f.getKey(),d.getKey(),20);
        return g;
    }

    @Test
    void init() {
        directed_weighted_graph dwg=graphCreator();
    }

    @Test
    void getGraph() {
        directed_weighted_graph dwg=graphCreator();
        dw_graph_algorithms dwga=new DWGraph_Algo();
        dwga.init(dwg);
        Assert.assertEquals(dwg.edgeSize(),dwga.getGraph().edgeSize());
        Assert.assertEquals(dwg.getNode(0).getKey(),dwga.getGraph().getNode(0).getKey());
    }

    @Test
    void copy() {
        directed_weighted_graph dwg=graphCreator();
        dw_graph_algorithms dwga=new DWGraph_Algo();
        dwga.init(dwg);
        directed_weighted_graph newG=new DWGraph_DS();
        dw_graph_algorithms copiedG=new DWGraph_Algo();
        copiedG.init(newG);
        Assert.assertNotEquals(copiedG,dwga);
        Assert.assertFalse(copiedG.equals(dwga));
        Assert.assertNotEquals(copiedG.getGraph().nodeSize(),dwga.getGraph().nodeSize());
        newG=dwga.copy();
        copiedG.init(newG);
        Assert.assertEquals(copiedG.getGraph().nodeSize(),dwga.getGraph().nodeSize());
        Assert.assertEquals(copiedG.getGraph().getMC(),dwga.getGraph().getMC());

    }

    @Test
    void isConnected() {
        directed_weighted_graph dwg=graphCreator();
        dw_graph_algorithms dwga=new DWGraph_Algo();
        dwga.init(dwg);
        Assert.assertTrue(dwga.isConnected());
        dwga.getGraph().removeEdge(4,5);
        Assert.assertFalse(dwga.isConnected());
        directed_weighted_graph newDWG=new DWGraph_DS();
        dw_graph_algorithms newDWGA=new DWGraph_Algo();
        newDWGA.init(newDWG);
        Assert.assertTrue(newDWGA.isConnected());
        node_data a=new NodeData(0);
        node_data b=new NodeData(1);
        newDWG.addNode(a);
        newDWG.addNode(b);
        Assert.assertFalse(newDWGA.isConnected());
        newDWG.connect(0,1,5);
        Assert.assertFalse(newDWGA.isConnected());
        newDWG.connect(1,0,5);
        Assert.assertTrue(newDWGA.isConnected());
    }

    @Test
    void shortestPathDist() {
        directed_weighted_graph dwg=graphCreator();
        dw_graph_algorithms dwga=new DWGraph_Algo();
        dwga.init(dwg);
        Assert.assertEquals(0,dwga.shortestPathDist(0,0),0.0000001);
        Assert.assertEquals(16,dwga.shortestPathDist(0,4),0.0000001);
        dwg.connect(0,4,5);
        Assert.assertNotEquals(16,dwga.shortestPathDist(0,4),0.0000001);
        Assert.assertEquals(5,dwga.shortestPathDist(0,4),0.0000001);
        node_data temp=new NodeData(7);
        dwg.addNode(temp);
        Assert.assertEquals(-1,dwga.shortestPathDist(0, temp.getKey()),0.0000001);
    }

    @Test
    void shortestPath() {
        directed_weighted_graph dwg=graphCreator();
        dw_graph_algorithms dwga=new DWGraph_Algo();
        dwga.init(dwg);
        List<node_data> checkList=dwga.shortestPath(0,4);
        int[] checkA={0,1,3,4};
        int i=0;
        for (node_data temp:checkList){
            Assert.assertEquals(temp.getKey(),checkA[i]);
            i++;
        }
        node_data temp=new NodeData(7);
        dwg.addNode(temp);
        dwga.init(dwg);
        Assert.assertNull(dwga.shortestPath(0,7));
    }

    @Test
    void save() {
    }

    @Test
    void load() {
        directed_weighted_graph dwg=graphCreator();
        dw_graph_algorithms dwga=new DWGraph_Algo();
        dwga.init(dwg);
        String str="dwga";
        dwga.save(str);
        directed_weighted_graph dwg1=new DWGraph_DS();
        dw_graph_algorithms dwga1=new DWGraph_Algo();
        dwga.init(dwg);
        Assert.assertNotEquals(dwga1,dwga);
        dwga1.load(str);
        Assert.assertEquals(dwga1.getGraph().nodeSize(),dwga.getGraph().nodeSize());
        Assert.assertEquals(dwga1.getGraph().getMC(),dwga.getGraph().getMC());
        Assert.assertEquals(dwga1.getGraph().edgeSize(),dwga.getGraph().edgeSize());
        edge_data temp1=dwga.getGraph().getEdge(0,2);
        edge_data temp2=dwga1.getGraph().getEdge(0,2);
        Assert.assertEquals(temp1.getWeight(),temp2.getWeight(),0.000001);
    }
}