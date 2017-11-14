package isasim.pipeline;

import isasim.main.Processor;

import java.util.BitSet;

public class Fetch extends PipelineStage {
    public Fetch(Processor p){
        super(p) ;
    }

    @Override
    public String GetStringFormatOfPipelineStage() {
        return null;
    }

    @Override
    public void OnTick() {
    //p.getDecode().SendToBuffer(p.rom.load(p.PC.getCount()));

    }
}
