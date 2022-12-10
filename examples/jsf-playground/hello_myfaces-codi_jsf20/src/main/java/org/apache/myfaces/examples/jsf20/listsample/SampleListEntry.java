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
package org.apache.myfaces.examples.jsf20.listsample;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.apache.myfaces.extensions.cdi.core.api.logging.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * backing bean which holds one single SampleListEntry
 */
@ViewAccessScoped
@Named
public class SampleListEntry implements Serializable
{
    private static final long serialVersionUID = 723390662860977802L;

    @Inject
    private Logger logger;

    private Integer id;
    private String value;

    /**
     * This just logs a INFO for each bean creation
     */
    @PostConstruct
    public void init()
    {
        logger.info("SampleListEntry bean got created");
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String back()
    {
        return "sampleList";
    }

    public String reload()
    {
        return "sampleListEntry";
    }
}
