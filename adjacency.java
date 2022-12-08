package Project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.lang.model.SourceVersion;
import javax.sql.rowset.spi.SyncResolver;
import javax.xml.crypto.Data;
import javax.xml.transform.Source;

import ch9.priorityqueue.Entry;

public class adjacency {
    static HashMap<String, Integer> Dict = new HashMap<>();
    static HashMap<Integer, ArrayList<Integer>> Pairs = new HashMap<>();
    static HashMap<Integer, String> ReverseDict = new HashMap<>();

    static String Path = "/Users/temkaisea/Desktop/code/java/Project/routes.csv";

    public static int rownumber() throws IOException {
        String Line = "";

        BufferedReader br = new BufferedReader(new FileReader(Path));

        int i = 0;
        while ((Line = br.readLine()) != null) {
            if (Line.contains("\\N")) {
                continue;
            }
            i++;
        }

        return i;
    }

    public static Integer[] SourceAirportdata() throws IOException {
        int n = rownumber();
        String Line = "";

        BufferedReader br = new BufferedReader(new FileReader(Path));
        int i = 0;
        Integer[] SourceAirport = new Integer[n];
        while ((Line = br.readLine()) != null) {
            String[] Values = Line.split(",");
            if (Line.contains("\\N")) {
                continue;
            }
            SourceAirport[i] = Integer.parseInt(Values[3]);

            i++;

        }
        return SourceAirport;

    }

    public static Integer[] DestinationAirportdata() throws IOException {
        int n = rownumber();
        String Line = "";
        BufferedReader br = new BufferedReader(new FileReader(Path));
        int i = 0;
        Integer[] DestinationAirport = new Integer[n];
        while ((Line = br.readLine()) != null) {

            String[] Values = Line.split(",");
            if (Line.contains("\\N")) {
                continue;
            }

            DestinationAirport[i] = Integer.parseInt(Values[5]);

            i++;

        }

        return DestinationAirport;

    }

    public static Integer[] reIndexdestination() throws IOException {
        int n = rownumber();

        Integer[] DestinationAirport = DestinationAirportdata();
        for (int i = 0; i < n; i++) {
            DestinationAirport[i] = i;
        }
        return DestinationAirport;
    }

    public static Integer[] reIndexsource() throws IOException {
        int n = rownumber();

        Integer[] SourceAirport = SourceAirportdata();
        for (int i = 0; i < n; i++) {
            SourceAirport[i] = i;
        }
        return SourceAirport;
    }

    public static String[] SourceNaming() throws IOException {
        int n = rownumber();
        String[] SourceName = new String[n];
        for (int i = 0; i < n; i++) {
            SourceName[i] = "";
        }
        String Line = "";
        BufferedReader br = new BufferedReader(new FileReader(Path));

        int i = 0;
        while ((Line = br.readLine()) != null) {
            String[] Values = Line.split(",");

            if (Line.contains("\\N")) {
                continue;
            }
            SourceName[i] = Values[2];

            i++;

        }
        return SourceName;
    }

    public static String[] DestinationNaming() throws IOException {
        int n = rownumber();
        String[] SourceName = new String[n];
        for (int i = 0; i < n; i++) {
            SourceName[i] = "";
        }
        String Line = "";
        BufferedReader br = new BufferedReader(new FileReader(Path));

        int i = 0;
        while ((Line = br.readLine()) != null) {
            String[] Values = Line.split(",");

            if (Line.contains("\\N")) {
                continue;
            }
            SourceName[i] = Values[4];

            i++;

        }
        return SourceName;
    }

    public static HashMap CreateDict() throws IOException {
        // DICT STARTS AT 1
        // add the airports to the dict and award them indexes.

        String Line = "";
        BufferedReader br = new BufferedReader(new FileReader(Path));
        int i = 1;
        while ((Line = br.readLine()) != null) {
            String[] Values = Line.split(",");

            if (Line.contains("\\N")) {
                continue;
            }
            if (Dict.containsKey(Values[2])) {
                continue;
            }
            Dict.put(Values[2], i);
            // Name Then INT

            i++;

        }
        return Dict;

    }

    public static HashMap CreatePairs() throws IOException {
        // create route pairs such that first index is the index of source and the
        // second is destination
        String Line = "";
        BufferedReader br = new BufferedReader(new FileReader(Path));

        while ((Line = br.readLine()) != null) {
            ArrayList<Integer> myArray = new ArrayList<Integer>();

            String[] Values = Line.split(",");
            if (Line.contains("\\N")) {
                continue;
            }

            if (Values[4] == "" || Values[2] == "") {
                continue;
            }
            myArray.add(Dict.get(Values[4]));
            if (Pairs.containsKey(Dict.get(Values[2]))) {
                myArray = Pairs.get(Dict.get(Values[2]));
                myArray.add(Dict.get(Values[4]));
            }
            Pairs.put(Dict.get(Values[2]), myArray);

        }

        return Pairs;

    }

    public static HashMap CreateReverseDict() throws IOException {
        for (Map.Entry<String, Integer> entry : Dict.entrySet()) {

            String key = entry.getKey();
            Integer value = entry.getValue();
            ReverseDict.put(value, key);

        }
        return ReverseDict;

    }

    public static void main(String[] args) throws IOException {
        CreateDict(); // AIRPORT ID = INTEGER
        CreatePairs(); // INTEGER = INTEGER
        CreateReverseDict();

    }

}
