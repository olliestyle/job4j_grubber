package ru.baib;

import java.util.Objects;

public class User {

    private String name;
    private int age;
    private String prof;

    public User() {

    }

    public User(String name, int age, String prof) {
        this.name = name;
        this.age = age;
        this.prof = prof;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return age == user.age
                && Objects.equals(name, user.name)
                && Objects.equals(prof, user.prof);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, prof);
    }

    @Override
    public String toString() {
        return "User{"
                + "name='" + name + '\''
                + ", age=" + age
                + ", prof='" + prof + '\''
                + '}';
    }
}
