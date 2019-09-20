package fr.nitorac.climodsupdator.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fr.nitorac.climodsupdator.CLIMApplication;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class LocalModpack {

    public static final String NAME = "name";
    public static final String GAME_VERSION = "gameVersion";
    public static final String MODS = "mods";

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String gameVersion;

    @Getter
    @Setter
    private List<LocalMod> mods;

    private File config;

    public LocalModpack(File config) throws FileNotFoundException {
        this.config = config;
        readConf();
    }

    public void readConf() throws FileNotFoundException {
        if (!config.exists()) {
            throw new FileNotFoundException("File modpack.json doesn't exist, you have to create it first ! (" + config.getAbsolutePath() + ")");
        } else {
            JsonObject root = new JsonParser().parse(new FileReader(config)).getAsJsonObject();
            name = root.get(NAME).getAsString();
            gameVersion = root.get(GAME_VERSION).getAsString();
            mods = CLIMApplication.gson.fromJson(root.get(MODS).getAsJsonArray(), new TypeToken<List<LocalMod>>() {
            }.getType());
        }
    }
}
