package gameClient;

import api.DWGraph_DS;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class Ex2JsonDeserializer implements JsonDeserializer<Ex2> {
    @Override
    public Ex2 deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

    }
}
