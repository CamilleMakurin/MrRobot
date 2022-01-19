package v2.core.action.configuration;

import v2.core.constant.ActionAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialActionConfigurationHolder {

    private static Map<String, List<SpecialActionConfiguration>> configurations;

    public static void initConfigurations() {
        configurations = new HashMap<>();

        List<SpecialActionConfiguration> wfConfigurations = new ArrayList<>();
        wfConfigurations.add(SpecialActionConfiguration.builder().
                workflowName("testWF").attribute(ActionAttribute.EXECUTABLE_FILE_PATH, "testPath").specialOrder(1)
        );
        configurations.put("testWF", wfConfigurations);
    }

    public static List<SpecialActionConfiguration> getSpecActionConfigs(String wfName) {
        return configurations.get(wfName);
    }

}
