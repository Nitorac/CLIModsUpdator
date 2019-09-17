package fr.nitorac.climodsupdator.commands;

import com.google.gson.reflect.TypeToken;
import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.models.RemoteMod;
import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public UpdateCommand(){
        projects = new ArrayList<>();
    }

    @ShellMethod("Fetch mods list from CurseForge")
    public String update() {
        String url = "https://addons-ecs.forgesvc.net/api/v2/addon/search?categoryId=0&gameId=432&gameVersion=1.12.2&index=0&pageSize=10000&searchFilter=&sectionId=6&sort=2";
        try {
            projects = CLIMApplication.gson.fromJson(new InputStreamReader(new URL(url).openStream()), new TypeToken<List<RemoteMod>>(){}.getType());
            System.out.println("Test");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "YES";
    }
}