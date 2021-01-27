import Algorithm.AbstractAlgorithm;

import java.io.IOException;

public class Main {

    public static void main (String[] args) throws IOException {
        AbstractAlgorithm algorithm = (new AlgorithmFactory(args[0])).getAlgorithm();

    }
}
