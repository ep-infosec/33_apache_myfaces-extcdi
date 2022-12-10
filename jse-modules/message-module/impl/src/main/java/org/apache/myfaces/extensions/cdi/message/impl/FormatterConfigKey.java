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
package org.apache.myfaces.extensions.cdi.message.impl;

import java.io.Serializable;
import java.util.Locale;

/**
 * Key for storing formatter-configs
 */
class FormatterConfigKey implements Serializable
{
    private static final long serialVersionUID = -6430653319283563370L;

    private Class type;
    private Locale locale;

    FormatterConfigKey(Class type, Locale locale)
    {
        this.type = type;
        this.locale = locale;
    }

    /*
    * generated
    */

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof FormatterConfigKey))
        {
            return false;
        }

        FormatterConfigKey that = (FormatterConfigKey) o;

        if (!locale.equals(that.locale))
        {
            return false;
        }
        //noinspection RedundantIfStatement
        if (!type.equals(that.type))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = type.hashCode();
        result = 31 * result + locale.hashCode();
        return result;
    }
}