import Algorithm.AbstractAlgorithm;
import Algorithm.Huffman;
import Algorithm.LZW;
import Algorithm.Optimized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main (String[] args) throws IOException {
        AbstractAlgorithm algorithm = (new AlgorithmFactory(args[0])).getAlgorithm();
        //readAllBytes will create a temp table that contains the same shit as the original file to comp/decomp
        byte[] inputData = Files.readAllBytes(Paths.get("src/exemple.txt"));

        String chosenAction = args[1];
        switch (chosenAction){
            case "-c":
                algorithm.compress(inputData);
                break;
            case "-d":
                algorithm.decompress(inputData);
                break;
        }

//        System.out.println(Arrays.toString(data));
//        System.out.println(data.length);
//        System.out.println(data);
//        System.out.println(Integer.toHexString(data[1]));

        /** THIS IS FOR TEST PURPOSES **/
        AbstractAlgorithm algo1 = new LZW();
        algo1.compress(inputData);
        algo1.decompress(inputData);

        AbstractAlgorithm algo2 = new Huffman();
        algo2.compress(inputData);
        algo2.decompress(inputData);

        AbstractAlgorithm algo3 = new Optimized();
        algo3.compress(inputData);
        algo3.decompress(inputData);

        /*switch(args[1]){
            case "-c":
                //compress will create temp table with compressed data and then we use that table to create a new file
                byte[] compressedData = algorithm.compress(data);

                // These lines are to check size of data vs compressed data TEST PURPOSES
                System.out.println("Original data: "+ data.length);
                System.out.println("Original data: "+ compressedData.length);
                break;
            case "-d":
                //decompress will create temp table with decompressed data and then we use that table to create a new file
                byte[] decompressedData = algorithm.decompress(data);

                // These lines are to check size of data vs decompressed data TEST PURPOSES
                System.out.println("Original data: "+ data.length);
                System.out.println("Original data: "+ decompressedData.length);
                break;
            default:
                System.out.println("that wasn't D or C you fuck");
        }
*/
    }
}
