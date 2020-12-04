
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class AuctionClientSeller extends AuctionClient {
    public AuctionClientSeller(){
        super();
    }
    public void add(AuctionInterface auction) throws RemoteException, IOException{
        String name;
        String desc;
        double reserve;
        int auctionID;
        System.out.println("Enter Item Name: ");
        name=input.readLine();
        System.out.println("Enter Item desc: ");
        desc=input.readLine();
        System.out.println("Enter Item reserve Price: ");
        reserve=Double.parseDouble(input.readLine());
        auctionID = auction.addItem(reserve, name, desc,userId);
        System.out.println("Successfully added item, your auction item is: "+auctionID);
    }
    public void close(AuctionInterface auction) throws RemoteException, IOException{
        System.out.println("Enter the ID for the auction you would like to shutdown");
        System.out.println(auction.closeBidding(Integer.parseInt(input.readLine()),userId));
    }
    public void run(){
        try {
            while(true){
                try {
                    AuctionInterface auction = (AuctionInterface)
                    Naming.lookup("rmi://localhost/AuctionService");
                    //Getting user command
                    System.out.print("\nCommands:\n\n1) View Auction Listings\n\n2) Add Item\n\n3) Close Auction\n\n4) Exit\n\n");
                    int userInput = Integer.parseInt(input.readLine());
                    if(userInput==1){//viewAuctionListings
                        browse(auction);
                    }
                    else if(userInput==2){//Add item
                        add(auction);
                    }
                    else if(userInput==3){//Close auction
                        close(auction);
                    }
                    else if(userInput==4){//exit
                        return;
                    }
                } catch (Exception e) {
                }
            }
            } catch (Exception murle) {
            System.out.println(murle);
        }
    }
    public static void main(String[] args){
        AuctionClient a = new AuctionClientSeller();
        a.run();
    }
}
