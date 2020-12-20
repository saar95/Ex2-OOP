package gameClient;

import api.*;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;


public class MyPanel extends JPanel {
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private game_service game;

    public MyPanel(Arena _ar,game_service game){
        super();
        this._ar = _ar;
        this.game=game;
    }
    private void updateFrame() {
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-20,20);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateFrame();
        drawEdges(g);
        drawNodes(g);
        drawPokemons(g);
        drawAgants(g);
        drawTime(g);

    }
    private void drawPokemons(Graphics g){
        int r=8;
        List<CL_Pokemon> pokList=_ar.getPokemons();
        Iterator<CL_Pokemon> pokeListIt=pokList.iterator();
        while (pokeListIt.hasNext()){
            CL_Pokemon tempPoke=pokeListIt.next();
            geo_location pokemonPosition = tempPoke.getLocation();
            geo_location fp = this._w2f.world2frame(pokemonPosition);
            g.setColor(Color.YELLOW);
            g.fillOval((int)fp.x()-r,(int)fp.y()-r,2*r,2*r);
            g.setColor(Color.green);

            g.drawString(""+tempPoke.getValue(),(int) fp.x()-r,(int) fp.y() - r);
        }
    }
    private void drawAgants(Graphics g) {
        int r = 8;
        List<CL_Agent> agentList=_ar.getAgents();
        Iterator<CL_Agent> agentListIt=agentList.iterator();
        while(agentListIt.hasNext()){
            CL_Agent tempAgent= agentListIt.next();
            geo_location agentPosition = tempAgent.getLocation();
            geo_location fp = this._w2f.world2frame(agentPosition);
            g.setColor(Color.red);
            g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);

            g.drawString(""+tempAgent.getID()+"->"+tempAgent.getValue(),(int) fp.x()-r,(int) fp.y() - r);
        }
    }
    private void drawEdges(Graphics g){
        g.setColor(Color.BLACK);
        directed_weighted_graph dwg=_ar.getGraph();
        Iterator<node_data> nodesIt=dwg.getV().iterator();
        while (nodesIt.hasNext()){
            node_data tempNode=nodesIt.next();
            Iterator<edge_data> edgesIt=_ar.getGraph().getE(tempNode.getKey()).iterator();
            while (edgesIt.hasNext()){
                edge_data tempEdge=edgesIt.next();
                drawEdge(tempEdge,g);
            }
        }
    }
    private void drawEdge( edge_data e, Graphics g) {
            directed_weighted_graph gg = _ar.getGraph();
            geo_location s = gg.getNode(e.getSrc()).getLocation();
            geo_location d = gg.getNode(e.getDest()).getLocation();
            geo_location s0 = this._w2f.world2frame(s);
            geo_location d0 = this._w2f.world2frame(d);
            g.drawLine((int)s0.x(), (int)s0.y(), (int)d0.x(), (int)d0.y());
            
    }
    private void drawNodes(Graphics g) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("", Font.CENTER_BASELINE, 18));

        Iterator<node_data>nodesIt=_ar.getGraph().getV().iterator();
        while(nodesIt.hasNext()){
            node_data tempNode= nodesIt.next();
            drawNode(tempNode, 5, g);
        }
        
    }

        private void drawNode(node_data n, int r, Graphics g) {
            geo_location pos = n.getLocation();
            geo_location fp = this._w2f.world2frame(pos);
            g.fillOval((int)fp.x()-r, (int)fp.y()-r, 18, 18);
            g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-2*r);
        }
        
        private void drawTime(Graphics g) {
        String min=String.valueOf(game.timeToEnd()/100000);
        String sec=String.valueOf(game.timeToEnd()/1000);
        g.setColor(Color.black);
        g.drawString(min+":"+sec,20,20);

         }
        }




