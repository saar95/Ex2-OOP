import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, HashMap<Integer, edge_data>> map;
    private HashMap<Integer, node_data> nodes;
    private int edgeCount;
    private int modeCount;

    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(this.nodes.containsKey(src)&&this.nodes.containsKey(dest))
            if(this.map.get(src).containsKey(dest))
                return this.map.get(src).get(dest);
            return null;
    }

    @Override
    public void addNode(node_data n) {
        HashMap<Integer, edge_data> tempmap=new HashMap<>();
        if(!this.nodes.containsKey(n.getKey())) {
            nodes.put(n.getKey(), n);
            map.put(n.getKey(),tempmap);
            modeCount++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
         if (this.nodes.containsKey(src)&&this.nodes.containsKey(dest)){
            edge_data edge=new EdgeData(nodes.get(src),nodes.get(dest),w);
            if(!this.map.get(src).containsKey(dest)) {
                this.map.get(src).put(dest, edge);
                modeCount++;
                edgeCount++;
            }
         }
    }

    @Override
    public Collection<node_data> getV() {
        return this.nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return map.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        if(!this.nodes.containsKey(key))
            return null;
        node_data temp=getNode(key);
        Iterator<Integer> it=this.map.get(key).keySet().iterator();
        while (it.hasNext())
        {
            map.get(key).remove(it.next());
            edgeCount--;
            modeCount++;
        }
        this.map.remove(key);
        this.nodes.remove(key);
        modeCount++;
        return temp;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if (getEdge(src,dest)!=null) {
            edge_data temp=getEdge(src, dest);
            this.map.get(src).remove(dest);
            modeCount++;
            edgeCount--;
            return temp;
        }
        return null;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeCount;
    }

    @Override
    public int getMC() {
        return modeCount;
    }
}
