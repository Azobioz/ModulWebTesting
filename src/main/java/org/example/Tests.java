package org.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.time.Duration.ofMillis;

public class Tests {

    WebDriver driver;

    @Before
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://demo.opencart.com/");
        Thread.sleep(10000); //При открытии сайта идет проверка на человека, которая мешает нажатиям по элементам
    }

    @Test
    public void Task1() throws InterruptedException {
        WebElement productImage = driver.findElement(By.id("carousel-banner-0"));
        productImage.click();
        driver.get("https://demo.opencart.com/index.php?route=product/product&path=57&product_id=49");
        driver.manage().timeouts().implicitlyWait(ofMillis(10000)); //Долго потому-что там проверка на человека перед переходом на другую страницу
        WebElement productPreview = driver.findElement(By.className("magnific-popup"));
        productPreview.click();
        driver.manage().timeouts().implicitlyWait(ofMillis(5000));
        WebElement chooseNextImage = driver.findElement(By.xpath("/html/body/div[2]/div/button[2]"));
        chooseNextImage.click();
    }

    @Test
    public void Task1_Negative() {
        try {
            WebElement productImage = driver.findElement(By.id("fdfdfd"));
            productImage.click();
        }
        catch (WebDriverException exe) {
            System.out.println("Исключение WebDriverExсeption поймана");
        }
    }

    @Test
    public void Tast2() {
        WebElement currency = driver.findElement(By.className("dropdown-toggle"));
        currency.click();
        driver.manage().timeouts().implicitlyWait(ofMillis(10000));
        WebElement changeCurrency = driver.findElement(By.xpath("/html/body/nav/div/div[1]/ul/li[1]/form/div/ul/li[1]/a"));
        changeCurrency.click();
    }
}