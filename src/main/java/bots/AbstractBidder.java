package bots;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBidder implements Bidder {

    /***
     * Implementds the basic needs of a bot. Call proposalOr before placing a bid for sanity check.
     */



    /**Keine private modifier, weil sonst hier getter/setter für Subklassen notwendig wären*/
    List<Integer> otherBids = new ArrayList<>();
    List<Integer> ownBids =  new ArrayList<>();

    Integer otherQty = 0;
    Integer ownQty = 0;

    Integer ownCash = 0;
    Integer otherCash = 0;

    Integer leftQty = 0;

    Integer initQty = 0;
    Integer initCash = 0;

    Integer roundsTotal = 0;
    /**zero based, round = 0 means first round*/
    Integer roundsLeft = 0;

    private String name;

    public AbstractBidder(String name){
        this.name = name;
    }

    @Override
    public abstract int placeBid();

    private int getRoundsPlayed(){
        return roundsTotal - roundsLeft;
    }

    @Override
    public void init(int quantity, int cash){
        this.initQty = this.leftQty =  quantity;
        this.initCash = this.ownCash = this.otherCash = cash;
        this.roundsTotal = this.roundsLeft = initQty / 2;
    }

    /**
     *
     * @param bidProposal
     * @param bidElse
     * @return Returns all money left if it's the last round or else the maximum from [bidProposal,bidElse,0]
     */
     int proposalOr(int bidProposal, int bidElse) {
        if(roundsLeft==1) return this.ownCash;
        if(bidProposal == 0) return 0;
        return bidProposal <= ownCash ? bidProposal : this.proposalOr(bidElse,0);
    }

    @Override
    public void bids(int own, int other){
        /**Das letzte If ist unnötig machts aber leserlicher*/
        if(own == other) {otherQty++; ownQty++;}
        else if(own > other) ownQty++;
        else if(own < other) otherQty++;

        ownCash -= own;
        otherCash -= other;

        roundsLeft--;

        ownBids.add(own);
        otherBids.add(other);
    }

    public String getName(){
         return this.name;
    };

    int getAverage() {
        return initCash * 2 / initQty;
    }
}
