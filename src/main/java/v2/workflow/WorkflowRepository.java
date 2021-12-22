package v2.workflow;


import v2.ApplicationContext;
import v2.constant.Configuration;
import v2.log.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkflowRepository {

    private static WorkflowRepository instance;
    private WorkflowMapper mapper;

    private WorkflowRepository(WorkflowMapper workflowMapper) {
        this.mapper = workflowMapper;
    }

    public static WorkflowRepository getInstance() {
        if (instance == null) {
            instance = new WorkflowRepository(new WorkflowMapper());
        }
        return instance;
    }

    public void saveWorkflow(Workflow wf) {
        try {
            Path outputFilePath = Paths.get(Configuration.OUT_DIR_PATH + ApplicationContext.getContext().getWorkflowName());
            if (!Files.exists(outputFilePath)) {
                Files.createFile(outputFilePath);
            }
            Files.write(outputFilePath,
                    mapper.mapToJson(wf).getBytes());
        } catch (IOException e) {
            Log.error("Failed to save workflow: " + e.getMessage());
        }
    }

    public Workflow getWorkflow() {
        try {
            Path outputFilePath = Paths.get(Configuration.OUT_DIR_PATH + ApplicationContext.getContext().getWorkflowName());
            return !Files.exists(outputFilePath) ? null : mapper.readJson(Files.readAllLines(
                    outputFilePath).get(0));
        } catch (IOException e) {
            Log.error("Failed to load workflow: " + e.getMessage());
            return null;
        }
    }

}
