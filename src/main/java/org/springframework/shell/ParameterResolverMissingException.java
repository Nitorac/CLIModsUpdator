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
package org.springframework.shell;

import org.springframework.core.MethodParameter;

/**
 * Thrown when no {@link ParameterResolver} is found for a parameter during a {@link ParameterResolver#resolve}
 * operation.
 *
 * @author Camilo Gonzalez
 */
public class ParameterResolverMissingException extends RuntimeException {
    public ParameterResolverMissingException(MethodParameter parameter) {
        super(String.format("Aucun traiteur de paramètre n'a été trouvé pour le paramètre d'indexe %d (appelé '%s') sur %s ", parameter.getParameterIndex(), parameter.getParameterName(), parameter.getMethod()));
    }
}
