package company;

public class printreceipt {
    void printrecp(String email,String name, String accno,int prev_amt,int updated_amt,int debited,  int cardnumber){
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");
        System.out.println("BANK OF NORWAY");
        System.out.println("8th Downhill Street, Tromso, Kingdom of Norway");
        System.out.println();
        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println("Customer Name - " + name);
        System.out.println("Account No - " + "XXXXXXXX"+accno.substring(8));
        System.out.println("Card number - " + cardnumber/10000+"XXXX");
        System.out.println("-------------Amount details -------------");
        System.out.println("Balance before : " + prev_amt);
        if(debited == 1) System.out.println("Amount Debited : " + (prev_amt-updated_amt));
        else if(debited == -1) System.out.println("Amount Credited : " + (updated_amt-prev_amt));
        else if (debited == 0) System.out.println("");
        System.out.println("Remaining Balance : " + updated_amt);
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("NET BALANCE :" + updated_amt);
        System.out.println();
        System.out.println("Thanks for using the ATM");
        System.out.println("Go Green, Pls don't waste paper");
        System.out.println("----------------------------------------------------");
        System.out.println("----------------------------------------------------");




        // Sending an email
        EmailUtility mailer = new EmailUtility();
        String subject;
        String message;
        if(debited == 1){
            subject = "Hey "+name+", Your debit summary ";
            message = "Hey "+name+"\n\n"+"Your account "+"XXXXXXXX"+accno.substring(8)+" has been debited by INR "+(prev_amt-updated_amt)+"\n"+
                    "Your current balance is now INR "+updated_amt+"\n\n"+
                    "If you haven't make this payment please contact your nearest branch or call at 123456789";
        }
        else if (debited == 0){
            subject = "Hey "+name+". Your balance summary";

            message = "Hey "+name+"\n\n"+"Your account "+"XXXXXXXX"+accno.substring(8)+" has a current balance of"+
                    "Remaining Balance : " + updated_amt;

        }
        else {
            subject = "Hey "+name+" Your Credit summary";
            message = "Hey "+name+"\n\n"+"Your account "+"XXXXXXXX"+accno.substring(8)+" has been credited by INR "+(updated_amt-prev_amt)+"\n"+
                    "Your current balance is now INR "+updated_amt+"\n\n"+
                    "In case of any discrepancy please contact your nearest branch or call at 123456789";
        }

        mailer.sendemail(email,subject,message);






    }
}
