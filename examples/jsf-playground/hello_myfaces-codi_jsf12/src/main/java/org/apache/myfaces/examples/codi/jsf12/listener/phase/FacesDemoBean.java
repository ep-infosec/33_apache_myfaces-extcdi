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
package org.apache.myfaces.examples.codi.jsf12.listener.phase;

import org.apache.myfaces.examples.codi.jsf12.view.DemoPages;
import org.apache.myfaces.examples.codi.jsf12.view.InvalidPage;
import org.apache.myfaces.extensions.cdi.core.api.config.view.View;
import org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.AfterPhase;
import org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.JsfPhaseId;
import org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.JsfLifecyclePhaseInformation;
import org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.BeforePhase;
import static org.apache.myfaces.extensions.cdi.jsf.api.listener.phase.JsfPhaseId.RESTORE_VIEW;
import org.apache.myfaces.extensions.cdi.jsf.api.request.RequestTypeResolver;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;
import javax.annotation.PostConstruct;

@Model
public class FacesDemoBean
{
    @Inject
    private FacesContext facesContext;

    @Inject
    private JsfLifecyclePhaseInformation phaseInformation;

    @Inject
    private ConfigDemoBean configDemoBean;

    @Inject
    private RequestTypeResolver requestTypeResolver;

    private String text;

    @PostConstruct
    public void triggerInternalDemos()
    {
        this.configDemoBean.isInitialRedirectDisable();
    }

    //no restriction via @View -> invoked before rendering any view
    public void preRenderView(@Observes @BeforePhase(JsfPhaseId.RENDER_RESPONSE) PhaseEvent event)
    {
        addGlobalMessage("preRenderView in phase:" + event.getPhaseId());

        this.text = "Hello MyFaces CODI";
    }

    @View(DemoPages.HelloMyFacesCodi.class)
    public void preInvokeApplication(@Observes @BeforePhase(JsfPhaseId.INVOKE_APPLICATION) PhaseEvent event)
    {
        addGlobalMessage("preInvokeApplication in phase:" + event.getPhaseId());
    }

    @View(InvalidPage.class)
    public void postRestoreViewInvalid(@Observes @AfterPhase(RESTORE_VIEW) PhaseEvent event)
    {
        addGlobalMessage("postRestoreViewInvalid in phase:" + event.getPhaseId());
    }

    @View({InvalidPage.class, DemoPages.HelloMyFacesCodi.class})
    public void postRestoreView(@Observes @AfterPhase(RESTORE_VIEW) PhaseEvent event)
    {
        addGlobalMessage("postRestoreView in phase:" + event.getPhaseId());
    }

    //no restriction via @View -> invoked for any view
    public void preAny(@Observes @BeforePhase(JsfPhaseId.ANY_PHASE) PhaseEvent event)
    {
        addGlobalMessage("preAny in phase:" + event.getPhaseId());
    }

    public void preAnyFiltered1(@Observes @BeforePhase(JsfPhaseId.ANY_PHASE) PhaseEvent event)
    {
        if (event.getPhaseId().equals(javax.faces.event.PhaseId.APPLY_REQUEST_VALUES) ||
                event.getPhaseId().equals(javax.faces.event.PhaseId.UPDATE_MODEL_VALUES))
        {
            addGlobalMessage("preAnyFiltered1 in phase:" + event.getPhaseId());
        }
    }

    public void preAnyFiltered2(@Observes @AfterPhase(JsfPhaseId.ANY_PHASE) PhaseEvent event)
    {
        if (this.phaseInformation.isProcessValidationsPhase() || this.phaseInformation.isUpdateModelValuesPhase())
        {
            addGlobalMessage("preAnyFiltered2 in phase:" + event.getPhaseId());
        }
    }

    public String getText()
    {
        return this.text;
    }

    private void addGlobalMessage(String messageText)
    {
        this.facesContext.addMessage(null, new FacesMessage(messageText));
    }
}
