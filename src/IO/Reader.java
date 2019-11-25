package IO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.Scanner;

public class Reader {

    private FileReader fr;
    private Scanner sc;

    public Reader(String filename){

        try { fr = new FileReader("Saved boards\\" + filename + ".txt"); }
        catch (FileNotFoundException e) { System.out.println("File" + "'" + filename + "'" + "not found!"); }

        sc = new Scanner(fr);
    }

    public String[] getBoardLayout(){

        String line = new String();

        while(sc.hasNext())
            line += sc.next() + " ";

        sc.close();
        return line.split(" ");
    }
}
