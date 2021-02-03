package Algorithm;

import Algorithm.AbstractAlgorithm;

import java.sql.SQLOutput;

public class Huffman extends AbstractAlgorithm {

    public Huffman(){
        System.out.println("You have chosen Huffman!");
    }
    @Override
    public byte[] compress(byte[] data){
        System.out.println("You have chosen to compress with Huffman!");
        //TODO
        return null;
    }

    @Override
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to compress with Huffman!");
        //TODO
        return null;
    }

    private void createFrequencyTable(){
        //TODO
    };

    private void createHuffmanTable(){
        //TODO
    };

    private void encode(){
        //TODO
    };
}
