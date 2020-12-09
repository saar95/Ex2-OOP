package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.*;
import com.google.gson.JsonObject;

import java.util.Iterator;
import java.util.LinkedList;


public class Ex2 {

    public static node_data[] loadPokemon(String pLocation,dw_graph_algorithms graph){
        int counter=0;
        node_data minNode=null;
        JsonObject pObject=new JsonObject();
        JsonArray pokemonArray = pObject.get("Pokemons").getAsJsonArray();
        for (JsonElement nodes : pokemonArray) {
            JsonObject pokemonObject = nodes.getAsJsonObject();
            counter++;
        }
       node_data[] arr=new node_data[counter];
        for (JsonElement nodes : pokemonArray) {
            JsonObject pokemonObject = nodes.getAsJsonObject();
            String pos = pokemonObject.get("pos").getAsString();
            String[] posArray = pos.split(",");
            geo_location geo=new geoLocation(Double.parseDouble(posArray[0]),Double.parseDouble(posArray[1]),Double.parseDouble(posArray[2]));
            double minDist=Double.MAX_VALUE;
            for (int i=0;i<arr.length;i++){
                Iterator<node_data> it1 = graph.getGraph().getV().iterator();
                while (it1.hasNext()){
                    node_data tempNode= it1.next();
                    double dist=geo.distance(tempNode.getLocation());
                    if(dist<minDist) {
                        minDist = dist;
                        minNode=tempNode;
                    }
                }
                arr[i]=minNode;
            }
        }
        return arr;
    }
    public static void main(String[] args) {
        int level_number=0;
        game_service game = Game_Server_Ex2.getServer(level_number);
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms startGraph = new DWGraph_Algo();
        startGraph.init(g1);
        startGraph.load("Data/A0");
        loadPokemon(game.getPokemons(), startGraph);
        System.out.println("s");

    }
}
