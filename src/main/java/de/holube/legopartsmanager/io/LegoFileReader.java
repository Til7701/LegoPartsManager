package de.holube.legopartsmanager.io;

import de.holube.legopartsmanager.log.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LegoFileReader {

    private LegoFileReader() {

    }

    public static List<String> readFile(String filename) {
        Log.debug("Loading: " + filename);
        List<String> lines = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(Objects.requireNonNull(LegoFileReader.class.getResource(filename)).getFile());
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
