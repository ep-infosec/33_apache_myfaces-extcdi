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
package org.apache.myfaces.extensions.cdi.bv.impl;

import javax.enterprise.inject.Typed;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import static org.apache.myfaces.extensions.cdi.core.impl.util.CodiUtils.injectFields;

/**
 * {@link ConstraintValidatorFactory} which adds support for dependency injection in {@link ConstraintValidator}
 */
//TODO review
@Typed()
class InjectionAwareConstraintValidatorFactory implements ConstraintValidatorFactory
{
    private ConstraintValidatorFactory wrapped;

    protected InjectionAwareConstraintValidatorFactory()
    {
    }

    InjectionAwareConstraintValidatorFactory(ConstraintValidatorFactory wrapped)
    {
        this.wrapped = wrapped;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"unchecked"})
    public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> targetClass)
    {
        T validator = this.wrapped.getInstance(targetClass);

        return injectFields(validator, false);
    }
}
