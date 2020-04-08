package DAO;

import Implementation.DaoImpl;
import entity.Wallet;
import entity.Currency;
import entity.User;

import java.util.List;
import java.util.Scanner;

public interface WalletDAO {
    void insertWallet(Scanner scanner, DaoImpl dao);
    List<Wallet> findAllWallets();
    List<Wallet> findWalletsByUser(User user);
    Wallet getWalletByUserAndCurrency(User user, Currency currency);
}
