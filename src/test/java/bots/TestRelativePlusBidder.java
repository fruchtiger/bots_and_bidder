package bots;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static auction.Auction.createAuction;
import static auction.Utils.getBids;
import static java.util.Arrays.asList;
import static java.util.stream.IntStream.iterate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRelativePlusBidder {


    @Test
    void testZeroBidderActions(){

        int totalQty = 10;
        int cashTotal = 100;
        var otherBids =
                        iterate(0, i -> 0)
                        .limit(totalQty/2)
                        .boxed()
                        .toArray(Integer[]::new);

        var fixedBot = new FixedBidder(otherBids);
        var testBot = new RelativePlusBidder(0.2);

        var auction = createAuction(cashTotal,totalQty, fixedBot, testBot);
        auction.start();

        var expected = asList(10,8,6,5,21);
        var actual = getBids(auction.getRecordsFor(testBot));
        assertEquals(expected, actual);
    }


}
