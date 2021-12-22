package v1.html;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class  TestTest {

    public static void main(String[] args) {
        String input = "chillout music mix,ambient,chillout sessions,chillout music,chillstep playlist,best of chillstep,chillout mix, chill mix,chillstep mix,chillout,chill,chillstep,ambient mix,melodic dubstep,2020,chill mix 2020,chillout mix 2020,chill music 2020,best of chillstep 2020,best of chill 2020,chillstep mix 2020,Lovely Chill Mix,chill mix,chill music,relaxing music,music for sleeping,stress,Mind,piano music,sleep music,music for studying,Epic Chillstep Collection,Music For Work,ambient music,beats to relax,chill radio,ambient music mixchill your mind,work music,relex music,work,Chill Vibes,Vibes,study,background,background music,backbround music mix,background mix,mix,downtempo,future,garage,future garage,electronic,melodic,atmospheric,relaxing,chilled,deep,vibes,bass,Fluidified,Beautiful Chill Mix,beautiful,relaxation,stress relief,mindfulness,meditation,music,chill music mix,calm,my,mind,calm my mind,calm my mind beautiful chill music mix,calm my mind mix,calm my mind chill mix,calm my mind beautiful chill mix,beautiful chill music,fyze";


        String[] split = input.split(",");
        List<String> collect = Arrays.asList(split).stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
    }
}
