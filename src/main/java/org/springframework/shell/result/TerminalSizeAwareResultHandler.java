/*
 * Copyright 2017 the original author or authors.
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

import org.springframework.shell.TerminalSizeAware;

/**
 * A ResultHandler that prints {@link TerminalSizeAware} according to the {@link org.jline.terminal.Terminal} size.
 *
 * @author Eric Bottard
 */
public class TerminalSizeAwareResultHandler extends TerminalAwareResultHandler<TerminalSizeAware> {


    @Override
    protected void doHandleResult(TerminalSizeAware result) {
        CharSequence toPrint = result.render(terminal.getWidth());
        terminal.writer().println(toPrint);
    }
}
