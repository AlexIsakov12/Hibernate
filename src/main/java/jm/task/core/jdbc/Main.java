package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alexey","Belyakov", (byte) 20);
        userService.saveUser("Dmitry","Makarov", (byte) 30);
        userService.saveUser("Igor","Tokarev", (byte) 40);
        userService.saveUser("Ivan","Ivanov", (byte) 50);

        for (User u: userService.getAllUsers()) {
            System.out.println(u.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
