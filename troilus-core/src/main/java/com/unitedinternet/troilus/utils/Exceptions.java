/*
 * Copyright (c) 2014 1&1 Internet AG, Germany, http://www.1und1.de
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.unitedinternet.troilus.utils;

import java.util.concurrent.ExecutionException;



/**
 * Exception utils
 *
 * @author grro
 */
public class Exceptions {    
     
    
    public static RuntimeException unwrapIfNecessary(Throwable ex)  {
        return unwrapIfNecessary(ex, 5);
    }
        
    
    public static RuntimeException unwrapIfNecessary(Throwable ex, int maxDepth)  {
        
        if (ExecutionException.class.isAssignableFrom(ex.getClass())) {
            Throwable e = ((ExecutionException) ex).getCause();

            if (maxDepth > 1) {
               ex = unwrapIfNecessary(e, maxDepth - 1);
            }
        }
        
        if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;
        } else {
            throw new RuntimeException(ex);
        }
    }   
}


