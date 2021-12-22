package v1.validation;

import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.file.FileTool;

public class Validator {

    private FileTool fileTool = new FileTool();

    public boolean checkAudioTracksPresent(int numberOfTracksRequired) {
        return numberOfTracksRequired <= fileTool.countFiles(Configuration.AMBIENT_TRACKS_DOWNLOAD_FOLDER);
    }

    public boolean checkImagesPresent(int numberOfImagesRequired) {
        return numberOfImagesRequired <= fileTool.countFiles(Configuration.IMAGE_DOWNLOAD_FOLDER);
    }
}
