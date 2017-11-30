package isasim.physical;

import isasim.main.Processor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ftoet on 30.11.2017.
 */
public class RomLoader {
    public static void FileInRom(String PathToFile, Processor main, int offset){
        Path path = Paths.get(PathToFile);
        byte[] data ;

        try {
            data = Files.readAllBytes(path) ;

            for (int i = 0 ; i < data.length;i+=4){
                //System.out.println(i) ;
                //System.out.println("Länge = " + data.length);
                byte[] Int = new byte[4] ;
                Int[0] = data[i] ;
                Int[1] = data[i+1] ;
                Int[2] = data[i+2] ;
                Int[3] = data[i+3] ;
                int a = byteArrayToLeInt(Int) ;
                main.rom.store(offset+(i/4),a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
    public static int byteArrayToLeInt(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getInt();
    }
}
