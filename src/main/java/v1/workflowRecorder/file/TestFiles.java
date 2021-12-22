package v1.workflowRecorder.file;

import v1.videoDescriptor.VideoDescriptor;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TestFiles {

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
       /* FileTool fileTool = new FileTool();
        int wavFileDuration = fileTool.getWavFileDuration("C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName\\tracks\\Aurora_Colossus_instrumental_LOSSLESS.wav");
        System.out.println(wavFileDuration);*/

        VideoDescriptor vd = new VideoDescriptor();
        vd.createDescription("C:\\Users\\user\\Desktop\\DeepWater\\Video\\2-SomeName");

    }
}
