package Algorithm;

import java.io.IOException;

public abstract class AbstractAlgorithm {
    public AbstractAlgorithm(){}

    public abstract byte[] compress(byte[] data) throws IOException;
    public abstract byte[] decompress(byte[] data);
}
