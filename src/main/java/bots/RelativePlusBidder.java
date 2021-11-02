package bots;

import static java.lang.String.format;

public class RelativePlusBidder extends AbstractBidder {

    /**
     * This bot tries to bid relatively more money than the previous bid from other bot.
     *
     */


    /**How much of the money left (budget) should be used to plus the last other bid*/
    double budgetForPlus = 0.1;

    public RelativePlusBidder(double budgetForPlus) {
        super(format("Plus%.0f%%",budgetForPlus*100));
        this.budgetForPlus = budgetForPlus;
    }

    @Override
    public int placeBid() {
        int lastOtherBidOrZero =  otherBids.size() > 0 ? otherBids.get(otherBids.size()-1) : 0;
        int plus = (int)Math.floor(this.ownCash.doubleValue() * budgetForPlus);
        int bidProposal = plus + lastOtherBidOrZero;
        return proposalOr(bidProposal,0);
    }


}

