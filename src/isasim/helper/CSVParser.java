package isasim.helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ftoet on 26.11.2017.
 */
public final class CSVParser {

    public static List<String[]> ParseCSV(String PathToFile,String delimiter){
        String csvFile = PathToFile;
        String line = "";
        String cvsSplitBy = delimiter;
        List<String[]> returnValue = new LinkedList<String[]>() ;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] csvValues = line.split(cvsSplitBy);
                returnValue.add(csvValues) ;

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue ;
    }

}
