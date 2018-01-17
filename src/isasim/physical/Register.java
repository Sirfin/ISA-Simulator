package isasim.physical;


import isasim.helper.BitSetHelper;

import java.util.BitSet;

/**
 * Klasse um ein virtuelles Register nachzustellen.
 * Für Register siehe: /**
 * Die Klasse um die momentanen Werte der Flags zu speichern.
 * Für mehr Informationen über Flags siehe: @see <a href="Register">https://de.wikipedia.org/wiki/Register_(Computer)</a>
 */
public class Register {
    private Integer memory ;

    private RegisterAddress address ;
    public Register(int size,int address){
        memory = 0 ;
        this.address = new RegisterAddress(address) ;
    }

    public RegisterAddress getAddress() {
        return address;
    }

    /**
     * Speicher einen Wert in diesem Register
     * @param ToSave Wert
     */
    public void save(int ToSave){
        this.memory = ToSave ;
    }

    /**
     * Läd den Wert des Register
     * @return Wert
     */
    public int load(){
        return this.memory;
    }





}
