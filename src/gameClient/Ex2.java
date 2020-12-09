package gameClient;

import Server.Game_Server_Ex2;
import api.*;

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
    }
}
