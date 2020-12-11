package gameClient;
import Server.Game_Server_Ex2;
import api.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Ex2 {

    public static node_data[] loadPokemon(List<CL_Pokemon> l,dw_graph_algorithms graph){
        node_data [] arr = new node_data[l.size()];
        node_data temp=null;
        Iterator<node_data> it = graph.getGraph().getV().iterator();
        for (int i=0;i<l.size();i++){
            double minDist =Double.MAX_VALUE;
            while(it.hasNext()) {
            temp = it.next();
                if(temp.getLocation().distance(l.get(i).getLocation())<minDist) {
                minDist = temp.getLocation().distance(l.get(i).getLocation());
                arr[i] = temp;
                }
            }
        }
        return arr;
    }

    public static HashMap<CL_Agent,HashMap<CL_Pokemon,List<node_data>>> matchPokemonsToAgents(List<CL_Pokemon> pokemonList, List<CL_Agent> agentList, dw_graph_algorithms startGraph){
        HashMap<CL_Agent,HashMap<CL_Pokemon,List<node_data>>> pokemonTable = new HashMap<>();
        CL_Pokemon tempPoke=null;
        CL_Agent bestAgent=null;
        double minDist=Double.MAX_VALUE;
        Iterator<CL_Pokemon> pokemonIt=pokemonList.iterator();
        while (pokemonIt.hasNext()){
            tempPoke=pokemonIt.next();
            Iterator<CL_Agent> agentIt=agentList.iterator();
                while (agentIt.hasNext()) {
                    CL_Agent tempAgent = agentIt.next();
                    if (startGraph.shortestPathDist(tempAgent.getID(),tempPoke.get_edge().getDest()) < minDist) {
                        minDist = startGraph.shortestPathDist(tempAgent.getID(),tempPoke.get_edge().getDest());
                        bestAgent = tempAgent;
                    }
                }
                List<node_data> patch = startGraph.shortestPath(bestAgent.getID(),tempPoke.get_edge().getDest());
                HashMap<CL_Pokemon,List<node_data>> agentPath = new HashMap<>();
                agentPath.put(tempPoke,patch);
                pokemonTable.put(bestAgent,agentPath);
                agentList.remove(bestAgent);
        }
//        Iterator<CL_Agent> agentIt= agentList.iterator();
//        CL_Agent bestAgent=null;
//        double minDist=Double.MAX_VALUE;
//        while (agentIt.hasNext()){
//            CL_Agent tempAgent=agentIt.next();
//            double dist=startGraph.shortestPathDist(pEdge.getSrc(), tempAgent.getSrcNode());
//            if (dist<minDist){
//                minDist=dist;
//                bestAgent=tempAgent;
//            }
//        }
        return pokemonTable;
    }

    public static node_data agentNextNode(CL_Agent currentAgent,CL_Pokemon p,dw_graph_algorithms startGraph){
        List<node_data> l = new ArrayList<node_data>();
        l=startGraph.shortestPath(currentAgent.getSrcNode(), p.get_edge().getSrc());
        node_data agentNextNode=l.get(1);
        return agentNextNode;
    }


    public static void main(String[] args) {
        int level_number=0;
        int countAgent=0;
        game_service game = Game_Server_Ex2.getServer(level_number);
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms startGraph = new DWGraph_Algo();
        startGraph.init(g1);
        startGraph.load("Data/A0");
        List<CL_Pokemon> pokemonList = Arena.json2Pokemons(game.getPokemons());
        List<CL_Agent> agentList = Arena.getAgents(game.getAgents(),startGraph.getGraph());

        //update the location of agents
        if(pokemonList.size()>agentList.size()) {
            for (int i = 0; i <agentList.size(); i++) {
                game.addAgent(loadPokemon(pokemonList, startGraph)[i].getKey());
            }
        }
        else {
            for (int i = 0; i < pokemonList.size(); i++) {
                game.addAgent(loadPokemon(pokemonList, startGraph)[i].getKey());
                countAgent++;
            }
            for (int i = countAgent;i<agentList.size();i++)
                game.addAgent(i);
        }

        game.startGame();
        while (game.isRunning()){
            //update all the edges in CL_Pokemon list.
            Iterator<CL_Pokemon> updatePokemonIt= pokemonList.iterator();
            while(updatePokemonIt.hasNext()) {
                CL_Pokemon p = updatePokemonIt.next();
                Arena.updateEdge(p, startGraph.getGraph());
            }
            /*moving all the agents to the closet pokemons.
            This code is ineffective.we must use threads here.
             */
                HashMap<CL_Agent,HashMap<CL_Pokemon,List<node_data>>> pokemonTable=matchPokemonsToAgents(pokemonList,agentList,startGraph);
                Iterator<CL_Agent> agentIt = pokemonTable.keySet().iterator();
                while (agentIt.hasNext()){
                    CL_Agent currAgent=agentIt.next();
                    Iterator<CL_Pokemon> pokemonIt=pokemonTable.get(currAgent).keySet().iterator();
                    CL_Pokemon currPokemon=pokemonIt.next();
                    if(!currAgent.isMoving()&&pokemonTable.get(currAgent).get(currPokemon)!=null){
                        for(int i=0;i<pokemonTable.get(currAgent).get(currPokemon).size();i++)
                        game.chooseNextEdge(currAgent.getID(),pokemonTable.get(currAgent).get(currPokemon).indexOf(i));
                        game.move();
                    }
                {
                  //  node_data agentDest=agentNextNode(matchPokemonsToAgents(pTempEdge,p,agentList,startGraph),p,startGraph);
                 //   game.chooseNextEdge(matchPokemonsToAgents(pTempEdge,p,agentList,startGraph).getID(),agentDest.getKey());
                    //game.move();
                }
            }
        }

    }


}
