import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.Date;


public class MainClass {

    public static void main(String[] args) {
        readAndWritePriceData();
    }

    public static void readAndWritePriceData() {

        PropertyHelper helper = PropertyHelper.getInstance();

        try {
            URL url = new URL(helper.getProperty("url"));
            URLConnection conn = url.openConnection();
            conn.addRequestProperty("User-Agent", helper.getProperty("browser"));
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while (in.readLine() != null){
                String line = in.readLine();
                if (line.contains(helper.getProperty("tokenone")) && line.contains(helper.getProperty("tokentwo"))) {

                    int valueStart= Integer.parseInt(helper.getProperty("valuestart"));
                    int valueEnd = Integer.parseInt(helper.getProperty("valueend"));

                    String price = line.substring(valueStart, line.length() - valueEnd);

                    Timestamp timeStamp = new Timestamp(new Date().getTime());
                    String timeStampStr = timeStamp.toString().substring(0, timeStamp.toString().length()-4);

                    System.out.println("One " + helper.getProperty("crypto") +
                            " equals " + price +  " " + helper.getProperty("currency") +
                            " (" + timeStampStr + ").");

                    System.out.println("Timezone: " + helper.getProperty("timezone"));
                    System.out.println("encoding: " + helper.getProperty("encoding"));

                    File file = new File(helper.getProperty("output"));
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    BufferedWriter bw =  new BufferedWriter(new FileWriter(file));

                    bw.write("One " + helper.getProperty("crypto") +
                            " equals " + price +  " " + helper.getProperty("currency") +
                            " (" + timeStampStr + ").");
                    System.out.println("File written...");

                    bw.close();
                }
            }
            in.close();

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
