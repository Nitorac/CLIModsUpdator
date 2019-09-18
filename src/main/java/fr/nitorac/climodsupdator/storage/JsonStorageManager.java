package fr.nitorac.climodsupdator.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class JsonStorageManager {

    public static final File OPTIONS = new File("options.json");

    public static JsonObject root = new JsonObject();

    public static final String WD = "workdir";

    public JsonStorageManager(){
        try {
            if(!OPTIONS.exists()){
                Files.write(OPTIONS.toPath(), root.toString().getBytes(), StandardOpenOption.CREATE);
            }
            root = new JsonParser().parse(new FileReader(OPTIONS)).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonObject getRoot(){
        return root;
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(OPTIONS, false);
            writer.write(root.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
