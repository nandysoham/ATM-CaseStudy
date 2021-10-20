package company;
import java.util.Random;


public class otpgenerator {
    public int genOtp(String email,String name){
        EmailUtility mailer = new EmailUtility();
        Random rand = new Random();

        int otp = rand.nextInt(100000);
        String subject = "Hello "+name+", Your OTP ..";
        String message = "Hey " + name + ", Your OTP for the transaction is " + otp + "\n \n " +
                "All Rights Reserved @Bank of Norway";

        mailer.sendemail(email,subject,message);
        return otp;
    }
}
