package v1.videoDescriptor;

import org.junit.jupiter.api.Test;
import v1.videoDescriptor.Util;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void formatTrackName() {
        assertEquals("Aurora Colossus", Util.formatTrackName("C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\tracks\\Aurora_Colossus_instrumental_LOSSLESS.wav"));
    }

    @Test
    void format_onlySeconds() {
        assertEquals("00:00:30",Util.formatDuration(30));
    }

    @Test
    void format_59Seconds() {
        assertEquals("00:00:59",Util.formatDuration(59));
    }

    @Test
    void format_1minute() {
        assertEquals("00:01:00",Util.formatDuration(60));
    }

    @Test
    void format_1minute1Second() {
        assertEquals("00:01:01",Util.formatDuration(61));
    }

    @Test
    void format_1minute11Second() {
        assertEquals("00:01:11",Util.formatDuration(71));
    }

    @Test
    void format_10minute() {
        assertEquals("00:10:00",Util.formatDuration(600));
    }

    @Test
    void format_10minute10Seconds() {
        assertEquals("00:10:10",Util.formatDuration(610));
    }

    @Test
    void format_1Hour() {
        assertEquals("01:00:00",Util.formatDuration(3600));
    }

    @Test
    void format_1Hour_1Second() {
        assertEquals("01:00:01",Util.formatDuration(3601));
    }

    @Test
    void format_1Hour_1_minute_1Second() {
        assertEquals("01:01:01",Util.formatDuration(3661));
    }

    @Test
    void extract_02() {
        assertEquals(2,Util.extract("02"));
    }

    @Test
    void extract_22() {
        assertEquals(22,Util.extract("22"));
    }

    @Test
    void extract_00() {
        assertEquals(0,Util.extract("00"));
    }

    @Test
    void extract_seconds() {
        assertEquals(11,Util.extract("00:00:11", "seconds"));
    }

    @Test
    void extract_minutes() {
        assertEquals(11,Util.extract("00:11:00","minutes"));
    }

    @Test
    void extract_hours() {
        assertEquals(11,Util.extract("11:00:00","hours"));
    }

    @Test
    void extract_seconds01() {
        assertEquals(1,Util.extract("00:00:01", "seconds"));
    }

    @Test
    void extract_minutes01() {
        assertEquals(1,Util.extract("00:01:00","minutes"));
    }

    @Test
    void extract_hours01() {
        assertEquals(1,Util.extract("01:00:00","hours"));
    }


    @Test
    void sumDuration_0_30seconds() {
        assertEquals("00:00:30",Util.sumDuration("00:00:00", "0:30"));
    }

    @Test
    void sumDuration_0_130seconds() {
        assertEquals("00:01:30",Util.sumDuration("00:00:00", "1:30"));
    }

    @Test
    void sumDuration_30_30seconds() {
        assertEquals("00:01:00",Util.sumDuration("00:00:30", "0:30"));
    }

    @Test
    void sumDuration_30_32seconds() {
        assertEquals("00:01:02",Util.sumDuration("00:00:30", "0:32"));
    }

    @Test
    void sumDuration_() {
        assertEquals("01:02:02",Util.sumDuration("00:58:30", "3:32"));
    }

}