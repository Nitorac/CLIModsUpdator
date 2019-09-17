package fr.nitorac.climodsupdator.springoverrides;

import fr.nitorac.climodsupdator.utils.PromptColor;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CLIMPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("[CLIM]-> ",
                AttributedStyle.DEFAULT.foreground(PromptColor.CYAN.toJlineAttributedStyle())
        );
    }
}
