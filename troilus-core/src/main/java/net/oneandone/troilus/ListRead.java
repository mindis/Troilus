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
package net.oneandone.troilus;





/**
 * List read query
 *
 * @param <T> the result type
 */
public interface ListRead<T> extends SingleRead<T> {

    /**
     * @param limit thr max num of records to read 
     * @return a cloned query instance with deactivated tracking
     */
    ListRead<T> withLimit(int limit);

    /**
     * @param fetchSize the fetch size 
     * @return a cloned query instance with deactivated tracking
     */
    ListRead<T> withFetchSize(int fetchSize);

    /**
     * @return a cloned query instance with distinct
     */
    ListRead<T> withDistinct();

    /**
     * @return a cloned query instance which allows filtering
     */
    ListRead<T> withAllowFiltering();
}