package net.langreader.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloControllerTest {
    @Test
    public void testHelloRestController() {
        HelloRestController helloController = new HelloRestController();
        String result = helloController.getHello();
        assertEquals(result, "hello");
    }
}