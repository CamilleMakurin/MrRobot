package v2.core.action.domain;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import v2.core.action.ActionType;

import java.io.IOException;

public class ActionDeserializer extends JsonDeserializer<Action> {
    @Override
    public Action deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);

        if (root.has("specialOrder")) {
            return mapper.readValue(root.toString(), SpecialAction.class);
        }
        if (root.has("keyCode")) {
            return mapper.readValue(root.toString(), KeyAction.class);
        }
        return mapper.readValue(root.toString(), MouseAction.class);
    }
}
