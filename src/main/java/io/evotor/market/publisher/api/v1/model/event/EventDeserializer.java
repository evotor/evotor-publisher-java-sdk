package io.evotor.market.publisher.api.v1.model.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.TextNode;
import io.evotor.market.api.v2.model.Resource;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * Currently jackson doesn't support multi level polymorphic types
 * So we can't support polymorphism both for events hierarchy and products hierarchy
 *
 * @see <a href=https://github.com/FasterXML/jackson-databind/issues/374>Deserialize with multiple levels of polymorphic type hierachy</a>
 * @see <a href=https://github.com/FasterXML/jackson-databind/issues/1188>Polymorphic deserialization doesn't support multiple levels of inheritance.</a>
 */
public class EventDeserializer extends StdDeserializer<ApplicationEvent> {

    protected EventDeserializer() {
        super(ApplicationEvent.class);
    }

    @Override
    public ApplicationEvent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TreeNode node = p.readValueAsTree();
        Resource resource = null;
        EventType eventType = getEventType(node.get("type"));

        if (eventType.getResourceClass() != null) {
            resource = p.getCodec().treeToValue(node.get("payload"), eventType.getResourceClass());
        }

        UUID id = UUID.fromString(((TextNode) node.get("id")).asText());
        Date timestamp = new Date(((LongNode) node.get("timestamp")).longValue());
        String action = ((TextNode) node.get("action")).asText();

        return new ApplicationEvent(id, timestamp, eventType, action, resource);
    }

    private EventType getEventType(TreeNode node) {
        try {
            return EventType.valueOf(((TextNode) node).asText());
        } catch (IllegalArgumentException e) {
            return EventType.SystemEvent;
        }
    }
}
