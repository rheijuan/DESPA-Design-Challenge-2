package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class CSVParser implements DataParser{

    public CSVParser() {
        tasks = new ArrayList<>();
    }

    public void readData() {

        try {
            //Test file. Replace the file name to something more appropriate
            File f = new File("To Do List.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String curLine = br.readLine();

            while(curLine != null) {

                String event[] = curLine.split(",");
                String[] date = event[0].split("/");
                String color = event[2].replaceAll("\\s", "");
                String[] startTime = event[4].split(":");
                String[] endTime = event[5].split(":");

                tasks.add(new Task(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]),
                        event[1], color, Classification.toClassification("Task"), Integer.parseInt(startTime[0]), Integer.parseInt(startTime[1]),
                        Integer.parseInt(endTime[0]), Integer.parseInt(endTime[1])));
                curLine = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendEvent(Occurrence task) {
        String filename = "C:\\Users\\Rhei Justin Juan\\Desktop\\IntelliJ Projects\\DesignChallenge2\\To Do List.csv";

        try {
            FileWriter fw = new FileWriter(filename, true);

            String[] line = task.toString().split("-");

            StringBuilder temp = new StringBuilder("");

            for (String aLine : line) {
                temp.append(aLine);
                temp.append(",");
            }

            fw.write(temp.toString() + "\n");
            fw.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void reWriteData(ArrayList<Occurrence> events) {

        String filename = "C:\\Users\\Rhei Justin Juan\\Desktop\\IntelliJ Projects\\DesignChallenge2\\To Do List.csv";

        try {
            FileWriter fw = new FileWriter(filename);

            for (Occurrence event : events) {

                String[] line = event.toString().split("-");

                StringBuilder temp = new StringBuilder("");

                for (String aLine : line) {
                    temp.append(aLine);
                    temp.append(",");
                }

                fw.write(temp.toString() + "\n");
            }

            fw.close();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Occurrence> getEvents() {
        return tasks;
    }

    private ArrayList<Occurrence> tasks;
}
