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
package org.apache.myfaces.extensions.cdi.jsf.impl.navigation;

import org.apache.myfaces.extensions.cdi.jsf.impl.listener.request.CodiFacesContextFactory;
import static org.apache.myfaces.extensions.cdi.jsf.impl.util.ConversationUtils.storeCurrentViewIdAsOldViewId;
import static org.apache.myfaces.extensions.cdi.jsf.impl.util.ConversationUtils.storeCurrentViewIdAsNewViewId;
import org.apache.myfaces.extensions.cdi.jsf.impl.util.JsfUtils;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;

/**
 * {@link NavigationHandler} needed for the
 * {@link org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped}
 */
public class AccessScopeAwareNavigationHandler extends NavigationHandler
{
    private final NavigationHandler navigationHandler;

    /**
     * Constructor for wrapping the given {@link NavigationHandler}
     * @param navigationHandler navigation-handler which should be wrapped
     */
    public AccessScopeAwareNavigationHandler(NavigationHandler navigationHandler)
    {
        this.navigationHandler = navigationHandler;
    }

    /**
     * {@inheritDoc}
     */
    public void handleNavigation(FacesContext facesContext, String s, String s1)
    {
        //we have to reset it due to possible redirects
        JsfUtils.resetCaches();

        //TODO check myfaces core - issue? facesContext is not wrapped here
        facesContext = wrapFacesContext(facesContext);

        storeCurrentViewIdAsOldViewId(facesContext); //don't change the order

        this.navigationHandler.handleNavigation(facesContext, s, s1);

        storeCurrentViewIdAsNewViewId(facesContext);
    }

    protected FacesContext wrapFacesContext(FacesContext facesContext)
    {
        return CodiFacesContextFactory.wrapFacesContext(facesContext);
    }
}
