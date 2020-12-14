package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Ex2 implements Runnable{
    private static MyJFrame _win;
    private static Arena _ar;
    public static void main(String[] a) {
        Thread client = new Thread(new Ex2());
        client.start();
    }

    @Override
    public void run() {
        int scenario_num = 0;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        //	int id = 999;
        //	game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        directed_weighted_graph gg = loadGraph(game).getGraph();
        init(game);
        List <CL_Pokemon> l = updatePoke(game);
        game.startGame();
        int ind=0;
        long dt=100;
        while(game.isRunning()) {
            moveAgants(game, gg);
            try {
                if(ind%1==0) {_win.repaint();}
                Thread.sleep(dt);
                ind++;
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }
    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {
        String lg = game.move();
        List<CL_Agent> agentList = Arena.getAgents(lg, gg);
        _ar.setAgents(agentList);
        String fs =  game.getPokemons();
        List<CL_Pokemon> pokemosList = updatePoke(game);
        _ar.setPokemons(pokemosList);
        HashMap<CL_Agent,HashMap<CL_Pokemon,List<node_data>>> pokemonTable = matchPokemonsToAgents(game);
        Iterator<CL_Agent> agentIt = pokemonTable.keySet().iterator();
        while(agentIt.hasNext()) {
            CL_Agent ag = agentIt.next();
            int id = ag.getID();
            int dest = ag.getNextNode();
            int src = ag.getSrcNode();
            double v = ag.getValue();
            if(dest==-1) {
                Iterator<CL_Pokemon> pokeIt = pokemonTable.get(ag).keySet().iterator();
                CL_Pokemon currPoke=pokeIt.next();
                if(pokemonTable.get(ag).get(currPoke)!=null) {
                    List<node_data> nodePath = pokemonTable.get(ag).get(currPoke);
                    dest = nodePath.get(1).getKey();
                    game.chooseNextEdge(ag.getID(), dest);
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);
                    nodePath.remove(0);
                }
                else if(pokemonTable.get(ag).get(currPoke)==null){
                    dest=currPoke.get_edge().getSrc();
                    game.chooseNextEdge(ag.getID(),dest);
                    System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);

                }
            }
        }
        game.move();
    }

    public static dw_graph_algorithms loadGraph(game_service game){
        dw_graph_algorithms startGraph = new DWGraph_Algo();
        try {
            FileWriter myWriter = new FileWriter("gameGraph.txt");
            myWriter.write(game.getGraph());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startGraph.load("gameGraph.txt");
        return startGraph;
    }
    private static HashMap<CL_Agent, HashMap<CL_Pokemon,List<node_data>>> matchPokemonsToAgents(game_service game){
        dw_graph_algorithms startGraph = loadGraph(game);
        List <CL_Pokemon> pokemonList = updatePoke(game);
        HashMap<CL_Agent,HashMap<CL_Pokemon,List<node_data>>> pokemonTable = new HashMap<>();
        CL_Pokemon tempPoke=null;
        CL_Agent bestAgent=null;
        double minDist=Double.MAX_VALUE;
        List<CL_Agent> agentList = new ArrayList<>( _ar.getAgents());


        Iterator<CL_Pokemon> pokemonIt=pokemonList.iterator();
        while (pokemonIt.hasNext()){

            tempPoke=pokemonIt.next();
            Iterator<CL_Agent> agentIt=agentList.iterator();

            while (agentIt.hasNext()) {
                CL_Agent tempAgent = agentIt.next();
                int a = tempAgent.getSrcNode();
                int b = tempPoke.get_edge().getDest();
                if (startGraph.shortestPathDist(tempAgent.getSrcNode(),tempPoke.get_edge().getDest()) < minDist) {
                    minDist = startGraph.shortestPathDist(tempAgent.getSrcNode(),tempPoke.get_edge().getDest());
                    bestAgent = tempAgent;
                }
            }
            List<node_data> path = startGraph.shortestPath(bestAgent.getSrcNode(),tempPoke.get_edge().getDest());
            HashMap<CL_Pokemon,List<node_data>> agentPath = new HashMap<>();
            agentPath.put(tempPoke,path);
            pokemonTable.put(bestAgent,agentPath);
            agentList.remove(bestAgent);
            minDist=Double.MAX_VALUE;
        }
        return pokemonTable;
    }

    private static List<CL_Pokemon> updatePoke(game_service game) {
        dw_graph_algorithms startGraph = loadGraph(game);
        List <CL_Pokemon> pokemonList = Arena.json2Pokemons(game.getPokemons());
        Iterator<CL_Pokemon> pokemonIt = pokemonList.iterator();
        while (pokemonIt.hasNext()){
            CL_Pokemon tempPoke = pokemonIt.next();
            Arena.updateEdge(tempPoke,startGraph.getGraph());
        }
        return pokemonList;
    }
    private void init(game_service game) {
        String g = game.getGraph();
        String fs = game.getPokemons();
        directed_weighted_graph gg = loadGraph(game).getGraph();
        //gg.init(g);
        _ar = new Arena();
        _ar.setGraph(gg);
        _ar.setPokemons(Arena.json2Pokemons(fs));
        _win = new MyJFrame("test Ex2",_ar);
        _win.setSize(1024, 768);
        MyPanel panel=new MyPanel(_ar);
        _win.add(panel);
        _win.show();
        _win.setTitle("Gonna cathem all" + game.toString());
        _win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");
            System.out.println(info);
            System.out.println(game.getPokemons());
            int src_node = 0;  // arbitrary node, you should start at one of the pokemon
            ArrayList<CL_Pokemon> cl_fs = Arena.json2Pokemons(game.getPokemons());
            for(int a = 0;a<cl_fs.size();a++) { Arena.updateEdge(cl_fs.get(a),gg);}
            for(int a = 0;a<rs;a++) {
                int ind = a%cl_fs.size();
                CL_Pokemon c = cl_fs.get(ind);
                int nn = c.get_edge().getDest();
                if(c.getType()<0 ) {nn = c.get_edge().getSrc();}

                game.addAgent(nn);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
    }
}