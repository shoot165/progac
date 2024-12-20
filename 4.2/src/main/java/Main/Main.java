package Main;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
     try {
          try ( Scanner sc = new Scanner(System.in); Connection connection = ConnectionFactory.getConnection()) {
              initDB(connection);

              while (true) {
                  System.out.println("Enter command: ");
                  System.out.print("1 - add client ");
                  System.out.print("2 - add product ");
                  System.out.print("3 - add order ");
                  System.out.print("4 - view clients ");
                  System.out.print("5 - view products ");
                  System.out.print("6 - view orders ");

                  String command = sc.nextLine();
                  switch (command) {
                      case "1":
                          addClient(sc, connection);
                          break;
                      case "2":
                          addProduct(sc, connection);
                          break;
                      case "3":
                          addOrder(sc, connection);
                          break;
                      case "4":
                          viewClients(connection);
                          break;
                      case "5":
                          viewProducts(connection);
                          break;
                      case "6":
                          viewOrders(connection);
                          break;
                      default:
                          System.out.println("Invalid command");
                          return;
                  }


              }


          }
      }catch (SQLException e){
          e.printStackTrace();
          return;
      }
  }




    private static void initDB (Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();
        try{
            stmt.execute("DROP TABLE IF EXISTS Products");
            stmt.execute("CREATE TABLE Products (id INT NOT NULL" +
                    "AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) " +
                    "NOT NULL, category VARCHAR(20)");

            stmt.execute("DROP TABLE IF EXISTS Clients");
            stmt.execute("CREATE TABLE Clients (id INT NOT NULL" +
                    "AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) " +
                    "NOT NULL, last_name VARCHAR(20) NOT NULL");

            stmt.execute("DROP TABLE IF EXISTS Orders");
            stmt.execute("CREATE TABLE Orders (id INT NOT NULL" +
                    "AUTO_INCREMENT PRIMARY KEY, client_id INT NOT NULL) " +
                    ", date DATE NOT NULL");

            stmt.execute("DROP TABLE IF EXISTS OrderItems");
            stmt.execute("CREATE TABLE OrderItems (order_id INT NOT NULL REFERENCES Orders(id) "+
                    ", product_id INT NOT NULL REFERENCES Products(id)");


        } finally {
            stmt.close();
        }

    }


    private static void addClient (Scanner sc, Connection conn) throws SQLException {
        System.out.println("Please enter your client name: ");
        String name = sc.nextLine();
        System.out.println("Please enter your client last name: ");
        String lastName = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Clients (name, last_name) VALUES (?, ?)");
        try {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    private static void addProduct(Scanner sc, Connection conn)throws SQLException {
        System.out.println("Please enter product name: ");
        String productName = sc.nextLine();
        System.out.println("Please enter product category: ");
        String productCategory = sc.nextLine();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO Products (name, category) VALUES (?, ?)");
        try {
            ps.setString(1, productName);
            ps.setString(2, productCategory);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }


    private static void addOrder(Scanner sc, Connection conn) throws SQLException {
        System.out.println();
        System.out.println("Please enter order id: ");
        String productId = sc.nextLine();
        int productOrderId = Integer.parseInt(productId);


        System.out.println("Please enter client id: ");
        String clientId = sc.nextLine();
        int clientOrderId = Integer.parseInt(clientId);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();



        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO Orders (client_id, date) VALUES (?, ?)")){
            ps.setInt(1, clientOrderId);
            ps.setString(2, dtf.format(now));
            ps.executeUpdate();
            System.out.println("Order added");
        }


        try (PreparedStatement ps2 = conn.prepareStatement("SELECT id FROM Orders WHERE client_id = ?")){
            ps2.setInt(1, clientOrderId);
            ResultSet rs = ps2.executeQuery();

            rs.next();
            int orderId = rs.getInt("Id");


            try (  PreparedStatement ps3 = conn.prepareStatement("INSERT INTO OrderItems (order_id, product_id) VALUES (?, ?)")){
                ps3.setInt(1, orderId);
                ps3.setString(2, productId);
                ps3.executeUpdate();
                System.out.println("Order item added");

            }
        }
    }

private static void viewResult (Connection conn, PreparedStatement ps) throws SQLException {

try (ResultSet rs = ps.executeQuery()){

        ResultSetMetaData rsmd = rs.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); i++)
            System.out.println(rsmd.getColumnName(i) + "\t\t");
        System.out.println();

        while (rs.next()) {
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                System.out.print(rs.getString(i) + "\t\t");
            }
            System.out.println();
        }
    }finally {
        conn.close();
    }
}

private static void viewClients (Connection conn) throws SQLException {
       try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Clients")){
           viewResult(conn, ps);}
       }

    private static void viewProducts (Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Products")){
            viewResult(conn, ps);}
}

    private static void viewOrders (Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM Orders")){
            viewResult(conn, ps);}
    }

    private static void viewOrdersProducts(Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM OrderItems")){
            viewResult(conn, ps);}
    }

}



