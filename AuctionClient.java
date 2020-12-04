import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;


public class AuctionClient {
    BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
    String userId;
    public AuctionClient(){
        userId="";
        System.out.println("Please enter a UserID:");
        try {
            userId = input.readLine();   
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    public void browse(AuctionInterface auction) throws RemoteException{
        String auctionListings = auction.printAuctionListings();
        System.out.println(auctionListings);
    }
    public void run(){
    }
}
