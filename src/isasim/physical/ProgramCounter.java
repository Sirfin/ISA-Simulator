package isasim.physical;

import isasim.main.Processor;

/**
 * Der Programm Zähler des Prozessors, welcher angibt , welcher Befehl als nächstes aus dem ROM geladen
 * wird.
 * @author Fin Töter
 */
public class ProgramCounter {
    private Processor processor ;

    /**
     * Initialisiert den PC mit dem zugehörigen Prozessor
     * @see Processor
     * @param processor zu intialisierender Prozessor
     */
    public ProgramCounter(Processor processor){
        this.processor = processor ;
        value = 0 ;
    }
    private int value ;
    private boolean overwridden = false ;

    /**
     * Gibt den momentan Wert zurück
     * @return Counter
     */
    public int getCount(){
        return value ;
    }

    /**
     * Überschreibt den Counter beim nächsten increment wird der Counter nicht hochgesetzt
     * @param count Neuer Wert
     */
    public void setCount(int count){

        value = count ;
        overwridden = true ;
    }

    /**
     * Inkrementiert den Zähler um 1 außer er wurde nach dem letzten call überschrieben.
     */
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

    /**
     * Resettet den Zähler
     */
    public void reset(){
        value = 0 ;
    }
}
