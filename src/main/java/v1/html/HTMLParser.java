package v1.html;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HTMLParser {


    //https://app.soundstripe.com/private_playlists/216598

    public static void main(String[] args) throws IOException, UnsupportedFlavorException {
        List<String> urls = Arrays.asList("https://www.youtube.com/watch?v=IaIdTA8ap-8",
                "https://www.youtube.com/watch?v=gLBl31mnwMY",
                "https://www.youtube.com/watch?v=jXDo3Al2cAk",
                "https://www.youtube.com/watch?v=8RloV4t8WTE");

        urls.forEach(url -> {
            try {
                doTheThing(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //doTheThing();
    }

    private static void doTheThing(String url) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //   System.out.println("start: " + LocalDateTime.now());
        Document doc = Jsoup.connect(url).get();
        //doc.getElementsByTag("meta").get(6).attributes.get("content");
        Elements meta = doc.getElementsByTag("script");
        Optional<Element> first = meta.stream().filter(m -> m.data().contains("window[\"ytInitialData\"]")).findFirst();
        if (first.isPresent()) {
            Element element = first.get();
            String data = element.data();
            // System.out.println("resplace: " +replace);
            String substring = data.substring(data.indexOf("window[\"ytInitialPlayerResponse\"] = "), data.indexOf("if (window"));
            substring = substring.replace("window[\"ytInitialPlayerResponse\"] = ", "");
            substring = substring.substring(0, substring.lastIndexOf(";"));
            //System.out.println("substring: " +substring);

            JsonWrapper jsonWrapper = objectMapper.readValue(substring, JsonWrapper.class);

            VideoDetails videoDetails = jsonWrapper.getVideoDetails();
            List<String> keywords = videoDetails.getKeywords();
            System.out.println(url + ": " + keywords);
        }
        // System.out.println("end: " + LocalDateTime.now());
    }
}
