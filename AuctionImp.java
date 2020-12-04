import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.util.MessageBatch;

public class AuctionImp extends java.rmi.server.UnicastRemoteObject implements AuctionInterface {
    private static final long serialVersionUID = 1L;
    private List<AuctionItem> auctionItems;
    private int idTracker = 0;
    private boolean changed = false;
    private Address host;

    public AuctionImp() throws java.rmi.RemoteException {
        super();
        auctionItems = new LinkedList<AuctionItem>();
        auctionItems.add(new AuctionItem(newID(), 100, "Wooden Horse", "Horse made of wood", "NO"));
        auctionItems.add(new AuctionItem(newID(), 1000, "Steel Horse", "Horse made of steel", "NO"));
        auctionItems.add(new AuctionItem(newID(), 10000, "Gold Horse", "Horse made of gold", "NO"));
    }

    private int newID() {
        idTracker++;
        return idTracker;
    }

    private AuctionItem findItem(int id) {
        Iterator<AuctionItem> i = auctionItems.iterator();
        while (i.hasNext()) {
            AuctionItem next = i.next();
            if (next.getId() == id) {
                return next;
            }
        }
        return null;
    }

    public void setAddress(Address a) {
        host = a;
    }

    private void update() {
        try {
            JChannel channel = new JChannel();
            channel.connect("AuctionCluster");
            Message msg = new Message(host, "UPDATE");
            channel.send(msg);
            channel.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public String printAuctionListings(){
        Iterator<AuctionItem> iterator = auctionItems.iterator();
        String auctionCatalog = "Current Auction Items:\n";
        while(iterator.hasNext()){
            
            AuctionItem nextAuctionItem = iterator.next();
            auctionCatalog=auctionCatalog+"Title: "+nextAuctionItem.getTitle()+"\n";
            auctionCatalog=auctionCatalog+"Description: "+nextAuctionItem.getDescription()+"\n";
            auctionCatalog=auctionCatalog+"Auction ID: "+nextAuctionItem.getId()+"\n";
            auctionCatalog=auctionCatalog+"Highest Bid: "+nextAuctionItem.getHighestBid()+"\n";
            auctionCatalog=auctionCatalog+"\n";
        }
        update();
        return(auctionCatalog);
    }

    public AuctionItem getSpec(int itemId){
        for(int i=0;i<auctionItems.size();i++){
            AuctionItem requestedItem = auctionItems.get(i);
            if(requestedItem.getId()==itemId){
                update();
                return requestedItem;
            }
            else{
                System.out.println("Item not found");
            }
        }
        update();
        return null;
    }
    public int addItem(double b, String c,String d,String userId){
        AuctionItem temp = new AuctionItem(newID(),b,c,d,userId);
        auctionItems.add(temp);
        update();
        return temp.getId();
    }
    public String bid(Bid bid){
        AuctionItem bidTarget = findItem(bid.getId());
        update();
        return bidTarget.addBid(bid);
    }
    public String closeBidding(int id, String uId){
        Iterator<AuctionItem> i = auctionItems.iterator();
        String r;
        int index = 0;
        while(i.hasNext()){
            AuctionItem temp = i.next();
            if(id==temp.getId()&&uId.equals(temp.getUserId())){
                r=temp.closeBidding();
                auctionItems.remove(index);
                update();
                return r;
            }
            index++;
        }
        update();
        return("Auction not found/Invalid credentials");
    }
    
}
