import Implementation.DaoImpl;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        DaoImpl dao = new DaoImpl();
        int command = 0;
        while (true) {
            System.out.print("1. Add new user\n2. Change Currency\n3. Add wallet\n4. Add money\n5. Show sum in UAH\n6. Transaction\n" +
                    "7. Exit\n>> ");
            command = in.nextInt();
            switch (command) {
                case 1: {
                    Scanner iin = new Scanner(System.in);
                    dao.insertUser(iin);
                }
                break;
                case 2: {
                    Scanner iin = new Scanner(System.in);
                    dao.insertCurrency(iin);
                }
                break;
                case 3: {
                    Scanner iin = new Scanner(System.in);
                    dao.insertWallet(iin, dao);
                }
                break;
                case 4: {
                    Scanner iin = new Scanner(System.in);
                    dao.addMoney(iin, dao);
                }
                break;
                case 5: {
                    Scanner iin = new Scanner(System.in);
                    dao.summary(iin, dao);
                }
                break;
                case 6: {
                    Scanner iin = new Scanner(System.in);
                    dao.transaction(iin, dao);
                }
                break;
                case 7: {
                    dao.close();
                }
                break;
                default:
                    System.out.println("Wrong Command");
            }
        }
    }
}