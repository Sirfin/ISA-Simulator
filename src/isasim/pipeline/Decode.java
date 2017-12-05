package isasim.pipeline;

import isasim.commands.CommandDecoder;
import isasim.commands.icommands.AddICommand;
import isasim.commands.icommands.LoadCommand;
import isasim.commands.icommands.StoreCommand;
import isasim.commands.jcommands.Condition;
import isasim.commands.jcommands.JCommand;
import isasim.commands.jcommands.JumpCommand;
import isasim.commands.rcommands.AddCommand;
import isasim.commands.Command;
import isasim.commands.rcommands.MoveCommand;
import isasim.main.Processor;

import javax.swing.*;
import java.util.LinkedList;

public class Decode extends PipelineStage {
    LinkedList<Integer> Buffer = new LinkedList<>();
    public Decode(Processor p){
        super(p) ;
    }
    int TestInt = 0 ;
    @Override
    public String GetStringFormatOfPipelineStage() {
        return Buffer.size()>0 ? Integer.toBinaryString(Buffer.get(0)) : "NOOP" ;
    }

    @Override
    public void OnTick() {
        if (Buffer.size() > 0 ) {
            Command c = CommandDecoder.decodeCommand(Buffer.pop(),p) ;
            if (c == null){
                JOptionPane.showMessageDialog(new JFrame(), "Unrecognized Command in Decode", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            p.getLoad().SendToBuffer(c); //TODO*/
        }
    }
    public void SendToBuffer(int a){
        Buffer.addLast(a);
    }
    public void flush(){
        this.Buffer.clear();
    }
}
