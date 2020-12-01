public class NodeData implements node_data{
    private int key;
    private double weight;
    private String info;
    private int tag;
    private geo_location geo;

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public geo_location getLocation() {
        return this.geo;
    }

    @Override
    public void setLocation(geo_location p) {
    this.geo=p;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
