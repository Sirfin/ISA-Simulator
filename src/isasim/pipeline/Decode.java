package isasim.pipeline;

import isasim.commands.AddCommand;
import isasim.commands.Command;
import isasim.commands.SubCommand;
import isasim.main.Processor;
import sun.awt.image.ImageWatched;

import java.util.BitSet;
import java.util.LinkedList;

public class Decode extends PipelineStage {
    LinkedList<BitSet> Buffer = new LinkedList<>();
    public Decode(Processor p){
        super(p) ;
    }
    int TestInt = 0 ;
    @Override
    public String GetStringFormatOfPipelineStage() {
        return null;
    }
    public Command decodeCommand(BitSet Command){
        TestInt++ ;
        if (TestInt % 2 == 0 ) {
            return new AddCommand(p.Registerbank.get(0), p.Registerbank.get(1), p.Registerbank.get(2));
        }
        return new SubCommand(p.Registerbank.get(0), p.Registerbank.get(1), p.Registerbank.get(2));
    }
    @Override
    public void OnTick() {
         p.getLoad().SendToBuffer(decodeCommand(new BitSet()));
         System.out.println("Send Command to Load");
    }
    public void SendToBuffer(BitSet a){
        Buffer.addLast(a);
    }
}
