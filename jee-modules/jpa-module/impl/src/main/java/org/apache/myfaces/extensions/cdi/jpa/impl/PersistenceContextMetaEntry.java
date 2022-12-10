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
package org.apache.myfaces.extensions.cdi.jpa.impl;

/**
 * Entry which stores information about a {@link javax.persistence.PersistenceContext}
 */
class PersistenceContextMetaEntry
{
    private Class sourceClass;

    private String fieldName;

    private String unitName;
    private boolean extended;

    PersistenceContextMetaEntry(Class sourceClass, String fieldName, String unitName, boolean extended)
    {
        this.sourceClass = sourceClass;
        this.fieldName = fieldName;
        this.unitName = unitName;
        this.extended = extended;
    }

    Class getSourceClass()
    {
        return sourceClass;
    }

    String getFieldName()
    {
        return fieldName;
    }

    String getUnitName()
    {
        return unitName;
    }

    boolean isExtended()
    {
        return extended;
    }
}
