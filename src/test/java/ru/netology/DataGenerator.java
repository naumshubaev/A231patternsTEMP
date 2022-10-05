package ru.netology;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {

    static String deliveryDate() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int daysAdded = ThreadLocalRandom.current().nextInt(3, 20);
        return today.plusDays(daysAdded).format(dtf);
    }

    static String name() {
        Faker faker;
        faker = new Faker(new Locale("ru"));
        String name = faker.name().fullName();
        return name;
    }

//    String phone = faker.phoneNumber().subscriberNumber(10); //test: should be 10
    // another option for temporary workaround - the region code problem still exists
    //       String phoneF = faker.numerify("##########");
}