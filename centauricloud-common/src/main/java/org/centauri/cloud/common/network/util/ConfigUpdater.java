package org.centauri.cloud.common.network.util;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ConfigUpdater {

    @Getter private static ConfigUpdater instance;

    public ConfigUpdater() {
        instance = this;
    }

    public void updateConfig(File currentfile, String defaultfilepath) {

        String split = checkFiletype(currentfile);
        if(split==null) {
            System.out.println("Invalid filetype to update configuration!");
            return;
        }

        try {

            List<String> defaultLines = new ArrayList<>();
            List<String> currentLines = new ArrayList<>();

            InputStream in = getClass().getResourceAsStream(defaultfilepath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            try {
                for (String line; (line = reader.readLine()) != null; ) {
                    defaultLines.add(line);
                }
            } finally {
                reader.close();
            }

            reader = new BufferedReader(new FileReader(currentfile));
            try {
                for (String line; (line = reader.readLine()) != null; ) {
                    currentLines.add(line);
                }
            } finally {
                reader.close();
            }

            for(int i = 0; i < defaultLines.size(); i++) {
                String defaultLine = defaultLines.get(i);
                String currentLine;
                try {
                    currentLine = currentLines.get(i);
                } catch (Exception e) {
                    currentLine = null;
                }
                if(currentLine != null) {
                    if(!currentLine.startsWith("#") && currentLine.contains("=")) {
                        String currentKey = currentLine.split("=")[0];
                        String defaultKey = defaultLine.split("=")[0];
                        if(!currentKey.equals(defaultKey)) {
                            currentLines.set(i, defaultLine);
                        }
                    } else {
                        currentLines.set(i, defaultLine);
                    }
                } else {
                    currentLines.add(defaultLine);
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(currentfile));
            try {
                for (String lineToWrite : currentLines) {
                    writer.write(lineToWrite + "\n");
                }
            } finally {
                writer.close();
            }

            System.out.println("Configuration updated!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String checkFiletype(File currentfile) {
        String s;
        switch(currentfile.getName().split("\\.")[currentfile.getName().split("\\.").length-1]) {
            case "properties":
                s="=";
                break;
            case "yml":
                s=":";
                break;
            default:
                s=null;
        }
        return s;
    }

}