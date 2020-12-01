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

            if (this.gr.nodeSize()<=1)
                return true;
            Queue<node_info> q = new LinkedList<node_info>();
            Iterator<node_info> it = this.gr.getV().iterator();
            node_info temp = it.next();
            q.add(temp);
            temp.setTag(0);
            while (q.isEmpty()==false)
            {
                node_info peek=q.peek();
                Iterator<node_info> neighbors = this.gr.getV(peek.getKey()).iterator();
                while (neighbors.hasNext())
                {
                    node_info neigh = neighbors.next();
                    if (neigh.getTag() == Double.MAX_VALUE)
                    {
                        q.add(neigh);
                        neigh.setTag(0);
                    }
                }
                q.poll();
            }
            Iterator<node_info> newIt = this.gr.getV().iterator();
            while (newIt.hasNext()) {
                temp = newIt.next();
                if (temp.getTag() == Double.MAX_VALUE)
                {
                    Iterator<node_info> reset = this.gr.getV().iterator();
                    while(reset.hasNext())
                    {
                        node_info n=reset.next();
                        n.setTag(Double.MAX_VALUE);
                    }
                    return false;
                }
            }

            Iterator<node_info> reset = this.gr.getV().iterator();
            while(reset.hasNext())
            {
                node_info n=reset.next();
                n.setTag(Double.MAX_VALUE);
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
