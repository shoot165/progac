package jpa1;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class App {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add apartment");
                    System.out.println("2: delete apartment");
                    System.out.println("3: change apartment");
                    System.out.println("4: view apartment");
                    System.out.println("5: view apartment by price");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addApartment(sc);
                            break;
                        case "2":
                            deleteApartment(sc);
                            break;
                        case "3":
                            changeApartment(sc);
                            break;
                        case "4":
                            viewApartments();
                            break;
                        case "5":
                            showApartmentByPrice(sc);
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

    private static void addApartment(Scanner sc) {
        System.out.print("Enter apartment district: ");
        String district = sc.nextLine();
        System.out.print("Enter apartment address: ");
        String address = sc.nextLine();
        System.out.print("Enter apartment area: ");
        String sArea = sc.nextLine();
        System.out.print("Enter apartment count of rooms: ");
        String sCor = sc.nextLine();
        System.out.print("Enter apartment price: ");
        String sPrice = sc.nextLine();

        int area = Integer.parseInt(sArea);
        int cor = Integer.parseInt(sCor);
        int price = Integer.parseInt(sPrice);


        em.getTransaction().begin();
        try {
            Apartments a = new Apartments(district, address, area, cor, price);
            em.persist(a);
            em.getTransaction().commit();

            System.out.println(a.getId()); // HQL == JPQL
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteApartment(Scanner sc) {
        System.out.print("Enter apartment id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Apartments a = em.getReference(Apartments.class, id);
        if (a == null) {
            System.out.println("Apartments not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(a);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeApartment(Scanner sc) {
        System.out.print("Enter apartment new district: ");
        String district = sc.nextLine();
        System.out.print("Enter apartment new address: ");
        String address = sc.nextLine();
        System.out.print("Enter apartment new area: ");
        String sArea = sc.nextLine();
        System.out.print("Enter apartment new count of rooms: ");
        String sCor = sc.nextLine();
        System.out.print("Enter apartment new price: ");
        String sPrice = sc.nextLine();

        int area = Integer.parseInt(sArea);
        int cor = Integer.parseInt(sCor);
        int price = Integer.parseInt(sPrice);



        Apartments a;
        try {

            Query query = em.createQuery("SELECT x FROM Apartments x WHERE x.address = :address", Apartments.class);

            query.setParameter("address", address);




            a = (Apartments) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Apartments not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }


        em.getTransaction().begin();
        try {
            a.setDistrict(district);
            a.setAddress(address);
            a.setArea(area);
            a.setCountOfRooms(cor);
            a.setPrice(price);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }



    private static void viewApartments() {
        Query query = em.createQuery("SELECT a FROM Apartments a", Apartments.class);
        List<Apartments> list = (List<Apartments>) query.getResultList();

        for (Apartments a : list)
            System.out.println(a);
    }


    public static void showApartmentByPrice(Scanner sc){
        System.out.println("'>' or '<'?");

        String moreOrLess = sc.nextLine();

        if(!moreOrLess.equals(">") && !moreOrLess.equals("<")){
            System.out.println("Invalid input");
            return;
        }

        System.out.println("What price?");
        int price = sc.nextInt();
        sc.nextLine();


        Query query = em.createQuery("SELECT x FROM Apartments x WHERE x.price" + moreOrLess + " :price", Apartments.class);
        query.setParameter("price", price);

        List<Apartments> list = (List<Apartments>) query.getResultList();

        for (Apartments a : list)
            System.out.println(a);

            }

}


