package bots;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class FixedBidder extends AbstractBidder {

    /**
     *
     * This bot always bids a fixed sequence of money if possible. Else bis 0
     *
     */


    List<Integer> bids = new ArrayList<>();

    /**
     * Beware, no checks are made if size of biddings matches roundsTotal
     *
     * @param bids
     */
    public FixedBidder(Integer ... bids){
        super("Fixed");
        this.bids.addAll(asList(bids));
    }

    @Override
    public int placeBid() {
        return proposalOr(bids.get(roundsTotal-roundsLeft),0);
    }
}
