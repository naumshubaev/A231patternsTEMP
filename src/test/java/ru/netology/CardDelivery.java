package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


public class CardDelivery {

    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    String deliveryDate() {
        // deliveryDate == today+daysAdded
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
// RANDOM will not work for the first click actually
        int daysAdded = 3;
                // for Random: ThreadLocalRandom.current().nextInt(3, 20);

        return today.plusDays(daysAdded).format(dtf);
    }

    @Test
    public void shouldOrderCardDelivery() {

        String name = faker.name().fullName();
        String phone = faker.phoneNumber().subscriberNumber(10); //test: should be 10
        // another option for temporary workaround - the region code problem still exists
 //       String phoneF = faker.numerify("##########");
// move faker to utility class
            // just for visualisation
        System.out.println(name);
        System.out.println(phone);
        System.out.println(deliveryDate());
//        System.out.println(phoneF);

        Configuration.headless = true;
        Configuration.holdBrowserOpen = false;
        // fastSetValue - I have no idea what it is)))
//        Configuration.fastSetValue = true;
        open("http://localhost:9999");

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Москва");
// THE DATE is already there
//        $("[data-test-id='date'] input").doubleClick().sendKeys(deliveryDate());
        $("[data-test-id='name'] input").setValue("Василий Пупкин");
        $("[data-test-id='phone'] input").val("+78121234567");
        $("[data-test-id='agreement']>span").click();
        $$("button").find(exactText("Запланировать")).click();
        $( "[class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + deliveryDate())
                        , Duration.ofSeconds(15));
        $$("button").find(exactText("Запланировать")).click();
          $("[data-test-id='replan-notification']")
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?")
                        , Duration.ofSeconds(15));
    }
}