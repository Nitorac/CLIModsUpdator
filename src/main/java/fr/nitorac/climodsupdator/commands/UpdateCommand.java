package fr.nitorac.climodsupdator.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.models.LocalMod;
import fr.nitorac.climodsupdator.models.RemoteMod;
import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@ShellComponent
public class UpdateCommand {
    @Autowired
    ShellHelper shellHelper;

    @Autowired
    @Lazy
    InputReader inputReader;

    @Autowired
    ProgressBar progressBar;

    @Autowired
    ProgressCounter progressCounter;

    private List<RemoteMod> projects;
    private List<LocalMod> localMods;

    public UpdateCommand(){
        projects = new ArrayList<>();
        localMods = new ArrayList<>();
    }

    @ShellMethod("Fetch mods list from CurseForge")
    public String update() {
        try {
            projects = getRemoteMods();
            shellHelper.printSuccess(projects.size() + " mods successfully loaded from CurseForge!");
            localMods = getLocalMods();
            shellHelper.printSuccess(localMods.size() + " mods successfully loaded from local storage!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "YES";
    }

    public List<RemoteMod> getRemoteMods() throws IOException{
        String url = "https://addons-ecs.forgesvc.net/api/v2/addon/search?categoryId=0&gameId=432&gameVersion=1.12.2&index=0&pageSize=10000&searchFilter=&sectionId=6&sort=2";
        return CLIMApplication.gson.fromJson(new InputStreamReader(new URL(url).openStream()), new TypeToken<List<RemoteMod>>(){}.getType());
    }

    public List<LocalMod> getLocalMods(){
        List<String> errors = new ArrayList<>();
        List<LocalMod> mods = new ArrayList<>();
        Arrays.stream(CLIMApplication.WORKING_DIRECTORY.listFiles((dir, name) -> name.toLowerCase().endsWith("jar"))).forEach(jar -> {
            try (ZipFile zipFile = new ZipFile(jar)) {
                ZipEntry entry = zipFile.getEntry("mcmod.info");
                if(entry != null){
                    JsonElement elem = new JsonParser().parse(new InputStreamReader(zipFile.getInputStream(entry), StandardCharsets.UTF_8));
                    mods.add(CLIMApplication.gson.fromJson(elem.isJsonArray() ? elem.getAsJsonArray().get(0) : elem.getAsJsonObject().getAsJsonArray("modList").get(0), LocalMod.class).setFile(jar));
                }else{
                    errors.add(jar.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        shellHelper.printWarning("Failed to parse mods " + Arrays.toString(errors.toArray()) + "!");
        return mods;
    }
}