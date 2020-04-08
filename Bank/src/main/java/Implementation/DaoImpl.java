package Implementation;

import entity.Wallet;
import entity.Currency;
import entity.Transaction;
import entity.User;
import DAO.WalletDAO;
import DAO.CurrencyDAO;
import DAO.DAO;
import DAO.UserDAO;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class DaoImpl implements DAO, CurrencyDAO, WalletDAO, UserDAO {
    private static EntityManagerFactory emFactory;
    private static EntityManager em;

    static {
        emFactory = Persistence.createEntityManagerFactory("Bank");
        em = emFactory.createEntityManager();
    }

    private static void transactionCommit(Object c) {
        em.getTransaction().begin();
        try {
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void insertCurrency(Scanner scanner) {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter value: ");
        int value = scanner.nextInt();
        transactionCommit(new Currency(name, value));
    }

    public void insertWallet(Scanner scanner, DaoImpl dao) {
        System.out.println("Enter currency: ");
        Currency currency = dao.getCurrencyByName(scanner.nextLine());
        System.out.println("Enter users phone num : ");
        User user = dao.getUserByNumber(scanner.nextLine());
        System.out.println("Enter quantity : ");
        int value = scanner.nextInt();
        transactionCommit(new Wallet(currency, user, value));
    }

    public void insertUser(Scanner scanner) throws Exception {
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.println("Enter email address: ");
        String email = scanner.nextLine();
        transactionCommit(new User(name, phone, email));
    }

    public List<Wallet> findAllWallets() {
        return em.createQuery("Select a from Wallet a", Wallet.class).getResultList();
    }

    public List<Wallet> findWalletsByUser(User user) {
        TypedQuery<Wallet> WalletTypedQuery = em.createQuery(
                "SELECT a from Wallet a where a.user = :user", Wallet.class);
        WalletTypedQuery.setParameter("user", user);
        return WalletTypedQuery.getResultList();

    }

    public void addMoney(Scanner scanner, DaoImpl dao) {
        System.out.println("Enter users phone num :");
        User user = dao.getUserByNumber(scanner.nextLine());
        System.out.println("Enter currency wallet :");
        Currency currency = dao.getCurrencyByName(scanner.nextLine());
        Wallet wallet = dao.getWalletByUserAndCurrency(user, currency);
        System.out.println("Enter value :");
        int value = scanner.nextInt();
        List<Wallet> list = findAllWallets();
        if (list.contains(wallet)) {
            EntityTransaction entityTransaction = em.getTransaction();
            try {
                int coefficient = transformCurrency(currency, wallet.getCurrency());
                wallet.setValue(wallet.getValue() + coefficient * value);
                entityTransaction.begin();
                em.persist(wallet);
                entityTransaction.commit();
                Transaction transaction = new Transaction(wallet, wallet, value * coefficient);
                em.persist(wallet);
                em.persist(wallet);
                em.persist(transaction);

            } catch (Exception e) {
                if (entityTransaction.isActive())
                    entityTransaction.rollback();
            }
        }
    }

    public int transformCurrency(Currency from, Currency to) {
        return from.getValue() / to.getValue();
    }

    public void transaction(Scanner scanner, DaoImpl dao) {
        System.out.println("Enter  first user phone num :");
        User userFrom = dao.getUserByNumber(scanner.nextLine());
        System.out.println("Enter first user currency wallet :");
        Currency currencyFrom = dao.getCurrencyByName(scanner.nextLine());
        Wallet walletFrom = dao.getWalletByUserAndCurrency(userFrom, currencyFrom);

        System.out.println("Enter second user phone num :");
        User userTo = dao.getUserByNumber(scanner.nextLine());
        System.out.println("Enter second user currency wallet :");
        Currency currencyTo = dao.getCurrencyByName(scanner.nextLine());
        Wallet walletTo = dao.getWalletByUserAndCurrency(userTo, currencyTo);

        System.out.println("Enter value :");
        int value = scanner.nextInt();

        if (em == null && emFactory == null) return;
        List<Wallet> list = findAllWallets();
        if (list.contains(walletFrom) && list.contains(walletTo) && (walletFrom.getValue() >= value)) {
            int coefficient = transformCurrency(walletFrom.getCurrency(), walletTo.getCurrency());
            EntityTransaction et = em.getTransaction();
            et.begin();
            try {
                walletFrom.setValue(walletFrom.getValue() - value);
                walletTo.setValue(walletTo.getValue() + value * coefficient);
                Transaction transaction = new Transaction(walletFrom, walletTo, value * coefficient);
                em.persist(walletFrom);
                em.persist(walletTo);
                em.persist(transaction);
                et.commit();
            } catch (Exception e) {
                if (et.isActive())
                    et.rollback();
            }
        }
    }

    public void summary(Scanner scanner, DaoImpl dao) {
        System.out.println("Enter users phone num :");
        User user = dao.getUserByNumber(scanner.nextLine());

        List<Wallet> Wallets = findWalletsByUser(user);

        TypedQuery<Currency> typedQueryCurrency = em.createQuery(
                "SELECT c from Currency c where c.name = :name", Currency.class);
        typedQueryCurrency.setParameter("name", "UAH");
        Currency nationalCurrency = typedQueryCurrency.getSingleResult();

        int summary = 0;
        for (Wallet Wallet : Wallets) {
            double coefficient = transformCurrency(Wallet.getCurrency(), nationalCurrency);
            summary += coefficient * Wallet.getValue();
        }
        System.out.println("Users summary in UAH = " + summary);
    }

    public void close() {
        if (em != null) em.close();
        if (emFactory != null) emFactory.close();
    }

    public Wallet getWalletByUserAndCurrency(User user, Currency currency) {
        TypedQuery<Wallet> typedQuery = em.createQuery(
                "SELECT a from Wallet a where a.user = :user and a.currency = :currency", Wallet.class);
        typedQuery.setParameter("user", user);
        typedQuery.setParameter("currency", currency);
        return typedQuery.getSingleResult();
    }

    public Currency getCurrencyByName(String name) {
        TypedQuery<Currency> typedQuery = em.createQuery(
                "SELECT c from Currency c where c.name = :name", Currency.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();
    }

    public User getUserByNumber(String phone) {
        TypedQuery<User> typedQuery = em.createQuery(
                "SELECT u from User u where u.phone = :phone", User.class);
        typedQuery.setParameter("phone", phone);
        return typedQuery.getSingleResult();
    }
}