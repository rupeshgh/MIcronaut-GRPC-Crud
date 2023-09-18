package com.studentportal;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.*;

@MicronautTest
class StudentPortalTest {


    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void testing() {
        List<Integer> li = List.of(1, 2, 5, 7, 9, 22, 11, 24);

        //Consumer
        Consumer<Integer> integerConsumer = value -> System.out.println(value);
        li.forEach(integerConsumer);


        //Map iteration
        Map<Integer, String> mp = Map.of(1, "hello", 2, "hi", 3, "bye");
        for (Map.Entry<Integer, String> elem : mp.entrySet()) {
            System.out.println(elem.getKey() + " " + elem.getValue());
        }

        //Biconsumer
        BiConsumer<Integer, String> mapConsumer = (k, v) -> System.out.println("key =" + k + "::value=" + v);
        mp.forEach(mapConsumer);

        //Function
        Function<String, Integer> lenFunction = String::length;
        Function<String, Integer> hashunction = Objects::hashCode;
        System.out.println(lenFunction.apply("hello"));

        //supplier
        Random random = new Random();
        Supplier<Integer> getRandomInt = () -> random.nextInt();
        System.out.println("Supply random:" + getRandomInt.get());


        //Predicate
        Predicate<String> checkEven = (value) -> value.length() % 2 == 0;
        System.out.println("Length of string is even ?" + checkEven.test("hello"));


//        li.stream().map()


    }

}
