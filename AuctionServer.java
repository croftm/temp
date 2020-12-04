import java.rmi.Naming;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class AuctionServer extends ReceiverAdapter {
    JChannel channel;
    AuctionInterface auction = null;
    boolean hasNode = false;
    Address a;

    public AuctionServer() {
        try {
            channel = new JChannel();
            channel.setReceiver(this);
            channel.connect("AuctionCluster");
            a = channel.getAddress();
            getNode();

        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }

    public void getNode() {
        Message m = new Message(null, null, "NODE");
        try {
            channel.send(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }
    public synchronized void receive(Message msg) {
        if(msg.src()!=a){
            System.out.println(msg.getObject());
            try {
                if(!hasNode){
                    auction=(AuctionInterface)msg.getObject();
                    auction.setAddress(a);
                    Naming.rebind("rmi://localhost/AuctionService",auction);
                    Message m = new Message(msg.src(), "Host");
                    channel.send(m);
                    hasNode = true;
                }
                if(msg.getObject().equals("UPDATE")){
                    Message m = new Message(null,null, auction);
                    channel.send(m);
                }
                else{
                    Message m = new Message(msg.src(), "Host");
                    channel.send(m);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    public void updateNodes(){
        
    }
    public static void main(String[] args) {
        new AuctionServer();
    }
}
