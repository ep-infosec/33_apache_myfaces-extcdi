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
package org.apache.myfaces.extensions.cdi.scripting.impl.language;

import org.apache.myfaces.extensions.cdi.scripting.api.language.Language;
import org.apache.myfaces.extensions.cdi.scripting.api.language.Groovy;

import javax.inject.Singleton;

/**
 * Type-safe language configuration
 */
@Singleton
public class GroovyLanguageBean extends AbstractLanguageBean
{
    private static final long serialVersionUID = -5606352201847472309L;

    protected GroovyLanguageBean()
    {
    }

    /**
     * {@inheritDoc}
     */
    public Class<? extends Language> getId()
    {
        return Groovy.class;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getAlternativeNames()
    {
        return new String[] {"Groovy", "groovy", "gr"};
    }
}