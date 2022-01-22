package v2.core.workflow;

import lombok.Getter;
import lombok.Setter;
import v2.core.action.wfconfig.SpecialActionConfig;
import v2.core.action.domain.Action;

import java.util.List;

@Setter
@Getter
public class Workflow {

    private String wfName;

    private List<Action> actionList;

}
