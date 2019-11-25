package IO;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {

    private FileWriter fw;

    public Writer(String filename){

        try { fw = new FileWriter("Saved boards\\" + filename + ".txt"); }
        catch (IOException e) {}
    }

    public void insertLine(String line) {

        try { fw.write(line + "\n"); }
        catch (IOException e) {}

        try { fw.flush(); }
        catch (IOException e) {}
    }

    public void finalize() {

        try { fw.close(); }
        catch (IOException e) {}
    }
}