package isasim.helper;

import java.util.BitSet;
import java.util.stream.IntStream;

public class BitSetHelper{
public static int BitSetToInt(BitSet b){
    int bitInteger = 0;
    for(int i = 0 ; i < 32; i++)
        if(b.get(i))
            bitInteger |= (1 << i);
    return bitInteger;
}
public static String BitSetToString(BitSet b){
    String a = ""  ;
    for (int i = b.size() ; i >= 0 ; i--){
        a+=b.get(i)?1:0 ;
    }
    return a ;
}
}
