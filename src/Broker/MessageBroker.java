package Broker;

import java.util.HashMap;
import java.util.Map;

public class MessageBroker {
    private Map<String, Topic> topics = new HashMap<>();

    private void addTopic(String name) {
        topics.put(name, new Topic(name));
    }

    public void putValue(String topic, String producerName, int value) {
        if(!topics.containsKey(topic)) {
            addTopic(topic);
        }
        topics.get(topic).putValue(producerName, value);
    }

    public int getValue(String topic, String groupName, String consumerName) throws NoSuchTopicException {
        if(!topics.containsKey(topic))
            throw new NoSuchTopicException(topic);

        return topics.get(topic).getValue(groupName,consumerName);
    }
}
