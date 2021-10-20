
package company;
import java.util.Scanner;

// id --> 1000
// pass --> abcd123

public class ATMScreen {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("WELCOME TO BANK OF NORWAY");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println();
        System.out.println("Enter 1 for Transaction");
//        System.out.println("Enter 2 for Checking Balance");
//        System.out.println("Enter 3 for Changing pin");
        System.out.println("Enter 2 (for SuperUser");

        int choice = sc.nextInt();
        if(choice == 1){
            Transaction tr = new Transaction();
            System.out.println("Enter 16 digit card number");
            int cardno = sc.nextInt();
            System.out.println("enter the 3 digit cvv");
            int cvv = sc.nextInt();
            System.out.println("enter the pin");
            int pin = sc.nextInt();
            tr.setCardnumber(cardno);
            tr.setCvv(cvv);
            tr.setPin(pin);
            boolean accepted = tr.getdetails();
            if(accepted){
                System.out.println("USER LOGGED IN");
                System.out.println("------------------------------------------");
                System.out.println();
                System.out.println("Press 1 for Cash Withdrawal");
                System.out.println("Press 2 for knowing balance");
                System.out.println("Press 3 for changing pin number");
                System.out.println("Press 4 for depositing money");
                int userchoice = sc.nextInt();
                boolean validopion = false;

                // running an infinite loop for taking a valid entry!!!
                while(!validopion){
                    validopion = true;
                    if(userchoice == 1){
                        tr.debit();

                    }
                    else if (userchoice == 2){
                        // knowing balance
                        boolean prbal = tr.printbalance(0,0);


                    }
                    else if(userchoice == 3){
                        // changing the pin number
                        tr.changePIn();


                    }
                    else if(userchoice == 4){
                        // credit
                        tr.credit();

                    }
                    else{
                        validopion = false;
                        System.out.println("please enter a valid option");
                    }
                }

            }
            else{
                System.out.println("Please enter correct credentials");
            }
        }
        else if(choice == 2){
            System.out.println("Enter the superuser id");
            int sid = sc.nextInt();
            System.out.println("enter the superuser password");
            String spass = sc.next();

            if(sid == 1000 && spass.equals("abcd123")){
                System.out.println("Hi user");
                superuser su = new superuser();
                System.out.println("enter the name of the client");
                String clientname = sc.next();
                System.out.println("enter the email id of the client");
                String clientemail = sc.next();
                System.out.println("enter the bank name ");
                String bankname  = sc.next();
                System.out.println("enter the Branch Name");
                String branch = sc.next();
                System.out.println("Enter the inditial amount");
                int initialcash = sc.nextInt();

                su.setBankname(bankname);
                su.setBranch(branch);
                su.setEmail(clientemail);
                su.setName(clientname);
                su.setCash(initialcash);

                boolean added = su.adduser();
                if(added) System.out.println("user added!!!");
                else System.out.println("some error occured");

            }

        }

    }
}
