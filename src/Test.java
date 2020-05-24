
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Test {

    RandomAccessFile raf;

    public Test() {
        try {
//            raf = new RandomAccessFile("E:\\___Educational\\University\\ap\\home works\\hw 4\\Messaging-master\\data.dat", "r");
            raf = new RandomAccessFile("data.dat", "r");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        while (true) {
            try {
                System.out.println(raf.readInt());
            } catch (IOException e) {

                break;
            }
        }
        try {
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Test();
    }
}
