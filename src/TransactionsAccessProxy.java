package src;

class TransactionsAccessProxy implements TransactionsAccess {
    private RealTransactionsAccess realTransactionsAccess;
    private String userRole;

    public TransactionsAccessProxy(String userRole) {
        this.userRole = userRole;
    }

    public void performConfidentialTransactions() {
        if ("Manager".equalsIgnoreCase(userRole)) {
            if (realTransactionsAccess == null) {
                realTransactionsAccess = new RealTransactionsAccess();
            }
            System.out.println("Proxy: Access granted. Forwarding request to RealTransactionsAccess.");
            realTransactionsAccess.performConfidentialTransactions();
        } else {
            System.out.println("Proxy: Access denied. Only managers can view the confidential Transactions.");
        }
    }
}