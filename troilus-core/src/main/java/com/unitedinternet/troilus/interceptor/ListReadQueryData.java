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
package com.unitedinternet.troilus.interceptor;


import java.util.Optional;

import com.datastax.driver.core.querybuilder.Clause;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;



 
public class ListReadQueryData {

    final ImmutableSet<Clause> whereClauses;
    final Optional<ImmutableMap<String, Boolean>> columnsToFetch;
    final Optional<Integer> optionalLimit;
    final Optional<Boolean> optionalAllowFiltering;
    final Optional<Integer> optionalFetchSize;
    final Optional<Boolean> optionalDistinct;

    
    public ListReadQueryData() {
        this(ImmutableSet.of(), 
             Optional.empty(),
             Optional.empty(),
             Optional.empty(),
             Optional.empty(),
             Optional.empty());
    }

    
    private ListReadQueryData(ImmutableSet<Clause> whereClauses, 
                             Optional<ImmutableMap<String, Boolean>> columnsToFetch, 
                             Optional<Integer> optionalLimit, 
                             Optional<Boolean> optionalAllowFiltering,
                             Optional<Integer> optionalFetchSize,
                             Optional<Boolean> optionalDistinct) {
        this.whereClauses = whereClauses;
        this.columnsToFetch = columnsToFetch;
        this.optionalLimit = optionalLimit;
        this.optionalAllowFiltering = optionalAllowFiltering;
        this.optionalFetchSize = optionalFetchSize;
        this.optionalDistinct = optionalDistinct;
    }
    

    
    public ListReadQueryData whereClauses(ImmutableSet<Clause> whereClauses) {
        return new ListReadQueryData(whereClauses,
                                     this.columnsToFetch,
                                     this.optionalLimit,
                                     this.optionalAllowFiltering,
                                     this.optionalFetchSize,
                                     this.optionalDistinct);  
    }

    
    public ListReadQueryData columnsToFetch(Optional<ImmutableMap<String, Boolean>> columnsToFetch) {
        return new ListReadQueryData(this.whereClauses,
                                     columnsToFetch,
                                     this.optionalLimit,
                                     this.optionalAllowFiltering,
                                     this.optionalFetchSize,
                                     this.optionalDistinct);  
    }

    
    public ListReadQueryData limit(Optional<Integer> optionalLimit) {
        return new ListReadQueryData(this.whereClauses,
                                     this.columnsToFetch,
                                     optionalLimit,
                                     this.optionalAllowFiltering,
                                     this.optionalFetchSize,
                                     this.optionalDistinct);  
    }

    
    public ListReadQueryData allowFiltering(Optional<Boolean> optionalAllowFiltering) {
        return new ListReadQueryData(this.whereClauses,
                                     this.columnsToFetch,
                                     this.optionalLimit,
                                     optionalAllowFiltering,
                                     this.optionalFetchSize,
                                     this.optionalDistinct);  
    }

    
    public ListReadQueryData fetchSize(Optional<Integer> optionalFetchSize) {
        return new ListReadQueryData(this.whereClauses,
                                     this.columnsToFetch,
                                     this.optionalLimit,
                                     this.optionalAllowFiltering,
                                     optionalFetchSize,
                                     this.optionalDistinct);  
    }

    
    public ListReadQueryData distinct(Optional<Boolean> optionalDistinct) {
        return new ListReadQueryData(this.whereClauses,
                                     this.columnsToFetch,
                                     this.optionalLimit,
                                     this.optionalAllowFiltering,
                                     this.optionalFetchSize,
                                     optionalDistinct);  
    }
    
    
    public ImmutableSet<Clause> getWhereClauses() {
        return whereClauses;
    }

    public Optional<ImmutableMap<String, Boolean>> getColumnsToFetch() {
        return columnsToFetch;
    }

    public Optional<Integer> getLimit() {
        return optionalLimit;
    }

    public Optional<Boolean> getAllowFiltering() {
        return optionalAllowFiltering;
    }

    public Optional<Integer> getFetchSize() {
        return optionalFetchSize;
    }

    public Optional<Boolean> getDistinct() {
        return optionalDistinct;
    }
}