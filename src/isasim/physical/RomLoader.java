package isasim.physical;

import isasim.main.Processor;

import javax.swing.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Klasse für das Laden einer Binärdatei in den Rom eines spezifierten Prozessors
 * @see Processor
 * @author Fin Töter
 */
public class RomLoader {
    /**
     * Funktion für das Laden ins Rom
     * @param PathToFile Pfad zur kompilierten Assembler Datei
     * @param main Der Processor in dessen Rom es geladen werden soll
     * @param offset Der Offset mit dem es ins Ram geladen werden soll
     */
    public static void FileInRom(String PathToFile, Processor main, int offset){
        Path path = Paths.get(PathToFile);
        byte[] data ;

        try {
            data = Files.readAllBytes(path) ;
            if (data.length % 36 != 0){
                JOptionPane.showMessageDialog(new JFrame(),
                        "Die Datei hat nicht das benötigte Format",
                        "File Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            for (int i = 0 ; i < data.length;i+=4){
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

    /**
     * Konvertiert ein ByteArray zu einem Int
     * @param b Byte Array
     * @return int
     */
    public static int byteArrayToLeInt(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getInt();
    }
}
