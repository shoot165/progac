package academy.prog.hw;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {

            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add client");
                    System.out.println("2: add product");
                    System.out.println("3: add order");


                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addClient(sc);
                            break;
                        case "2":
                            addProduct(sc);
                            break;
                        case "3":
                            addOrder(sc);
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    public static void addClient(Scanner sc) {
        System.out.println("Enter client's name: ");
        String name = sc.nextLine();
        System.out.println("Enter client's last name: ");
        String lastName = sc.nextLine();
        System.out.println("Enter client's email: ");
        String email = sc.nextLine();


        em.getTransaction().begin();
        try{
            Client c = new Client(name, lastName, email);
            em.persist(c);
            em.getTransaction().commit();

        System.out.println("Client added");
        }catch(Exception e){
            em.getTransaction().rollback();
        }
    }

    public static void addProduct(Scanner sc) {
        System.out.println("Enter product's name: ");
        String name = sc.nextLine();
        System.out.println("Enter product's price: ");
        String pprice = sc.nextLine();

        int price = Integer.parseInt(pprice);


        em.getTransaction().begin();
        try{
            Product p = new Product(name, price);
            em.persist(p);
            em.getTransaction().commit();

            System.out.println("Product added");
        }catch(Exception e){
            em.getTransaction().rollback();
        }
    }

    public static void addOrder(Scanner sc) {
        System.out.println("Enter client's ID: ");
        String ccId = sc.nextLine();
        System.out.println("Enter product's ID: ");
        String ppId = sc.nextLine();


        int cId = Integer.parseInt(ccId);
        int pId = Integer.parseInt(ppId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        em.getTransaction().begin();
        try{
            Client clientId = em.find(Client.class, cId);
            Product productId = em.find(Product.class, pId);

        if(clientId == null || productId == null) {
            System.out.println("Invalid transaction");
            em.getTransaction().rollback();
            return;
        }

            Order o = new Order(clientId, productId, dtf.format(now));
            em.persist(o);
            em.getTransaction().commit();

            System.out.println("Order added");
        }catch(Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    }

