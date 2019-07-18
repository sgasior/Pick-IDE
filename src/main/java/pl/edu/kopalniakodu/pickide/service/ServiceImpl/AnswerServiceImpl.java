package pl.edu.kopalniakodu.pickide.service.ServiceImpl;

import org.springframework.stereotype.Service;
import pl.edu.kopalniakodu.pickide.domain.Criteria;
import pl.edu.kopalniakodu.pickide.domain.util.Comparison;
import pl.edu.kopalniakodu.pickide.service.ServiceInterface.AnswerService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

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
}
