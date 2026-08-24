import java.io.File;       // Import the File class
import java.io.IOException; // Import IOException to handle errors

public class formula {

    //P = (C+H) * A
    //C, H, A must be random from 1 to 100
    private int c;
    private int h;
    private int a;

    private int M;
    private int T;

    public formula (int M, int T){
        this.T = T;
        this.M = M;
    }

    //must create te formula m times, and sleep t times
    //m is random from 1 to M (maximum times)
    //t is random from 10 to T (maximum sleep)

    public int calculateP(){
        return ((this.c + this.h) * this.);
    }


    public void createFile{
        File myObj = new File("filename.txt"); // Create File object
    }
}



public class CreateFile {
    public static void main(String[] args) {
        try {
            File myObj = new File("filename.txt"); // Create File object
            if (myObj.createNewFile()) {           // Try to create the file
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace(); // Print error details
        }
    }
}