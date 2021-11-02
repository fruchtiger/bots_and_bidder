package bots;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static auction.Auction.createAuction;
import static auction.Utils.getBids;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFixedPlusBidder {


    @Test
    void testZeroBidderActions(){

        int totalQty = 12;
        int cashTotal = 100;
        int plus = 2;
        var otherBot =
                IntStream.iterate(0, i -> 0)
                .limit(totalQty/2)
                .boxed()
                .toArray(Integer[]::new);
        var bidder = new FixedBidder(otherBot);
        var testBidder = new FixedPlusBidder(plus);
        var auction = createAuction(cashTotal,totalQty, bidder, testBidder);
        auction.start();


        var expected = Arrays.asList(2,2,2,2,2,40);
        var actual = getBids(auction.getRecordsFor(testBidder));
        assertEquals(expected, actual);

    }

    @Test
    void testRandomBidderActions(){
        int totalQty = 50;
        int cashTotal = 1000;
        int plus = 17;

        var bidder = new RandomBidder(cashTotal/2);
        var testBidder = new FixedPlusBidder(plus);

        var auction = createAuction(cashTotal,totalQty, bidder, testBidder);
        auction.start();

        var randomBids = getBids(auction.getRecordsFor(bidder));
        var plusBids = getBids(auction.getRecordsFor(testBidder));

        /** Ignore bids of the first and last round */
        plusBids = plusBids.subList(1,plusBids.size()-1);

        /** Ignore two last bids of the auction */
        randomBids = randomBids.subList(0,randomBids.size()-2);

        var expected = randomBids
                .stream()
                .map(i -> i+ plus)
                .toList();

        for(int r =0; r < plusBids.size()-1; r++){
            /**if PlusBidder run out of money, it cannot bid plus. So stop testing*/
            if(plusBids.get(r)==0) break;
            assertEquals(plusBids.get(r),expected.get(r));
        }


    }


}
