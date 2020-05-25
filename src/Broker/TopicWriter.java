package Broker;

import Logs.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public class TopicWriter {
    RandomAccessFile buffer;

    private Topic topic;
    private volatile HashMap<String, Transaction> transactions;

    TopicWriter(Topic topic) {
        this.topic=topic;
        transactions = new HashMap<>();

        try {
            buffer=new RandomAccessFile(this.topic.getTopicFile(),"rwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void putValue(String producerName, int value) {
        if(value <= 0) {
            handleTransactionOperation(producerName, value);
        }
        else {
            handleInsertOperation(producerName, value);
        }
    }

    private void handleTransactionOperation(String producerName, int value) {
        switch (value) {
            case 0:
                startTransaction(producerName);
                break;
            case -1:
                commitTransaction(producerName);
                break;
            case -2:
                cancelTransaction(producerName);
        }
    }

    private void handleInsertOperation(String producerName, int value) {
        if(transactions.containsKey(producerName)) {
            transactions.get(producerName).putValue(value);
        }
        else {
            synchronized (this) {
                writeValue(value);
            }
        }
    }

    private void addTransaction(String producerName) {
        transactions.put(producerName, new Transaction(this, producerName));
    }

    /**
     * This method is used to start a transaction for putting a transaction of values inside the buffer.
     * @return Nothing.
     */
    private void startTransaction(String producerName) {
        if(transactions.containsKey(producerName)) {
            Logger.getInstance().writeLog("error",
                    "producer "+producerName+
                            " is trying to start a transaction before finalizing the previous one");
            //To Do - Log the problem in finalizing previous transaction.
            commitTransaction(producerName);
        }
        addTransaction(producerName);
    }

    /**
     * This method is used to end the transaction for putting a its values inside the file.
     * @return Nothing.
     */
    private void commitTransaction(String producerName) {
        if(transactions.containsKey(producerName)) {
            synchronized (this) {
                transactions.get(producerName).commit();
                transactions.remove(producerName);
            }
        }
        else {
            //To Do - Log the problem in committing a non-existing transaction.
            Logger.getInstance().writeLog("error",
                    "producer "+producerName+ " is trying to commit a non-existing transaction");
        }
    }

    /**
     * This method is used to cancel a transaction.
     * @return Nothing.
     */
    private void cancelTransaction(String producerName) {
        if(transactions.containsKey(producerName)) {
            transactions.remove(producerName);
        }
        else {
            //To Do - Log the problem in canceling a non-existing transaction.
            Logger.getInstance().writeLog("error",
                    "producer "+producerName+ " is trying to cancel a non-existing transaction");
        }
    }

    public synchronized void writeValue(int value) {
        //To Do - Put the given value at the end of the topicFile
        try {
            buffer.writeInt(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
