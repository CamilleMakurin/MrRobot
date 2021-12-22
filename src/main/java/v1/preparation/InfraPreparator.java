package v1.preparation;

import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.file.FileTool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InfraPreparator {

    FileTool fileTool = new FileTool();

    public boolean checkAudioTracksPresent(int numberOfTracksRequired) {
        return numberOfTracksRequired <= fileTool.countFiles(Configuration.AMBIENT_TRACKS_DOWNLOAD_FOLDER);
    }

    public boolean checkImagesPresent(int numberOfImagesRequired) {
        return numberOfImagesRequired <= fileTool.countFiles(Configuration.IMAGE_DOWNLOAD_FOLDER);
    }

    public String createDirectories(String videoName) {
        try {
            //Log.info("Creating working directory for video: " + videoName);
            List<String> listOfFiles = fileTool.getListOfFiles(Configuration.VIDEO_ROOT_FOLDER);
            int lastCreatedNumber = getLastCreatedNumber(listOfFiles) + 1;
            String directoryName = lastCreatedNumber + "-" + videoName;
            fileTool.createDirectory(Configuration.VIDEO_ROOT_FOLDER, directoryName);
            String rootDirectoryName = Configuration.VIDEO_ROOT_FOLDER + "\\" + directoryName;
            fileTool.createDirectory(rootDirectoryName, "done");
            fileTool.createDirectory(rootDirectoryName, "foto");
            fileTool.createDirectory(rootDirectoryName, "source");
            fileTool.createDirectory(rootDirectoryName, "tracks");
            fileTool.createFile(rootDirectoryName + "\\config.txt");
            // Log.info("Working directory created: " + rootDirectoryName);
            return rootDirectoryName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getLastCreatedNumber(List<String> listOfFiles) {
        return Collections.max(listOfFiles.stream().
                filter(s -> !s.startsWith("template - Copy") && !s.startsWith("UPLOADED")).
                map(s -> s.substring(0, s.indexOf("-"))).
                map(Integer::valueOf).
                collect(Collectors.toList()));
    }

    public void moveAudioFiles(String rootDirectoryPath) {
        // Log.info("Moving audio files to working directory: " + rootDirectoryPath);
        List<String> listOfFiles = fileTool.getListOfFiles(Configuration.AMBIENT_TRACKS_DOWNLOAD_FOLDER);
        Random rnd = new Random();
        List<String> trackNames = new ArrayList<>();
        for (int i = 0; i < Configuration.NUMBER_OF_TRACKS_PER_VIDEO; i++) {
            boolean added = false;
            do {
                int nextInt = rnd.nextInt(listOfFiles.size());
                String trackName = listOfFiles.get(nextInt);
                if (!trackNames.contains(trackName)) {
                    trackNames.add(trackName);
                    added = true;
                }
            } while (!added);
        }
        trackNames.forEach(name -> fileTool.moveFile(name, Configuration.AMBIENT_TRACKS_DOWNLOAD_FOLDER, rootDirectoryPath + "\\tracks"));
        //  Log.info("Audio files moved");
    }

    public void moveImageFiles(String rootDirectoryPath) throws IOException {
        //   Log.info("Moving image files to working directory: " + rootDirectoryPath);
        List<String> listOfFiles = fileTool.getListOfFiles(Configuration.IMAGE_DOWNLOAD_FOLDER);
        Random rnd = new Random();
        String imageName = listOfFiles.get(rnd.nextInt(listOfFiles.size()));
        String thumbnailImageName;
        String videoImageName;
        if (imageName.contains("(1)")) {
            videoImageName = imageName;
            thumbnailImageName = imageName.substring(0, imageName.indexOf("(1)")).trim();
        } else {
            videoImageName = imageName + " (1)";
            thumbnailImageName = imageName;
        }

        List<String> collect = listOfFiles.stream().filter(name -> name.contains(videoImageName) || name.contains(thumbnailImageName)).collect(Collectors.toList());
        if (collect.size() == 1) {
            String s = collect.get(0);
            //fileTool.copyFile(s, Configuration.IMAGE_DOWNLOAD_FOLDER, rootDirectoryPath + "\\foto");
            Path copied = Paths.get(rootDirectoryPath + "\\foto\\" + s);
            Path originalPath = new File(Configuration.IMAGE_DOWNLOAD_FOLDER + "\\" + s).toPath();
            Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
            fileTool.moveFile(s, Configuration.IMAGE_DOWNLOAD_FOLDER, rootDirectoryPath + "\\source");

            File file = new File(rootDirectoryPath + "\\source" + "\\" + s);
            file.renameTo(new File(rootDirectoryPath + "\\source" + "\\" + "photo.jpg"));
        } else {
            String large = collect.stream().filter(s -> s.contains("(1)")).findFirst().get();
            fileTool.copyFile(large, Configuration.IMAGE_DOWNLOAD_FOLDER, rootDirectoryPath + "\\source");
            String medium = collect.stream().filter(s -> !s.contains("(1)")).findFirst().get();
            fileTool.copyFile(medium, Configuration.IMAGE_DOWNLOAD_FOLDER, rootDirectoryPath + "\\foto");

            File file = new File(rootDirectoryPath + "\\source" + "\\" + large);
            file.renameTo(new File(rootDirectoryPath + "\\source" + "\\" + "photo.jpg"));
        }
        // Log.info("Image files moved");
    }
}
