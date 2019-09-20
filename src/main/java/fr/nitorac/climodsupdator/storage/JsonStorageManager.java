package fr.nitorac.climodsupdator.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.nitorac.climodsupdator.models.LocalModpack;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class JsonStorageManager {

    public static final File OPTIONS = new File("options.json");
    public static JsonObject rootOptions = new JsonObject();

    public static final String WD = "workdir";

    private File loadedModpackFile;
    private LocalModpack loadedModpack;

    public JsonStorageManager(){
        try {
            if(!OPTIONS.exists()){
                Files.write(OPTIONS.toPath(), rootOptions.toString().getBytes(), StandardOpenOption.CREATE);
            }
            rootOptions = new JsonParser().parse(new FileReader(OPTIONS)).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalModpack load(File directory) throws FileNotFoundException {
        if (directory.isFile()) {
            directory = directory.getParentFile();
        }
        loadedModpackFile = new File(directory, "modpack.json");
        return loadedModpack = new LocalModpack(loadedModpackFile);
    }

    public LocalModpack getLoadedModpack() {
        return loadedModpack;
    }

    public boolean isModpackLoaded() {
        return getLoadedModpack() != null;
    }

    public JsonObject getOptions() {
        return rootOptions;
    }

    public void saveOptions() {
        try {
            FileWriter writer = new FileWriter(OPTIONS, false);
            writer.write(rootOptions.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
