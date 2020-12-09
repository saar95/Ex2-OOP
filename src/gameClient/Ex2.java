package gameClient;

import Server.Game_Server_Ex2;
import api.*;


public class Ex2 {
    public static void main(String[] args) {
        int level_number=0;
        game_service game = Game_Server_Ex2.getServer(level_number);
        directed_weighted_graph g1 = new DWGraph_DS();
        dw_graph_algorithms startGraph = new DWGraph_Algo();
        startGraph.init(g1);
        startGraph.load("A0");
        System.out.println("a");


    }
}
