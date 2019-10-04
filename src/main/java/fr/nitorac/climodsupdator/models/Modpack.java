package fr.nitorac.climodsupdator.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Modpack {

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
    private List<UnparsedMod> mods;

    @Getter
    @Setter
    private List<Mod> remoteMods;
    private File curseCache;

    private List<Mod> localMods;

    private File config;

    private ShellHelper shell;

    public Modpack(File config) throws FileNotFoundException {
        this(config, null);
    }

    public Modpack(File config, ShellHelper shell) throws FileNotFoundException {
        this.config = config;
        this.curseCache = new File(config.getParentFile(), "curse_cache.json");
        this.remoteMods = new ArrayList<>();
        this.localMods = new ArrayList<>();
        this.shell = shell;
        readConf();
    }

    public void loadCache() {
        loadCache(false);
    }

    private void loadCache(boolean isRetried) {
        remoteMods.clear();
        try {
            remoteMods.addAll(CLIMApplication.gson.fromJson(new FileReader(curseCache), new TypeToken<List<Mod>>() {
            }.getType()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonParseException ex) {
            try {
                updateCache();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!isRetried) {
                loadCache(true);
                return;
            }
            if (shell != null) {
                shell.printError("Impossible de mettre à jour le cache de Curse");
            }
        }
    }

    public void updateCache() throws IOException {
        JsonArray root = new JsonArray();
        String url = "https://addons-ecs.forgesvc.net/api/v2/addon/search?categoryId=0&gameId=432&gameVersion=" + CLIMApplication.getStorageManager().getLoadedModpack().getGameVersion() + "&index=0&pageSize=10000&searchFilter=&sectionId=6&sort=2";
        FileWriter writer = new FileWriter(curseCache, false);
        writer.write(CLIMApplication.gson.toJson(CLIMApplication.gson.fromJson(new InputStreamReader(new URL(url).openStream()), new TypeToken<List<Mod>>() {
        }.getType()), new TypeToken<List<Mod>>() {
        }.getType()));
        writer.flush();
        writer.close();
        loadCache();
    }

    public void readConf() throws FileNotFoundException {
        if (!config.exists()) {
            throw new FileNotFoundException("File modpack.json doesn't exist, you have to create it first ! (" + config.getAbsolutePath() + ")");
        } else {
            JsonObject root = new JsonParser().parse(new FileReader(config)).getAsJsonObject();
            name = root.get(NAME).getAsString();
            gameVersion = root.get(GAME_VERSION).getAsString();
            mods = CLIMApplication.gson.fromJson(root.get(MODS).getAsJsonArray(), new TypeToken<List<UnparsedMod>>() {
            }.getType());
        }
    }

    public File getBaseDir() {
        return config.getParentFile();
    }
}