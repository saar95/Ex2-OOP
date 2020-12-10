package api;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class represents a directional weighted graph.
 * Every edge have weight and can be connected from 2 direction (even with different weight)
 */
public class DWGraph_DS implements directed_weighted_graph {
    private HashMap<Integer, HashMap<Integer, edge_data>> map;
    private HashMap<Integer, node_data> nodes;
    private int edgeCount;
    private int modeCount;

    public DWGraph_DS(){
        this.map=new HashMap<Integer, HashMap<Integer,edge_data>>() ;
        this.nodes=new HashMap<Integer, node_data>();
        this.edgeCount=0;
        this.modeCount=0;
    }

    /**
     * This method checks if there is a node_data with the given node_id in the graph.
     * return the node if there is, else return null.
     * @param key - the node_id
     * @return node_data
     */
    @Override
    public node_data getNode(int key) {
        if(this.map.containsKey(key))
        return nodes.get(key);
        return null;
    }

    /**
     * This method checks if there is an edge from src to dest (src -> dest)
     * return edge_data of the edge if there is an edge from src to dest, else return null
     * @param src
     * @param dest
     * @return edge_data of the edge (src -> dest)
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(this.nodes.containsKey(src)&&this.nodes.containsKey(dest))
            if(this.map.get(src).containsKey(dest))
                return this.map.get(src).get(dest);
            return null;
    }

    @Override
    /**
     * This method adds the given node to the graph.
     * if the graph already have this node, do nothing.
     */
    public void addNode(node_data n) {
        HashMap<Integer, edge_data> tempmap=new HashMap<>();
        if(!this.nodes.containsKey(n.getKey())) {
            nodes.put(n.getKey(), n);
            map.put(n.getKey(),tempmap);
            modeCount++;
        }
    }

    /**
     * This method connect an edge from src to dest (src -> dest) with the given weight (w)
     * If there is an edge from src to dest (src -> dest) do nothing
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) from src to dest src-->dest.
     */
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

    /**
     * This method removes node with the given key from the graph.
     * This method also go through all the nodes in the graph and delete all the edges
     * which include the given node (key).
     * If there is no node with the given key in the graph return null.
     * @param key
     * @return data of the removed node
     */
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

    /**
     * This method removes an edge from src to dest (src -> dest) from the graph.
     * return egde_data of the edge from src to dest (src -> dest)
     * If there is no edge return null
     * @param src
     * @param dest
     * @return egde_data of the edge from src to dest (src -> dest)
     */
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

    /**
     * This method returns the number of the nodes in the graph.
     * @return
     */
    @Override
    public int nodeSize() {
        return nodes.size();
    }

    /**
     * This method returns the number of the edges in the graph (one direction edges).
     * @return
     */
    @Override
    public int edgeSize() {
        return edgeCount;
    }

    /**
     * This method returns the number of changes in the graph.
     * @return
     */
    @Override
    public int getMC() {
        return modeCount;
    }
}
