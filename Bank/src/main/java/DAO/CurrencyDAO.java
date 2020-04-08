package DAO;

import entity.Currency;

import java.util.Scanner;

public interface CurrencyDAO {
    void insertCurrency(Scanner scanner);
    Currency getCurrencyByName(String name);
}