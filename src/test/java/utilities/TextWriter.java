package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextWriter {
    //txt dosyasına arama sonuçlarını yazdırmak için
    public void writeText(String text) {
        String date = new SimpleDateFormat("_hh_mm_ss_dd_MM_yy").format(new Date());

        String filePath = System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "test"
                + File.separator + "resources"
                + File.separator + "AddedProductInfo" + date + ".txt";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(text);
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("File write error: " + e.getMessage());
        }
    }
}
