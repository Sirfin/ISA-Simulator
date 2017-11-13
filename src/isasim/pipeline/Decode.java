package isasim.pipeline;

import isasim.main.Processor;
import sun.awt.image.ImageWatched;

import java.util.BitSet;
import java.util.LinkedList;

public class Decode extends PipelineStage {
    LinkedList<Integer> Buffer = new LinkedList<>();
    public Decode(Processor p){
        super(p) ;
    }

    @Override
    public String GetStringFormatOfPipelineStage() {
        return null;
    }

    @Override
    public void OnTick() {
    }
    public void SendToBuffer(Integer a){
        Buffer.addLast(a);
    }
}
