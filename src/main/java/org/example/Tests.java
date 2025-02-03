package org.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.time.Duration.ofMillis;

public class Tests {

    WebDriver driver;

    @Before
    public void setUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.get("https://demo.opencart.com/");
        driver.manage().timeouts().implicitlyWait(ofMillis(20000)); //При открытии сайта иногда может произойти проверка на человека, которая мешает нажатиям по элементам
    }

    //Инога может выйти исключение из-за того, что на сайте может быть долгая проверка на человека, поэтому может потребоваться запускать несколько раз, чтобы тесты прошли
    @Test
    public void Task1() throws InterruptedException {
        WebElement productImage = driver.findElement(By.id("carousel-banner-0"));
        productImage.click();

        driver.get("https://demo.opencart.com/index.php?route=product/product&path=57&product_id=49");
        driver.manage().timeouts().implicitlyWait(ofMillis(10000)); // Также идет проверка на человека перед переходом на другую страницу
        WebElement productPreview = driver.findElement(By.className("magnific-popup"));
        productPreview.click();
        driver.manage().timeouts().implicitlyWait(ofMillis(5000));
        WebElement chooseNextImage = driver.findElement(By.xpath("/html/body/div[2]/div/button[2]"));
        chooseNextImage.click();
    }

    @Test
    public void Task1_Negative() { //Если находим элемент по несуществующему id
        try {
            WebElement productImage = driver.findElement(By.id("fdfdfd"));
            productImage.click();
        }
        catch (WebDriverException exe) {
            System.out.println("Исключение WebDriverException поймана");
        }
    }

    @Test
    public void Task2() {
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement dropDown = driver.findElement(By.xpath("//*[@id=\"form-currency\"]/div/a"));
        String currency = dropDown.findElement(By.xpath("//*[@id=\"form-currency\"]/div/a/strong")).getText();
        Assert.assertEquals(currency, "$");
        dropDown.click();
        driver.manage().timeouts().implicitlyWait(ofMillis(10000));

        WebElement euro = driver.findElement(By.xpath("//*[@id=\"form-currency\"]/div/ul/li[1]/a"));
        euro.click();

        driver.manage().timeouts().implicitlyWait(ofMillis(10000));

        dropDown = driver.findElement(By.xpath("//*[@id=\"form-currency\"]/div/a"));
        currency = dropDown.findElement(By.xpath("//*[@id=\"form-currency\"]/div/a/strong")).getText();
        Assert.assertEquals(currency, "€");

    }

    @Test
    public void Task2_Negative() { //выбираем валюту, которого нет в списке
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement currencyDropdown = driver.findElement(By.cssSelector("#form-currency > div > a"));
        String currency = currencyDropdown.findElement(By.cssSelector("#form-currency > div > a > strong")).getText();
        Assert.assertEquals("$", currency);

        currencyDropdown.click();

        driver.manage().timeouts().implicitlyWait(ofMillis(5000));

        try {
            driver.findElement(By.xpath("RUB"));
        }
        catch (WebDriverException exe) {
            System.out.println("Такой валюты нет в списке");
        }
    }

    @Test
    public void Task3() {
        // Тут когда новое окно открывается, то сделайте его во весь экран, чтобы тест прошел. Хз почему он так работает
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement desktopDropdown = driver.findElement(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/a"));
        desktopDropdown.click();

        WebElement choosePCButton = driver.findElement(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/div/div/ul/li[1]/a"));
        choosePCButton.click();

        WebElement listOfProducts = driver.findElement(By.xpath("//*[@id=\"content\"]/p"));

        Assert.assertEquals("There are no products to list in this category.", listOfProducts.getText());
    }

    @Test
    public void Task3_Negative() {
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement desktopDropdown = driver.findElement(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/a"));
        desktopDropdown.click();

        WebElement choosePCButton = driver.findElement(By.xpath("//*[@id=\"narbar-menu\"]/ul/li[1]/div/div/ul/li[1]/a"));
        choosePCButton.click();

        WebElement listOfProducts = driver.findElement(By.xpath("//*[@id=\"content\"]/p"));

        Assert.assertNotEquals("PC1, PC2, Pc3", listOfProducts.getText());
    }

    @Test
    public void Task4() {
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement chooseFromMyAccountList = driver.findElement(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/a"));
        chooseFromMyAccountList.click();

        WebElement chooseRegister = driver.findElement(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/ul/li[1]"));
        chooseRegister.click();

        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement firstNameInput = driver.findElement(By.xpath("//*[@id=\"input-firstname\"]"));
        firstNameInput.sendKeys("Ben");
        firstNameInput.sendKeys(Keys.ENTER);

        WebElement lastNameInput = driver.findElement(By.xpath("//*[@id=\"input-lastname\"]"));
        lastNameInput.sendKeys("Benson");
        lastNameInput.sendKeys(Keys.ENTER);

        WebElement emailInput = driver.findElement(By.xpath("//*[@id=\"input-email\"]"));
        emailInput.sendKeys("ben12356343@mail.com");
        emailInput.sendKeys(Keys.ENTER);

        WebElement passwordInput = driver.findElement(By.xpath("//*[@id=\"input-password\"]"));
        passwordInput.sendKeys("1234567890");
        passwordInput.sendKeys(Keys.ENTER);

        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement agreePrivacyPolicy = driver.findElement(By.name("agree"));
        agreePrivacyPolicy.click();

        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"form-register\"]/div/button"));
        continueButton.click();

    }

    @Test
    public void Task4_Negative() { //Нажали на continue, но не ввели имя пользователя
        driver.navigate().refresh();
        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement chooseFromMyAccountList = driver.findElement(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/a"));
        chooseFromMyAccountList.click();

        WebElement chooseRegister = driver.findElement(By.xpath("//*[@id=\"top\"]/div/div[2]/ul/li[2]/div/ul/li[1]"));
        chooseRegister.click();

        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        WebElement continueButton = driver.findElement(By.xpath("//*[@id=\"form-register\"]/div/button"));
        continueButton.click();

        Assert.assertEquals("First Name must be between 1 and 32 characters!", driver.findElement(By.id("error-firstname")).getText());
    }

    @Test
    public void Task5() {
        WebElement searchInput = driver.findElement(By.name("search"));
        searchInput.sendKeys("iMac");
        searchInput.sendKeys(Keys.ENTER);

        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        Assert.assertEquals("iMac", driver.findElement(By.xpath("//*[@id=\"product-list\"]/div/div/div[2]/div/h4/a")).getText());
    }

    @Test
    public void Task5_Negative() { //Ничего не вводим в поисковик и нажимаем enter
        WebElement searchInput = driver.findElement(By.name("search"));
        searchInput.sendKeys("");
        searchInput.sendKeys(Keys.ENTER);

        driver.manage().timeouts().implicitlyWait(ofMillis(20000));

        Assert.assertEquals("There is no product that matches the search criteria.", driver.findElement(By.xpath("//*[@id=\"content\"]/p")).getText());
    }
}