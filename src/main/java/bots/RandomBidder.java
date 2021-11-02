package bots;

import static java.lang.Math.min;
import static java.lang.Math.random;
import static java.lang.String.format;

public class RandomBidder extends AbstractBidder {

    /**
     * This bot just randomly makes a bid (which can be limited)
     *
     */

    /**value > 0 Limits the randomness / crazyiness */
    int limit;

    public RandomBidder(){
        this(Integer.MAX_VALUE);
    }

    public RandomBidder(int limit){
        super(format("Crazy%d",limit));
        this.limit = limit;
    }


    @Override
    public int placeBid() {
        int bidProposal = min((int)(random() * ownCash),limit);
        return proposalOr(bidProposal,0);
    }
}
