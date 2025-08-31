package com.autowaterer.autowatererbackend;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class MoistureLogger {
    private final File csvFile;
    private final String[] columns = {"Date", "Moisture"};
    public MoistureLogger(){
        this.csvFile = new File("./Moisture.csv");
        initFile();
    }

    public void initFile(){
        try {
            FileWriter writer = new FileWriter("Moisture.csv");
            writer.write(this.columns[0]);
            for(String col: Arrays.copyOfRange(this.columns, 1, columns.length)){
                writer.write("," + col);
            }
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void logMoisture(int moisture){
        try {
            LocalDateTime currTime = LocalDateTime.now();

            FileWriter writer = new FileWriter("Moisture.csv", true);
            writer.write(System.getProperty("line.separator"));
            writer.write(currTime.toString() + ", " + moisture);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
