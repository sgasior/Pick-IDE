package pl.edu.kopalniakodu.pickide.util.ahp;

public class AhpAnalyzerImpl implements AhpAnalyzer {

    private double[][] ahpMatrix;

    private double[] weights;

    private double lambdaMax;

    private int matrixRank;

    private double consistencyIndex;

    private double consistencyRatio;

    private double maxConsistencyRatio = 0.10;

    private double randomIndex;

    public AhpAnalyzerImpl(double[][] ahpMatrix) {
        this.ahpMatrix = ahpMatrix;
        setMatrixRank();
        setWeights();
        setLambdaMax();
        setConsistencyIndex();
        setRandomIndex();
        setConsistencyRatio();
    }

    /*
     ************* SETTERS ****************
     */


    private void setMatrixRank() {
        this.matrixRank = ahpMatrix.length;
    }

    private void setWeights() {

        double[] geometricMean = geometricMean(ahpMatrix);
        double sumValuesFromGeometricMean = sumValues(geometricMean);

        weights = new double[geometricMean.length];
        for (int i = 0; i < weights.length; i++) {
            double weight = geometricMean[i] / sumValuesFromGeometricMean;
            weight = (double) Math.round(weight * 1000d) / 1000d;
            weights[i] = weight;
        }
    }

    private void setLambdaMax() {
        double[] columnSum = getSumOfEachColumn(this.ahpMatrix);
        this.lambdaMax = matrixMult(weights, columnSum);
    }

    private void setConsistencyIndex() {
        double consistencyIndex = (lambdaMax - matrixRank) / (double) (matrixRank - 1);
        this.consistencyIndex = (double) Math.round(consistencyIndex * 1000d) / 1000d;
    }

    private void setConsistencyRatio() {
        double consistencyRatio = this.consistencyIndex / this.randomIndex;
        this.consistencyRatio = (double) Math.round(consistencyRatio * 1000d) / 1000d;
    }

    private void setRandomIndex() {
        if (matrixRank == 1) {
            this.randomIndex = 0.0;
        } else if (matrixRank == 2) {
            this.randomIndex = 0.0;
        } else if (matrixRank == 3) {
            this.randomIndex = 0.58;
        } else if (matrixRank == 4) {
            this.randomIndex = 0.90;
        } else if (matrixRank == 5) {
            this.randomIndex = 1.12;
        } else if (matrixRank == 6) {
            this.randomIndex = 1.24;
        } else if (matrixRank == 7) {
            this.randomIndex = 1.32;
        } else if (matrixRank == 8) {
            this.randomIndex = 1.41;
        } else if (matrixRank == 9) {
            this.randomIndex = 1.45;
        } else if (matrixRank == 10) {
            this.randomIndex = 1.49;
        }
    }

    /*
     ************* GETTERS ****************
     */

    public double[][] getAhpMatrix() {
        return ahpMatrix;
    }

    public int getMatrixRank() {
        return matrixRank;
    }

    public double[] getWeights() {
        return weights;
    }

    public double getLambdaMax() {
        return lambdaMax;
    }

    public double getConsistencyIndex() {
        return consistencyIndex;
    }

    public double getConsistencyRatio() {
        return consistencyRatio;
    }

    public double getRandomIndex() {
        return randomIndex;
    }

    public double getMaxConsistencyRatio() {
        return maxConsistencyRatio;
    }

    /*
     *************HELPERS METHODS****************
     */


    private double matrixMult(double[] weights, double[] columnSum) {
        double sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (weights[i] * columnSum[i]);
        }
        sum = (double) Math.round(sum * 100d) / 100d;
        return sum;
    }

    private double[] getSumOfEachColumn(double[][] ahpMatrix) {
        double[] result = new double[ahpMatrix.length];
        double sum = 0;

        for (int i = 0; i < ahpMatrix.length; ++i) {
            for (int j = 0; j < ahpMatrix.length; ++j) {
                sum = sum + ahpMatrix[j][i];
            }
            result[i] = sum;
            sum = 0;
        }
        return result;
    }

    private double sumValues(double[] valueTab) {
        double sum = 0;
        for (double value : valueTab) {
            sum += value;
        }

        return sum;
    }

    private double[] geometricMean(double[][] ahpMatrix) {
        double[] result = new double[ahpMatrix.length];

        for (int i = 0; i < ahpMatrix.length; i++) {
            double resultValue = 1;
            for (int j = 0; j < ahpMatrix[i].length; j++) {
                resultValue *= ahpMatrix[i][j];
            }
            result[i] = Math.pow(resultValue, 1d / (ahpMatrix.length));
        }

        return result;
    }

}
