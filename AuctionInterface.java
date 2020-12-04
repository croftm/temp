import java.io.Serializable;

import org.jgroups.Address;

/*
Simple rmi interface for an auction system
*/
public interface AuctionInterface extends java.rmi.Remote, Serializable{
    //returns auction item based on item id and client id
    public AuctionItem getSpec(int itemId) throws java.rmi.RemoteException;
    public int addItem(double reservePrice, String title, String desc,String userID) throws java.rmi.RemoteException;
    public String printAuctionListings() throws java.rmi.RemoteException;
    public String bid(Bid bid)throws java.rmi.RemoteException;
    public String closeBidding(int id,String uId) throws java.rmi.RemoteException;
    public void setAddress(Address a) throws java.rmi.RemoteException;
}
