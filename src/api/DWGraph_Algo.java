package api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

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
        node_data temp = null;
        double tempWeight=0;
        if(src==dest)
            return 0;
        PriorityQueue<node_data> q = new PriorityQueue<node_data>();
        q.add(this.dwga.getNode(src));
        dwga.getNode(src).setWeight(0);
        while (!q.isEmpty()) {
            temp = q.peek();
            if (temp.getInfo() == "") {
                temp.setInfo("1");
                if (temp.getKey() == dest) break;
                Iterator<edge_data> it = dwga.getE(temp.getKey()).iterator();
                while (it.hasNext()) {
                    node_data n = dwga.getNode(it.next().getDest());
                    if (n.getInfo() == "") {
                        tempWeight = dwga.getEdge(temp.getKey(), n.getKey()).getWeight();
                        if (tempWeight != -1 && tempWeight + temp.getWeight() < n.getWeight()) {
                            n.setWeight(tempWeight + temp.getWeight());
                            if (!q.contains(n)) q.add(n);
                        }
                    }
                }
            }
            q.poll();
        }
        double weight=dwga.getNode(dest).getWeight();
        if(weight!=Double.MAX_VALUE) {
            resInfo(dwga);
            return weight;
        }
        resInfo(dwga);
        return -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> l = new ArrayList<node_data>();
        node_data temp = null;
        double tempWeight=0;
        if(src==dest)
            return null;
        PriorityQueue<node_data> q = new PriorityQueue<node_data>();
        q.add(this.dwga.getNode(src));
        dwga.getNode(src).setWeight(0);
        while (!q.isEmpty()) {
            temp = q.peek();
            if (temp.getInfo() == "") {
                temp.setInfo("1");
                if (temp.getKey() == dest) break;
                Iterator<edge_data> it = dwga.getE(temp.getKey()).iterator();
                while (it.hasNext()) {
                    node_data n = dwga.getNode(it.next().getDest());
                    if (n.getInfo() == "") {
                        tempWeight = dwga.getEdge(temp.getKey(), n.getKey()).getWeight();
                        if (tempWeight != -1 && tempWeight + temp.getWeight() < n.getWeight()) {
                            n.setWeight(tempWeight + temp.getWeight());
                            if (!q.contains(n)) q.add(n);
                        }
                    }
                }
            }
            q.poll();
        }
        if(dwga.getNode(dest).getWeight()==Double.MAX_VALUE)
            return null;
        l.add(dwga.getNode(dest));
        this.dwga=redirect(dwga);
        listMakerSrc(dwga.getNode(dest),l);
        this.dwga=redirect(dwga);
        Iterator<node_data> srcIt=l.listIterator();
            Stack<node_data> s = new Stack<node_data>();
            for (int i = 0; i < l.size(); ) {
                s.push(l.remove(i));
            }
            while (!s.isEmpty())
                l.add(s.pop());
            resInfo(dwga);
            return l;
        }

    private void listMakerSrc(node_data src ,List<node_data> l) {
        node_data temp=null;
        Iterator <edge_data> ni = dwga.getE(src.getKey()).iterator();
        while(ni.hasNext()){
            temp = dwga.getNode(ni.next().getDest());
            if(dwga.getEdge(src.getKey(), temp.getKey())!=null) {
                if(src.getWeight()==dwga.getEdge(src.getKey(), temp.getKey()).getWeight()+temp.getWeight()) {
                    l.add(temp);
                    listMakerSrc(temp, l);
                }
            }
        }
    }

    @Override
    public boolean save(String file) {
        Gson gson=new GsonBuilder().setPrettyPrinting().create();
        JsonObject edgesObject=new JsonObject();
        JsonObject nodesObject=new JsonObject();
        JsonArray edgesArray=new JsonArray();
        JsonArray nodesArray=new JsonArray();
        Iterator <node_data> it1 = this.dwga.getV().iterator();
        while (it1.hasNext()){
            node_data tempNode=it1.next();
            geo_location geo=tempNode.getLocation();
            double x=geo.x();
            double y=geo.y();
            double z=geo.z();
            String xLocation=String.valueOf(x);
            String yLocation=String.valueOf(y);
            String zLocation=String.valueOf(z);
            nodesObject.addProperty("pos",xLocation+","+yLocation+","+zLocation);
            nodesObject.addProperty("id",tempNode.getKey());
            nodesArray.add(nodesObject);
            Iterator <edge_data> it2 = this.dwga.getE(tempNode.getKey()).iterator();
            while (it2.hasNext()){
                edge_data tempEdge=it2.next();
                edgesObject.addProperty("src",tempEdge.getSrc());
                edgesObject.addProperty("w",tempEdge.getWeight());
                edgesObject.addProperty("dest",tempEdge.getDest());
                edgesArray.add(edgesObject);
            }
        }
        JsonObject graphObject=new JsonObject();
        graphObject.add("Edges",edgesArray);
        graphObject.add("Nodes",nodesArray);
        String json=gson.toJson(graphObject);

        try{
            PrintWriter pw=new PrintWriter(new File(file));
            pw.write(json);
            pw.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        //deserialize
        try {
            GsonBuilder builder=new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class,new DWGraphJsonDeserializer());
            Gson gson=builder.create();

            FileReader reader=new FileReader(file);
            directed_weighted_graph dwg=gson.fromJson(reader,DWGraph_DS.class);
            this.init(dwg);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void resInfo(directed_weighted_graph dwga){
        Iterator <node_data> it = dwga.getV().iterator();
        node_data temp =null;
        while (it.hasNext()){
            temp=it.next();
            temp.setTag(Integer.MAX_VALUE);
            temp.setInfo("");
            temp.setWeight(Double.MAX_VALUE);
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
//        g.addNode(e);
//        g.addNode(f);
        g.connect(a.getKey(),b.getKey(),2);
        g.connect(a.getKey(),c.getKey(),4);
        g.connect(b.getKey(),d.getKey(),6);
        g.connect(c.getKey(),b.getKey(),1);
        g.connect(d.getKey(),b.getKey(),9);
        //g.connect(d.getKey(),a.getKey(),12);
        //g.connect(b.getKey(),a.getKey(),1);
        dw_graph_algorithms wga=new DWGraph_Algo();
        wga.init(g);
        directed_weighted_graph copy=new DWGraph_DS();
        System.out.println(wga.isConnected());
        System.out.println(wga.shortestPathDist(d.getKey(),a.getKey()));
        List l = wga.shortestPath(c.getKey(),a.getKey());
        System.out.println(wga.save("wga.json"));
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms gaga =new DWGraph_Algo();
        gaga.load("wga.json");
        gaga.save("wga1.json");
        System.out.println(wga.load("wga.json"));









    }
}
