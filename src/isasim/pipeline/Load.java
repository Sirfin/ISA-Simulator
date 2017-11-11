package isasim.pipeline;

import isasim.commands.Command;
import isasim.commands.RCommand;
import isasim.main.Processor;

import java.util.LinkedList;

public class Load extends PipelineStage {
    LinkedList<Command> Buffer = new LinkedList<Command>() ;
    public Load(Processor p){
        super(p) ;
    }
    @Override
    public void OnTick() {
    Command c = Buffer.pop() ;
    if (c instanceof RCommand){
        ((RCommand) c).setValue1(((RCommand) c).getQuelle1().load());
        ((RCommand) c).setValue2(((RCommand) c).getQuelle2().load());
    }

    p.getExec().SendToBuffer(c);


    }
    public void SendToBuffer(Command c){
        Buffer.addLast(c);
    }
}
