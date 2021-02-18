package ru.baib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsersParser {

    private List<User> userList = new ArrayList<>();

    public void parse() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                    User user = new User();
                    user.setName(line);
                    user.setAge(Integer.valueOf(br.readLine()));
                    user.setProf(br.readLine());
                    userList.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUserList() {
        return this.userList;
    }

    public static void main(String[] args) {
        UsersParser parser = new UsersParser();
        parser.parse();
        for (User user: parser.getUserList()) {
            System.out.println(user);
        }
    }
}
