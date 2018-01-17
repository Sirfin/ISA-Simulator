package isasim.pipeline;

import isasim.helper.Tuple;
import isasim.main.Processor;
import isasim.physical.RegisterAddress;

import java.util.LinkedList;

public class RegisterWriteBack extends PipelineStage {
    LinkedList<Tuple<RegisterAddress,Integer>> Buffer = new LinkedList<Tuple<RegisterAddress,Integer>>();
    public RegisterWriteBack(Processor p ){
        super(p) ;
    }
    @Override
    public void OnTick() {
        if (Buffer.size() > 0) {
            Tuple<RegisterAddress, Integer> toSave = Buffer.pop();
            if (toSave != null){
                if (toSave.x.m_Address != 0) {
                    p.Registerbank.get(toSave.x.m_Address).save(toSave.y);
                }
            }
        }
    }
    public void SendToBuffer(Tuple<RegisterAddress,Integer> a){
        Buffer.addLast(a);
    }


    @Override
    public String GetStringFormatOfPipelineStage() {
        if (Buffer.size() > 0) {
            return  Buffer.get(0).y.toString() + " -> R" + String.valueOf(Buffer.get(0).x.m_Address) ;
        }
        return "NOOP" ;
    }

    public void flush(){
        this.Buffer.clear();
    }
}
