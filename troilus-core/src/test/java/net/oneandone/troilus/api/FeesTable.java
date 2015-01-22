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
package net.oneandone.troilus.api;

import net.oneandone.troilus.Schema;



public interface FeesTable  {
   
    public static final String TABLE = "fees";
    
    public static final String CUSTOMER_ID = "customer_id";
    public static final String YEAR = "year";
    public static final String AMOUNT = "amount";
 
    public static final String CREATE_STMT = Schema.load("com/unitedinternet/troilus/example/fees.ddl");
}
