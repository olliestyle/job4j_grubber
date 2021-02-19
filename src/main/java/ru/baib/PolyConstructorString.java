package ru.baib;

public class PolyConstructorString {
    public static void main(String[] args) {
        new B();
    }
}
class B extends A {
    private final String radius = "1";
    void draw() {
        System.out.println("B.draw(): " + radius);
    }
}
class A {
    void draw() {
        System.out.println("A.draw()");
    }
    public A() {
        draw();
    }
}
