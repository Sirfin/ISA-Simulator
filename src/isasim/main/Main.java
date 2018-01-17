package isasim.main;

import isasim.commands.CommandDecoder;
import isasim.gui.MainWindow;

import javax.swing.*;
public class Main {
    public MainWindow m ;
    public Main(){


        m = new MainWindow() ;
    }
    public static void main(String[] args) {

        if (args[0] == null) {
            new Main();
        }
        else{
            new Processor(Integer.parseInt(args[1]),args[0]) ;
        }
    }
}
