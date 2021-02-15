package Algorithm;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class containing LZW algorithm. Class extends from AbstractAlgorithm.
 * Written by Nick-Karl Chao and Ai-Vi Nguyen.
 */
public class LZW extends AbstractAlgorithm {
    private static final int DICT_INIT_SIZE = 256;
    //to optimize with hashmap at the end
    private ArrayList<String> dictionary;
    private ArrayList<Integer> dataCode;
    private ArrayList<String> dataDecode;
    private String tempString = "ababcbababaaaaaaa";

    public LZW(){
        System.out.println("You have chosen LZW!");
        dictionary = new ArrayList<>();
        dataCode = new ArrayList<>();
        dataDecode = new ArrayList<>();
    }

    /**
     * Initiate dictionnary with the intial size containing
     * all bytes characters existing in ASCII alphabet.
     */
    private void initiateDictionary(){
        dictionary.clear();
        for(int i = 0; i < DICT_INIT_SIZE; i++){
            dictionary.add((Character.toString((char)i)));
        }
    }

    /**
     * This function iterates through the dictionnary created to compress the
     * data by creating entries for repeatings chains of bytes; therefore, it
     * will compress the data by coding it with a new table of equivalences.
     * @param data the byte array extracted from the file.
     * @return dataCode, an array of byte transformed from the original data
     */
    @Override
    public byte[] compress(byte[] data){
        System.out.println("You have chosen to compress with LZW!");
        initiateDictionary();
        tempString = new String(data, StandardCharsets.UTF_8);
        //System.out.println("string to compress: " + tempString);
        String s = Character.toString(tempString.charAt(0));

        for(int i = 1; i < tempString.length(); i++){
            char c = tempString.charAt(i); //(char)data[i];
            if (dictionary.contains(s+c)){
                s=s+c;
            } else {
                dataCode.add(dictionary.indexOf(s));
                //System.out.println(dictionary.indexOf(s));
                dictionary.add(s+c);
                s = Character.toString(c);
            }
        }
        dataCode.add(dictionary.indexOf(s));
        System.out.println(dataCode);

        System.out.println("NEED TO TEST:" + dictionary.indexOf(s));

        return dataCode.toString().
                replaceAll("]", "").
                replaceAll("\\[", "").
                replaceAll(",", "").
                getBytes(StandardCharsets.UTF_8);
    }

    /**
     * This function iterates through the data and repopulates the dictionnary
     * based new bytes encountered. As this happens, the original message is
     * decoded by comapring the coded byte to the values in the dictionnary.
     * @param data the byte array extracted from the file.
     * @return dataDecode, an array of byte decoded from the input data
     */
    @Override
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to decompress with LZW!");
        initiateDictionary();
        String s = null;
        String seq = null;
        tempString = new String(data, StandardCharsets.UTF_8);

        for(int i = 0; i < tempString.length(); i++){
            char k = tempString.charAt(i);
            //System.out.println("what's about to get added:" + k);

            if (k < dictionary.size()){
                seq = dictionary.get(k);
            } else {
                seq = null;
            }

            if (seq == null){
                seq = s + s.charAt(0);
            }

            dataDecode.add(seq);

            if (s != null){
                dictionary.add(s+seq.charAt(0));
            }

            s = seq;

            //System.out.println(dataDecode.toString());
        }
        return dataDecode.toString().getBytes(StandardCharsets.UTF_8);
    }
}
