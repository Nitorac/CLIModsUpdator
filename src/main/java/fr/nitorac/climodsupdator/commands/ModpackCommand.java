package fr.nitorac.climodsupdator.commands;

import fr.nitorac.climodsupdator.CLIMApplication;
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
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellComponent
public class ModpackCommand {
    @Autowired
    ShellHelper shellHelper;

    @Autowired
    @Lazy
    InputReader inputReader;

    @Autowired
    ProgressBar progressBar;

    @Autowired
    ProgressCounter progressCounter;

    public Availability isModpackLoaded() {
        return CLIMApplication.getStorageManager().isModpackLoaded() ? available() : unavailable("vous devez charger un modpack !");
    }

    @ShellMethod(value = "Crée un modpack dans le dossier spécifié", key = {"create", "create-modpack"})
    public void createModpack(@ShellOption("Le dossier 'mods' du modpack") String path) {
        try {
            String name = inputReader.prompt("Entrez le nom du modpack ", UUID.randomUUID().toString());
            String gameVersion = inputReader.prompt("Entrez la version Minecraft du modpack ", "1.12.2");
            CLIMApplication.getStorageManager().create(new File(path.replace("\\", "\\\\")), name, gameVersion);
            shellHelper.printSuccess("Modpack crée et chargé !");
        } catch (IOException e) {
            e.printStackTrace();
            shellHelper.printError("Impossible de charger le modpack ! (" + e.getMessage() + ")");
        }
    }

    @ShellMethodAvailability("isModpackLoaded")
    @ShellMethod(value = "Décharge le modpack chargé", key = {"unload", "unload-modpack"})
    public void unloadModpack() {
        CLIMApplication.getStorageManager().unload();
        shellHelper.printSuccess("Modpack déchargé !");
    }

    @ShellMethod(value = "Charge le modpack du dossier spécifié", key = {"load", "load-modpack"})
    public void loadModpack(@ShellOption("Le dossier 'mods' du modpack") String path) {
        try {
            CLIMApplication.getStorageManager().load(new File(path.replace("\\", "\\\\")));
            shellHelper.printSuccess("Modpack chargé !");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            shellHelper.printError("Impossible de charger le modpack !");
        }
    }
}
