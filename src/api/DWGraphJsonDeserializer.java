package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

public class DWGraphJsonDeserializer implements JsonDeserializer<DWGraph_DS> {
    @Override
    public DWGraph_DS deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String edges = jsonObject.get("Edges").getAsString();
        directed_weighted_graph dwg = new DWGraph_DS();
        for (Map.Entry<>)

    }

}
