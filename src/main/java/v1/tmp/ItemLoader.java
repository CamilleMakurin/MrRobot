package v1.tmp;

import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.constant.ConfigurationTMP;
import v1.workflowRecorder.player.WorkflowExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ItemLoader {


    public static void doWorkflow() throws IOException {
        List<ItemEntry> entries = loadFile();
        if (entries.isEmpty()) {
            System.out.println("No entries");
        } else {
            for (ItemEntry entry : entries) {
                WorkflowExecutor.executeWorkflowTMP(ConfigurationTMP.OUT_DIR_PATH, entry);
            }
        }
    }

    private static List<ItemEntry> loadFile() throws IOException {
        Path trackInfoFile = Paths.get(ConfigurationTMP.ENTRIES);
        BufferedReader bufferedReader = Files.newBufferedReader(trackInfoFile);
        return readTrackInfo(bufferedReader);

    }

    private static List<ItemEntry> readTrackInfo(BufferedReader bufferedReader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ItemEntry> trackInfos = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
            trackInfos.add(objectMapper.readValue(line, ItemEntry.class));
        }
        return trackInfos;
    }


}
