package v1.components.instagram;

import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.file.FileTool;
import v1.workflowRecorder.player.WorkflowExecutor;

import java.util.List;
import java.util.Random;

public class Instagram {

    FileTool fileTool = new FileTool();

    WorkflowExecutor workflowExecutor = new WorkflowExecutor();

    public void openInstagram() {
        WorkflowExecutor.executeWorkflow(null, Configuration.DO_INSTAGRAM_POST_WORKFLOW_PATH);
    }

    public void doInstagramPost() {
        List<String> listOfFiles = fileTool.getListOfFiles(Configuration.INSTAGRAM_WORKING_DIRECTORY_PHOTOS);
        Random rnd = new Random();
        String fileName = listOfFiles.get(rnd.nextInt(listOfFiles.size()));
        fileTool.moveFile(fileName, Configuration.INSTAGRAM_WORKING_DIRECTORY_PHOTOS, Configuration.INSTAGRAM_WORKING_DIRECTORY_SOURCE);
        WorkflowExecutor.executeWorkflow(null, Configuration.DO_INSTAGRAM_POST_WORKFLOW_PATH);
        fileTool.moveFile(fileName, Configuration.INSTAGRAM_WORKING_DIRECTORY_SOURCE, Configuration.INSTAGRAM_WORKING_DIRECTORY_POSTED);
    }

    public void switchAccount() {

    }

    public void followAccounts(int accountsToFollow) {
        WorkflowExecutor.executeWorkflow(null, Configuration.GO_TO_SUGGESTED_PEOPLE_WORKFLOW_PATH);
        for (int i = 0; i < accountsToFollow; i++) {
            WorkflowExecutor.executeWorkflow(null, Configuration.FOLLOW_ACCOUNT_WORKFLOW_PATH);
        }
    }
}
