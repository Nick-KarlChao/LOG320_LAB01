package FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class CreateFile {
    private String data;
    private String fileName = "COMPRESSED";
    private String fileType = ".txt";
    private Path filePath;

    public CreateFile(String data){
        this.data = data;
    }

    private void createFile(String data) throws IOException {
        String fileID = fileName + fileType;
        try{
            File newFile = new File(fileID);
            if(newFile.exists()){
                System.out.println("File already exists.");
            } else {
                FileOutputStream fos = new FileOutputStream(newFile);
                fos.write(convertStringToByte(data));
                System.out.println("New file created.");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private byte[] convertStringToByte(String data){
        byte[] stringInByte = data.getBytes(StandardCharsets.UTF_8);
        return stringInByte;
    }
}

// https://www.w3schools.com/java/java_files_create.asp
