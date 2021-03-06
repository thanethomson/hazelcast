package com.hazelcast.monitor.impl;

import com.eclipsesource.json.JsonObject;
import com.hazelcast.test.HazelcastParallelClassRunner;
import com.hazelcast.test.annotation.QuickTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(HazelcastParallelClassRunner.class)
@Category(QuickTest.class)
public class LocalQueueStatsImplTest {

    @Test
    public void testDefaultConstructor() {
        LocalQueueStatsImpl localQueueStats = new LocalQueueStatsImpl();
        localQueueStats.setMinAge(13);
        localQueueStats.setMaxAge(28);
        localQueueStats.setAveAge(18);
        localQueueStats.setOwnedItemCount(1234);
        localQueueStats.setBackupItemCount(15124);

        assertTrue(localQueueStats.getCreationTime() > 0);
        assertEquals(13, localQueueStats.getMinAge());
        assertEquals(28, localQueueStats.getMaxAge());
        assertEquals(18, localQueueStats.getAvgAge());
        assertEquals(1234, localQueueStats.getOwnedItemCount());
        assertEquals(15124, localQueueStats.getBackupItemCount());
        assertNotNull(localQueueStats.toString());
    }

    @Test
    public void testSerialization() {
        LocalQueueStatsImpl localQueueStats = new LocalQueueStatsImpl();

        localQueueStats.incrementOtherOperations();
        localQueueStats.incrementOtherOperations();
        localQueueStats.incrementOffers();
        localQueueStats.incrementOffers();
        localQueueStats.incrementOffers();
        localQueueStats.incrementRejectedOffers();
        localQueueStats.incrementRejectedOffers();
        localQueueStats.incrementRejectedOffers();
        localQueueStats.incrementRejectedOffers();
        localQueueStats.incrementPolls();
        localQueueStats.incrementEmptyPolls();
        localQueueStats.incrementEmptyPolls();
        localQueueStats.incrementReceivedEvents();
        localQueueStats.incrementReceivedEvents();
        localQueueStats.incrementReceivedEvents();

        JsonObject serialized = localQueueStats.toJson();
        LocalQueueStatsImpl deserialized = new LocalQueueStatsImpl();
        deserialized.fromJson(serialized);

        assertTrue(deserialized.getCreationTime() > 0);
        assertEquals(2, deserialized.getOtherOperationsCount());
        assertEquals(3, deserialized.getOfferOperationCount());
        assertEquals(4, deserialized.getRejectedOfferOperationCount());
        assertEquals(1, deserialized.getPollOperationCount());
        assertEquals(2, deserialized.getEmptyPollOperationCount());
        assertEquals(3, deserialized.getEventOperationCount());
        assertEquals(6, deserialized.total());
        assertNotNull(deserialized.toString());
    }
}
