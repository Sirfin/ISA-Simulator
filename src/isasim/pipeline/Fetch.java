package isasim.pipeline;

import isasim.main.Processor;

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

    }
}
