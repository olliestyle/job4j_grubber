package ru.baib;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TestFirstTest {

    @Test
    public void simpleTest() {
        assertThat(TestFirst.doPlusOne(4), is(5));
    }

}