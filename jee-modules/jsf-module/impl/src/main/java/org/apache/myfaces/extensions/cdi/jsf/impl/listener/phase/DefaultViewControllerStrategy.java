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
package org.apache.myfaces.extensions.cdi.jsf.impl.listener.phase;

import org.apache.myfaces.extensions.cdi.jsf.impl.listener.phase.spi.ViewControllerStrategy;
import org.apache.myfaces.extensions.cdi.jsf.impl.config.view.ViewConfigCache;
import org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.BeforePhase;
import org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.AfterPhase;
import org.apache.myfaces.extensions.cdi.core.api.config.view.ViewConfig;
import org.apache.myfaces.extensions.cdi.core.api.config.view.View;

import javax.interceptor.InvocationContext;
import javax.faces.context.FacesContext;
import javax.enterprise.context.Dependent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Strategy which allows to restrict calls to phase-listener-methods
 */
@Dependent
public class DefaultViewControllerStrategy implements ViewControllerStrategy
{
    private static final long serialVersionUID = -4380864019324445036L;

    /**
     * {@inheritDoc}
     */
    public Object execute(InvocationContext invocationContext) throws Exception
    {
        Object result = null;

        if(invokeListenerMethod(invocationContext))
        {
            result = invocationContext.proceed();
        }

        return result;
    }

    private boolean invokeListenerMethod(InvocationContext invocationContext)
    {
        if(!isObserverMethod(invocationContext))
        {
            return true;
        }

        View view = getViewAnnotation(invocationContext);

        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if(view.inline().length > 1 || !"".equals(view.inline()[0]))
        {
            return isMethodBoundToView(view.inline(), viewId);
        }
        return isMethodBoundToViewDefinition(view.value(), viewId);
    }

    private boolean isObserverMethod(InvocationContext invocationContext)
    {
        for(Annotation[] annotations : invocationContext.getMethod().getParameterAnnotations())
        {
            for(Annotation annotation : annotations)
            {
                if(BeforePhase.class.isAssignableFrom(annotation.annotationType()) ||
                        AfterPhase.class.isAssignableFrom(annotation.annotationType()))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private View getViewAnnotation(InvocationContext invocationContext)
    {
        View view;
        Method method = invocationContext.getMethod();
        if(method.isAnnotationPresent(View.class))
        {
            view = method.getAnnotation(View.class);
        }
        else
        {
            view = method.getDeclaringClass().getAnnotation(View.class);
        }
        return view;
    }

    private boolean isMethodBoundToView(String[] viewIds, String viewId)
    {
        for(String current : viewIds)
        {
            if(current.equals(viewId))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isMethodBoundToViewDefinition (Class<? extends ViewConfig>[] viewDefinitions, String viewId)
    {
        for(Class<? extends ViewConfig> viewDefinition : viewDefinitions)
        {
            if(resolveViewId(viewDefinition).equals(viewId))
            {
                return true;
            }
        }
        return false;
    }

    private String resolveViewId(Class<? extends ViewConfig> viewDefinitionClass)
    {
        return ViewConfigCache.getViewConfigDescriptor(viewDefinitionClass).getViewId();
    }
}
