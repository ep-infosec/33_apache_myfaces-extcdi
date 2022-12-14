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

import org.apache.myfaces.extensions.cdi.message.api.AbstractMessageWithSeverity;
import org.apache.myfaces.extensions.cdi.message.api.Message;
import org.apache.myfaces.extensions.cdi.message.api.MessageContext;
import org.apache.myfaces.extensions.cdi.message.api.NamedArgument;
import org.apache.myfaces.extensions.cdi.message.api.payload.MessagePayload;
import org.apache.myfaces.extensions.cdi.message.api.payload.MessageSeverity;

import java.io.Serializable;
import java.util.Set;

/**
 * {@inheritDoc}
 */
public class DefaultMessage extends AbstractMessageWithSeverity
{
    private static final long serialVersionUID = -6466664764243947830L;

    public DefaultMessage(Message message)
    {
        super(message);
    }

    public DefaultMessage(String messageDescriptor)
    {
        super(messageDescriptor, MessageSeverity.INFO);
    }

    public DefaultMessage(String messageDescriptor, Set<NamedArgument> namedArguments)
    {
        super(messageDescriptor, MessageSeverity.INFO, namedArguments);
    }

    public DefaultMessage(String messageDescriptor,
                          MessagePayload severity,
                          Serializable... arguments)
    {
        super(messageDescriptor, severity, arguments);
    }

    public DefaultMessage(String messageDescriptor,
                          MessagePayload severity,
                          Set<NamedArgument> namedArguments)
    {
        super(messageDescriptor, severity, namedArguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        if (getMessageContextConfig() != null)
        {
            return toString(new DefaultMessageContext(getMessageContextConfig()));
        }
        return toString(new DefaultMessageContext());
    }

    /**
     * {@inheritDoc}
     */
    public String toString(MessageContext messageContext)
    {
        return messageContext.message()
                .text(getDescriptor())
                .argument(getArguments())
                .payload(getMessagePayload())
                .toText();
    }
}
