package isasim.pipeline;

import isasim.commands.CommandDecoder;
import isasim.commands.icommands.AddICommand;
import isasim.commands.icommands.ICommand;
import isasim.commands.icommands.LoadCommand;
import isasim.commands.icommands.StoreCommand;
import isasim.commands.jcommands.Condition;
import isasim.commands.jcommands.JCommand;
import isasim.commands.jcommands.JumpCommand;
import isasim.commands.rcommands.AddCommand;
import isasim.commands.Command;
import isasim.commands.rcommands.MoveCommand;
import isasim.commands.rcommands.RCommand;
import isasim.main.Processor;

import javax.swing.*;
import java.util.LinkedList;

public class DecodeLoad extends PipelineStage {
    LinkedList<Integer> Buffer = new LinkedList<>();
    public DecodeLoad(Processor p){
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
                JOptionPane.showMessageDialog(new JFrame(), "Unrecognized Command in DecodeLoad", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (c instanceof RCommand) {
                ((RCommand) c).setValue1(((RCommand) c).getQuelle1().load());
                ((RCommand) c).setValue2(((RCommand) c).getQuelle2().load());
            }
            if (c instanceof ICommand){
                ((ICommand) c).setValue1(((ICommand) c).getQuelle1().load());
            }
            if(c instanceof JCommand){
                ((JCommand) c).setBasis_loaded(((JCommand) c).getBasis().load());
            }
            p.getExec().SendToBuffer(c);
        }
    }
    public void SendToBuffer(int a){
        Buffer.addLast(a);
    }
    public void flush(){
        this.Buffer.clear();
    }
}
