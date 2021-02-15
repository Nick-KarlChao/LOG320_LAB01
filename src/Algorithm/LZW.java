package Algorithm;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


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

    private void initiateDictionary(){
        dictionary.clear();
        for(int i = 0; i < DICT_INIT_SIZE; i++){
            dictionary.add((Character.toString((char)i)));
        }
    }

    @Override
    public byte[] compress(byte[] data){
        System.out.println("You have chosen to compress with LZW!");
        initiateDictionary();
        tempString = new String(data, StandardCharsets.UTF_8);
        //System.out.println("string to compress: " + tempString);
        String s = Character.toString(tempString.charAt(0)); //Character.toString((char)data[1]);

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
