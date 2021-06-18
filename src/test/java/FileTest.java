import org.example.presentation.App;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTest {

    @Test
    public void dirTest(){
        try {
            String s = FileTest.class.getResource("org/example/gfx/saved/").toURI().toString();
            String[] sA = s.split(":");
            String path = sA[1] + ":" +  sA[2];
            File f = new File(path);
            File[] fA = f.listFiles();
            for (File file : fA){
                System.out.println(file.getName());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void tcpTest() throws IOException {
        //String s_url = "https://csgostash.com/stickers/capsule/313/2020-RMR-Contenders";
        String s_url = "https://csgostash.com/stickers/capsule/312/2020-RMR-Challengers";
        URL url = new URL(s_url);
        Scanner input = new Scanner(url.openStream());
        StringBuffer result = new StringBuffer();

        Pattern pricePattern = Pattern.compile("<span class=\"pull-right\">([0-9,]+)(.)</span></a>");
        Pattern stopPattern = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");
        Matcher m;
        //Skip to relevant
        while (input.hasNext()){
            m = stopPattern.matcher(input.nextLine());
            if (m.find()) result.append(input.nextLine());
            //if (input.nextLine().equals("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png?id=4726663ef591c414c8c6\" alt=\"BitSkins Logo\">BitSkins</span>")){
            //    result.append(input.nextLine());
            //}
        }
        input.close();
        Matcher matcher = pricePattern.matcher(result);
        if (matcher.find()) {
            double d = Double.parseDouble(matcher.group(1).replace(",", "."));
            char c = matcher.group(2).toCharArray()[0];
            System.out.println("price: " + d + c);
        }
    }

    @Test
    public void regexTest(){
        Pattern stopPattern = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");
        String test = "<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png?id=4726663ef591c414c8c6\" alt=\"BitSkins Logo\">BitSkins</span>";
        Matcher m = stopPattern.matcher(test);
        System.out.println(m.find());
    }

    @Test
    public void browserTest(){
        App.getInstance().getHostServices().showDocument("www.google.com");
    }
}
