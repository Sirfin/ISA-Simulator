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

import java.util.LinkedList;

public class Decode extends PipelineStage {
    LinkedList<Integer> Buffer = new LinkedList<>();
    public Decode(Processor p){
        super(p) ;
    }
    int TestInt = 0 ;
    @Override
    public String GetStringFormatOfPipelineStage() {
        return Buffer.size()>0 ? Integer.toString(Buffer.get(0)) : "NOOP" ;
    }
    private Command decodeCommand(int Command){
        //todo
        TestInt++ ;
        if (TestInt % 2 == 0 ) {
            return new LoadCommand(p.Registerbank.get(0), 15, p.Registerbank.get(10));
        }
        return new JumpCommand(p.Registerbank.get(15),200, Condition.al) ;
    }



    @Override
    public void OnTick() {
        if (Buffer.size() > 0 ) {
            p.getLoad().SendToBuffer(decodeCommand(Buffer.pop()));
            //p.getLoad().SendToBuffer(CommandDecoder.decodeCommand(Buffer.pop(),p)); //TODO
        }
    }
    public void SendToBuffer(int a){
        Buffer.addLast(a);
    }
}
