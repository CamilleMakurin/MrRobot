package v2.configuration;

public class Configuration {

    public static final String WORKING_DIRECTORY = "C:\\Dev\\projects\\MouseRobot\\working_directory\\";
    public static final String OUT_DIR_PATH = WORKING_DIRECTORY + "output\\";

    //Map Generator configs
    public static final String GEKO_DRIVER_PATH = "C:\\Dev\\projects\\MouseRobot\\src\\main\\resources\\driver\\geckodriver.exe";
    public static final String RAW_MAP_DIRECTORY = WORKING_DIRECTORY + "rawmaps";
    public static final String MAP_URL = "http://localhost:8080/index.html";


    //application initialization controls
    public static final boolean runGUI = false;
    public static final boolean runMapGenerator = false;
    public static final boolean runWFRecorder = true;

}
