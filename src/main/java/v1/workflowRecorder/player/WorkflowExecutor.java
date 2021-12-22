package v1.workflowRecorder.player;

import v1.tmp.ItemEntry;
import v1.workflowRecorder.WorkflowExecutableActionProducer;
import v1.workflowRecorder.action.ActionExecutor;
import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.configuration.ConfigCreationService;
import v1.workflowRecorder.configuration.ConfigService;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.constant.Configuration;
import v1.videoDescriptor.VideoDescriptor;
import v1.workflowRecorder.action.executable.special.MergeAudioTracks;
import v1.workflowRecorder.file.FileTool;

import java.io.IOException;
import java.util.*;

public class WorkflowExecutor {

    private static ConfigService configService = new ConfigService();
    private static ActionExecutor actionExecutor = new ActionExecutor();
    private static ConfigCreationService configCreationService = new ConfigCreationService();
    private static VideoDescriptor descriptor = new VideoDescriptor();
    private static WorkflowExecutableActionProducer actionProducer = new WorkflowExecutableActionProducer();

    private static FileTool fileTool = new FileTool();
    private static final List<String> arguments = Arrays.asList("C:\\Users\\user\\Desktop\\DeepWater\\Video\\11111-test");

    public static void main(String[] args) {
        //execute without UI
        arguments.forEach(System.out::println);
        executeWorkflows(arguments);
    }

    public static void executeWorkflows(List<String> workingDirectories) {
        configCreationService.createConfigFiles(workingDirectories);
        workingDirectories.forEach(dir -> {
            try {
                configService.loadConfigurationFile(dir);
                descriptor.createDescription(dir);
                //   move as workflow step
                MergeAudioTracks mergeAudioTracks = new MergeAudioTracks();
                mergeAudioTracks.execute(null);

                executeWorkflow(dir, Configuration.CREATE_VIDEO_WORKFLOW_PATH);
                executeWorkflow(dir, Configuration.CREATE_THUMBNAIL_WORKFLOW_PATH);
                fileTool.moveFile("thumbnail.jpg", dir + "\\foto", dir + "\\done");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //for tests
    public static void executeWorkflow() {
        executeWorkflow(Configuration.CONFIG_DIRECTORY);
    }

    //for tests
    public static void executeWorkflow(String configDirectory) {
        executeWorkflow(configDirectory, Configuration.OUT_DIR_PATH);
        //TODO: TEMPORARY:
        //executeWorkflow(configDirectory, ConfigurationTMP.OUT_DIR_PATH);
    }

    public static void executeWorkflow(String configDirectory, String workflowPath) {
        try {
            List<ExecutableAction> workflowActions = actionProducer.getWorkflowActions(workflowPath);
            actionExecutor.executeActions(workflowActions);
        } catch (IOException e) {
        }
    }

    public static void executeWorkflowRandom(String configDirectory, String workflowPath) {
        try {
            List<ExecutableAction> workflowActions = actionProducer.getWorkflowActions(workflowPath);
            actionExecutor.executeActions(workflowActions);
        } catch (IOException e) {
        }
    }

    public static void executeWorkflowTMP(String workflowPath, ItemEntry entry) {
        try {
            List<ExecutableAction> workflowActions = actionProducer.getWorkflowActions(workflowPath);
            //{"name":"copyPathToFotoLocation","actionType":"COPY_TO_CLIPBOARD","attributes":{}}

            ExecutableAction copyItemName = workflowActions.stream().filter(action -> action.getName().equals("copyItemName")).findFirst().get();
            Map<ActionAttribute, String> attributes = new HashMap<>();
            attributes.put(ActionAttribute.VALUE_TO_COPY, entry.getName());
            copyItemName.setAttributes(attributes);

            ExecutableAction copyItemValue = workflowActions.stream().filter(action -> action.getName().equals("copyItemValue")).findFirst().get();
            Map<ActionAttribute, String> attrs = new HashMap<>();
            attrs.put(ActionAttribute.VALUE_TO_COPY, entry.getValue());
            copyItemValue.setAttributes(attrs);

            actionExecutor.executeActions(workflowActions);
        } catch (IOException e) {
            System.out.println("asdf");
        }
    }
}
