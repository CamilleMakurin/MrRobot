package v1.videoDescriptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import v1.workflowRecorder.constant.Configuration;
import v1.workflowRecorder.trackinfo.TrackInfo;
import v1.workflowRecorder.file.FileTool;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VideoDescriptor {

    private static String KEYWORDS = "ambient,chill mix,chillout,chill,ambient mix,chill mix 2020,chill music 2020,Lovely Chill Mix,relaxing music,music for sleeping,stress,sleep music,music for studying,Music For Work,ambient music,beats to relax,work music,relex music,work,Chill Vibes,study,background,background music,backbround music mix,mix,downtempo,electronic,melodic,atmospheric,relaxing,vibes,bass,relaxation,stress relief,mindfulness,meditation,chill music mix,calm,ambient music mix";

    private static final String DESCRIPTION = "Enjoy this peaceful nature scene with beautiful ambient music mix. This music mix video was designed to " +
            "help you relax, sleep and simply calm down your thoughts. The music mix will also help you to focus and concentrate while " +
            "you study or work as background music. " +
            "Please enjoy, comment, like and subscribe to find more beautiful music.";

    private static final String DESCRIPTION_TAGS = "#ambient#backgroundmusic#concentrationmusic#chilloutmusicmix#ambient#chilloutsessions#chilloutmusic#chillstepplaylist#bestofchillstep#chilloutmix#chillmix#chillstepmix#chillout#chill#chillstep#ambientmix#melodicdubstep#2020#chillmix2020#chilloutmix2020#chillmusic2020#bestofchillstep2020#bestofchill2020#chillstepmix2020#LovelyChillMix#chillmix#chillmusic#relaxingmusic#musicforsleeping#stress#Mind#pianomusic#sleepmusic#musicforstudying#EpicChillstepCollection#MusicForWork#ambientmusic#beatstorelax#chillradio#ambientmusicmixchillyourmind#workmusic#relexmusic#work#ChillVibes#Vibes#study#background#backgroundmusic#backbroundmusicmix#backgroundmix#mix#downtempo#future#garage#futuregarage#electronic#melodic#atmospheric#relaxing#chilled#deep#vibes#bass#Fluidified#BeautifulChillMix#beautiful#relaxation#stressrelief#mindfulness#meditation#music#chillmusicmix#calm#my#mind#calmmymind#calmmymindbeautifulchillmusicmix#calmmymindmix#calmmymindchillmix#calmmymindbeautifulchillmix#beautifulchillmusic#fyze";

    private FileTool fileTool = new FileTool();

    public void createDescription(String dir) throws IOException {
        System.out.println("creating descriptor file in directory: " + dir);
        Path descriptorFile = Paths.get(dir + "\\descriptor.txt");
        List<String> tags = getTags(dir);
        //  Files.write(file, tags, StandardCharsets.UTF_8);
        FileWriter fl = new FileWriter(dir + "\\descriptor.txt", true);
        fl.append(DESCRIPTION);
        fl.append("\n");
        fl.append("\n");
        fl.append("Tracks: \n");
        for (String tag : tags) {
            fl.append(tag+"\n");
        }

        for (int i = 0; i < 20; i++) {
            fl.append("\n");
        }
        fl.append(DESCRIPTION_TAGS);

        fl.append("\n");
        fl.append("\n");
        fl.append("\n");
        fl.append("keywords: \n");
        fl.append(KEYWORDS);
        fl.close();

        System.out.println("created descriptor file");
    }

    private List<String> getTags(String dir) throws IOException {
        FileTool fileTool = new FileTool();
        List<String> listOfFilesWithFullPath = fileTool.getListOfFilesWithFullPath(dir + "\\tracks");
        Path trackInfoFile = Paths.get(Configuration.TRACKS_DOWNLOAD_FOLDER + "\\trackinfo.txt");
        BufferedReader bufferedReader = Files.newBufferedReader(trackInfoFile);
        List<TrackInfo> trackInfos = readTrackInfo(bufferedReader);
        return createTimelineTags(listOfFilesWithFullPath, trackInfos);
    }

    protected List<String> createTimelineTags(List<String> listOfFilesWithFullPath, List<TrackInfo> trackInfos) {
        List<String> tags = new ArrayList<>();
        String totalTime = "00:00:00";
        for (String trackName : listOfFilesWithFullPath) {
            Optional<TrackInfo> first = trackInfos.stream().filter(ti -> trackName.contains(ti.getSoundStripeFormatedName())).findFirst();
            if (!first.isPresent()) {
                System.out.println("no track info for track " + trackName);
                continue;
            }
            TrackInfo trackInfo = first.get();
            tags.add(totalTime + "  " + trackInfo.getTrackAuthor() + " - " + trackInfo.getTrackName());
            totalTime = Util.sumDuration(totalTime, trackInfo.getTrackDuration());
        }
        return tags;
    }

    private static List<TrackInfo> readTrackInfo(BufferedReader bufferedReader) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<TrackInfo> trackInfos = new ArrayList<>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            trackInfos.add(objectMapper.readValue(line, TrackInfo.class));
        }
        return trackInfos;
    }



}
