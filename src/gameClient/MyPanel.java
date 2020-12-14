package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;

    public MyPanel(Arena _ar){
        super();
        this._ar = _ar;
    }
    private void updateFrame() {////good
        Range rx = new Range(20,this.getWidth()-20);
        Range ry = new Range(this.getHeight()-20,20);
        Range2D frame = new Range2D(rx,ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g,frame);
    }

    @Override
    protected void paintComponent(Graphics g) {//שינוי סדר
        super.paintComponent(g);
        updateFrame();
        drawEdges(g);
        drawNodes(g);
        drawPokemons(g);
        drawAgants(g);

    }
    private void drawPokemons(Graphics g){
        int r=8;
    for(CL_Pokemon pokemon: _ar.getPokemons()){//iterator
        geo_location pokemonPosition = pokemon.getLocation();
        geo_location fp = this._w2f.world2frame(pokemonPosition);
        g.setColor(Color.YELLOW);
        g.fillOval((int)fp.x()-r,(int)fp.y()-r,2*r,2*r);
        g.setColor(Color.BLACK);

    }
    }
    private void drawAgants(Graphics g) {
        int r = 8;
        for (CL_Agent agent : _ar.getAgents()) {
            geo_location agentPosition = agent.getLocation();
            geo_location fp = this._w2f.world2frame(agentPosition);
            g.setColor(Color.green);
            g.fillOval((int) fp.x() - r, (int) fp.y() - r, 2 * r, 2 * r);
        }
    }
    private void drawEdges(Graphics g){
        g.setColor(Color.BLACK);
        for(node_data node: _ar.getGraph().getV()){//iterator
            for(edge_data Edge: _ar.getGraph().getE(node.getKey()))
            {
                drawEdge(Edge,g);
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
        for (node_data node : _ar.getGraph().getV()) {//iterator
            drawNode(node, 5, g);
        }
    }

        private void drawNode(node_data n, int r, Graphics g) {
            geo_location pos = n.getLocation();
            geo_location fp = this._w2f.world2frame(pos);
            g.fillOval((int)fp.x()-r, (int)fp.y()-r, 18, 18);
            g.drawString(""+n.getKey(), (int)fp.x(), (int)fp.y()-2*r);
        }

    }

