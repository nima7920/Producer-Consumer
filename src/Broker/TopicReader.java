package Broker;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TopicReader {

    private volatile RandomAccessFile topicFile;

    private Topic topic;
    private String groupName;

    // my fields
    private volatile String currentConsumer = "";
    private volatile boolean inTransaction = false;
    private Object consumerMonitor = new Object(),eofMonitor=new Object();

    TopicReader(Topic topic, String groupName) {
        this.topic = topic;
        this.groupName = groupName;
        //To Do - Generate topicFile
        try {
            topicFile = new RandomAccessFile(this.topic.getTopicFile(), "r");

        } catch (IOException e) {

        }
    }

    public int getValue(String consumerName) {
        // -3 is a rubbish value
        synchronized (eofMonitor){
            try {
                if(topicFile.getFilePointer()>=topicFile.length()-4) {
                    eofMonitor.wait();
                    return -3;
                }
            } catch (IOException e) {
                return -3;
            } catch (InterruptedException e) {
                return -3;
            }
        }
        synchronized (consumerMonitor) {
            int value = 0;
            try {
                if (inTransaction && !currentConsumer.equals(consumerName))
                    consumerMonitor.wait();
                value = topicFile.readInt();
                System.out.println(topicFile.getFilePointer());
                if (value == 0) {
                    inTransaction = true;
                    currentConsumer = consumerName;
                } else if (value == -1) {
                    inTransaction = false;
                    consumerMonitor.notify();
                }

            } catch (Exception e) {
                return -3;
            }
            return value;
        }
    }
    // my code
    void releaseEOF(){
        synchronized (eofMonitor) {
            eofMonitor.notifyAll();
        }    }
}
