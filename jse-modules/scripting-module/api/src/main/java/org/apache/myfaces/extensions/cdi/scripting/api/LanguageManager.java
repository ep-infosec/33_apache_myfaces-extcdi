/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.extensions.cdi.scripting.api;

import org.apache.myfaces.extensions.cdi.scripting.api.language.Language;

import java.io.Serializable;

/**
 * A language manager returns the name for the script-engine for a given type-safe language key
 */
public interface LanguageManager extends Serializable
{
    /**
     * Calculates the script-language-name for the given {@link Language} definition.
     * The name will be used for the {@link javax.script.ScriptEngineManager#getEngineByName}
     * @param languageType language-type which will be mapped to the name of the language
     * @return name of the script-language
     */
    String getLanguageName(Class<? extends Language> languageType);
}
