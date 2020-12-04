import java.io.Serializable;

/*
Simple class for auction items
*/
public class AuctionItem implements Serializable{
    private static final long serialVersionUID = 1L;
    private int auctionId;
    private double reservePrice;
    private String itemTitle;
    private String itemDescription;
    private double highestBid;
    private Bid highestBidder;
    private String userId;
    //Contructor for auction item allows initialisation of items
    public AuctionItem(int a, double b, String c, String d,String uId){
        auctionId = a;
        reservePrice = b;
        itemTitle = c;
        itemDescription = d;
        highestBid=0;
        userId=uId;
        highestBidder=new Bid(0,0,"No Bidders","noBidder@lameitem.com");

    }
    //returns Id as an int
    public int getId(){
        return auctionId;
    }
    //returns title as string
    public String getTitle(){
        return itemTitle;
    }
    //returns description as a string
    public String getDescription(){
        return itemDescription;
    }
    public double getReservePrice(){
        return reservePrice;
    }
    public double getHighestBid(){
        return highestBid;
    }
    public String getUserId(){
        return userId;
    }
    public String addBid(Bid bid){
        if(bid.getAmount()>highestBidder.getAmount()){
            highestBid=bid.getAmount();
            highestBidder=bid;
            return("Bid Successful");
        }
        else{
            return("Bid failed");
        }
    }
    public String closeBidding(){
        String temp;
        if(highestBid>reservePrice){
            temp = "The reserve Price was reached.\n"+"Name: "+ highestBidder.getName()+"\nEmail: "+highestBidder.getEmail();
            return temp;
        }
        else{
            return("Failed to reach reserve price. Highest bid was "+highestBid);
        }
    }
}
