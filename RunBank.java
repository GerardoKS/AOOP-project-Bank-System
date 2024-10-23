import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;
/*
 * Driver class containg User Interface and CSV conversion to create the bank system. 
 * @author Gerardo Sillas
 * @author Hannah Ayala
 */
public class RunBank{
    public static void main(String args[]){
        //declare file location (file path)
        String filePath = "./CS 3331 - Bank Users.csv";
        //read CSV file and create a list of "Customer"s from the entreis in the file
        List<Map<?, Customer>> customerList = listOfCustomersFromCSV(filePath);

        filePath = "./Result";
        //Update the CSV with any changes made the to the list of "Customer"s
        updateCSVFile(customerList, filePath);

    }
    /*
     * converts the entries given in the CSV to a List of "Customer" building each "Customer" object and their 3 subclass "Account" objects.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Map<Object, Object>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent maps are for quickly looking up the customer based on their name or on their account number
     */
    private static List<Map<?,Customer>> listOfCustomersFromCSV(String filePath){
        List<Map<?,Customer>> customerList = new ArrayList<>();
        Map<String,Customer> customerNameList = new HashMap<>();
        Map<Integer,Customer> customerAccountNumberList = new HashMap<>();
        String line;
        //try to read CSV file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            boolean skipCategoryNames = true;
            //read each line until no more lines
            while((line = br.readLine())!= null){
                //skip first lines since it is just attribute names
                if(skipCategoryNames){
                    skipCategoryNames = false;
                    continue;
                }
                //split line by the commas and store in a String array
                String[] customerData = line.split(",");
                // Create customer and set all its attributes
                Customer currentAccountHolder = new Customer();
                currentAccountHolder.setId(Integer.parseInt(customerData[0]));
                currentAccountHolder.setFirstName(customerData[1]);
                currentAccountHolder.setLastName(customerData[2]);
                currentAccountHolder.setDOB(customerData[3]);
                currentAccountHolder.setAddress(customerData[4]);
                currentAccountHolder.setPhoneNumber(customerData[5]);
                //Create the Accounts and set all its attributes
                int checkingAccountNumber = Integer.parseInt(customerData[6]);
                double checkingAccountBalance = Double.parseDouble(customerData[7]);
                Account checkingAccount = new Checking(checkingAccountNumber, checkingAccountBalance, currentAccountHolder);

                int savingAccountNumber = Integer.parseInt(customerData[8]);
                double savingAccountBalance = Double.parseDouble(customerData[9]);
                Account savingAccount = new Saving(savingAccountNumber, savingAccountBalance, currentAccountHolder);

                int creditAccountNumber = Integer.parseInt(customerData[10]);
                double creditMax = Double.parseDouble(customerData[11]);
                double creditAccountBalance = Double.parseDouble(customerData[12]);
                Account creditAccount = new Credit(creditAccountNumber,creditAccountBalance, currentAccountHolder, creditMax);
                //set the accounts in the Customer object
                Account[] accounts = new Account[] {checkingAccount, savingAccount, creditAccount};
                currentAccountHolder.set_accounts(accounts);
                //Store Customer in Map of Customers with the key as the ID
                //wrote with the getter to be more readable
                customerAccountNumberList.put(checkingAccountNumber, currentAccountHolder);
                customerAccountNumberList.put(savingAccountNumber, currentAccountHolder);
                customerAccountNumberList.put(creditAccountNumber, currentAccountHolder);
                customerNameList.put(currentAccountHolder.getFirstName()+" "+currentAccountHolder.getLastName(),currentAccountHolder);
            }
        }
        //catch if reading failed
        catch(IOException e){
            e.printStackTrace();
        
        }
        //return all customers read 
        customerList.add(customerAccountNumberList);
        customerList.add(customerNameList);
        return customerList;
    }
    /*
     * Update the CSV File with the new entries and any altered entry that happened throughout the life cycle of the program
     * @param List<Customer> list of Customers that are going have there attributes and account attributes convereted into strings and updated in the CSV file
     * @param filePath to increase flexibility, filePath added incase needed again for future project
     */
    public static void updateCSVFile(List<Map<?,Customer>> customerList, String filePath){
        //try to update CSV
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            //write the titles before the data
            String titles = "Identification Number,First Name,Last Name,Date of Birth,Address,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance";
            writer.write(titles);
            writer.newLine();
            //Update each customer one by one
            for(Customer currentAccountHolder : customerList.values()){
                //turn Customer attribute into a String
                String data =   Integer.toString(currentAccountHolder.getId())+","+currentAccountHolder.getFirstName()+","+currentAccountHolder.getLastName()+","+ currentAccountHolder.getDOB()+"," currentAccountHolder.getAddress()+","+ currentAccountHolder.getPhoneNumber()+",";
                //get accounts and store in more descriptive varaibles for readability
                Account[] accounts = currentAccountHolder.get_accounts();
                Account checkingAccount = accounts[0];
                Account savingAccount = accounts[1];
                Account creditAccount = accounts[2];
                //turn Accounts' attributes into String and then added them to the end of data
                data = data + Integer.toString(checkingAccount.getAccountNumber()) + "," + Double.toString(checkingAccount.getBalance()) + "," + Integer.toString(savingAccount.getAccountNumber()) + "," + Double.toString(savingAccount.getBalance()) + "," + Integer.toString(creditAccount.getAccountNumber()) + "," + Double.toString(creditAccount.getMax()) +","+ Double.toString(creditAccount.getBalance());
                //write Customers data into the CSV file
                writer.write(data);
                //create the next line for the next entry
                writer.newLine();
            }
        }
        //catch if update fails
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
