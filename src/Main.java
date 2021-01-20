import Algorithm.AbstractAlgorithm;

public class Main {

    public static void main (String[] args){
        AbstractAlgorithm algorithm = (new AlgorithmFactory(args[0])).getAlgorithm();


    }
}
