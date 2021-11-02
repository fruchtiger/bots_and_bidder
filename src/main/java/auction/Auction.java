package auction;

import bots.Bidder;
import bots.FixedPlusBidder;
import bots.RandomBidder;
import de.vandermeer.asciitable.AsciiTable;

import java.util.*;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;

public class Auction {

    /**
     * Runs an auction for two bidder / bots.
     *
     */


    private final int cashTotal;
    private final int qtyTotal;

    private int qtyLeft;

    private final Bidder botA;
    private int qtyA = 0;
    private int cashLeftA = 0;

    private final Bidder botB;
    private int qtyB = 0;
    private int cashLeftB = 0;


    private final Map<Bidder,List<AuctionRecord>> botRecords = new HashMap<>();

    private List<AuctionRecord> recordsBotA = new ArrayList<>();
    private List<AuctionRecord> recordsBotB = new ArrayList<>();


    private Auction(int cashTotal, int qtyTotal, Bidder botA, Bidder botB){
        this.cashTotal = cashTotal;
        this.qtyTotal = qtyTotal;
        this.cashLeftB = this.cashLeftA = cashTotal/2;

        this.botA = botA;
        this.botB = botB;

        botRecords.put(botA, new ArrayList<>());
        botRecords.put(botB, new ArrayList<>());
    }


    public static Auction createAuction(int cashTotal, int qtyTotal, Bidder botA, Bidder botB){
        check(qtyTotal, cashTotal, botA, botB);
        botA.init(qtyTotal, cashTotal/2);
        botB.init(qtyTotal, cashTotal/2);

        return new Auction(cashTotal, qtyTotal, botA, botB);
    }

    private static void check(int qtyTotal, int cashTotal, Bidder botA, Bidder botB) {
        boolean qtyOK = false;
        boolean cashOK = false;

        boolean botAOK = botA!=null;
        boolean botBOk = botB!=null;

        if(qtyTotal >= 2 && (qtyTotal % 2 == 0)) qtyOK = true;
        if(cashTotal >= 2 && (cashTotal % 2 == 0)) cashOK = true;

        if(qtyOK && cashOK && botBOk && botAOK) return;
        throw new IllegalArgumentException("Total qty and cash must be bigger than two and divisable by two");
    }

    public void start(){

        for(qtyLeft = qtyTotal; qtyLeft > 0; qtyLeft-=2){

            int bidA = botA.placeBid();
            int bidB = botB.placeBid();

            botA.bids(bidA, bidB);
            botB.bids(bidB, bidA);

            if(bidA == bidB) {
                qtyA++;
                qtyB++;
            }
            else if (bidA > bidB) qtyA +=2;
            /**Leserlicher, der Compiler müsste das wegradieren */
            else if (bidA < bidB) qtyB +=2;

            cashLeftA -= bidA;
            cashLeftB -= bidB;

            var recordA = new AuctionRecord(bidA,qtyA,cashLeftA);
            var recordB = new AuctionRecord(bidB,qtyB,cashLeftB);

            botRecords.get(botA).add(recordA);
            botRecords.get(botB).add(recordB);

            recordsBotA.add(recordA);
            recordsBotB.add(recordB);

            checkAuction();

        }

    }

    private void checkAuction() {
        String errorBot = null;
        if(cashLeftA < 0) errorBot ="A";
        else if(cashLeftB < 0) errorBot ="B";

        if(errorBot!=null) {
            String errorMsg = "Bot X spent more cash than available".replace("X", errorBot);
            throw new IllegalArgumentException(errorMsg);
        }

    }


    public void print(String nameA, String nameB) {

        AsciiTable at = new AsciiTable();
        String token ="|";


        at.addRule();
        String[] header = new String[]{
                "Runde",
                format("Gebot\n(%s%s%s)",nameA,token,nameB),
                format("Menge\n(%s%s%s)",nameA,token,nameB),
                format("Geld\n(%s%s%s)",nameA,token,nameB)
        };
        at.addRow(header);

        for(int round = 0; round < botRecords.get(botA).size(); round++){
            at.addRule();

            var recordA = botRecords.get(botA).get(round);
            var recordB = botRecords.get(botB).get(round);

            at.addRow(round+1,
                    format("%d%s%d",recordA.bid(),token,recordB.bid()),
                    format("%d%s%d",recordA.qtyAfter(),token,recordB.qtyAfter()),
                    format("%d%s%d",recordA.cashAfter(),token,recordB.cashAfter())
                    );
        }
        at.addRule();
        at.addRow(header);
        at.addRule();
        System.out.println(at.render());
    }


    public List<AuctionRecord> getRecordsFor(Bidder bidder){
        return unmodifiableList(botRecords.get(bidder));
    }

    public static void main(String[] args) {

        int cashTotal = 1_000;
        int qtyTotal = 50;
        //var thatBidder = new RelativePlusBidder("RPB", 0.1);
        var botA = new RandomBidder((int)((cashTotal/qtyTotal)*1.1));
        //var botA = new FixedPlusBidder(2);
        var botB = new FixedPlusBidder(5);
        //var botB = new AverageBidder();
        var auction = createAuction(cashTotal,qtyTotal, botA, botB);
        auction.start();
        auction.print(botA.getName(), botB.getName());
    }

}
