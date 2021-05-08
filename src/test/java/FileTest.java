import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

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
}
