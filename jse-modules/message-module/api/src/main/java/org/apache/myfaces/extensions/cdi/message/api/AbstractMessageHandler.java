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
package org.apache.myfaces.extensions.cdi.message.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Basic behaviour for message-handler implementations
 */
public abstract class AbstractMessageHandler implements MessageHandler
{
    private Set<MessageFilter> messageFilters = new CopyOnWriteArraySet<MessageFilter>();

    /**
     * {@inheritDoc}
     */
    public void addMessage(MessageContext messageContext, Message message)
    {
        if (isMessageAllowed(messageContext, message))
        {
            processMessage(messageContext, message);
        }
    }

    protected abstract void processMessage(MessageContext messageContext, Message message);

    /**
     * {@inheritDoc}
     */
    public void addMessageFilter(MessageFilter... messageFilters)
    {
        this.messageFilters.addAll(Arrays.asList(messageFilters));
    }

    /**
     * {@inheritDoc}
     */
    public Set<MessageFilter> getMessageFilters()
    {
        return Collections.unmodifiableSet(this.messageFilters);
    }

    private boolean isMessageAllowed(MessageContext messageContext, Message message)
    {
        for (MessageFilter messageFilter : this.messageFilters)
        {
            if (!messageFilter.processMessage(messageContext, message))
            {
                return false;
            }
        }
        return true;
    }
}
