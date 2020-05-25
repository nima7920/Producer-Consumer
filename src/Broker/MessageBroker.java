package Broker;

import java.util.HashMap;
import java.util.Map;

public class MessageBroker {
    private Map<String, Topic> topics = new HashMap<>();

    private void addTopic(String name) {
        topics.put(name, new Topic(name));
    }

    public void putValue(String topic, String producerName, int value) {

        topics.get(topic).putValue(producerName, value);
    }

    public int getValue(String topic, String groupName, String consumerName) throws NoSuchTopicException {
        if(!topics.containsKey(topic))
            throw new NoSuchTopicException(topic);

        return topics.get(topic).getValue(groupName,consumerName);
    }

    public void addProducerGroup(String groupName){
        addTopic(groupName);
    }

    public void addConsumerGroup(String groupName,String topicName) throws NoSuchTopicException{
        if(!topics.containsKey(topicName))
            throw new NoSuchTopicException(topicName);
        topics.get(topicName).addGroup(groupName);
    }
}
