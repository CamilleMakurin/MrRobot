package v1.workflowRecorder.action.executable.special;

import v1.workflowRecorder.action.executable.ExecutableAction;
import v1.workflowRecorder.configuration.ConfigService;
import v1.workflowRecorder.constant.ActionAttribute;
import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.file.FileTool;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class MergeAudioTracks implements ExecutableAction {

    public static final String SOURCE_TRACKS_PATH = "sourceTracksPath";
    public static final String MERGED_TRACK_OUTPUT_DIR = "mergedTrackOutputDir";


    private String name;
    private Map<ActionAttribute, String> attributes;

    public MergeAudioTracks() {
    }

    public MergeAudioTracks(String name, Map<ActionAttribute, String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    @Override
    public void execute(Robot robot) {
        FileTool ft = new FileTool();
        String sourceTrackDirectory = getConfigFile(SOURCE_TRACKS_PATH);
        List<String> listOfFilesWithFullPath = ft.getListOfFilesWithFullPath(sourceTrackDirectory).
                stream().map(s -> s.replace("\\", "/")).collect(Collectors.toList());
        String outPutDirectory = getConfigFile(MERGED_TRACK_OUTPUT_DIR);
        outPutDirectory = outPutDirectory.replace("\\", "/") + "/audio.wav";
        try {
            boolean fileExists = false;
            while (!fileExists) {
                mergetFiles(ft, listOfFilesWithFullPath, outPutDirectory);
                fileExists = ft.fileExists(outPutDirectory);
            }
            //Log.info("Audio track merge completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mergetFiles(FileTool ft, List<String> listOfFilesWithFullPath, String outPutDirectory) throws IOException {
        log(listOfFilesWithFullPath);
        ft.executePythonScript(Configuration.MERGE_FILES_PY_DIRECTORY, outPutDirectory, listOfFilesWithFullPath);
    }

    private void log(List<String> listOfFilesWithFullPath) {
        //Log.info("Starting audio track merge with params: ");
        //Log.info("script file path: " + Configuration.MERGE_FILES_PY_DIRECTORY);
        // Log.info("output directory path: " + Configuration.MERGE_FILES_PY_DIRECTORY);
        // Log.info("list of track to merge: ");
        listOfFilesWithFullPath.forEach(System.out::println);
    }

    private String getConfigFile(String configName) {
        Optional<String> sourceTracksDirectoryOpt = ConfigService.getConfigValue(configName);
        String sourceTrackDirectory = null;
        if (!sourceTracksDirectoryOpt.isPresent()) {
            // Log.info("Track Merge Failed. Source track directory name is missing in config");
        } else {
            sourceTrackDirectory = sourceTracksDirectoryOpt.get();
        }
        return sourceTrackDirectory;
    }


}
