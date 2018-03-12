package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class PSVParser {

    public PSVParser() {
        events = new ArrayList<>();
    }

    public void readData() {
        int i = 0;

        try {

            File f = new File("DLSU Unicalendar.psv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line[];
            String curLine = br.readLine();

            while(curLine != null) {

                line = curLine.split("\n");

                for (String item:line)  {
                    String[] elements = item.split("|");

                    StringBuilder temp = new StringBuilder();

                    while(i < item.length()) {
                        temp.append(elements[i]);
                        i++;
                    }

                    i = 0;
                    String event[] = temp.toString().split(Pattern.quote("|"));
                    String[] date = event[1].split("/");
                    String color = event[2].replaceAll("\\s", "");

                    for(int j = 0; j < date.length; j++)
                        date[j] = date[j].replaceAll("\\s+", "");

                    String[] startTime = event[3].split(":");
                    String[] endTime = event[4].split(":");


                    events.add(new Event(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                            event[0], color, Classification.toClassification("Event"), Integer.parseInt(startTime[0]),
                            Integer.parseInt(startTime[1]), Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1])));

                    curLine = br.readLine();

                }
            }
            br.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void appendEvent(Occurrence event) {

        String filename = "C:\\Users\\Rhei Justin Juan\\Desktop\\IntelliJ Projects\\DesignChallenge2\\DLSU Unicalendar.psv";

        try {
            FileWriter fw = new FileWriter(filename, true);

            String[] line = event.toString().split("-");

            String temp = "" + line[1] +
                    "|" +
                    line[0] +
                    "|" +
                    line[2] +
                    "|" +
                    line[4] +
                    "|" +
                    line[5];

            fw.write("\n" + temp );
            fw.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Occurrence> getEvents() {
        return events;
    }

    public void reWriteData(ArrayList<Occurrence> events) {

        String filename = "C:\\Users\\Rhei Justin Juan\\Desktop\\IntelliJ Projects\\DesignChallenge2\\DLSU Unicalendar.psv";

        try {
            FileWriter fw = new FileWriter(filename);
            for (Occurrence event : events) {
                String[] line = event.toString().split("-");

                StringBuilder temp = new StringBuilder("");

                for (String aLine : line) {
                    temp.append(aLine);
                    temp.append("|");
                }

                fw.write(temp.toString() + "\n");
            }

            fw.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Occurrence> events;
}
