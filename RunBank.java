import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RunBank{
    public static void main(String args[]){
        String filePath = "./CS 3331 - Bank Users.csv";
        List<Customer> customerList = listOfCustomersFromCSV(filePath);
        System.out.println("customerList obtained");

    }
    private static List<Customer> listOfCustomersFromCSV(String filePath){
        List<Customer> customerList = new ArrayList<>();
        String line;

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            boolean skipCategoryNames = true;
            while((line = br.readLine())!= null){
                if(skipCategoryNames){
                    skipCategoryNames = false;
                    continue;
                }
                String[] customerData = line.split(",");

                Customer accountHolder = new Customer();
                accountHolder.setId(Integer.parseInt(customerData[0]));
                accountHolder.setFirstName(customerData[1]);
                accountHolder.setLastName(customerData[2]);
                accountHolder.setDOB(customerData[3]);
                accountHolder.setAddress(customerData[4]);
                accountHolder.setPhoneNumber(customerData[5]);

                int checkingAccountNumber = Integer.parseInt(customerData[6]);
                double checkingAccountBalance = Double.parseDouble(customerData[7]);
                Account checkingAccount = new Checking(checkingAccountNumber, checkingAccountBalance, accountHolder);

                int savingAccountNumber = Integer.parseInt(customerData[8]);
                double savingAccountBalance = Double.parseDouble(customerData[9]);
                Account savingAccount = new Saving(savingAccountNumber, savingAccountBalance, accountHolder);

                int creditAccountNumber = Integer.parseInt(customerData[10]);
                double creditMax = Double.parseDouble(customerData[11]);
                double creditAccountBalance = Double.parseDouble(customerData[12]);
                Account creditAccount = new Credit(creditAccountNumber,creditAccountBalance, accountHolder, creditMax);

                Account[] accounts = new Account[] {checkingAccount, savingAccount, creditAccount};

                accountHolder.set_accounts(accounts);
                customerList.add(accountHolder);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return customerList;
    }
}
