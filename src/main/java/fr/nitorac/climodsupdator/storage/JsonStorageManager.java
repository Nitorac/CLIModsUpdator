package fr.nitorac.climodsupdator.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.nitorac.climodsupdator.models.Modpack;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class JsonStorageManager {

    public static final String MODPACK_NAME = "clim_modpack.json";

    public static final File OPTIONS = new File("options.json");
    public static JsonObject rootOptions = new JsonObject();

    public static final String AUTOLOAD_KEY = "autoload";
    public static final String AUTOLOAD_STORE_KEY = "autoload-store";

    @Autowired
    public ShellHelper shellHelper;

    private File loadedModpackFile;
    private Modpack loadedModpack;

    public JsonStorageManager(){
        try {
            if(!OPTIONS.exists()){
                Files.write(OPTIONS.toPath(), rootOptions.toString().getBytes(), StandardOpenOption.CREATE);
            }
            rootOptions = new JsonParser().parse(new FileReader(OPTIONS)).getAsJsonObject();
            if (rootOptions.has(AUTOLOAD_KEY) && rootOptions.has(AUTOLOAD_STORE_KEY) && rootOptions.get(AUTOLOAD_KEY).getAsBoolean() && !rootOptions.get(AUTOLOAD_STORE_KEY).getAsString().trim().isEmpty()) {
                load(new File(rootOptions.get(AUTOLOAD_STORE_KEY).getAsString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void create(File directory, String name, String gameVersion) throws IOException {
        File modpackFile = new File(directory, MODPACK_NAME);
        JsonObject root = new JsonObject();
        root.addProperty(Modpack.NAME, name);
        root.addProperty(Modpack.GAME_VERSION, gameVersion);
        root.add(Modpack.MODS, new JsonArray());
        FileWriter wr = new FileWriter(modpackFile, false);
        wr.write(root.toString());
        wr.flush();
        wr.close();
        load(directory);
    }

    public void unload() {
        loadedModpackFile = null;
        loadedModpack = null;
    }

    public Modpack load(File directory) throws FileNotFoundException {
        if (directory.isFile()) {
            directory = directory.getParentFile();
        }
        loadedModpackFile = new File(directory, MODPACK_NAME);
        getOptions().addProperty(JsonStorageManager.AUTOLOAD_STORE_KEY, directory.getAbsolutePath());
        saveOptions();
        return (loadedModpack = new Modpack(loadedModpackFile, shellHelper));
    }

    public Modpack getLoadedModpack() {
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
