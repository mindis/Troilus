/*
 * Copyright 1&1 Internet AG, https://github.com/1and1/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.oneandone.troilus.java7;

import net.oneandone.troilus.Count;
import net.oneandone.troilus.EntityList;


/**
 * list read query 
 * 
 * @param <T>  the result type
 */
public interface ListReadWithUnit<T> extends ListReadWithColumns<T> {

    /**
     * @return  a cloned query instance which reads all columns
     */
    ListRead<T> all();

    /**
     * @return a cloned query instance which returns the count
     */
    ListRead<Count> count();

    /**
     * @param objectClass  the entity type
     * @return  a cloned query instance with the modified behavior 
     */
    <E> ListRead<EntityList<E>> asEntity(Class<E> objectClass);
} 