package src;

import java.io.BufferedReader;
import java.io.FileReader;

class RealTransactionsAccess implements TransactionsAccess {
    public void performConfidentialTransactions() {
        String transactionsPath = "./resources/Transactions(1).csv";
        System.out.println("Transactions started");

        try(BufferedReader br = new BufferedReader(new FileReader(transactionsPath))){
            String line = br.readLine();
            while((line = br.readLine())!= null){ //conitnue reading until the line is empty
                String[] input = new String[8];
                for(int i = 0; i<7; i++){
                    input[i] = line.substring(0, line.indexOf(",")); //populate the list
                    line = line.substring(line.indexOf(",") + 1);
                }
                input[7] = line;

                //get from first and last name
                String fromFirst = input[0];
                String fromLast = input[1];
                String fromName = fromFirst + " " + fromLast;
                //get from account name/number
                String fromAccount = input[2];
                fromAccount = fromAccount.toLowerCase();
                if (fromAccount.equals("savings")) fromAccount = "saving";
                //get action
                String action = input[3];
                //get to first and last name
                String toFirst = input[4];
                String toLast = input[5];
                String toName = toFirst + " " + toLast;
                //get to account
                String toAccount = input[6];
                toAccount = toAccount.toLowerCase();
                if (toAccount.equals("savings")) toAccount = "saving";
                //get amount
                double amount = 0;
                if (!input[7].equals("")) amount = Double.parseDouble(input[7]);
                
                switch (action.toLowerCase()){
                    case("pays"):
                        RunBank.transferHelper(fromName, toName, fromAccount, toAccount, amount);
                        break;
                    case("transfers"):
                        RunBank.transferHelper(fromName, toName, fromAccount, toAccount, amount);
                        break;
                    case("inquires"):
                        RunBank.customerList.get(fromName).getBalance(fromAccount);
                        break;
                    case("withdraws"):
                        RunBank.customerList.get(fromName).deposit(fromAccount, amount);
                        break;

                    case("deposits"):
                        RunBank.customerList.get(toName).deposit(toAccount, amount);
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Transactions ended");

    }
}