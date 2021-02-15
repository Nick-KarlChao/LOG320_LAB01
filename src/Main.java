import Algorithm.AbstractAlgorithm;
import Algorithm.Huffman;
import Algorithm.LZW;
import Algorithm.Optimized;
import FileManager.ReadFile;
import FileManager.WriteFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * LOG320 - LAB 01
 * The purpose of this lab is to conceptualize and implement 2 compression algorithms:
 * LZW and Huffman. The program is to compress and decompress files of all types using
 * terminal commands.
 * Written by Nick-Karl Chao and Ai-Vi Nguyen.
 * Date: 14/02/2021
 */
public class Main {

    public static void main (String[] args) throws IOException {
        AbstractAlgorithm algorithm = (new AlgorithmFactory(args[0])).getAlgorithm();
        String inputFilePath = args[2];
        String outputFilePath = args [3];
        File f = new File(inputFilePath);
        byte[] inputData = new ReadFile().readFileBytes(inputFilePath);
        byte[] outputData = null;

        String chosenAction = args[1];
        switch (chosenAction){
            case "-c":
                outputData = algorithm.compress(inputData);
                break;
            case "-d":
                outputData = algorithm.decompress(inputData);
                break;
        }
        new WriteFile().createFile(outputData, outputFilePath);
    }
}
