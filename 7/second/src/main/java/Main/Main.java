package Main;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Scanner;

public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPATest");
    static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        em.getTransaction().begin();
        try {
            User user1 = new User("Bob");
            User user2 = new User("Ann");

            em.persist(user1);
            em.persist(user2);

            Wallet wallet1 = new Wallet("uah", 160.00);
            Wallet wallet2 = new Wallet("usd", 200.00);
            Wallet wallet3 = new Wallet("eur", 10.00);
            Wallet wallet4 = new Wallet("uah", 112.00);
            Wallet wallet5 = new Wallet("usd", 1000.00);
            Wallet wallet6 = new Wallet("eur", 1200.00);
            em.persist(wallet1);
            em.persist(wallet2);
            em.persist(wallet3);
            em.persist(wallet4);
            em.persist(wallet5);
            em.persist(wallet6);


            user1.addWallet(wallet1);
            user1.addWallet(wallet2);
            user1.addWallet(wallet3);
            user2.addWallet(wallet4);
            user2.addWallet(wallet5);
            user2.addWallet(wallet6);

            em.persist(user1);
            em.persist(user2);


            Exchange exchange = new Exchange(41.28, 43.43, 1.0);
            exchange.addWallet(wallet1);
            exchange.addWallet(wallet2);
            exchange.addWallet(wallet3);
            exchange.addWallet(wallet4);
            exchange.addWallet(wallet5);
            exchange.addWallet(wallet6);

            em.persist(exchange);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }

        showUserWallets(em.getReference(User.class, 1L));
        walletReplenishment(em.getReference(Wallet.class,3L), 200.00);
        showUserWallets(em.getReference(User.class, 1L));


        transfer(em.getReference(Wallet.class,7L), em.getReference(Wallet.class, 5L), 200.00);
        transfer(em.getReference(Wallet.class,6L), em.getReference(Wallet.class, 3L), 100.00);

        showUserWallets(em.getReference(User.class, 1L));
        showUserWallets(em.getReference(User.class, 2L));

        convertMoney(em.getReference(User.class, 1L));
        showUserWallets(em.getReference(User.class, 1L));

        getSummary(em.getReference(User.class, 1L));
        System.out.println();

        showUserWallets(em.getReference(User.class, 1L));
        }

        public static void walletReplenishment(Wallet wallet, Double amount) {
        em.getTransaction().begin();
        try{
            wallet.setAmount(wallet.getAmount() + amount);
            Transaction transaction = new Transaction("replenishment", amount, wallet.getCurrency());
            wallet.getUser().addTransaction(transaction);
            em.merge(wallet);
            em.getTransaction().commit();

        }catch (Exception e) {
            em.getTransaction().rollback();
        }

        }

        public static void transfer (Wallet from, Wallet to, Double amount) {
        Double convertedAmount;

        em.getTransaction().begin();
        try{
            checkAmount(from, amount);
            from.setAmount(from.getAmount() - amount);
            Transaction transaction = new Transaction("transfer", amount, from.getCurrency());
            from.getUser().addTransaction(transaction);
            if(!from.getCurrency().equals(to.getCurrency())) {
                convertedAmount = convertAmount(from,to, amount);
                to.setAmount(to.getAmount() + convertedAmount);
            }else {
                to.setAmount(to.getAmount() + amount);
            }

            Transaction transaction2 = new Transaction("transfer", amount, to.getCurrency());
            to.getUser().addTransaction(transaction2);
            em.merge(from);
            em.merge(to);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
        }

        public static void checkAmount(Wallet wallet, Double amount) {
        if(wallet.getAmount() < amount) {
            throw new IllegalArgumentException("The amount in the wallet is less than requested amount");
        }
        }

        public static Double convertAmount(Wallet walletFrom, Wallet walletTo, Double amount) {
        String walletFromCurrency = walletFrom.getCurrency();
        String walletToCurrency = walletTo.getCurrency();
        Exchange exchange = walletFrom.getExchange();
        Double convertedAmount = 0.0;

        if ("usd".equals(walletFromCurrency) && "eur".equals(walletToCurrency)) {
            convertedAmount = amount * exchange.getEur() / exchange.getUsd();
        }else if ("eur".equals(walletFromCurrency) && "usd".equals(walletToCurrency)) {
            convertedAmount = amount * exchange.getUsd() / exchange.getEur();
        }else if ("usd".equals(walletFromCurrency) && "uah".equals(walletToCurrency)) {
            convertedAmount = amount * exchange.getUsd() / exchange.getUah();
        }else  if ("uah".equals(walletFromCurrency) && "usd".equals(walletToCurrency)) {
            convertedAmount = amount * exchange.getUah() / exchange.getUsd();
        }else if ("eur".equals(walletFromCurrency) && "uah".equals(walletToCurrency)) {
            convertedAmount = amount * exchange.getEur() / exchange.getUah();
        }else if ("uah".equals(walletFromCurrency) && "eur".equals(walletToCurrency)) {
            convertedAmount = amount * exchange.getUah() / exchange.getEur();
        }
        return convertedAmount;
        }

        public static void convertMoney(User user){
            Scanner sc = new Scanner(System.in);
            System.out.print("Which currency you want to convert? ");
            String fromCurrency = sc.nextLine();
            System.out.print("Enter amount? ");
            String amountInsert = sc.nextLine();
            Double amount = Double.parseDouble(amountInsert);

            System.out.print("In which currency you want to convert? ");
            String toCurrency = sc.nextLine();
            Wallet from = findUserWallet(user, fromCurrency);
            Wallet to = findUserWallet(user, toCurrency);
            Double convertedAmount = convertAmount(from,to,amount);

            em.getTransaction().begin();
            try {
                checkAmount(from, amount);
                from.setAmount(from.getAmount() - amount);
                to.setAmount(to.getAmount() + convertedAmount);
                Transaction transaction = new Transaction("convert", amount, from.getCurrency());
                Transaction transaction2 = new Transaction("convert", amount, to.getCurrency());
            user.addTransaction(transaction);
            user.addTransaction(transaction2);
            em.merge(user);
            em.merge(from);
            em.merge(to);
            em.getTransaction().commit();
            }catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
            }


        }

        public static Wallet findUserWallet(User user, String currency) {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Wallet> query = cb.createQuery(Wallet.class);
            Root<Wallet> from = query.from(Wallet.class);

            Predicate userPredicate = cb.equal(from.get("user"),user);
            Predicate currencyPredicate = cb.equal(from.get("currency"), currency);

            query.select(from).where(cb.and(userPredicate,currencyPredicate));

            return em.createQuery(query).getSingleResult();
        }


        public static void getSummary (User user) {
        Wallet walletUSD = findUserWallet(user, "usd");
        System.out.println(walletUSD);
        Wallet walletEUR = findUserWallet(user, "eur");
        Wallet walletUAH = findUserWallet(user, "uah");


        Double amountFromUSD = convertAmount(walletUSD, walletUAH, walletUSD.getAmount());
        Double amountFromEUR = convertAmount(walletEUR, walletUAH, walletEUR.getAmount());

        Double amountResult = amountFromUSD + amountFromEUR + walletUAH.getAmount();
        System.out.println("The amount is " + amountResult);


        }

        public static void showUserWallets (User user) {
        user.getWallets().forEach(System.out::println);
        }

        public static void showUserTransactions (User user) {
        user.getTransactions().forEach(System.out::println);
        }


    }
