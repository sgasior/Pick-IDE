package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.*;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.repository.AnswerAlternativeRepository;
import pl.edu.kopalniakodu.pickide.repository.AnswerCriteriaRepository;
import pl.edu.kopalniakodu.pickide.repository.AnswerRepository;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.SurveyService;
import pl.edu.kopalniakodu.pickide.util.ahp.AhpAnalyzer;
import pl.edu.kopalniakodu.pickide.util.ahp.AhpAnalyzerImpl;

import java.util.*;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Logger log = LoggerFactory.getLogger(AnswerServiceImpl.class);

    private AnswerCriteriaRepository answerCriteriaRepository;
    private AnswerAlternativeRepository answerAlternativeRepository;
    private SurveyService surveyService;
    private AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerCriteriaRepository answerCriteriaRepository, AnswerAlternativeRepository answerAlternativeRepository, SurveyService surveyService, AnswerRepository answerRepository) {
        this.answerCriteriaRepository = answerCriteriaRepository;
        this.answerAlternativeRepository = answerAlternativeRepository;
        this.surveyService = surveyService;
        this.answerRepository = answerRepository;
    }

    @Override
    public Optional<Answer> findAnswerById(Long answer_id) {
        return answerRepository.findById(answer_id);
    }


    @Override
    public List<Comparison<Criteria>> findAllCriteriaComparison(List<Criteria> criterias) {

        Comparison<Criteria> criteriaComparison = new Comparison<>();
        return criteriaComparison.findAllComparison(criterias);

    }

    @Override
    public List<Comparison<Alternative>> findAllAlternativeComparison(List<Alternative> alternatives) {
        Comparison<Alternative> criteriaComparison = new Comparison<>();
        return criteriaComparison.findAllComparison(alternatives);
    }


    @Override
    public Map<Criteria, Map<Double, Double>> findWeightsOfAllCriteria(List<Comparison<Criteria>> comparisonList, String[] criteriaRating, List<Criteria> criteriaList) {

        Map<Criteria, Map<Double, Double>> result = new LinkedHashMap<>();
        double[][] ahpMatrix = generateAHPMatrix(comparisonList, criteriaRating, criteriaList);
        AhpAnalyzer ahpAnalyzer = new AhpAnalyzerImpl(ahpMatrix);

        double[] weight = ahpAnalyzer.getWeights();
        for (int i = 0; i < criteriaList.size(); i++) {

            Map<Double, Double> mapWeightAndCR = new LinkedHashMap<>();
            mapWeightAndCR.put(weight[i], ahpAnalyzer.getConsistencyRatio());
            result.put(criteriaList.get(i), mapWeightAndCR);
        }

        return result;
    }

    @Override
    public Map<Alternative, Map<Double, Double>> findWeightsOfAllAlternative(List<Comparison<Alternative>> alternativeComparisonList, String[] alternativeRating, List<Alternative> alternatives) {

        Map<Alternative, Map<Double, Double>> result = new LinkedHashMap<>();
        double[][] ahpMatrix = generateAHPMatrix(alternativeComparisonList, alternativeRating, alternatives);
        AhpAnalyzer ahpAnalyzer = new AhpAnalyzerImpl(ahpMatrix);

        double[] weight = ahpAnalyzer.getWeights();
        for (int i = 0; i < alternatives.size(); i++) {

            Map<Double, Double> mapWeightAndCR = new LinkedHashMap<>();
            mapWeightAndCR.put(weight[i], ahpAnalyzer.getConsistencyRatio());
            result.put(alternatives.get(i), mapWeightAndCR);
        }

        return result;
    }

    @Override
    public Map<Criteria, Double> findAverageWeightsOfAllCriteria(Survey survey) {

        Map<Criteria, Double> result = new LinkedHashMap<>();

        for (Criteria criteria : survey.getCriterias()) {

            double avg = 0;
            double sum = 0;
            List<AnswerCriteria> answerCriteriaList = answerCriteriaRepository.findAllByCriteria(criteria);

            for (AnswerCriteria answerCriteria : answerCriteriaList) {
                sum += answerCriteria.getWeight();
            }
            avg = sum / answerCriteriaList.size();
            avg = Math.round(avg * 1000d) / 1000d;
            result.put(criteria, avg);
        }


        return result;
    }

    @Override
    public Map<Alternative, Double> rank(Survey survey) {
        Map<Alternative, Double> result = new LinkedHashMap<>();

        List<AnswerAlternative> averageAnswerAlternativeList = averageWeightOfAnswerAlternative(survey);
        List<Alternative> alternativeList = survey.getAlternatives();

        Map<Criteria, Double> averageWeightsOfAllCriteria = findAverageWeightsOfAllCriteria(survey);


        for (Alternative alternative : alternativeList) {
            double sumOfPriority = 0;

            List<AnswerAlternative> answerAlternativeList = findAllAnswersAlternativeByAlternativeName(alternative.getAlternativeName(), averageAnswerAlternativeList);

            for (int i = 0; i < answerAlternativeList.size(); i++) {
                sumOfPriority = sumOfPriority + (answerAlternativeList.get(i).getWeight() * findWeightOfCriteriaByCriteriaName(averageWeightsOfAllCriteria, answerAlternativeList.get(i).getCriteria().getCriteriaName()));
            }

            result.put(alternative, sumOfPriority);

        }

        return result;
    }


    private double findWeightOfCriteriaByCriteriaName(Map<Criteria, Double> map, String name) {

        for (Map.Entry<Criteria, Double> entry : map.entrySet()) {
            Criteria key = entry.getKey();
            Double value = entry.getValue();

            if (key.getCriteriaName().equals(name)) {
                return value;
            }

        }

        return 0;
    }

    private List<AnswerAlternative> findAllAnswersAlternativeByAlternativeName(String alternativeName, List<AnswerAlternative> averageAnswerAlternativeList) {

        List<AnswerAlternative> result = new ArrayList<>();
        for (AnswerAlternative averageAnswerAlternative : averageAnswerAlternativeList) {
            if (averageAnswerAlternative.getAlternative().getAlternativeName().equals(alternativeName)) {
                result.add(averageAnswerAlternative);
            }
        }

        return result;

    }


    private List<AnswerAlternative> averageWeightOfAnswerAlternative(Survey survey) {
        List<AnswerAlternative> averageAnswerAlternativeList = new ArrayList<>();

        List<Criteria> criteriaList = survey.getCriterias();
        for (Criteria criteria : criteriaList) {

            for (Alternative alternative : survey.getAlternatives()) {
                List<AnswerAlternative> answerAlternativeList =
                        answerAlternativeRepository.findAllByCriteriaAndAlternative(criteria, alternative);

                AnswerAlternative newItemInAverageAnswerAlternativeList = new AnswerAlternative();
                double avg = 0;
                double sum = 0;

                for (AnswerAlternative answerAlternative : answerAlternativeList) {

                    newItemInAverageAnswerAlternativeList.setAlternative(answerAlternative.getAlternative());
                    sum += answerAlternative.getWeight();

                }
                avg = sum / answerAlternativeList.size();
                avg = Math.round(avg * 1000d) / 1000d;

                newItemInAverageAnswerAlternativeList.setWeight(avg);
                newItemInAverageAnswerAlternativeList.setCriteria(criteria);
                averageAnswerAlternativeList.add(newItemInAverageAnswerAlternativeList);
            }
        }

        return averageAnswerAlternativeList;
    }


    @Override
    public void saveAnswerAlternative(Answer answer, Map<Alternative, Map<Double, Double>> weightsOfAllAlternative, Criteria criteria) {

        weightsOfAllAlternative.forEach((k, v) -> {
            AnswerAlternative answerAlternative = new AnswerAlternative(answer, criteria);
            answerAlternative.setAlternative(k);

            v.forEach((weight, consistencyRatio) -> {
                answerAlternative.setWeight(weight);
                answerAlternative.setConsistencyRatio(consistencyRatio);

            });

            answerAlternativeRepository.save(answerAlternative);
        });

    }


    @Override
    public void saveAnswerCriteria(Answer answer, Map<Criteria, Map<Double, Double>> weightsOfAllCriteria) {

        weightsOfAllCriteria.forEach((k, v) -> {
            AnswerCriteria answerCriteria = new AnswerCriteria(answer);
            answerCriteria.setCriteria(k);

            v.forEach((weight, consistencyRatio) -> {
                answerCriteria.setWeight(weight);
                answerCriteria.setConsistencyRatio(consistencyRatio);

            });


            answerCriteriaRepository.save(answerCriteria);
        });

    }


    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
    }


    private <T> double[][] generateAHPMatrix(List<Comparison<T>> comparisonList, String[] criteriaRating, List<T> listDeterminant) {

        for (int i = 0; i < criteriaRating.length; i++) {
            comparisonList.get(i).setValue(Integer.parseInt(criteriaRating[i]));
        }

//        for (Comparison<Criteria> comparison : comparisonList) {
//            System.out.println(comparison.getChoice1() + " - " + comparison.getChoice2() + " value: " + comparison.getValue());
//        }

        int ahpMatrixSize = listDeterminant.size();
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


}