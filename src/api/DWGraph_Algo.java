package api;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DWGraph_Algo implements dw_graph_algorithms{
    private directed_weighted_graph dwga;

    @Override
    public void init(directed_weighted_graph g) {
    this.dwga=g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return this.dwga;
    }

    @Override
    public directed_weighted_graph copy() {
        node_data tempNode=null;
        edge_data tempEdge=null;
        if(dwga!=null){
            directed_weighted_graph temp_graph = new DWGraph_DS();
            Iterator<node_data> it =this.dwga.getV().iterator();
            while(it.hasNext()){
                temp_graph.addNode(it.next());
            }
            Iterator <node_data> it2 = this.dwga.getV().iterator();
            while (it2.hasNext()){
                tempNode=it2.next();
                Iterator <edge_data> it3 = this.dwga.getE(tempNode.getKey()).iterator();
                while (it3.hasNext()){
                    tempEdge=it3.next();
                    temp_graph.connect(tempNode.getKey(),tempEdge.getDest(),this.dwga.getEdge(tempNode.getKey(), tempEdge.getDest()).getWeight());
                }
            }
            return temp_graph;
        }
        return null;
    }

    @Override
    public boolean isConnected() {
        if (this.dwga.nodeSize() <= 1)
            return true;
        Queue<node_data> q = new LinkedList<node_data>();
        Iterator<node_data> it = this.dwga.getV().iterator();
        node_data temp = it.next();
        q.add(temp);
        temp.setTag(0);
        while (q.isEmpty() == false) {
            node_data peek = q.peek();
            Iterator<edge_data> edges = this.dwga.getE(peek.getKey()).iterator();
            while (edges.hasNext()) {
                edge_data neigh = edges.next();
                if (this.dwga.getNode(neigh.getDest()).getTag() == Integer.MAX_VALUE) {
                    q.add(this.dwga.getNode(neigh.getDest()));
                    this.dwga.getNode(neigh.getDest()).setTag(0);
                }
            }
            q.poll();
        }
        Iterator<node_data> nodeIt = this.dwga.getV().iterator();
        while (nodeIt.hasNext()) {
            node_data check = nodeIt.next();
            if (check.getTag() != 0) {
                resInfo(this.dwga);
                return false;
            }
        }
        resInfo(dwga);
        directed_weighted_graph redirectedGraph = redirect(this.dwga);
        Queue<node_data> q1 = new LinkedList<node_data>();
        Iterator<node_data> itr = redirectedGraph.getV().iterator();
        node_data temp1 = itr.next();
        q1.add(temp1);
        temp1.setTag(0);
        while (q1.isEmpty() == false) {
            node_data peek = q1.peek();
            Iterator<edge_data> edges = redirectedGraph.getE(peek.getKey()).iterator();
            while (edges.hasNext()) {
                edge_data neigh = edges.next();
                if (redirectedGraph.getNode(neigh.getDest()).getTag() == Integer.MAX_VALUE) {
                    q1.add(redirectedGraph.getNode(neigh.getDest()));
                    redirectedGraph.getNode(neigh.getDest()).setTag(0);
                }
            }
            q1.poll();
        }
        Iterator<node_data> nodeIt1 = redirectedGraph.getV().iterator();
        while (nodeIt1.hasNext()) {
            node_data check = nodeIt1.next();
            if (check.getTag() != 0) {
                resInfo(redirectedGraph);
                return false;
            }
        }
        return true;
        }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }

    private void resInfo(directed_weighted_graph dwga){
        Iterator <node_data> it = dwga.getV().iterator();
        node_data temp =null;
        while (it.hasNext()){
            temp=it.next();
            temp.setTag(Integer.MAX_VALUE);
            temp.setInfo("");
        }
    }

    private directed_weighted_graph redirect(directed_weighted_graph dwga){
        node_data tempNode=null;
        edge_data tempEdge=null;
            directed_weighted_graph temp_graph = new DWGraph_DS();
            Iterator<node_data> it =this.dwga.getV().iterator();
            while(it.hasNext()){
                temp_graph.addNode(it.next());
            }
            Iterator <node_data> it2 = this.dwga.getV().iterator();
            while (it2.hasNext()){
                tempNode=it2.next();
                Iterator <edge_data> it3 = this.dwga.getE(tempNode.getKey()).iterator();
                while (it3.hasNext()){
                    tempEdge=it3.next();
                    temp_graph.connect(tempEdge.getDest(),tempNode.getKey(),this.dwga.getEdge(tempNode.getKey(), tempEdge.getDest()).getWeight());
                }
            }
            return temp_graph;
        }



    public static void main(String[] args) {
        directed_weighted_graph g = new DWGraph_DS();
        node_data a = new NodeData();
        node_data b = new NodeData();
        node_data c = new NodeData();
        node_data d = new NodeData();
        node_data e = new NodeData();
        node_data f = new NodeData();
        g.addNode(a);
        g.addNode(b);
        g.addNode(c);
        g.addNode(d);
//        g.addNode(e);
//        g.addNode(f);
        g.connect(a.getKey(),b.getKey(),2);
        g.connect(a.getKey(),c.getKey(),4);
        g.connect(b.getKey(),d.getKey(),6);
        g.connect(c.getKey(),b.getKey(),6);
        g.connect(d.getKey(),b.getKey(),6);
        g.connect(d.getKey(),a.getKey(),6);
        dw_graph_algorithms wga=new DWGraph_Algo();
        wga.init(g);
        directed_weighted_graph copy=new DWGraph_DS();
        System.out.println(wga.isConnected());
        System.out.println("a");








    }
}
