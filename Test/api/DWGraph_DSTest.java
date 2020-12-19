package api;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class DWGraph_DSTest {

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
    void getNode() {
        directed_weighted_graph dwg=graphCreator();
        dwg.getNode(0).setTag(8);
        dwg.getNode(0).setInfo("check");
        Assert.assertEquals(0,dwg.getNode(0).getKey());
        Assert.assertEquals(8,dwg.getNode(0).getTag());
        Assert.assertEquals("check",dwg.getNode(0).getInfo());
        Assert.assertNull(dwg.getNode(10));
    }

    @Test
    void getEdge() {
        directed_weighted_graph dwg=graphCreator();
        edge_data temp=dwg.getEdge(dwg.getNode(0).getKey(),dwg.getNode(1).getKey());
        Assert.assertEquals(2,temp.getWeight(),0.00000001);
        Assert.assertEquals(1,temp.getDest());
        edge_data temp1=dwg.getEdge(dwg.getNode(0).getKey(),dwg.getNode(5).getKey());
        Assert.assertNull(temp1);
    }

    @Test
    void addNode() {
        directed_weighted_graph dwg=graphCreator();
        node_data temp=new NodeData(6);
        dwg.addNode(temp);
        Assert.assertEquals(6,temp.getKey());
    }

    @Test
    void connect() {
        directed_weighted_graph dwg=graphCreator();
        Assert.assertNull(dwg.getEdge(0,5));
        dwg.connect(0,5,28);
        Assert.assertEquals(28,dwg.getEdge(0,5).getWeight(),0.0000001);
    }

    @Test
    void getV() {
        directed_weighted_graph dwg=graphCreator();
        Queue<node_data> q = new LinkedList<node_data>();
        for (int i=0;i<6;i++){
            q.add(dwg.getNode(i));
        }
        Iterator<node_data> nodesIt=dwg.getV().iterator();
        while(nodesIt.hasNext()){
            node_data temp= nodesIt.next();
            Assert.assertEquals(temp.getKey(),q.poll().getKey());
        }
    }

    @Test
    void getE() {
        directed_weighted_graph dwg=graphCreator();
        dwg.connect(0,3,5);
        Iterator<edge_data> edgesIt=dwg.getE(0).iterator();
        Queue<edge_data> q=new LinkedList<edge_data>();
        q.add(dwg.getEdge(0,1));
        q.add(dwg.getEdge(0,2));
        q.add(dwg.getEdge(0,3));
        while(edgesIt.hasNext()){
            edge_data temp= edgesIt.next();
            Assert.assertEquals(q.poll().getWeight(),temp.getWeight(),0.0000001);
        }
    }

    @Test
    void removeNode() {
        directed_weighted_graph dwg=graphCreator();
        Assert.assertEquals(dwg.getNode(0).getKey(),0);
        Assert.assertEquals(6,dwg.nodeSize());
        node_data temp=dwg.removeNode(0);
        Assert.assertEquals(5,dwg.nodeSize());
        Assert.assertNull(dwg.getNode(0));
        Assert.assertEquals(temp.getKey(),0);
    }

    @Test
    void removeEdge() {
        directed_weighted_graph dwg=graphCreator();
        Assert.assertEquals(4,dwg.getEdge(0,2).getWeight(),0.000001);
        edge_data temp=dwg.removeEdge(0,2);
        Assert.assertNull(dwg.getEdge(0,2));
        Assert.assertEquals(temp.getWeight(),4,0.000001);
    }

    @Test
    void nodeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        node_data a = new NodeData(0);
        node_data b = new NodeData(1);
        node_data c = new NodeData(2);
        Assert.assertEquals(0,g.nodeSize());
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        Assert.assertEquals(3,g.nodeSize());
        g.removeNode(1);
        Assert.assertEquals(2,g.nodeSize());
    }

    @Test
    void edgeSize() {
        directed_weighted_graph dwg=graphCreator();
        Assert.assertEquals(10,dwg.edgeSize());
        dwg.connect(1,4,7);
        Assert.assertEquals(11,dwg.edgeSize());
        dwg.removeEdge(0,2);
        dwg.removeEdge(0,1);
        Assert.assertEquals(9,dwg.edgeSize());
    }

    @Test
    void getMC() {//need to check again
        directed_weighted_graph g=new DWGraph_DS();
        Assert.assertEquals(0,g.getMC());
        node_data a = new NodeData(0);
        node_data b = new NodeData(1);
        node_data c = new NodeData(2);
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        Assert.assertEquals(3,g.getMC());
        g.connect(0,1,10);
        g.connect(0,2,10);
        g.connect(1,0,10);
        g.connect(2,0,10);
        g.connect(1,2,10);
        Assert.assertEquals(8,g.getMC());
        g.removeNode(0);
        Assert.assertEquals(11,g.getMC());
    }
}