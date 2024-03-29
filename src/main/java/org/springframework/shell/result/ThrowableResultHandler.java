/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.shell.result;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStringBuilder;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.CommandRegistry;
import org.springframework.shell.ResultHandler;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.util.StringUtils;

/**
 * A {@link ResultHandler} that prints thrown exceptions messages in red.
 *
 * <p>Also stores the last exception reported, so that details can be printed using a dedicated command.</p>
 *
 * @author Eric Bottard
 */
public class ThrowableResultHandler extends TerminalAwareResultHandler<Throwable> {

    /**
     * The name of the command that may be used to print details about the last error.
     */
    public static final String DETAILS_COMMAND_NAME = "stacktrace";

    private Throwable lastError;

    @Autowired
    @Lazy
    private CommandRegistry commandRegistry;

    @Autowired
    @Lazy
    private InteractiveShellApplicationRunner interactiveRunner;

    @Override
    protected void doHandleResult(Throwable result) {
        lastError = result;
        String toPrint = StringUtils.hasLength(result.getMessage()) ? result.getMessage() : result.toString();
        terminal.writer().println(new AttributedString(toPrint,
                AttributedStyle.DEFAULT.foreground(AttributedStyle.RED)).toAnsi());
        if (interactiveRunner.isEnabled() && commandRegistry.listCommands().containsKey(DETAILS_COMMAND_NAME)) {
            terminal.writer().println(
                    new AttributedStringBuilder()
                            .append("Les détails de l'erreur ont été masqués. Vous pouvez utiliser la commande ", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
                            .append(DETAILS_COMMAND_NAME, AttributedStyle.DEFAULT.foreground(AttributedStyle.RED).bold())
                            .append(" pour afficher l'erreur complète.", AttributedStyle.DEFAULT.foreground(AttributedStyle.RED))
                            .toAnsi()
            );
        }
        terminal.writer().flush();
        if (!interactiveRunner.isEnabled()) {
            if (result instanceof RuntimeException) {
                throw (RuntimeException) result;
            } else if (result instanceof Error) {
                throw (Error) result;
            } else {
                throw new RuntimeException(result);
            }
        }
    }

    /**
     * Return the last error that was dealt with by this result handler.
     */
    public Throwable getLastError() {
        return lastError;
    }
}
