import javafx.util.Pair;
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
    public void tcpTest2() throws IOException {
        String s_url = "https://csgostash.com/skin/424/UMP-45-Indigo";
        URL url = new URL(s_url);
        Scanner input = new Scanner(url.openStream());
        String result;
        boolean stat = false;
        boolean sov = false;
        Pair<Double[],Character[]> prices = new Pair<>(new Double[4], new Character[4]);
        int pIndex = 0;

        Pattern pricePattern = Pattern.compile("<span class=\"pull-right\">([0-9,-]+)(.)</span>");
        Pattern conPattern = Pattern.compile("<span class=\"pull-left\">Factory New</span>");
        Pattern stPattern = Pattern.compile("<span class=\"pull-left price-details-st\">StatTrak</span>");
        Pattern svPattern = Pattern.compile("<span class=\"pull-left price-details-souv\">Souvenir</span>");
        Matcher m;
        Matcher st;
        Matcher sv;
        //Skip to relevant
        while (input.hasNext()){
            if (stat){
                st = stPattern.matcher(input.nextLine());
                if (st.find()) {
                    m = conPattern.matcher(input.nextLine());
                    if (m.find()) {
                        result = input.nextLine();
                        Matcher matcher = pricePattern.matcher(result);
                        if (matcher.find()) {
                            double d = Double.parseDouble(matcher.group(1).replace(",", ".").replace("-", "0"));
                            char c = matcher.group(2).toCharArray()[0];
                            prices.getKey()[pIndex] = d;
                            prices.getValue()[pIndex] = c;
                        }
                        else {
                            prices.getKey()[pIndex] = 0.0;
                            prices.getValue()[pIndex] = '-';
                        }
                        pIndex++;
                    }
                }
            }
            else if (sov){
                sv = svPattern.matcher(input.nextLine());
                if (sv.find()) {
                    m = conPattern.matcher(input.nextLine());
                    if (m.find()) {
                        result = input.nextLine();
                        Matcher matcher = pricePattern.matcher(result);
                        if (matcher.find()) {
                            double d = Double.parseDouble(matcher.group(1).replace(",", ".").replace("-", "0"));
                            char c = matcher.group(2).toCharArray()[0];
                            prices.getKey()[pIndex] = d;
                            prices.getValue()[pIndex] = c;
                        }
                        else {
                            prices.getKey()[pIndex] = 0.0;
                            prices.getValue()[pIndex] = '-';
                        }
                        pIndex++;
                    }
                }
            }
            else{
                m = conPattern.matcher(input.nextLine());
                if (m.find()) {
                    result = input.nextLine();
                    Matcher matcher = pricePattern.matcher(result);
                    if (matcher.find()) {
                        double d = Double.parseDouble(matcher.group(1).replace(",", ".").replace("-", "0"));
                        char c = matcher.group(2).toCharArray()[0];
                        prices.getKey()[pIndex] = d;
                        prices.getValue()[pIndex] = c;
                    }
                    else {
                        prices.getKey()[pIndex] = 0.0;
                        prices.getValue()[pIndex] = '-';
                    }
                    pIndex++;
                }
            }
        }
        input.close();
        System.out.println("Steam:");
        System.out.println("Normal:\t" + prices.getKey()[0] + prices.getValue()[0]);
        System.out.println("StatTrack:\t" + prices.getKey()[1] + prices.getValue()[1]);
        System.out.println("BitSkins");
        System.out.println("Normal:\t" + prices.getKey()[2] + prices.getValue()[2]);
        System.out.println("StatTrack:\t" + prices.getKey()[3] + prices.getValue()[3]);
    }

    @Test
    public void regexTest(){
        Pattern stopPattern = Pattern.compile("<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png\\?id=[0-9a-zA-Z]+\" alt=\"BitSkins Logo\">BitSkins</span>");
        String test = "<span class=\"pull-left\"><img class=\"item-table-icon\" src=\"https://csgostash.com/img/core/bitskins.png?id=4726663ef591c414c8c6\" alt=\"BitSkins Logo\">BitSkins</span>";
        Matcher m = stopPattern.matcher(test);
        System.out.println(m.find());
    }
}
