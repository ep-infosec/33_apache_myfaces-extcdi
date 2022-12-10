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
package org.apache.myfaces.examples.codi.jsf12.conversation.grouped;

import org.apache.myfaces.extensions.cdi.core.api.logging.LoggerDetails;
import org.apache.myfaces.extensions.cdi.core.api.logging.Logger;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ConversationScoped;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ConversationGroup;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.WindowContext;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.Conversation;
import org.apache.myfaces.examples.codi.jsf12.view.DemoPages;
import org.apache.myfaces.extensions.cdi.jsf.api.Jsf;
import org.apache.myfaces.extensions.cdi.message.api.MessageContext;

import static org.apache.myfaces.examples.codi.jsf12.conversation.grouped.qualifier.QualifierInstances.qualifier3;

import javax.inject.Named;
import javax.inject.Inject;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;

/**
 * Grouped conversation scoped bean
 */
@Named
@ConversationScoped
@ConversationGroup(ConversationGroup1.class)
public class ConversationDemoBean1 implements Serializable
{
    private String value = "Hello grouped conversation1! ";
    private Date createdAt;
    private static final long serialVersionUID = -4238520498463300564L;

    @Inject
    private WindowContext windowContext;

    @Inject
    private Conversation conversation;

    @Inject
    private Logger logger1;

    @Inject
    @LoggerDetails(name = "logger2")
    private Logger logger2;

    @Inject
    private Logger.Factory loggerFactory;

    @Inject
    @Jsf
    private MessageContext messageContext;

    @PostConstruct
    public void init()
    {
        this.createdAt = new Date();
        String message = getClass().getName() + " created at " + this.createdAt;
        this.logger1.info(message);
        this.logger2.info(message);
        this.loggerFactory.getLogger("logger3").info(message);
    }

    public String next()
    {
        this.messageContext.message()
                .text("message preserved over a redirect")
                .add();
        return DemoPages.HelloMyFacesCodi1.class.getName();
    }

    public String next2()
    {
        return DemoPages.HelloMyFacesCodi2.class.getName();
    }

    public String next3()
    {
        return DemoPages.HelloMyFacesCodi3.class.getName();
    }

    public String endGroup1()
    {
        this.windowContext.closeConversationGroup(ConversationGroup1.class);
        return null;
    }

    public String endGroup1WithQualifiers()
    {
        //this.windowContext.closeConversation(ConversationGroup1.class, DefaultAnnotation.of(Qualifier3.class));
        this.windowContext.closeConversation(ConversationGroup1.class, qualifier3());
        return null;
    }

    public String endConversation()
    {
        //this.conversation.end();
        this.conversation.restart();
        return null;
    }

    public String getValue()
    {
        return value + createdAt.toLocaleString();
    }
}
