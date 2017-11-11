package isasim.pipeline;

import isasim.main.Processor;

import java.nio.channels.Pipe;

public abstract class PipelineStage {
    Processor p ;
    public abstract void OnTick() ;
    public PipelineStage(Processor p ){
        this.p = p ;
    }
}
