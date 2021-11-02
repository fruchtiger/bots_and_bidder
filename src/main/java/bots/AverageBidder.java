package bots;


public class AverageBidder extends AbstractBidder {

    /**
     * This bot just bids the average price of the product
     *
     */

    AverageBidder() {
        super("Average");
    }

    @Override
    public int placeBid() {
        return proposalOr(getAverage(),1);
    }
}
