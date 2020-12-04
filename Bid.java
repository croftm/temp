import java.io.Serializable;

public class Bid implements Serializable {
    private static final long serialVersionUID = 1L;
    private int auctionId;
    private double amount;
    private String name;
    private String email;
    public Bid(double a,int id, String n, String e){
        amount = a;
        name = n;
        email = e;
        auctionId=id;
    }
    public int getId(){
        return auctionId;
    }
    public double getAmount(){
        return amount;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
}
