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



import java.time.Duration;

import net.oneandone.troilus.Context;
import net.oneandone.troilus.DeleteQuery;

import com.datastax.driver.core.querybuilder.Clause;





/**
 * Java8 adapter of a DeleteQuery
 */
class DeleteQueryAdapter extends MutationQueryAdapter<Deletion, DeleteQuery> implements Deletion {

    /**
     * @param ctx     the context
     * @param query   the query
     */
    DeleteQueryAdapter(Context ctx, DeleteQuery query) {
        super(ctx, query);
    }
    
    @Override
    protected Deletion newQuery(Context newContext) {
        return new DeleteQueryAdapter(newContext, getQuery().newQuery(newContext));
    }
    
    @Override
    public Deletion withTtl(Duration ttl) {
        return newQuery(getContext().withTtl((int) ttl.getSeconds()));
    }

    @Override
    public Deletion onlyIf(Clause... onlyIfConditions) {
        return new DeleteQueryAdapter(getContext(), getQuery().onlyIf(onlyIfConditions));
    }
    
    @Override
    public Deletion ifExists() {
        return new DeleteQueryAdapter(getContext(), getQuery().ifExists());
    }
}