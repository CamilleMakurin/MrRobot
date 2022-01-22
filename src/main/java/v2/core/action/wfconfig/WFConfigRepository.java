package v2.core.action.wfconfig;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
public class WFConfigRepository {

    private static Map<String,WorkflowConfig> inMemoryRepository;

    @PostConstruct
    public void initConfigurations() {
        inMemoryRepository = new HashMap<>();
        //here builders of wfConfigs

    }

    public WorkflowConfig getWorkflowConfig(String wfName) {
        return inMemoryRepository.get(wfName);
    }

}
