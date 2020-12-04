import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class AuctionClientBuyer extends AuctionClient {
    public AuctionClientBuyer(){
        super();
    }
    public void bid(AuctionInterface auction) throws RemoteException, IOException{
        int id;
        String email;
        System.out.println("Enter auction ID:\n");
        id = Integer.parseInt(input.readLine());
        System.out.println("Enter Email:");
        email = input.readLine();
        System.out.println("Amount:");
        System.out.println(auction.bid(new Bid(Double.parseDouble(input.readLine()),id,userId,email)));
    }
    public void run(){
        try {
            while(true){
                try {
                    AuctionInterface auction = (AuctionInterface)
                    Naming.lookup("rmi://localhost/AuctionService");
                    System.out.print("\nCommands:\n\n1) View Auction Listings\n\n2) Bid Item\n\n3) Exit\n\n");
                    int userInput = Integer.parseInt(input.readLine());
                    if(userInput==1){//Browsing
                        browse(auction);
                    }
                    else if(userInput==2){//Bidding
                        bid(auction);
                    }
                    else if(userInput==3){//Exit
                        input.close();
                        return;
                    }
                } catch (Exception e) {

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args){
       AuctionClient c = new AuctionClientBuyer();
       c.run();
    }
}
