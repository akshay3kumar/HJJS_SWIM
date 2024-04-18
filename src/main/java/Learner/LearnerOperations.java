package Learner;
import java.util.HashMap;
import java.util.Map;

import static UniqueIdGenerator.UniqueIdGenerator.generateUniqueId;

public class LearnerOperations {
    private Map<String, Learner> learnerMap;

    public LearnerOperations() {
        learnerMap = new HashMap<>();
    }

    public void createLearner(String name,String gender,String age,String emContact,int gLevel) {
            int uniqueId= Integer.parseInt(generateUniqueId());
            Learner newLearner = new Learner(uniqueId,name,gender,age,emContact,gLevel);
            learnerMap.put(name,newLearner);
    }


}
