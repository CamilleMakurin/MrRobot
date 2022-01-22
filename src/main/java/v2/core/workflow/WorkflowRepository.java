package v2.core.workflow;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import v2.core.ApplicationContext;
import v2.configuration.Configuration;
import v2.core.log.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class WorkflowRepository {

    private WorkflowMapper mapper;

    @Autowired
    public WorkflowRepository(WorkflowMapper mapper) {
        this.mapper = mapper;
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
