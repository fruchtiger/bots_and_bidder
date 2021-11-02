package bots;

import static java.lang.String.format;

public class FixedPlusBidder extends AbstractBidder {

    /**
     *
     * This bot tries to bide x more money as the previous bid from other bot.
     * Great against bots making the same bids in a row
     *
     */



    private int plus;
    private int limit;


    public FixedPlusBidder(int plus) {
        this(format("Plus%d", plus),plus,Integer.MAX_VALUE);
    }

    public FixedPlusBidder(int plus, int limit) {
        this(format("Plus%d", plus),plus,limit);
    }

    public FixedPlusBidder(String name, int plus, int limit) {
        super(name);
        this.plus = plus;
        this.limit = limit;
    }

    @Override
    public int placeBid() {
        int lastOtherBidOrZero =  otherBids.size() > 0 ? otherBids.get(otherBids.size()-1) : 0;
        int bidProposal = Math.min(plus + lastOtherBidOrZero, limit);
        return proposalOr(bidProposal,0);
    }
}
