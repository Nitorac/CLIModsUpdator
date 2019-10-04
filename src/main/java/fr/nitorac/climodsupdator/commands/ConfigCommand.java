package fr.nitorac.climodsupdator.commands;

import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.storage.JsonStorageManager;
import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static org.springframework.shell.Availability.available;
import static org.springframework.shell.Availability.unavailable;

@ShellComponent
public class ConfigCommand {
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

    @ShellMethod(value = "Définir l'autoload pour charger le modpack au démarrage", key = {"autoload"})
    public void setAutoload() {
        boolean autoload = CLIMApplication.getStorageManager().getOptions().has(JsonStorageManager.AUTOLOAD_KEY) && CLIMApplication.getStorageManager().getOptions().get(JsonStorageManager.AUTOLOAD_KEY).getAsBoolean();
        CLIMApplication.getStorageManager().getOptions().addProperty(JsonStorageManager.AUTOLOAD_KEY, !autoload);
        CLIMApplication.getStorageManager().saveOptions();
        shellHelper.printSuccess(!autoload ? "L'autoload est activé !" : "L'autoload est désactivé !");
    }
}
