package Algorithm;

import java.util.ArrayList;

public class LZW extends AbstractAlgorithm {
    //to optimize with hashmap at the end
    private ArrayList<String> dictionary;
    private ArrayList<Integer> dataCode;
    private ArrayList<String> dataDecode;
    private String testString = "ababcbababaaaaaaa";

    public LZW(){
        System.out.println("You have chosen LZW!");
        dictionary = new ArrayList<>();
        initiateDictionary();
        dataCode = new ArrayList<>();
        dataDecode = new ArrayList<>();
    }

    private void initiateDictionary(){
        dictionary.clear();
        for(int i = 97; i < 100; i++){
            dictionary.add((Character.toString((char)i)));
        }
    }

    @Override
    //fix type of return... not byte[]?
    public byte[] compress(byte[] data){
        System.out.println("You have chosen to compress with LZW!");
        //change back after test
        String s = Character.toString(testString.charAt(0)); //Character.toString((char)data[1]);
        char c;

        for(int i = 1; i < testString.length(); i++){
            c = testString.charAt(i); //(char)data[i];
            if (dictionary.contains(s+c)){
                s=s+c;
            } else {
                dataCode.add(dictionary.indexOf(s));
                dictionary.add(s+Character.toString(c));
                s = Character.toString(c);
            }
        }
        dataCode.add(dictionary.indexOf(s));

        System.out.println(dataCode.toString());
        System.out.println(dictionary.toString());
        return null;
    }

    @Override
    //fix type of return... not byte[]?
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to decompress with LZW!");
        String s = null;
        int k;
        String seq = null;
        initiateDictionary();

        for(int i = 0; i < dataCode.size(); i++){
            k = dataCode.get(i); //(char)data[i];

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

            System.out.println(dataDecode.toString());
            System.out.println(dictionary.toString());
        }
        return null;
    }
}
