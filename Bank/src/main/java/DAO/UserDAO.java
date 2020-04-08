package DAO;

import entity.User;

import java.util.Scanner;

public interface UserDAO {
    void insertUser(Scanner scanner) throws Exception;
    User getUserByNumber(String phone);
}
