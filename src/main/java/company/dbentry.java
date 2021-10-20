package company;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class dbentry {
    private String customerid = "";
    private String customername = "";
    private String customeremail = "";

    public String getCustomeremail() {
        return customeremail;
    }

    public String getCustomername() {
        return customername;
    }

    public boolean verifycredential(int cardno, int cvv, int pin){
        try {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("ATM");
            DBCollection collection = database.getCollection("bankdetails");
            BasicDBObject document = new BasicDBObject();


            BasicDBObject searchperson = new BasicDBObject();
            searchperson.put("cardnumber",cardno);
            searchperson.put("cvv", cvv);
            searchperson.put("pin", pin);
            DBCursor cursor = collection.find(searchperson);
//        System.out.println("hello");

            if(cursor.hasNext()) {
                this.customerid =cursor.one().get("_id").toString();
                this.customername = cursor.one().get("name").toString();
                this.customeremail = cursor.one().get("email").toString();
                System.out.println();
                System.out.println();
                System.out.println(this.customerid);
                System.out.println("HI " + customername);
                return true;
            }
            else{
                System.out.println("Please enter valid credentials");
                return false;
            }

        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }



    }

    public boolean changepin(){
        Scanner sc = new Scanner(System.in);
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("ATM");
            DBCollection collection = database.getCollection("bankdetails");


            BasicDBObject searchperson = new BasicDBObject();
            searchperson.put("_id",new ObjectId(this.customerid));
            DBCursor cursor = collection.find(searchperson);
            if(cursor.hasNext()) {
                int thispin = Integer.parseInt(cursor.one().get("pin").toString());
                int pincnt = 0;
                boolean pinotp = false;
                while(!pinotp && pincnt < 3){
                    System.out.println("Please enter your previous pin");
                    int olderpin = sc.nextInt();
                    if(olderpin == thispin){
                        pinotp = true;

                        int newpin, newpinconfirm;
                        while(true){
                            System.out.println("Please enter a 4 digit(NUMERIC) pin");
                            newpin = sc.nextInt();
                            System.out.println("Please enter the print to reconfirm");
                            newpinconfirm = sc.nextInt();
                            if(newpin == newpinconfirm){
                                if(newpin < 10000 ){
                                    break;
                                }
                                else{
                                    System.out.println("Pin should be of 4 digits");
                                }
                            }
                            else{
                                System.out.println("pin and confirmation dont match");
                            }

                        }

                        // now the main processing
                        BasicDBObject updateperson = new BasicDBObject();
                        updateperson.put("pin", newpin);

                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", updateperson);

                        collection.update(searchperson, updateObject);
                        System.out.println("Pin updated succesfully");
                        System.out.println("Thanks for using our service");
                        return true;



                    }
                    else{
                        System.out.println("wrong pin provide");
                        System.out.println("you have three more attempts remaining");
                        pincnt++;
                        return false;
                    }
                }

            }
            else{
                System.out.println("soem error occured in verfying credentials");
                return false;
            }

            return false;

        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("some internal database error occured");
            return false;
        }
    }

    public boolean printbalance(int debited,int prevamt){
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("ATM");
            DBCollection collection = database.getCollection("bankdetails");


            BasicDBObject searchperson = new BasicDBObject();
            searchperson.put("_id",new ObjectId(this.customerid));
            DBCursor cursor = collection.find(searchperson);
            if(cursor.hasNext()) {
                String accno =cursor.one().get("_id").toString();
                String cusname =cursor.one().get("name").toString();
                int updated_amt = Integer.parseInt(cursor.one().get("amount").toString());
                int cardno = Integer.parseInt(cursor.one().get("cardnumber").toString());

                printreceipt pr = new printreceipt();
                pr.printrecp(customeremail,cusname,accno,prevamt,updated_amt,debited,cardno);
                return true;

            }
            else{

                System.out.println("some error in finding the account");
                return false;
            }

        }
        catch(Exception e){
            System.out.println("some error in connection with the database");
            return false;
        }

    }

    public boolean creditcash(int cash){
        System.out.println(this.customerid);
        System.out.println(this.customername);
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("ATM");
            DBCollection collection = database.getCollection("bankdetails");


            BasicDBObject searchperson = new BasicDBObject();
            searchperson.put("_id",new ObjectId(this.customerid));
            DBCursor cursor = collection.find(searchperson);
            if(cursor.hasNext()) {
                int accountcash = Integer.parseInt(cursor.one().get("amount").toString());

                    BasicDBObject updateperson = new BasicDBObject();
                    int remainingbal = accountcash+cash;
                    updateperson.put("amount", remainingbal);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", updateperson);

                    collection.update(searchperson, updateObject);
                    System.out.println("Upadating... Processing ...");
                    System.out.println("Please check your balance");
                    System.out.println();
                    boolean prbal = printbalance(-1,accountcash);
                    // generate statement
                    return true;


            }
            else{
                System.out.println("some interval server error");
                return false;
            }

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some problem in connecting with the database");
            return false;
        }




    }


    public boolean debitcash(int cash){
        System.out.println(this.customerid);
        System.out.println(this.customername);
        try{
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            DB database = mongoClient.getDB("ATM");
            DBCollection collection = database.getCollection("bankdetails");


            BasicDBObject searchperson = new BasicDBObject();
            searchperson.put("_id",new ObjectId(this.customerid));
            DBCursor cursor = collection.find(searchperson);
            if(cursor.hasNext()) {
                int accountcash = Integer.parseInt(cursor.one().get("amount").toString());
                if(cash > accountcash){
                    System.out.println("Not enough cash available ... Please try again");
                    return false;
                }
                else{
                    BasicDBObject updateperson = new BasicDBObject();
                    int remainingbal = accountcash-cash;
                    updateperson.put("amount", remainingbal);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", updateperson);

                    collection.update(searchperson, updateObject);
                    System.out.println("Trancsaction Processing ...");
                    System.out.println("Please collect your cash");
                    System.out.println();
                    // generate statement
                    printbalance(1,accountcash);
                    return true;
                }

            }
            else{
                System.out.println("some interval server error");
                return false;
            }

        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("some problem in connecting with the database");
            return false;
        }




    }
}
