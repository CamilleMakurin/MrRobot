package v1.workflowRecorder.configuration;

import javafx.util.Pair;

public class ConfigUtil {

    private static final String SEPARATOR = "==";

    public static Pair<String, String> readValue(String line)  {
        String name = line.substring(0,line.indexOf(SEPARATOR));
        String value = line.substring(line.indexOf(SEPARATOR)+2, line.length());
        return new Pair<>(name.trim(), value.trim());
    }

    public static String getDirectoryPath(String input){
        //  input "C:\\Program Files\\Mozilla Firefox\\firefox.exe"
        return input.substring(0, input.lastIndexOf("\\"));
    }

}
