package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

//public class DWGraphJsonDeserializer implements JsonDeserializer<DWGraph_DS> {
//    @Override
//    public DWGraph_DS deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//        JsonObject jsonObject = json.getAsJsonObject();
//        directed_weighted_graph dwg = new DWGraph_DS();
//        JsonObject edgesJsonObj = jsonObject.get("map").getAsJsonObject();
//        for (Map.Entry<String, JsonElement> set : edgesJsonObj.entrySet()){
//            //edge_data edge=new EdgeData();
//            String edgesKey=set.getKey();
//            JsonElement jsonValueElement = set.getValue();
//            double edgeWeight=jsonValueElement.getAsJsonObject().get("w").getAsDouble();
//            node_data src = new NodeData();
//            node_data dest = new NodeData();
//            dwg.connect(src.getKey(), dest.getKey(),edgeWeight);
//        }
//
//        JsonObject nodesJsonObj = jsonObject.get("nodes").getAsJsonObject();
//        for (Map.Entry<String, JsonElement> set : nodesJsonObj.entrySet()) {
//            node_data node = new NodeData();
//            String nodesKey = set.getKey();
//            JsonElement jsonValueElement = set.getValue();
//            int nodeId = jsonValueElement.getAsJsonObject().get("id").getAsInt();
//            geo_location geo = node.getLocation();
//            node.setLocation(geo);
//            dwg.addNode(node);
//        }
//
//        return dwg;
//
//}
