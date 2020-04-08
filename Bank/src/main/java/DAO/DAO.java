package DAO;

import Implementation.DaoImpl;
import java.util.Scanner;

public interface DAO {
    void addMoney(Scanner scanner, DaoImpl dao);
    void transaction(Scanner scanner, DaoImpl dao);
    void summary(Scanner scanner, DaoImpl dao);
}
