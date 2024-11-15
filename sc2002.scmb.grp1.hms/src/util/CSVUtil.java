package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
	public static void removeEmptyRows(String filePath) throws IOException {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();

        // Read the CSV file and filter out empty lines
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line is not empty or contains only whitespace
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }

        // Rewrite the file with the filtered lines
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

}
