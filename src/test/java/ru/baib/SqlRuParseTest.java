package ru.baib;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class SqlRuParseTest {

    SqlRuParse sqlRuParser = new SqlRuParse();

    @Test
    public void test() {
        String url = "https://www.sql.ru/forum/1332697/java-programmist-urovnya-middle-senior";
        Post post = sqlRuParser.detail(url);
        assertEquals(LocalDateTime.of(2021, Month.JANUARY, 20, 22, 40, 00),
                post.getCreationDate());
        assertEquals("Java-программист уровня middle-senior", post.getName());
    }

    @Test
    public void test2() {
        String url = "https://www.sql.ru/forum/1327195/back-end-developer-ruby-v-insurtech-startap-v-germanii";
        Post post = sqlRuParser.detail(url);
        assertEquals(LocalDateTime.of(2020, Month.JULY, 9, 19, 27, 00),
                post.getCreationDate());
        assertEquals("Back-End Developer (Ruby) в InsurTech стартап в Германии", post.getName());
    }

}