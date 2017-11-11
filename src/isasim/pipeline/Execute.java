package isasim.pipeline;

import isasim.commands.Command;
import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.RegisterAddress;

import java.util.LinkedList;

public class Execute extends PipelineStage {
    LinkedList<Command> Buffer = new LinkedList<Command>() ;
    public Execute(Processor p ){
        super(p) ;
    }
    @Override
    public void OnTick() {
        Command c = Buffer.pop() ;
        if (c != null) c.execute(p);
    }
    public void SendToBuffer(Command a){
        Buffer.addLast(a);
    }
}
