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
package org.apache.myfaces.extensions.cdi.core.api.tools;

import org.apache.myfaces.extensions.cdi.core.api.activation.ExpressionActivated;
import org.apache.myfaces.extensions.cdi.core.api.interpreter.ExpressionInterpreter;

import javax.enterprise.inject.Typed;

/**
 * Base implementation for simple (property) expressions
 */
@Typed()
//TODO move to impl
public abstract class AbstractPropertyExpressionInterpreter implements ExpressionInterpreter<String, Boolean>
{
    /**
     * {@inheritDoc}
     */
    //TODO
    public final Boolean evaluate(String expressions)
    {
        boolean result = false;
        String[] foundExpressions = expressions.split(";");
        String configFileName = null;

        SimpleOperationEnum operation;
        for(String expression : foundExpressions)
        {
            result = false;
            if(expression.contains(SimpleOperationEnum.IS.getValue()))
            {
                operation = SimpleOperationEnum.IS;
            }
            else if(expression.contains(SimpleOperationEnum.NOT.getValue()))
            {
                operation = SimpleOperationEnum.NOT;
            }
            else if(expression.startsWith("configName:"))
            {
                configFileName = expression.split(":")[1];
                //TODO refactor it - current impl. ensures backward compatibility (see PropertyFileResolver)
                configFileName = configFileName.replace(".", "@");
                continue;
            }
            else
            {
                throw new IllegalStateException("expression: " + expression + " isn't supported by " +
                        ExpressionActivated.class.getName() +
                        " supported operations: " + SimpleOperationEnum.getOperations() + "separator: ';'");
            }

            String[] keyValue = expression.split(operation.getValue());

            String configuredValue = getConfiguredValue(keyValue[0]);

            configuredValue = configuredValue.trim();

            if("".equals(configuredValue))
            {
                //TODO refactor it - current impl. ensures backward compatibility (see PropertyFileResolver)
                String internalKey = keyValue[0] + "_";

                configuredValue = getConfiguredValue(configFileName + "." + internalKey);
                configuredValue = configuredValue.trim();
            }

            if(!"*".equals(keyValue[1]) && "".equals(configuredValue))
            {
                continue;
            }

            if("*".equals(keyValue[1]) && !"".equals(configuredValue))
            {
                result = true;
                continue;
            }

            if(SimpleOperationEnum.IS.equals(operation) && !keyValue[1].equalsIgnoreCase(configuredValue))
            {
                return false;
            }
            else if(SimpleOperationEnum.NOT.equals(operation) && keyValue[1].equalsIgnoreCase(configuredValue))
            {
                return false;
            }
            result = true;
        }

        return result;
    }

    protected abstract String getConfiguredValue(String key);
}
