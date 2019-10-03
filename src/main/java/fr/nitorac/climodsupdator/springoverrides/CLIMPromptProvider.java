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
        String suffix = "";
        if (CLIMApplication.getStorageManager().isModpackLoaded()) {
            suffix = ":" + PromptColor.GREEN + CLIMApplication.getActiveModpack().getName();
        }
        return new AttributedString(PromptColor.CYAN + "[CLIM" + suffix + PromptColor.CYAN + "] -> " + PromptColor.RESET);
    }
}
