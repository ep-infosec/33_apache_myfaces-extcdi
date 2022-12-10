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
package org.apache.myfaces.examples.jsf20.converter;

import org.apache.myfaces.extensions.cdi.core.api.Advanced;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * Dependency injection aware JSF converter
 */
@Advanced
@FacesConverter("pojoConverter")
public class PojoConverter implements Converter
{

    public PojoConverter()
    {
        // set breakpoint for instance creation debugging here
    }

    @Inject
    private PojoFactory pojoFactory;

    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) throws ConverterException
    {
        return pojoFactory.getPojo(s);
    }

    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) throws ConverterException
    {
        if (o instanceof Pojo)
        {
            Pojo pojo = (Pojo) o;

            return Integer.toString(pojo.getId());
        }
        else
        {
            return null;
        }
    }

}
