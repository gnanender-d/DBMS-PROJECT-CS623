import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//Group 2 – The depot d1 is deleted from Depot and Stock.
public class Main {

    public static void main(String args[]) throws SQLException, IOException, ClassNotFoundException {

        // Load the MySQL driver
        Class.forName("org.postgresql.Driver");
        //Enter Server Port followed by Database Name
        String url = "jdbc:postgresql://localhost:5432/group2";
        //Enter your own machine's root here
        String root = "postgres";
        //Enter your own postgres password here
        String password = "postgres";
        //Establishing connection
        Connection conn = DriverManager.getConnection(url, root, password);
        // For atomicity
        conn.setAutoCommit(false);

        // For isolation
        conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        Statement stmt = null;
        try {

            stmt = conn.createStatement();

            //Tables Before delete operation
            System.out.println("Before delete operation\n");


            ResultSet productsTable = stmt.executeQuery("select * from Product;");
            System.out.println("Product Table:");
            System.out.println("prodid " + "pname " + "price");
            while(productsTable.next()) {
                System.out.println( productsTable.getString("prodid")
                        + "\t " + productsTable.getString("pname")
                        + "\t " + productsTable.getInt("price"));
            }

            ResultSet depotTable = stmt.executeQuery("select * from Depot;");
            System.out.println("\nDepot Table:");
            System.out.println("depid " + "address " + "volume");
            while(depotTable.next()) {
                System.out.println( depotTable.getString("depid")
                        + "\t " + depotTable.getString("addr")
                        + "\t " + depotTable.getInt("volume"));
            }

            ResultSet stockTable = stmt.executeQuery("select * from Stock;");
            System.out.println("\nStock Table");
            System.out.println("prodid " + "depid " + "quantity");
            while(stockTable.next()) {
                System.out.println(stockTable.getString("prodid")
                        + "\t " + stockTable.getString("depid")
                        + "\t " + stockTable.getInt("quantity"));
            }

            System.out.println("\nDeleting d1 from Depot table");
            stmt.executeUpdate("DELETE FROM Depot WHERE depid = 'd1'");
            System.out.println("\nDeleted d1 from Depot table and Stock table");

            //Tables Before delete operation
            System.out.println("After delete operation\n");


            ResultSet productsTableAfter = stmt.executeQuery("select * from Product;");
            System.out.println("Product Table:");
            System.out.println("prodid " + "pname " + "price");
            while(productsTableAfter.next()) {
                System.out.println( productsTableAfter.getString("prodid")
                        + "\t " + productsTableAfter.getString("pname")
                        + "\t " + productsTableAfter.getInt("price"));
            }

            ResultSet depotTableAfter = stmt.executeQuery("select * from Depot;");
            System.out.println("\nDepot Table:");
            System.out.println("depid " + "address " + "volume");
            while(depotTableAfter.next()) {
                System.out.println( depotTableAfter.getString("depid")
                        + "\t " + depotTableAfter.getString("addr")
                        + "\t " + depotTableAfter.getInt("volume"));
            }

            ResultSet stockTableAfter = stmt.executeQuery("select * from Stock;");
            System.out.println("\nStock Table");
            System.out.println("prodid " + "depid " + "quantity");
            while(stockTableAfter.next()) {
                System.out.println(stockTableAfter.getString("prodid")
                        + "\t " + stockTableAfter.getString("depid")
                        + "\t " + stockTableAfter.getInt("quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Error: "+e);

            // For atomicity
            conn.rollback();
            stmt.close();
            conn.close();
            return;
        } // main

        System.out.println("\n-----Data is now Committed-----");

        // clean-up / conclude environment
        conn.commit();
        stmt.close();
        conn.close();

    }
}