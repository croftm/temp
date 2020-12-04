import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;

public class AuctionNode extends ReceiverAdapter {
    JChannel channel;
    String user_name = System.getProperty("user.name", "n/a");
    AuctionInterface auction;
    Address client;

    public AuctionNode() {
        try {
            channel = new JChannel();
            channel.connect("AuctionCluster");
            channel.setReceiver(this);
            auction = new AuctionImp();
            Message msg = new Message(null, null, auction);
            channel.send(msg);
        } catch (Exception e) {
            System.out.println("Server Error: " + e);
        }
    }

    public void receive(Message msg) {
        if (msg.src() != channel.getAddress()) {
            System.out.println(msg.getObject());
            if (msg.getObject().equals("Host")) {
                client = msg.src();
                Message m = new Message(client, "UPDATE");
                try {
                    channel.send(m);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (msg.getObject().equals("NODE")) {
                client = msg.src();
                Message m = new Message(msg.src(), auction);
                try {
                    channel.send(m);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(msg.getObject() instanceof AuctionInterface){
                System.out.println("UPDATED");
                auction = (AuctionInterface)msg.getObject();
            }
        }
    }
    public static void main(String[] args) {
        new AuctionNode();
    }
}

