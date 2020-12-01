import java.util.Iterator;
import java.util.List;

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
        return false;
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
        g.addNode(e);
        g.addNode(f);
        dw_graph_algorithms wga=new DWGraph_Algo();
        wga.init(g);
        directed_weighted_graph copy=new DWGraph_DS();
        copy=wga.copy();
        System.out.println("a");








    }
}
