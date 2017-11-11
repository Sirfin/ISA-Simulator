package isasim.pipeline;

import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.RegisterAddress;

import java.util.LinkedList;

public class MemoryWriteBack extends PipelineStage {
    LinkedList<Tuple<RegisterAddress,Integer>> Buffer = new LinkedList<Tuple<RegisterAddress,Integer>>();
    public MemoryWriteBack(Processor p ){
        super(p) ;
    }
    @Override
    public void OnTick() {
        Tuple<RegisterAddress,Integer> toSave = Buffer.pop() ;
        if (toSave != null)  p.Registerbank.get(toSave.x.m_Address).save(toSave.y);
    }
    public void SendToBuffer(Tuple<RegisterAddress,Integer> a){
        Buffer.addLast(a);
    }
}
