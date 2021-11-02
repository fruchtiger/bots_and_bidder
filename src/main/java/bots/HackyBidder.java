package bots;

public class HackyBidder extends AbstractBidder {

    /**
     * This bot tries to generate money with negative bids.
     * This of course only works with no sanity checks and using a simple adding mechanism in the auction implementations.
     * Use this to test your auction implementation :-)
     */


    public HackyBidder(String name) {
        super(name);
    }

    @Override
    public int placeBid() {
        //not yet implemented
        return 0;
    }
}
