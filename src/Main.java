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

//        System.out.println(Arrays.toString(data));
//        System.out.println(data.length);
//        System.out.println(data);
//        System.out.println(Integer.toHexString(data[1]));

        /** THIS IS FOR TEST PURPOSES **/
        //AbstractAlgorithm algo1 = new LZW();
        //algo1.compress(inputData);
        //algo1.decompress(inputData);

        //AbstractAlgorithm algo2 = new Huffman();
        //algo2.compress(inputData);
        //algo2.decompress(inputData);

//        AbstractAlgorithm algo3 = new Optimized();
//        algo3.compress(inputData);
//        algo3.decompress(inputData);

    }
}
