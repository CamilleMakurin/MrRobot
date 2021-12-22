package v2.workflow;

import lombok.Getter;
import lombok.Setter;
import v2.action.domain.Action;

import java.util.List;

@Setter
@Getter
public class Workflow {

    private List<Action> actionList;


}
