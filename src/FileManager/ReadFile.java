package FileManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class takes care of the file readings. The code is based on
 * https://www.tutorialspoint.com/get-file-extension-name-in-java and
 * written by Nick-Karl Chao and Ai-Vi Nguyen.
 */
public class ReadFile {

    public ReadFile() {
    }

    public byte[] readFileBytes (String filePath) throws IOException {
        File newFile = new File(filePath);
        byte[] fileBytes = null;

        if (!newFile.exists()){
            System.out.println("File does not exist. Please choose a new file.");
        } else if (!newFile.isFile()){
            System.out.println("Input error: input is not a file.");
        } else if (!newFile.canRead()){
            System.out.println("File cannot be read.");
        } else {
            fileBytes = Files.readAllBytes(Paths.get(filePath));
        }

        return fileBytes;
    }
}
