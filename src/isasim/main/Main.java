package isasim.main;

import isasim.commands.CommandDecoder;
import isasim.gui.MainWindow;
import javax.swing.*;
import org.apache.commons.cli.* ;
public class Main {
    public MainWindow m ;
    public Main(){


        m = new MainWindow() ;
    }
    public static void main(String[] args) {
        Options option = new Options() ;
        Option input = new Option("i","input",true,"input file path") ;
        input.setRequired(false);
        option.addOption(input) ;

        Option output = new Option("o","output",true,"text dump path") ;
        output.setRequired(false);
        option.addOption(output) ;

        Option ticks = new Option("n","ticks",true,"Times the processor ticks") ;
        ticks.setRequired(false);
        option.addOption(ticks) ;

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(option, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", option);

            System.exit(1);
            return;
        }

        String inputPath = (cmd.getOptionValue("input")==null?"output":cmd.getOptionValue("input"))  ;
        String outputPath=  (cmd.getOptionValue("output")==null?"Dump.txt":cmd.getOptionValue("output")) ;
        int ticksValue =  (cmd.getOptionValue("ticks")==null?0:Integer.parseInt(cmd.getOptionValue("ticks")))  ;

        if (args[0] == null) {
            new Main();
        }
        else{
            new Processor(ticksValue,inputPath,outputPath) ;
        }
    }
}
