package isasim.physical;

import isasim.main.Processor;

public class ProgramCounter {
    private Processor processor ;
    public ProgramCounter(Processor processor){
        this.processor = processor ;
        value = 0 ;
    }
    private int value ;
    private boolean overwridden = false ;
    public int getCount(){
        return value ;
    }
    public void setCount(int count){

        value = count ;
        overwridden = true ;
    }
    public void increment(){
        if (overwridden){
            overwridden = false ;
            return ;
        }
        value++ ;
        if (value >= processor.ROM_SIZE_WORDS){
            value = 0;
        }
    }
    public void reset(){
        value = 0 ;
    }
}
