package Broker;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TopicReader {

    RandomAccessFile topicFile;

    private Topic topic;
    private String groupName;

    TopicReader(Topic topic, String groupName) {
        this.topic = topic;
        this.groupName=groupName;
        //To Do - Generate topicFile
        try{
            topicFile=new RandomAccessFile(this.topic.getTopicFile(),"r");

        }catch (IOException e){

        }
    }

    public int getValue(String consumerName) {
        int value = 0;
        //To Do - Read next value from topicFile and return the value
        //To Do - Handle the transaction constraints
        return value;
    }
}
