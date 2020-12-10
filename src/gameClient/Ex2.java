package gameClient;

import Server.Game_Server_Ex2;
import api.*;

import java.util.ArrayList;
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

    public static CL_Agent findTheBestAgent(edge_data pEdge,CL_Pokemon p,List<CL_Agent> agentList,dw_graph_algorithms startGraph){
        Iterator<CL_Agent> agentIt= agentList.iterator();
        CL_Agent bestAgent=null;
        double minDist=Double.MAX_VALUE;
        while (agentIt.hasNext()){
            CL_Agent tempAgent=agentIt.next();
            double dist=startGraph.shortestPathDist(pEdge.getSrc(), tempAgent.getSrcNode());
            if (dist<minDist){
                minDist=dist;
                bestAgent=tempAgent;
            }
        }
        return bestAgent;
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
        ///////////////////////////////////////////////////////////////////////////////after starting the game
        game.startGame();
        while (game.isRunning()){

            //update the pokemons edges and move the agents according the shortestPath
            Iterator<CL_Pokemon> pokemonIt= pokemonList.iterator();
            while(pokemonIt.hasNext()){
                CL_Pokemon p=pokemonIt.next();
                Arena.updateEdge(p,startGraph.getGraph());//מתאימה לכל פוקימון את הצלע שלו בגרף
                edge_data pTempEdge=p.get_edge();

                if (!findTheBestAgent(pTempEdge,p,agentList,startGraph).isMoving() && agentNextNode(findTheBestAgent(pTempEdge,p,agentList,startGraph),p,startGraph)!=null)//בודק שהסוכן המתאים לא בתנועה
                {

                    node_data agentDest=agentNextNode(findTheBestAgent(pTempEdge,p,agentList,startGraph),p,startGraph);
                    game.chooseNextEdge(findTheBestAgent(pTempEdge,p,agentList,startGraph).getID(),agentDest.getKey());
                    game.move();
                }
            }
        }

    }


}
