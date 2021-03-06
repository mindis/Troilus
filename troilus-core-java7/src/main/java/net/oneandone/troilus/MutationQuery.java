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




import com.datastax.driver.core.BatchStatement.Type;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;



/**
 * abstract mutation query implementation
 */
abstract class MutationQuery<Q> extends AbstractQuery<Q> implements Batchable {
    
    
    /**
     * @param ctx   the context
     */
    MutationQuery(Context ctx) {
        super(ctx);
    }
    
    public BatchMutationQuery combinedWith(Batchable other) {
        return new BatchMutationQuery(getContext(), Type.LOGGED, ImmutableList.of(this, other));
    }
  
    
    public Result execute() {
        return ListenableFutures.getUninterruptibly(executeAsync());
    }
    
    public abstract ListenableFuture<Result> executeAsync();
}