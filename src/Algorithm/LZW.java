package Algorithm;

import Algorithm.AbstractAlgorithm;

import java.util.ArrayList;

public class LZW extends AbstractAlgorithm {
    //to optimize with hashmap at the end
    private ArrayList<String> dictionnary;
    private ArrayList<Integer> dataCode;
    private ArrayList<Character> dataDecode;
    private String testString = "ababcbababaaaaaaa";

    public LZW(){
        System.out.println("You have chosen LZW!");
        dictionnary = new ArrayList<>();
        initiateDictionnary();
        dataCode = new ArrayList<>();
        dataDecode = new ArrayList<>();
    }

    private void initiateDictionnary(){
        for(int i = 97; i < 100; i++){
            dictionnary.add((Character.toString((char)i)));
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
            if (dictionnary.contains(s+c)){
                s=s+c;
            } else {
                dataCode.add(dictionnary.indexOf(s));
                dictionnary.add(s+Character.toString(c));
                s = Character.toString(c);
            }
        }
        dataCode.add(dictionnary.indexOf(s));

        System.out.println(dataCode.toString());
        System.out.println(dictionnary.toString());
        return null;
    }

    @Override
    //fix type of return... not byte[]?
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to compress with LZW!");
        String s = null;
        char k;
        char seq;

        for(int i = 0; i < testString.length(); i++){
            k = testString.charAt(i); //(char)data[i];

            seq = (char)dictionnary.indexOf(k);

            // is this how we check if char is NULL?
            if (seq == 0){
                seq = s + s[0];
            }
            dataDecode.add((char)seq);
            if (s != null){
                dictionnary.add(s+seq[0]);
            }

        }
        return null;
    }

}
