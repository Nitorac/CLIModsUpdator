package fr.nitorac.climodsupdator.commands;

import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.storage.JsonStorageManager;
import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

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

    @ShellMethod(value = "Autoload", prefix = "autoload")
    public static void setAutoload(@ShellOption("True if modpack loaded at startup") String load) {
        CLIMApplication.getStorageManager().getOptions().addProperty(JsonStorageManager.AUTOLOAD_KEY, Boolean.parseBoolean(load));
        CLIMApplication.getStorageManager().saveOptions();
    }
}
