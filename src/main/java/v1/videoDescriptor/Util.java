package v1.videoDescriptor;

public class Util {


    //_instrumental_LOSSLESS.wav
    //_background_vocals_LOSSLESS.wav

    public static String formatTrackName(String fullTrackName) {
        return fullTrackName.substring(fullTrackName.lastIndexOf("\\") + 1).
                replace("_instrumental_LOSSLESS.wav", "").
                replace("_background_vocals_LOSSLESS.wav", "")
                .replace("_", " ");
    }


    public static String formatDuration(int durationInSeconds) {
        if (durationInSeconds < 60) {
            return "00:00:" + formatNumberWithZero(durationInSeconds);
        }
        int minutes = durationInSeconds / 60;
        int minutesInSeconds = minutes * 60;
        int leftOverSeconds = durationInSeconds - minutesInSeconds;
        String hoursAndMinutes = getHoursTime(minutes);
        return hoursAndMinutes + ":" + formatNumberWithZero(leftOverSeconds);
    }

    private static String getHoursTime(int minutes) {
        if (minutes >= 60) {
            int hours = minutes / 60;
            int hoursInMinutes = hours * 60;
            int minutesLeftOver = minutes - hoursInMinutes;
            return "0" + hours + ":" + formatNumberWithZero(minutesLeftOver);
        }

        return "00:" + formatNumberWithZero(minutes);
    }

    private static String formatNumberWithZero(int time) {
        if (time < 10) {
            return "0" + time;
        }
        return String.valueOf(time);
    }

    public static String sumDuration(String totalTime, String trackDuration) {
        trackDuration = "00:0"+trackDuration;
        int seconds = extract(totalTime, "seconds");
        int minutes = extract(totalTime, "minutes");
        int hours = extract(totalTime, "hours");
        int trackSeconds = extract(trackDuration, "seconds");
        int trackMinutes = extract(trackDuration, "minutes");

        int secondsSum = seconds + trackSeconds;
        int minutesFromSeconds = secondsSum / 60;
        int secondsFinal = secondsSum % 60;

        int minutesTotal = minutes + trackMinutes + minutesFromSeconds;
        int hoursFromMinutes = minutesTotal / 60;
        int minutestFinal = minutesTotal % 60;

        int hoursFinal = hours + hoursFromMinutes;

        return formatToString(hoursFinal, minutestFinal, secondsFinal);
    }

    private static String formatToString(int hoursFinal, int minutestFinal, int secondsFinal) {
        return String.format("%s:%s:%s", formatToString(hoursFinal),formatToString(minutestFinal),formatToString(secondsFinal));
    }

    private static String formatToString(int unit) {
        if (unit > 9) {
            return String.valueOf(unit);
        }
        return "0" + unit;
    }

    protected static int extract(String totalTime, String unit) {
        //01234567
        //00:00:00
        if (unit == "seconds") {
            return extract(totalTime.substring(6, 8));
        } else if (unit == "minutes") {
            return extract(totalTime.substring(3, 5));
        } else {
            return extract(totalTime.substring(0, 2));
        }

    }

    protected static Integer extract(String value) {
        if (value.startsWith("0")) {
            value = value.substring(1, 2);
        }
        return Integer.valueOf(value);
    }
}
