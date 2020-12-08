package api;

import com.google.gson.annotations.SerializedName;

public class EdgeData implements edge_data {
    private node_data src;
    private node_data dest;
    @SerializedName("w")
    private double weight;
    private int tag;
    private String info;


    public EdgeData(node_data src,node_data dest,double weight){
    this.src=src;
    this.dest=dest;
    this.weight=weight;
    this.tag=0;
    this.info="";

    }
    @Override
    public int getSrc() {
        return this.src.getKey();
    }

    @Override
    public int getDest() {
        return this.dest.getKey();
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
    this.info=s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
    this.tag=t;
    }
}
