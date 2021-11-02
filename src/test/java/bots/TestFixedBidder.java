package bots;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static auction.Auction.createAuction;
import static auction.Utils.getBids;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFixedBidder {

    @Test
    void testFixedBidderActions(){

        int totalQty = 10;
        int cashTotal = 20;
        var testBot = new FixedBidder(1,2,3,4,5);
        var otherBot = new AverageBidder();
        var auction = createAuction(cashTotal,totalQty, testBot, otherBot);
        auction.start();

        var expected = asList(1,2,3,4,0);
        var actual = getBids(auction.getRecordsFor(testBot));
        assertEquals(expected, actual);
    }




}
