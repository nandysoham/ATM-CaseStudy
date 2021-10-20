package company;

import java.util.Scanner;

public class Transaction extends dbentry{
    int cardnumber;
    int pin;
    int cvv;

    public void setCardnumber(int cardnumber) {
        this.cardnumber = cardnumber;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean getdetails(){
        boolean idfound = verifycredential(cardnumber,cvv,pin);
        if(idfound) return true;
        else return false;

//        System.out.println(this.cardnumber+" "+this.pin+" "+this.cvv);
    }

    public void credit(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the amount you want to credit");
        int creditcashamt = sc.nextInt();
        // otp - 1234;

        // otp generator
        otpgenerator otpobj = new otpgenerator();

        String client_email = getCustomeremail();
        String client_name = getCustomername();
        int validotp = otpobj.genOtp(client_email,client_name);



        int otptimes = 0;
        boolean validotpprovided = false;
        while(!validotpprovided && otptimes < 3){
            System.out.println("Enter the otp sent to your mail");
            int obtainedotp = sc.nextInt();

            if(obtainedotp == validotp){
                validotpprovided = true;
                System.out.println("Resetting Details");
                System.out.println();
                System.out.println();
                boolean creditted = creditcash(creditcashamt);
                if(creditted){
                    System.out.println("Process successful");
                }
                else{
                    System.out.println("process failed");
                }

            }
            else{
                otptimes++;
                System.out.println("incorrect otp");
                System.out.println("You have 2 more chances left");
            }

        }


    }


    public void changePIn(){
        boolean changed = changepin();
        if(changed){
            System.out.println("Process Successful");
        }
        else{
            System.out.println("some error occured during the process");
        }
    }

    public void debit(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the amount you want");
        int debitcashamt = sc.nextInt();

        // otp generator
        otpgenerator otpobj = new otpgenerator();

        String client_email = getCustomeremail();
        String client_name = getCustomername();
        int validotp = otpobj.genOtp(client_email,client_name);



        int otptimes = 0;
        boolean validotpprovided = false;
        while(!validotpprovided && otptimes < 3){
            System.out.println("Enter the otp sent to your mail");
            int obtainedotp = sc.nextInt();

            if(obtainedotp == validotp){
                validotpprovided = true;
                System.out.println("Processing Transaction....");
                System.out.println();
                System.out.println();
                boolean debitted = debitcash(debitcashamt);
                if(debitted){
                    System.out.println("Process successful");
                }
                else{
                    System.out.println("process failed");
                }

            }
            else{
                otptimes++;
                System.out.println("incorrect otp");
                System.out.println("You have 2 more chances left");
            }

        }

    }


}
