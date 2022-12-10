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
package org.apache.myfaces.extensions.cdi.jsf2.impl.security;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.util.HashMap;
import java.util.Map;

/**
 * {@inheritDoc}
 */
public class SecurityAwareViewHandler
        extends org.apache.myfaces.extensions.cdi.jsf.impl.security.SecurityAwareViewHandler
{
    /**
     * {@inheritDoc}
     */
    public SecurityAwareViewHandler(ViewHandler wrapped)
    {
        super(wrapped);
    }

    /**
     * {@inheritDoc}
     *
     * see EXTCDI-167
     */
    @Override
    public UIViewRoot createView(FacesContext facesContext, String viewId)
    {
        UIViewRoot originalViewRoot = facesContext.getViewRoot();
        UIViewRoot newViewRoot;

        Map<String, Object> viewMap = null;
        if(originalViewRoot != null)
        {
            Map<String, Object> originalViewMap = originalViewRoot.getViewMap(false);

            if(originalViewMap != null && !originalViewMap.isEmpty())
            {
                viewMap = new HashMap<String, Object>();
                viewMap.putAll(originalViewMap);
            }
        }

        //workaround for PreDestroyViewMapEvent which would be caused by the security check
        deactivatePreDestroyViewMapEvent(facesContext);

        try
        {
            newViewRoot = super.createView(facesContext, viewId);
        }
        finally
        {
            activatePreDestroyViewMapEvent(facesContext);
        }

        if(viewMap != null)
        {
            originalViewRoot.getViewMap(true).putAll(viewMap);
        }
        return newViewRoot;
    }

    private void deactivatePreDestroyViewMapEvent(FacesContext facesContext)
    {
        facesContext.getExternalContext().getRequestMap().put(SecurityAwareViewHandler.class.getName(), Boolean.TRUE);
    }

    private void activatePreDestroyViewMapEvent(FacesContext facesContext)
    {
        facesContext.getExternalContext().getRequestMap().put(SecurityAwareViewHandler.class.getName(), Boolean.FALSE);
    }
}
