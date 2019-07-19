package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.Survey;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private SurveyService surveyService;

    public AnswerServiceImpl(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Override
    public List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias) {

        List<Comparison<Criteria>> result = new ArrayList<>();

        for (int i = 0; i < criterias.size() - 1; i++) {
            int nextIndex = i + 1;
            while (nextIndex < criterias.size()) {
                Comparison<Criteria> criteriaComparison = new Comparison<>(criterias.get(i), criterias.get(nextIndex));
                result.add(criteriaComparison);
                nextIndex = nextIndex + 1;
            }
        }
        return result;
    }


    @Override
    public Map<Criteria, Double> findWeightsOfAllCriteria(List<Comparison<Criteria>> comparisonList, String[] criteriaRating) {

        double[][] ahpMatrix = generateAHPMatrix(comparisonList, criteriaRating);

        printMatrix(ahpMatrix);

        return null;
    }

    private double[][] generateAHPMatrix(List<Comparison<Criteria>> comparisonList, String[] criteriaRating) {

        for (int i = 0; i < criteriaRating.length; i++) {
            comparisonList.get(i).setValue(Integer.parseInt(criteriaRating[i]));
        }

//        for (Comparison<Criteria> comparison : comparisonList) {
//            System.out.println(comparison.getChoice1() + " - " + comparison.getChoice2() + " value: " + comparison.getValue());
//        }

        int ahpMatrixSize = comparisonList.get(0).getChoice1().getSurvey().getCriterias().size();
        double[][] ahpMatrix = new double[ahpMatrixSize][ahpMatrixSize];

        int criteriaCounter = 0;
        for (int i = 0; i < ahpMatrix.length; i++) {
            for (int j = 0; j < ahpMatrix[i].length; j++) {
                if (i == j) {
                    ahpMatrix[i][j] = 1;
                }
            }

            for (int k = i + 1; k < ahpMatrixSize; k++) {
                ahpMatrix[i][k] = convertToSaatyScale(comparisonList.get(criteriaCounter).getValue());
                criteriaCounter++;
            }
        }

        for (int i = 0; i < ahpMatrix.length; i++) {
            for (int j = 0; j < ahpMatrix[i].length; j++) {
                if (ahpMatrix[i][j] == 0) {
                    ahpMatrix[i][j] = 1 / ahpMatrix[j][i];
                }
            }

        }
        return ahpMatrix;
    }

    private void printMatrix(double[][] ahpMatrix) {
        for (int i = 0; i < ahpMatrix.length; i++) {
            for (int j = 0; j < ahpMatrix[i].length; j++) {
                System.out.print(" " + ahpMatrix[i][j]);
            }
            System.out.println();
        }
    }


    private static double convertToSaatyScale(int value) {

        if (value == 1) {
            return 9;
        } else if (value == 2) {
            return 8;
        } else if (value == 3) {
            return 7;
        } else if (value == 4) {
            return 6;
        } else if (value == 5) {
            return 5;
        } else if (value == 6) {
            return 4;
        } else if (value == 7) {
            return 3;
        } else if (value == 8) {
            return 2;
        } else if (value == 9) {
            return 1;
        } else if (value == 10) {
            return 1 / 2d;
        } else if (value == 11) {
            return 1 / 3d;
        } else if (value == 12) {
            return 1 / 4d;
        } else if (value == 13) {
            return 1 / 5d;
        } else if (value == 14) {
            return 1 / 6d;
        } else if (value == 15) {
            return 1 / 7d;
        } else if (value == 16) {
            return 1 / 8d;
        } else {
            return 1 / 9d;
        }

    }

    @Override
    public void save(Survey survey) {
        log.info(("method save() not impl"));
    }


}