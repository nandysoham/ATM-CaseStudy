package company;
import com.mongodb.*;
import java.util.Random;

public class superuser {
    private String name;
    private int cardnumber;
    private int pin;
    private int cvv;
    private int cash;
    private String bankname;
    private String branch;
    private String email;

    public void setCash(int cash) {
        this.cash = cash;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean adduser(){



        Random rand = new Random();

        this.cardnumber = rand.nextInt(100000000) ;
        this.cvv = rand.nextInt(1000);
        this.pin = rand.nextInt(10000);


        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("ATM");
            DBCollection collection = database.getCollection("bankdetails");
            BasicDBObject document = new BasicDBObject();




            document.put("name", this.name);
            document.put("cardnumber",this.cardnumber);
            document.put("pin",this.pin);
            document.put("cvv",this.cvv);
            document.put("branch",this.branch);
            document.put("bankname",this.bankname);
            document.put("email",this.email);
            document.put("amount",this.cash);


            collection.insert(document);
            System.out.println("Details entered successfully");
            System.out.println("-------------------------------");
//            System.out.println("Name - "+ this.name);
//            System.out.println("Bank - " + this.bankname);
//            System.out.println("branch - " + this.branch);
//            System.out.println("card number - "+ this.cardnumber);
//            System.out.println("pin - " + this.pin);
//            System.out.println("cvv - " + this.cvv);
//            System.out.println("Please forward this details to your client");


            EmailUtility mailer = new EmailUtility();
            String subject = "Hello "+this.name+", Welcome to Bank of Norway!";
            String message = "Welcome on Board!!!...\n You can now manage all you account details online \n"+
                    "But First have a look over your new account !\n\n"+
                    "----------------------------------------------------"+
                    "Name - "+ this.name+
                    "Bank - " + this.bankname+
                    "branch - " + this.branch+
                    "card number - "+ this.cardnumber+
                    "pin - " + this.pin+
                    "cvv - " + this.cvv + "\n\n" +
                    "in case of any discrepancy please contact the nearest branch or call at 1234567890 \n"+
                    "Have a nice day!!!";

            mailer.sendemail(email,subject,message);






            return true;

        }
        catch (Exception e){
            System.out.println(e);
            return false;
        }



    }
}
