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





import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.ExecutionInfo;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.policies.RetryPolicy;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;


/**
 * Query implementation base
 *  
 * @param <Q> the query type
 */
abstract class AbstractQuery<Q> {
    
    private final Context ctx;
    
    
    /**
     * constructor
     * @param ctx  the context to use
     */
    AbstractQuery(Context ctx) {
        this.ctx = ctx;
    }

    
    /**
     * @param newContext  the new context
     * @return a "cloned" query considering the new context 
     */
    abstract protected Q newQuery(Context newContext);
    

    
    ////////////////////////
    // default implementations
  
  
    /**
     * @param consistencyLevel  the consistency level to use
     * @return a cloned query instance with the modified behavior
     */
    public Q withConsistency(ConsistencyLevel consistencyLevel) {
        return newQuery(ctx.withConsistency(consistencyLevel));
    }

    
    /**
     * @return a cloned query instance with activated tracking
     */
    public Q withTracking() {
        return newQuery(ctx.withEnableTracking());
    }
    

    /**
     * @return a cloned query instance with deactivated tracking 
     */
    public Q withoutTracking() {
        return newQuery(ctx.withDisableTracking());
    }
    
    /**
     * @param policy  the retry policy
     * @return a cloned query instance with the modified behavior
     */
    public Q withRetryPolicy(RetryPolicy policy) {
        return newQuery(ctx.withRetryPolicy(policy));
    }
    
    /**
     * @param microsSinceEpoch the writetime in since epoch to set
     * @return a cloned query instance with the modified behavior
     */
    public Q withWritetime(long writetimeMicrosSinceEpoch) {
        return newQuery(getContext().withWritetime(writetimeMicrosSinceEpoch));
    }
       
    /**
     * @param consistencyLevel  the consistency level to use
     * @return a cloned query instance with the modified behavior
    */
    public Q withSerialConsistency(ConsistencyLevel consistencyLevel) {
        return newQuery(getContext().withSerialConsistency(consistencyLevel));
    }

    

    
    
    ////////////////////////
    // utility methods
    
    /**
     * @return the context
     */
    protected Context getContext() {
        return ctx; 
    }
    
    
    /**
     * @param statementFuture  the statement to perform in an async way
     * @return the result future 
     */
    protected ListenableFuture<ResultSet> performAsync(ListenableFuture<Statement> statementFuture) {
        
        Function<Statement, ListenableFuture<ResultSet>> statementToResultSetFuture = new Function<Statement, ListenableFuture<ResultSet>>() {
            @Override
            public ListenableFuture<ResultSet> apply(Statement statement) {
                return performAsync(statement);
            }
        };
        
        return ListenableFutures.transform(statementFuture, statementToResultSetFuture, MoreExecutors.directExecutor());
    }
        
    
    /**
     * @param statementFuture  the statement to perform in a sync way
     * @return the result future 
     */
    protected ListenableFuture<ResultSet> performAsync(Statement statement) {
        if (getContext().getConsistencyLevel() != null) {
            statement.setConsistencyLevel(getContext().getConsistencyLevel());
        }
        
        if (getContext().getWritetime() != null) {
            statement.setDefaultTimestamp(getContext().getWritetime());
        }

        if (getContext().getRetryPolicy() != null) {
            statement.setRetryPolicy(getContext().getRetryPolicy());
        }

        if (getContext().getEnableTracing() != null) {
            if (getContext().getEnableTracing()) {
                statement.enableTracing();
            } else {
                statement.disableTracing(); 
            }
        }
        
        return ctx.getDbSession().getSession().executeAsync(statement);
    }
    
    
    /**
     * @param rs  the underlying result set
     * @return the new result 
     */
    Result newResult(ResultSet rs) {
        return new ResultImpl(rs);
    }
    
    
    
    private static class ResultImpl implements Result {
        private final ResultSet rs;
        
        ResultImpl(ResultSet rs) {
            this.rs = rs;
        }
        
        @Override
        public boolean wasApplied() {
            return rs.wasApplied();
        }
        
        @Override
        public ExecutionInfo getExecutionInfo() {
            return rs.getExecutionInfo();
        }
        
        @Override
        public ImmutableList<ExecutionInfo> getAllExecutionInfo() {
            return ImmutableList.copyOf(rs.getAllExecutionInfo());
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder(); 
            for (ExecutionInfo info : getAllExecutionInfo())  {

                builder.append("queried=" + info.getQueriedHost());
                builder.append("\r\ntried=")
                       .append(Joiner.on(",").join(info.getTriedHosts()));


                if (info.getAchievedConsistencyLevel() != null) {
                    builder.append("\r\nachievedConsistencyLevel=" + info.getAchievedConsistencyLevel());
                }
                
                if (info.getQueryTrace() != null) {
                    builder.append("\r\ntraceid=" + info.getQueryTrace().getTraceId());
                    builder.append("\r\nevents:\r\n" + Joiner.on("\r\n").join(info.getQueryTrace().getEvents()));
                }
            }
            return builder.toString();
        }
    }    
}