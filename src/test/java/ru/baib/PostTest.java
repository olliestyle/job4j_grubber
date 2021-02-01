package ru.baib;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class PostTest {
    @Test
    public void test() {
        String url = "https://www.sql.ru/forum/1332697/java-programmist-urovnya-middle-senior";
        Post post = new Post();
        post.fillPost(url);
        assertEquals(LocalDateTime.of(2021, Month.JANUARY, 20, 22, 40, 00),
                     post.getCreationDate());
    }
}