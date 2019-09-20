package fr.nitorac.climodsupdator.springoverrides;

import fr.nitorac.climodsupdator.utils.InputReader;
import fr.nitorac.climodsupdator.utils.PromptColor;
import fr.nitorac.climodsupdator.utils.ShellHelper;
import fr.nitorac.climodsupdator.widget.ProgressBar;
import fr.nitorac.climodsupdator.widget.ProgressCounter;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.JLineShellAutoConfiguration;

import java.util.Properties;

@Configuration
public class CLIMSpringShellConfig {
    @Bean
    Properties shellProperties() {
        return new Properties();
    }

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelper(terminal);
    }

    @Bean
    public ProgressCounter progressCounter(@Lazy Terminal terminal) {
        return new ProgressCounter(terminal);
    }

    @Bean
    public ProgressBar progressBar(@Lazy Terminal terminal) {
        return new ProgressBar(this.shellHelper(terminal));
    }

    @Bean
    public InputReader inputReader(
            @Lazy Terminal terminal,
            @Lazy Parser parser,
            JLineShellAutoConfiguration.CompleterAdapter completer,
            @Lazy History history,
            ShellHelper shellHelper
    ) {
        LineReaderBuilder lineReaderBuilder = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .history(history)
                .highlighter(
                        (LineReader reader, String buffer) -> {
                            return new AttributedString(
                                    buffer, AttributedStyle.BOLD.foreground(PromptColor.WHITE.toJlineAttributedStyle())
                            );
                        }
                ).parser(parser);

        LineReader lineReader = lineReaderBuilder.build();
        lineReader.unsetOpt(LineReader.Option.INSERT_TAB);
        return new InputReader(lineReader, shellHelper);
    }
}