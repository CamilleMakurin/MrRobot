package v1.workflowRecorder.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ConfigCreationService {

    private static final List<String> nameOptions = Arrays.asList("Beautiful Ambient Mix", "Relaxing Ambient Mix", "Calm Ambient Mix");

    public void createConfigFiles(List<String> workingDirectories) {
        workingDirectories.forEach(this::createConfigFile);
    }

    private void createConfigFile(String dir) {
        //example C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName

        /*
        copyPathToSourceLocation == C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\source
        copyOutPutPath == C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\done
        copyOutPutFileName == SomeName.mp4
        sourceTracksPath == C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\tracks
        mergedTrackOutputDir == C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\source

        copyVideoName == Another Life | Beautifuf Ambient Mix
        copyVideoTags == #DeepWater #ambient #studymusic #workmusic #sleepmusic
        copyPathToFotoLocation == C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\foto
        thumbNailName == SomeName OUTY
        copyXvalue == 0
        */
        try {
            System.out.println("Creating config file for directory: " + dir);
            String videoName = extractFileName(dir);
            Path file = Paths.get(dir + "\\config.txt");
            List<String> lines = new ArrayList<>();
            lines.add("copyPathToSourceLocation == " + dir + "\\source");
            lines.add("copyOutPutPath == " + dir + "\\done");
            lines.add("copyOutPutFileName == " + videoName + ".mp4");
            lines.add("sourceTracksPath == " + dir + "\\tracks");
            lines.add("mergedTrackOutputDir == " + dir + "\\source");

            lines.add("copyVideoName == " + videoName + " | " + getNameOption());
            lines.add("copyVideoTags == #DeepWater #ambient #studymusic #workmusic #sleepmusic");
            lines.add("copyPathToFotoLocation == " + dir + "\\foto");
            lines.add("thumbNailName == " + videoName.toUpperCase());
            lines.add("copyXvalue == " + getXValue(videoName));
            lines.add("copyVideoNameInTemplate == " + videoName);


            Files.write(file, lines, StandardCharsets.UTF_8);
            System.out.println("Config file created");
        } catch (IOException e) {
            System.out.println("Failed to create config file: ");
            e.printStackTrace();
        }
    }

    private String getXValue(String videoName) {
        //12 ->0
        //step 100 px

        int length = videoName.length();
        if (length > 12) {
            System.out.println("Video name longer than 12 chars. ERROR");
            return null;
        }
        int step = 1200 - length * 100;
        return String.valueOf(step);
    }

    private String getNameOption() {
        Random rd = new Random();
        return nameOptions.get(rd.nextInt(nameOptions.size()));
    }


    private String extractFileName(String dir) {
        return dir.substring(dir.lastIndexOf("-") + 1);
    }
}
