package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class DWGraphJsonDeserializer implements JsonDeserializer<DWGraph_DS> {
    @Override
    public DWGraph_DS deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        DWGraph_DS dwg = new DWGraph_DS();
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray edgesArray = jsonObject.get("Edges").getAsJsonArray();
        JsonArray nodesArray = jsonObject.get("Nodes").getAsJsonArray();

        for (JsonElement nodes : nodesArray) {
            JsonObject nodesObject = nodes.getAsJsonObject();
            int id = nodesObject.get("id").getAsInt();
            node_data tempNode = new NodeData(id);
            String pos = nodesObject.get("pos").getAsString();
            String[] posArray = pos.split(",");
            double x = Double.parseDouble(posArray[0]);
            double y = Double.parseDouble(posArray[1]);
            double z = Double.parseDouble(posArray[2]);
            geo_location geo = new geoLocation(x, y, z);
            tempNode.setLocation(geo);
            dwg.addNode(tempNode);
        }
        for (JsonElement edges : edgesArray) {
            JsonObject edgesObject = edges.getAsJsonObject();
            int src = edgesObject.get("src").getAsInt();
            double weight = edgesObject.get("w").getAsDouble();
            int dest = edgesObject.get("dest").getAsInt();
            dwg.connect(src, dest, weight);
        }
        return dwg;
    }
}
