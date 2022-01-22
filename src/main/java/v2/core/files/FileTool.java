package v2.core.files;

import v1.workflowRecorder.configuration.ConfigUtil;
import v1.workflowRecorder.constant.Configuration;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileTool {

    // "C:\\Users\\user\\Desktop\\DeepWater\\1- Another Life\\done\\AnotherLifeDone.wav"
    public int getWavFileDuration(String filePath) throws IOException, UnsupportedAudioFileException {
        System.out.println("calculating track duration: " + filePath);
        MathContext m = new MathContext(3);
        File file = new File(filePath);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long audioFileLength = file.length();

        int frameSize = format.getFrameSize();
        float frameRate = format.getFrameRate();

        BigDecimal audioFileLengthBD = BigDecimal.valueOf(audioFileLength);
        BigDecimal frameSizeBD = BigDecimal.valueOf(frameSize);
        BigDecimal frameRateBD = BigDecimal.valueOf(frameRate);
        BigDecimal multiply = frameRateBD.multiply(frameSizeBD);
        BigDecimal result = audioFileLengthBD.divide(multiply, m);
        return result.round(m).intValue();

        /*int durationInSeconds = (int) (audioFileLength / (frameSize * frameRate));
        //System.out.println(durationInSeconds);
        return durationInSeconds;*/

    }

    public void createFile(String filePath) {
        try {
            File myObj = new File(filePath);
            myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDirectory(String targetDirectory, String directoryName) throws IOException {
        //FileAlreadyExistsException if directory exits
        String directoryPath = targetDirectory + "\\" + directoryName + "\\";
        Path path = Paths.get(directoryPath);
        Files.createDirectory(path);
    }

    public int countFiles(String directoryPath) {
        List<String> listOfFiles = getListOfFiles(directoryPath);
        return listOfFiles.size();
    }

    public List<String> getListOfFiles(String directoryPath) {
        //System.out.println("debug: " + directoryPath);
        File file = new File(directoryPath);
        return Arrays.asList(Objects.requireNonNull(file.list()));
    }


    public List<String> getListOfFilesWithFullPath(String directoryPath) {
        List<String> listOfFiles = getListOfFiles(directoryPath);
        return listOfFiles.stream().map(s -> directoryPath + "\\" + s).collect(Collectors.toList());
    }

    public void renameFile(String filePath, String renameTo) {
        Path path = Paths.get(filePath);
        File file = path.toFile();
        File newFileName = new File(renameTo);
        file.renameTo(newFileName);
    }

    public void executePythonScript(String fullPathToScriptFile, String outputFileDestination, List<String> params) throws IOException {

        String[] cmd = new String[params.size() + 3];
        cmd[0] = "python";
        cmd[1] = fullPathToScriptFile;
        cmd[2] = outputFileDestination;
        fillParams(cmd, params);
        Runtime r = Runtime.getRuntime();
        Process p = r.exec(cmd);
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String s;
        while ((s = in.readLine()) != null) {
            System.out.println(s);
        }
        System.out.println("Python workflowRecorder.file launched");
    }

    private void fillParams(String[] cmd, List<String> params) {
        for (int i = 0; i < params.size(); i++) {
            cmd[i + 3] = params.get(i);
        }
    }

    public void executeEXEFile(String fullPathToFile) throws IOException {
        // fullPathToFile = "C:\\Program Files\\Mozilla Firefox\\firefox.exe"
        // directoryPath = "C:\\Program Files\\Mozilla Firefox"
        String directoryPath = ConfigUtil.getDirectoryPath(fullPathToFile);
        Runtime.getRuntime().exec(fullPathToFile, null, new File(directoryPath));
    }

    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth     absolute width in pixels
     * @param scaledHeight    absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath,
                              String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output workflowRecorder.file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);

        // writes to output workflowRecorder.file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }

    /**
     * Resizes an image by a percentage of original size (proportional).
     *
     * @param inputImagePath  Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent         a double number specifies percentage of the output image
     *                        over the input image.
     * @throws IOException
     */
    public static void resize(String inputImagePath,
                              String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }

    public boolean filesValid(String dir) {
        boolean result;
        System.out.println("Validating files in director: " + dir);
        List<String> listOfFilesWithFullPath = getListOfFilesWithFullPath(dir);
        List<String> unsupported = new ArrayList<>();
        for (String path : listOfFilesWithFullPath) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            } catch (UnsupportedAudioFileException e) {
                //System.out.println("Unsupported file: " + path);
                unsupported.add("Unsupported file: " + path);
            } catch (Exception e) {
                //System.out.println("Exception while checking file validity: " + path);
                e.printStackTrace();
                unsupported.add("Exception while checking file validity: " + path);
            }
        }
        if (unsupported.isEmpty()) {
            result = true;
        } else {
            System.out.println("Tracks with errors found:");
            unsupported.forEach(System.out::println);

            result = false;
        }
        System.out.println("Finished validation. Valid: " + result);
        return result;
    }

    public boolean audionFilesInDownloadsFolder() {
        FileTool ft = new FileTool();
        List<String> listOfFiles = ft.getListOfFiles(Configuration.DOWNLOADS_FOLDER);
        return listOfFiles.stream().anyMatch(s -> s.contains(".mp3") || s.contains(".wav"));
    }

    public boolean imagesInDownloadsFolder() {
        FileTool ft = new FileTool();
        List<String> listOfFiles = ft.getListOfFiles(Configuration.DOWNLOADS_FOLDER);
        return listOfFiles.stream().anyMatch(s -> s.contains(".png") || s.contains(".jpg"));
    }

    public void moveTrackFromDownloads(String fileName) {
        moveFile(fileName, Configuration.DOWNLOADS_FOLDER, Configuration.TRACKS_DOWNLOAD_FOLDER);
    }

    public void moveFile(String fileName, String fromPath, String toPath) {
        File file = new File(fromPath + "\\" + fileName);
        file.renameTo(new File(toPath + "\\" + fileName));
        file.delete();
        System.out.println("Moved " + fileName + " from " + fromPath + " to " + toPath);
    }

    public void copyFile(String fileName, String fromPath, String toPath) {
        File file = new File(fromPath + "\\" + fileName);
        file.renameTo(new File(toPath + "\\" + fileName));
        System.out.println("Copied " + fileName + " from " + fromPath + " to " + toPath);
    }

    public boolean fileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }




}
