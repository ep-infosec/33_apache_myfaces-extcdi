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
package org.apache.myfaces.examples.codi.jsf12.security;

import org.apache.myfaces.extensions.cdi.core.api.security.SecurityViolation;
import org.apache.myfaces.extensions.cdi.core.api.security.AbstractAccessDecisionVoter;
import org.apache.myfaces.extensions.cdi.message.api.MessageContext;
import org.apache.myfaces.extensions.cdi.jsf.api.Jsf;

import javax.interceptor.InvocationContext;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Set;

/**
 * AccessDecisionVoter which throws a violation in any case
 */
@ApplicationScoped
public class ForcedViolationVoter extends AbstractAccessDecisionVoter
{
    private static final long serialVersionUID = -3321616879108078874L;

    @Inject
    @Jsf
    private MessageContext messageContext;

    public void checkPermission(InvocationContext invocationContext, Set<SecurityViolation> violations)
    {
        String reason = this.messageContext.message().text("{inactive_user_violation}").toText();
        violations.add(newSecurityViolation(reason));
    }
}
