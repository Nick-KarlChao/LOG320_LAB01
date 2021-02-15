package Algorithm;

import Algorithm.AbstractAlgorithm;

public class Optimized extends AbstractAlgorithm {

    public Optimized(){
        System.out.println("You have chosen Optimized!");
    }

    @Override
    public byte[] compress(byte[] data){
        System.out.println("You have chosen to compress!");
        return null;
    }

    @Override
    public byte[] decompress(byte[] data){
        System.out.println("You have chosen to compress!");
        return null;
    }
}
