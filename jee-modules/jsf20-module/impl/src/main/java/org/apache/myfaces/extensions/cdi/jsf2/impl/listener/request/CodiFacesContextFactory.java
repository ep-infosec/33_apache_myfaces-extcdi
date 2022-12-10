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
package org.apache.myfaces.extensions.cdi.jsf2.impl.listener.request;

import org.apache.myfaces.extensions.cdi.core.api.activation.Deactivatable;
import org.apache.myfaces.extensions.cdi.core.impl.util.ClassDeactivation;
import org.apache.myfaces.extensions.cdi.jsf.impl.util.JsfUtils;

import javax.faces.context.FacesContextFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;
import javax.faces.lifecycle.Lifecycle;

/**
 * keep in sync with org.apache.myfaces.extensions.cdi.jsf.impl.listener.request.CodiFacesContextFactory
 */
public class CodiFacesContextFactory extends FacesContextFactory implements Deactivatable
{
    private final FacesContextFactory wrappedFacesContextFactory;

    private final boolean deactivated;

    /**
     * Constructor for wrapping the given {@link FacesContextFactory}
     * @param wrappedFacesContextFactory wrapped faces-context-factory which should be used
     */
    public CodiFacesContextFactory(FacesContextFactory wrappedFacesContextFactory)
    {
        this.wrappedFacesContextFactory = wrappedFacesContextFactory;
        this.deactivated = !isActivated();
    }

    /**
     * Wrapps the created {@link FacesContext} with {@link CodiFacesContextWrapper}
     *
     * {@inheritDoc}
     */
    @Override
    public FacesContext getFacesContext(Object context,
                                        Object request,
                                        Object response,
                                        Lifecycle lifecycle)
    {
        FacesContext facesContext =
                this.wrappedFacesContextFactory.getFacesContext(context, request, response, lifecycle);

        if (facesContext == null)
        {
            return null;
        }

        if(this.deactivated)
        {
            return facesContext;
        }

        return new CodiFacesContextWrapper(facesContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FacesContextFactory getWrapped()
    {
        return wrappedFacesContextFactory;
    }

    /**
     * Allows to wrap the given {@link FacesContext} manually
     * @param facesContext current faces-context
     * @return the wrapped faces-context, or the given faces-context if it was wrapped already
     */
    public static FacesContext wrapFacesContext(FacesContext facesContext)
    {
        if(isWrappedAlreadyByCodi(facesContext))
        {
            return facesContext;
        }

        facesContext.getExternalContext().getRequestMap()
                .put(JsfUtils.FACES_CONTEXT_MANUAL_WRAPPER_KEY, Boolean.TRUE);

        //TODO has to be deactivatable
        return new CodiFacesContextWrapper(facesContext);
    }

    private static boolean isWrappedAlreadyByCodi(FacesContext facesContext)
    {
        if(facesContext instanceof FacesContextWrapper)
        {
            return containsCodiFacesContextWrapper((FacesContextWrapper) facesContext);
        }
        return false;
    }

    private static boolean containsCodiFacesContextWrapper(FacesContextWrapper facesContext)
    {
        if(facesContext == null)
        {
            return false;
        }

        if(facesContext instanceof CodiFacesContextWrapper)
        {
            return true;
        }

        if(facesContext.getWrapped() instanceof FacesContextWrapper)
        {
            return containsCodiFacesContextWrapper((FacesContextWrapper)facesContext.getWrapped());
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isActivated()
    {
        return ClassDeactivation.isClassActivated(getClass());
    }
}
