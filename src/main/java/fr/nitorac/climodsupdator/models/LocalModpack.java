package fr.nitorac.climodsupdator.models;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class LocalModpack {

    public static final String GAME_VERSION = "gameVersion";
    public static final String MODS = "mods";

    private File modPackConf;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String gameVersion;

    @Getter
    @Setter
    private List<LocalMod> mods;

    public LocalModpack(String name, File modPackConf) {
        this.name = name;
        this.modPackConf = modPackConf;
    }

    public void readConf(ShellHelper helper) {
        try {
            if (!modPackConf.exists()) {
            } else {
                JsonObject root = new JsonParser().parse(new FileReader(modPackConf)).getAsJsonObject();
                gameVersion = root.get(GAME_VERSION).getAsString();
                mods = CLIMApplication.gson.fromJson(root.get(MODS).getAsJsonArray(), new TypeToken<List<LocalMod>>() {
                }.getType());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
