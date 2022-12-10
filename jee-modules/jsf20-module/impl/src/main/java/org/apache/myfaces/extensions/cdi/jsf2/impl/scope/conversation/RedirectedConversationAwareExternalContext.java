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
package org.apache.myfaces.extensions.cdi.jsf2.impl.scope.conversation;

import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.config.WindowContextConfig;
import org.apache.myfaces.extensions.cdi.core.impl.util.CodiUtils;
import org.apache.myfaces.extensions.cdi.jsf.impl.scope.conversation.spi.WindowHandler;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;
import java.io.IOException;

import static org.apache.myfaces.extensions.cdi.jsf.impl.util.ConversationUtils.getWindowHandler;
import static org.apache.myfaces.extensions.cdi.jsf.impl.util.ConversationUtils.sendRedirect;

/**
 * keep in sync with
 * org.apache.myfaces.extensions.cdi.jsf.impl.scope.conversation.RedirectedConversationAwareExternalContext
 */
public class RedirectedConversationAwareExternalContext extends ExternalContextWrapper
{
    private final ExternalContext wrapped;

    private WindowHandler windowHandler;

    private boolean encodeActionURLs;

    private boolean sessionInvalidated = false;

    /**
     * Constructor for wrapping the given {@link ExternalContext}
     * @param wrapped external-context which should be wrapped
     */
    public RedirectedConversationAwareExternalContext(ExternalContext wrapped)
    {
        this.wrapped = wrapped;
    }

    /**
     * {@inheritDoc}
     */
    public ExternalContext getWrapped()
    {
        return this.wrapped;
    }

    /**
     * Adds the current window-id to the URL (if permitted)
     * {@inheritDoc}
     */
    public String encodeActionURL(String s)
    {
        lazyInit();

        if(this.encodeActionURLs)
        {
            String url = addWindowIdToUrl(s);
            return this.wrapped.encodeActionURL(url);
        }
        return this.wrapped.encodeActionURL(s);
    }

    /**
     * Triggers a redirect which is aware of the current window and preserves the
     * {@link javax.faces.application.FacesMessage}s.
     * {@inheritDoc}
     */
    @Override
    public void redirect(String url)
            throws IOException
    {
        if(this.sessionInvalidated)
        {
            this.wrapped.redirect(url);
        }
        else
        {
            lazyInit();
            sendRedirect(this.wrapped, url, this.windowHandler);
        }
    }

    private void lazyInit()
    {
        if(this.windowHandler == null)
        {
            this.windowHandler = getWindowHandler();

            this.encodeActionURLs = CodiUtils
                    .getContextualReferenceByClass(WindowContextConfig.class)
                    .isAddWindowIdToActionUrlsEnabled();
        }
    }

    private String addWindowIdToUrl(String url)
    {
        return this.windowHandler.encodeURL(url);
    }

    @Override
    public void invalidateSession()
    {
        super.invalidateSession();
        this.sessionInvalidated = true;
    }
}
