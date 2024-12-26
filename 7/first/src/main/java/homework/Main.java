package homework;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
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
                    System.out.println("1: Add dish");
                    System.out.println("2: Price (from to)");
                    System.out.println("3: Only at discount");
                    System.out.println("4: Weight under 1 kg");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addDish(sc);
                            break;
                        case "2":
                            showByPrice(sc);
                            break;
                        case "3":
                            showAtDiscount(sc);
                            break;
                        case "4":
                            showWeightUnderOneKilogram(sc);
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

    public static void showByPrice(Scanner sc) {
        System.out.println("Enter the price from which we will start the search: ");
        String dStart = sc.nextLine();
        System.out.println("Enter the price at which we will end the search: ");
        String dFinish = sc.nextLine();


       double start = Double.parseDouble(dStart);
       double finish = Double.parseDouble(dFinish);

        Query query = em.createQuery("SELECT d FROM Menu d WHERE d.price >= :dStart AND d.price <= :dFinish");

        query.setParameter("dStart", start);
        query.setParameter("dFinish", finish);

        List<Menu> dishes = query.getResultList();
        for (Menu dish : dishes) {
            System.out.println(dish);
        }

    }

    public static void addDish(Scanner sc) {
        System.out.println("Enter dish name: ");
        String dishName = sc.nextLine();
        System.out.println("Enter dish price: ");
        String dPrice = sc.nextLine();
        System.out.println("Enter dish weight: ");
        String dWeight = sc.nextLine();
        System.out.println("Is dish at discount? (true/false): ");
        Boolean isDishOnSale = Boolean.parseBoolean(sc.nextLine());

        double dishPrice = Double.parseDouble(dPrice);
        double dishWeight = Double.parseDouble(dWeight);


        em.getTransaction().begin();
        try{
            Menu m = new Menu(dishName, dishPrice, dishWeight, isDishOnSale);
            em.persist(m);
            em.getTransaction().commit();

            System.out.println("Dish added to menu");
        }catch(Exception e){
            em.getTransaction().rollback();
        }
    }

    public static void showAtDiscount(Scanner sc) {


        Query query = em.createQuery("SELECT d FROM Menu d WHERE d.discount = true");


        List<Menu> dishes = query.getResultList();
        for (Menu dish : dishes) {
            System.out.println(dish);
        }

    }

    public static void showWeightUnderOneKilogram(Scanner sc) {

        double totalWeight = 0;

       Query query = em.createQuery("SELECT d FROM Menu d ORDER BY d.weight ASC");
        List<Menu> dishes = query.getResultList();
        for (Menu dish : dishes) {
            totalWeight += dish.getWeight();
            if (totalWeight > 1000) {
                break;
            }

            System.out.println(dish);
        }



}}

