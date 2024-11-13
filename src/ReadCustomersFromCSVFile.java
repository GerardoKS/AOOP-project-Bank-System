package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;


/**
 * This class implements the FileUsage interface
 * The class takes an input filePath and reads the CSV file given. It then populateds the customerList and accountList which are used for quick look up during the program
 * @author Gerardo Sillas
 */
public class ReadCustomersFromCSVFile implements FileUsage {
      /**
     * In this method every input line read is converted into Customer with fully set attributes and Account attributes. They are then returned for use in the system
     * The last dictionary is used as a way of sorting the list when you update the CSV file. CSV file can overwrite original but we decided not to just in case it is needed.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Map<?, Customer>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent maps are for quickly looking up the customer based on their name or on their account number. The last dictionary is used for keeping track of the order for later use when updating the CSV file
     */
    public void Use(String filePath){
        Random random = new Random();
        String line;
        //try to read CSV file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            line = br.readLine();
            RunBank.titles = line;
            //Get the categories so you can set the attributes of the customer and accounts properly
            String[] categories = line.split(",");
            //read each line until no more lines
            int currentID = 1;
            while((line = br.readLine())!= null){
                //split line by the commas and store in a String array
                String[] customerData = line.split(",");
                // Create customer and set all its attributes
                Customer currentAccountHolder = new Customer();
                Account checkingAccount = new Checking();
                checkingAccount.setAccountHolder(currentAccountHolder);
                Account savingAccount = new Saving();
                savingAccount.setAccountHolder(currentAccountHolder);
                Credit creditAccount = new Credit();
                creditAccount.setAccountHolder(currentAccountHolder);

                int checkingAccountNumber = 0;
                int savingAccountNumber = 0;
                int creditAccountNumber = 0;
                checkingAccountNumber = currentID + 999;
                checkingAccount.setAccountNumber(checkingAccountNumber);
                savingAccountNumber = currentID + 1999;
                savingAccount.setAccountNumber(savingAccountNumber);
                creditAccountNumber = currentID + 2999;
                creditAccount.setAccountNumber(creditAccountNumber);

                int customerDataCurrentIndex = 0;
                for(String category: categories){
                    switch (category){
                        case "Identification Number":
                            currentAccountHolder.setId(Integer.parseInt(customerData[customerDataCurrentIndex]));

                        case "First Name":
                            currentAccountHolder.setFirstName(customerData[customerDataCurrentIndex]);
                            break;
                        case "Last Name":
                            currentAccountHolder.setLastName(customerData[customerDataCurrentIndex]);
                            break;
                        case "Date of Birth":
                            currentAccountHolder.setDOB(customerData[customerDataCurrentIndex]);
                            break;
                        case "Address":
                            currentAccountHolder.setAddress(customerData[customerDataCurrentIndex]+","+customerData[customerDataCurrentIndex+ 1]+","+ customerData[customerDataCurrentIndex+2]);
                            customerDataCurrentIndex += 2;
                            break;
                        case "Phone Number":
                            currentAccountHolder.setPhoneNumber(customerData[customerDataCurrentIndex]);
                            break;

                        case "Checking Account Number":
                            checkingAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            checkingAccount.setAccountNumber(checkingAccountNumber);
                            break;

                        case "Checking Starting Balance":
                            checkingAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Savings Account Number":
                            savingAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            savingAccount.setAccountNumber(savingAccountNumber);
                            break;

                        case "Savings Starting Balance":
                            savingAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Account Number":
                            creditAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            creditAccount.setAccountNumber(creditAccountNumber);
                            break;

                        case "Credit Max":
                            creditAccount.setMax(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Starting Balance":
                            creditAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Score":
                            int creditScore = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            currentAccountHolder.setCreditScore(creditScore);
                            if(creditScore <= 580)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(599) + 100);
                            else if(581<= creditScore && creditScore<= 669)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(4299) + 700);
                            else if(670<= creditScore && creditScore<= 739)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(2499) + 5000);
                            else if(740<= creditScore && creditScore<= 799)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(8499) + 7500);
                            else
                                creditAccount.setMax(random.nextDouble() + random.nextInt(9000) + 16000);
                            break;
                                
                    }
                    customerDataCurrentIndex++;
                }
                currentAccountHolder.setId(currentID);
                currentID++;
                Dictionary<String,Account> accounts = new Hashtable<>();
                accounts.put("checking", checkingAccount);
                accounts.put("saving", savingAccount);
                accounts.put("credit", creditAccount);
                //put accoounts in the Customers accounts Dictionary
                currentAccountHolder.setAccounts(accounts);
                //create the object that will store the transactions
                Transaction transactions = new Transaction();
                //set the information in the Transaction object, informaiton is retrieved from object
                transactions.setInformation(currentAccountHolder);
                //put the Transaction object in the customer object
                currentAccountHolder.setTransactions(transactions);
                //Store Customer in Dictionary of Customers with the key as the ID
                //wrote with the getter to be more readable
                RunBank.IDList.put(currentAccountHolder.getId(), currentAccountHolder);
                RunBank.accountList.put(checkingAccountNumber, currentAccountHolder);
                RunBank.accountList.put(savingAccountNumber, currentAccountHolder);
                RunBank.accountList.put(creditAccountNumber, currentAccountHolder);
                RunBank.customerList.put(currentAccountHolder.getName(),currentAccountHolder);
                //create a list storing all the names so that when you update your CSV file you can keep the order
                RunBank.names.add(currentAccountHolder.getName());
            }
        }
        //catch if reading failed
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
