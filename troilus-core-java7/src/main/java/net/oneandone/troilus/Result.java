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


import com.datastax.driver.core.ExecutionInfo;
import com.google.common.collect.ImmutableList;


/**
 * The query result
 */
public interface Result {

    /**
     * @return the execution info for the last query made for this ResultSet
     */
    ExecutionInfo getExecutionInfo();
    
    /**
     * @return a list of the execution info for all the queries made for this ResultSet
     */
    ImmutableList<ExecutionInfo> getAllExecutionInfo();
    
    /**
     * @return if the query was a conditional update, whether it was applied. true for other types of queries.
     */
    boolean wasApplied();
}



