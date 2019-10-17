import java.sql.*;

public class SQLiteDataAdapter implements IDataAdapter {

    public static final int PRODUCT_SAVED_OK = 0;
    public static final int PRODUCT_DUPLICATE_ERROR = 1;

    public static final int CUSTOMER_SAVED_OK = 0;
    public static final int CUSTOMER_DUPLICATE_ERROR = 1;

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }

    public int saveProduct(ProductModel product) {
        try {
            /*String sql = "CREATE TABLE \"Products\" (\n" +
                    "\t\"ProductID\"\tINTEGER, \n" +
                    "\t\"Name\"\tTEXT, \n" +
                    "\t\"Price\"\tNUMERIC, \n" +
                    "\t\"Quantity\"\tNUMERIC, \n" +
                    "\tPRIMARY KEY(\"ProductID\")\n" +
                    ");" +
            */
            String sql =        "INSERT INTO Products(ProductId, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }

        return PRODUCT_SAVED_OK;
    }

    public CustomerModel loadCustomer(int customerID) {
        CustomerModel customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerId, Name, Address, Phone, PaymentInfo FROM Customers WHERE CustomerId = " + customerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            customer.mCustomerID = rs.getInt("CustomerId");
            customer.mName = rs.getString("Name");
            customer.mAddress = rs.getString("Address");
            customer.mPhone = rs.getString("Phone");
            customer.mPaymentInfo = rs.getString("PaymentInfo");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public int saveCustomer(CustomerModel customer) {
        try {
            /*String sql = "CREATE TABLE \"Customers\" (\n" +
                    "\t\"CustomerID\"\tINTEGER, \n" +
                    "\t\"Name\"\tTEXT, \n" +
                    "\t\"Address\"\tTEXT, \n" +
                    "\t\"Phone\"\tTEXT, \n" +
                    "\t\"PaymentInfo\"\tTEXT, \n" +
                    "\tPRIMARY KEY(\"CustomerID\")\n" +
                    ");" +
            */
            String sql =        "INSERT INTO Customers(CustomerId, Name, Address, Phone, PaymentInfo) VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;
        }

        return CUSTOMER_SAVED_OK;
    }

    public int savePurchase(PurchaseModel purchase) {
        try {
            /*String sql = "CREATE TABLE \"Purchase\" (\n" +
                    "\t\"Purchase\"\tINTEGER, \n" +
                    "\t\"Product\"\tINTEGER, \n" +
                    "\t\"Customer\"\tINTEGER, \n" +
                    "\t\"Quantity\"\tINTEGER, \n" +
                    "\t\"Price\"\tNUMERIC, \n" +
                    "\t\"Cost\"\tNUMERIC, \n" +
                    "\t\"Tax\"\tNUMERIC, \n" +
                    "\t\"Total\"\tNUMERIC, \n" +
                    "\t\"Date\"\tTEXT, \n" +
                    "\tPRIMARY KEY(\"Purchase\")\n" +
                    ");" +
            */
            String sql = "INSERT INTO Purchase(Purchase, Product, Customer, Quantity, Price, Cost, Tax, Total, Date) VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = new PurchaseModel();

        try {
            String sql = "SELECT Purchase, Product, Customer, Quantity, Price, Cost, Tax, Total, Date FROM Purchase WHERE PurchaseId = " + purchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            purchase.mPurchaseID = rs.getInt("PurchaseID");
            purchase.mProductID = rs.getInt("ProductID");
            purchase.mCustomerID = rs.getInt("CustomerID");
            purchase.mQuantity = rs.getInt("Quantity");
            purchase.mPrice = rs.getDouble("Price");
            purchase.mCost = rs.getDouble("Cost");
            purchase.mTax = rs.getDouble("Tax");
            purchase.mTotal = rs.getDouble("Total");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }

    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

}

