package fr.nitorac.climodsupdator.commands;

import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

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

    @ShellMethod(value = "Load a modpack from a directory", prefix = "load")
    public void loadModpack() {
    }
}
