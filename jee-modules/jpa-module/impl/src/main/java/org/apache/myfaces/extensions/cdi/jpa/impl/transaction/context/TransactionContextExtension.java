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
package org.apache.myfaces.extensions.cdi.jpa.impl.transaction.context;

import org.apache.myfaces.extensions.cdi.core.api.activation.Deactivatable;
import org.apache.myfaces.extensions.cdi.core.impl.util.ClassDeactivation;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;

/**
 * CDI Extension which registers and manages the {@link TransactionContext}.
 *
 */
public class TransactionContextExtension implements Extension, Deactivatable
{
    /**
     * Register the TransactionContext as a CDI Context
     * @param afterBeanDiscovery after-bean-discovery event
     * @param beanManager current bean-manager
     */
    public void registerTransactionContext(@Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager)
    {
        if(!isActivated())
        {
            return;
        }

        TransactionContext transactionContext = new TransactionContext();
        afterBeanDiscovery.addContext(transactionContext);
    }

    public boolean isActivated()
    {
        return ClassDeactivation.isClassActivated(getClass());
    }
}
