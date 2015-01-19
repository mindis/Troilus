package net.oneandone.troilus.reactive;



import java.util.concurrent.CompletableFuture;

import net.oneandone.troilus.AbstractCassandraBasedTest;
import net.oneandone.troilus.Dao;
import net.oneandone.troilus.DaoImpl;
import net.oneandone.troilus.Record;
import net.oneandone.troilus.Result;
import net.oneandone.troilus.api.FeesTable;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableList;


public class ReactiveTest extends AbstractCassandraBasedTest {
    
    
    @Test
    public void testAsync() throws Exception {
        Dao feeDao = new DaoImpl(getSession(), FeesTable.TABLE);

        
        ////////////////
        // inserts
        CompletableFuture<Result> insert1 = feeDao.writeWithKey(FeesTable.CUSTOMER_ID, "132", FeesTable.YEAR, 3)
                                                  .value(FeesTable.AMOUNT, 23433)
                                                  .executeAsync();
        
        CompletableFuture<Result> insert2 = feeDao.writeWithKey(FeesTable.CUSTOMER_ID, "132", FeesTable.YEAR, 4)
                                                  .value(FeesTable.AMOUNT, 1223)
                                                  .executeAsync();

        CompletableFuture<Result> insert3 = feeDao.writeWithKey(FeesTable.CUSTOMER_ID, "132", FeesTable.YEAR, 8)
                                                  .value(FeesTable.AMOUNT, 23233)
                                                  .executeAsync();
        
        CompletableFuture.allOf(insert1, insert2, insert3)
                         .get();  // waits for completion
        
        
        
        ////////////////
        // reads
        MySubscriber<Record> testSubscriber = new MySubscriber<>();
        
        feeDao.readWhere()
              .columns(FeesTable.CUSTOMER_ID, FeesTable.YEAR, FeesTable.AMOUNT)
              .executeAsync()
              .thenAccept(result -> result.subscribe(testSubscriber));
        
        
        ImmutableList<Record> records = testSubscriber.getAll();
        Assert.assertEquals(3,  records.size());
    }        
}

