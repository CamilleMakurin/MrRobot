package v2.core.action.wfconfig;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkflowConfig {

    private String workflowName;
    private List<SpecialActionConfig> specActionConfigs;

    private WorkflowConfig(String name) {
        this.workflowName = name;
    }

    public static WorkflowConfig forWorkflow(String wfName) {
        return new WorkflowConfig(wfName);
    }

    public WorkflowConfig withSpecActionConfig(SpecialActionConfig config) {
        specActionConfigs.add(config);
        return this;
    }
}
