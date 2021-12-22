package v1.workflowRecorder.constant;

public enum TrackType {

    AMBIENT("ambient", "C:\\Users\\user\\Desktop\\DeepWater\\Tracks\\ambient"),
    DANCE("ambient", "C:\\Users\\user\\Desktop\\DeepWater\\Tracks\\dance"),

    LOFI("lofi", "C:\\Users\\user\\Desktop\\DeepWater\\Tracks\\lofi");

    private String typeName;
    private String downloadDirectoryPath;

    TrackType(String typeName, String downloadDirectoryPath) {
        this.typeName = typeName;
        this.downloadDirectoryPath = downloadDirectoryPath;
    }


    public String getTypeName() {
        return typeName;
    }

    public String getDownloadDirectoryPath() {
        return downloadDirectoryPath;
    }
}
