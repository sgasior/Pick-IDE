package pl.edu.kopalniakodu.pickide.util.ahp;

public interface AhpAnalyzer {

    double[][] getAhpMatrix();

    int getMatrixRank();

    double[] getWeights();

    double getLambdaMax();

    double getConsistencyIndex();

    double getConsistencyRatio();

    double getRandomIndex();

    double getMaxConsistencyRatio();

}
