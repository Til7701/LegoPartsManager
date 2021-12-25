package de.holube.legopartsmanager.io;

import de.holube.legopartsmanager.log.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LegoFileWriter {

    private LegoFileWriter() {}

    public static void saveLinesToFile(List<String> lines, String filename) throws IOException {
        Log.debug("Saving " + lines.size() + " lines to " + filename);
        StringBuilder builder = new StringBuilder();

        for (String line : lines) {
            builder.append(line).append("\n");
        }

        Path path = Paths.get(filename);
        byte[] strToBytes = builder.toString().getBytes();

        Files.write(path, strToBytes);
        Log.debug("Save done");
    }
}
