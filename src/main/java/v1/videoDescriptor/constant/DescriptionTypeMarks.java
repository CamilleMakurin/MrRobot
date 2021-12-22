package v1.videoDescriptor.constant;

public enum DescriptionTypeMarks {

    TRACK_TAGS("------- TRACK TAGS START-------", "------- TRACK TAGS END-------");

    DescriptionTypeMarks(String startName, String endName) {
        this.startName = startName;
        this.endName = endName;
    }

    private String startName;
    private String endName;

    public String getStartName() {
        return startName;
    }

    public void setStartName(String startName) {
        this.startName = startName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }
}
