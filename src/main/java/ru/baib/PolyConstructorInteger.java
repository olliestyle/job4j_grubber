package ru.baib;

public class PolyConstructorInteger {
    public static void main(String[] args) {
        new D();
    }
}
class D extends C {
    private final Integer radius = 1;
    void draw() {
        System.out.println("D.draw(): " + radius);
    }
}
class C {
    void draw() {
        System.out.println("C.draw()");
    }
    public C() {
        draw();
    }
}
