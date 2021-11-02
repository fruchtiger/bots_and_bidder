package auction;

import java.util.List;

public class Utils {

    public static List<Integer> getBids(List<AuctionRecord> records){
        return records.stream().map(r -> r.bid()).toList();
    }
}
