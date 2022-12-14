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
package org.apache.myfaces.extensions.cdi.message.test;

import org.apache.myfaces.extensions.cdi.message.api.MessageContext;
import org.apache.myfaces.extensions.cdi.message.api.MessageResolver;
import org.apache.myfaces.extensions.cdi.message.api.payload.MessagePayload;
import org.apache.myfaces.extensions.cdi.message.api.payload.MessageSeverity;

import java.util.Map;

/**
 * {@inheritDoc}
 */
class TestPayloadAwareMessageResolver implements MessageResolver
{
    private static final long serialVersionUID = -8103367805263154062L;
    private boolean isPayloadAvailable = false;

    public String getMessage(MessageContext messageContext,
                             String key,
                             Map<Class, MessagePayload> payload)
    {
        if(MessageSeverity.WARN.equals(payload.get(MessageSeverity.class)))
        {
            isPayloadAvailable = true;
        }
        return key;
    }

    public boolean isPayloadAvailable()
    {
        return isPayloadAvailable;
    }
}
