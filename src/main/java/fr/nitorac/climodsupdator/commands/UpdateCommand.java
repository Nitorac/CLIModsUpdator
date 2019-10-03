package fr.nitorac.climodsupdator.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.models.UnparsedMod;
import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

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

    private List<UnparsedMod> unparsedMods;

    public UpdateCommand(){
        unparsedMods = new ArrayList<>();
    }

    public Availability isAvailable() {
        return CLIMApplication.getStorageManager().isModpackLoaded() ? available() : unavailable("vous devez charger un modpack !");
    }

    @ShellMethod("Fetch mods list from CurseForge")
    @ShellMethodAvailability("isAvailable")
    public String update() {
        try {
            CLIMApplication.getActiveModpack().updateCache();
            shellHelper.printSuccess(CLIMApplication.getActiveModpack().getRemoteMods().size() + " mods successfully loaded from CurseForge!");
        } catch (IOException e) {
            e.printStackTrace();
            shellHelper.printError("Erreur : " + e.getMessage());
        }
        return "";
    }

    public List<UnparsedMod> getUnparsedLocalMods() {
        List<String> errors = new ArrayList<>();
        List<UnparsedMod> mods = new ArrayList<>();
        File[] files = CLIMApplication.getStorageManager().getLoadedModpack().getBaseDir().listFiles((dir, name) -> name.toLowerCase().endsWith("jar"));
        if (files == null || files.length == 0) {
            shellHelper.printError("No mod found in base folder : " + CLIMApplication.getStorageManager().getLoadedModpack().getBaseDir().getAbsolutePath());
            return mods;
        }
        Arrays.stream(files).forEach(jar -> {
            try (ZipFile zipFile = new ZipFile(jar)) {
                ZipEntry entry = zipFile.getEntry("mcmod.info");
                if(entry != null){
                    JsonElement elem = new JsonParser().parse(new InputStreamReader(zipFile.getInputStream(entry), StandardCharsets.UTF_8));
                    mods.add(CLIMApplication.gson.fromJson(elem.isJsonArray() ? elem.getAsJsonArray().get(0) : elem.getAsJsonObject().getAsJsonArray("modList").get(0), UnparsedMod.class).setFile(jar));
                }else{
                    errors.add(jar.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        shellHelper.printWarning("Failed to parse mods " + Arrays.toString(errors.toArray()) + "!");
        shellHelper.print("");
        errors.forEach(jar -> {
            String url = inputReader.prompt("Entrez l'URL pour le mod " + jar + " : ");
        });
        return mods;
    }
}