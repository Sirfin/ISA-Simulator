package isasim.physical;

import isasim.helper.BitSetHelper;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Die Klasse die die Struktur unseres virtuellen Speichers beschreibt.
 * @author Fin Töter
 */
public class Memory {
    ArrayList<Integer> memory  = new ArrayList<>() ;

    /**
     * Initialisiert den Speicher mit einer Größe.
     * @param size Die spezifizierte Größe
     */
    public Memory(int size){
        for (int i = 0 ; i < size ; i++ ){
            memory.add(0) ;
        }
    }

    /**
     * Gibt eine Referenz auf den Speicher zurück.
     * @return Referenz
     */
    public ArrayList<Integer> getMemory () {
        return memory ;
    }

    /**
     * Lädt ein Wert aus dem Speicher
     * @param Address Adresse an dem der Wert geladen wird
     * @return geladener Wert
     */
    public int load(int Address){
        return memory.get(Address) ;
    }

    /**
     * Speichert einen Wert an einer Adresse
     * @param Address Adresse an der gespeichert wird.
     * @param value Wert der gespeichert wird.
     */
    public void store(int Address,int value){
        if (memory.size() > Address) {
            memory.set(Address, value);
        }
    }
}
