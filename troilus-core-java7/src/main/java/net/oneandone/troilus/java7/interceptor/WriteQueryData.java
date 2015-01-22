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
package net.oneandone.troilus.java7.interceptor;


import com.datastax.driver.core.querybuilder.Clause;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;


 
/**
 * Write query data
 *
 */
public interface WriteQueryData {

    /**
     * @param keys  the keys
     * @return the new write query data
     */
    WriteQueryData keys(ImmutableMap<String, Object> keys);

    /**
     * @param whereConditions   the where conditions
     * @return the new write query data
     */
    WriteQueryData whereConditions(ImmutableList<Clause> whereConditions);

    /**
     * @param valuesToMutate  the values to mutate
     * @return the new write query data
     */
    WriteQueryData valuesToMutate(ImmutableMap<String, Optional<Object>> valuesToMutate);

    /**
     * @param setValuesToAdd   the set values to add
     * @return the new write query data
     */
    WriteQueryData setValuesToAdd(ImmutableMap<String, ImmutableSet<Object>> setValuesToAdd);
    
    /**
     * @param setValuesToRemove   the set values to remove
     * @return the new write query data
     */
    WriteQueryData setValuesToRemove(ImmutableMap<String, ImmutableSet<Object>> setValuesToRemove);

    /**
     * @param listValuesToAppend  the list values to append
     * @return the new write query data
     */
    WriteQueryData listValuesToAppend(ImmutableMap<String, ImmutableList<Object>> listValuesToAppend);

    /**
     * @param listValuesToPrepend  the list values to prepend
     * @return the new write query data
     */
    WriteQueryData listValuesToPrepend(ImmutableMap<String, ImmutableList<Object>> listValuesToPrepend);

    /**
     * @param listValuesToRemove the list values to remove
     * @return the new write query data
     */
    WriteQueryData listValuesToRemove(ImmutableMap<String, ImmutableList<Object>> listValuesToRemove);

    /**
     * @param mapValuesToMutate  the list values to mutate
     * @return the new write query data
     */
    WriteQueryData mapValuesToMutate(ImmutableMap<String, ImmutableMap<Object, Optional<Object>>> mapValuesToMutate);

    /**
     * @param onlyIfConditions  the onlyIf conditions
     * @return the new write query data
     */
    WriteQueryData onlyIfConditions(ImmutableList<Clause> onlyIfConditions);

    /**
     * @param ifNotExists  the ifNotExists flag
     * @return the new write query data
     */
    WriteQueryData ifNotExists(Boolean ifNotExists);

    /**
     * @return  the keys
     */
    ImmutableMap<String, Object> getKeys();

    /**
     * @return  the where condtions
     */
    ImmutableList<Clause> getWhereConditions();

    /**
     * @return the values to mutate
     */
    ImmutableMap<String, Optional<Object>> getValuesToMutate();

    /**
     * @return the set values to add
     */
    ImmutableMap<String, ImmutableSet<Object>> getSetValuesToAdd();

    /**
     * @return the set values to remove
     */
    ImmutableMap<String, ImmutableSet<Object>> getSetValuesToRemove();

    /**
     * @return the list values to append
     */
    ImmutableMap<String, ImmutableList<Object>> getListValuesToAppend();

    /**
     * @return  the list values to prepend
     */
    ImmutableMap<String, ImmutableList<Object>> getListValuesToPrepend();

    /**
     * @return the list values to remove
     */
    ImmutableMap<String, ImmutableList<Object>> getListValuesToRemove();

    /**
     * @return the map values to mutate
     */
    ImmutableMap<String, ImmutableMap<Object, Optional<Object>>> getMapValuesToMutate();

    /**
     * @return the onyIf conditions
     */
    ImmutableList<Clause> getOnlyIfConditions();

    /**
     * @return the ifNotExists flag
     */
    Boolean getIfNotExits();
}