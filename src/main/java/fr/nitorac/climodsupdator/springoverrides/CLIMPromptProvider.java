package fr.nitorac.climodsupdator.springoverrides;

import fr.nitorac.climodsupdator.CLIMApplication;
import fr.nitorac.climodsupdator.utils.PromptColor;
import org.jline.utils.AttributedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CLIMPromptProvider implements PromptProvider {

    @Autowired
    Properties shellProperties;

    @Override
    public AttributedString getPrompt() {
        return new AttributedString(PromptColor.CYAN + "[CLIM" + (CLIMApplication.getStorageManager().isModpackLoaded() ? "" : ":" + PromptColor.GREEN + CLIMApplication.getStorageManager().getLoadedModpack().getName() + PromptColor.CYAN) + "] -> " + PromptColor.RESET);
    }
}
