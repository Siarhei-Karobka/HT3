package com.epam.ta.steps;

import com.epam.ta.driver.DriverSingleton;
import com.epam.ta.pages.*;
import com.epam.ta.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

public class Steps {
    WebElement element;

    private WebDriver driver;

    private final Logger logger = LogManager.getRootLogger();

    public void openBrowser() {
        driver = DriverSingleton.getDriver();
    }

    public void closeBrowser() {
        DriverSingleton.closeDriver();
    }

    public void loginGithub(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openPage();
        loginPage.login(username, password);
    }

    public String getLoggedInUserName() {
        LoginPage loginPage = new LoginPage(driver);
        return loginPage.getLoggedInUserName().trim().toLowerCase();
    }

    public void createNewRepository(String repositoryName, String repositoryDescription) {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickOnCreateNewRepositoryButton();
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        createNewRepositoryPage.createNewRepository(repositoryName, repositoryDescription);
    }

    public boolean currentRepositoryIsEmpty() {
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        return createNewRepositoryPage.isCurrentRepositoryEmpty();
    }

    public String getCurrentRepositoryName() {
        CreateNewRepositoryPage createNewRepositoryPage = new CreateNewRepositoryPage(driver);
        return createNewRepositoryPage.getCurrentRepositoryName();
    }

    public String generateRandomRepositoryNameWithCharLength(int howManyChars) {
        String repositoryNamePrefix = "testRepo_";
        return repositoryNamePrefix.concat(Utils.getRandomString(howManyChars));
    }


    //********************************************
    public void forkRepository(String name) {
        MainPage mainPage = new MainPage(driver);
        mainPage.findRepo(name);
        mainPage.forkRepo(name);
    }

    public String getRepositoryDescription() {
        element = driver.findElement(By.xpath("//span[@class='text']"));
        return element.getText();
    }

    public void findUserRepository(String name) {
        MainPage mainPage = new MainPage(driver);
        mainPage.findUserRepo(name);
    }

    public boolean checkUserRepository(String name) {
        MainPage mainPage = new MainPage(driver);
        return mainPage.checkUserRepo(name);
    }

    public void clickSettings() {
        RepositoryPage repositoryPage = new RepositoryPage(driver);
        repositoryPage.clickSettingsButton();
    }

    public void clickAndDelete(String name) {
        RepositoryPageSettings repositoryPageSettings = new RepositoryPageSettings(driver);
        repositoryPageSettings.clickDeleteButton();
        repositoryPageSettings.confirmDelete(name);
    }

    public void findReadmeFile() {
        RepositoryPage repositoryPage = new RepositoryPage(driver);
        repositoryPage.findReadme();
    }

    public boolean commitReadmeFile() {
        RepositoryPage repositoryPage = new RepositoryPage(driver);
        repositoryPage.commitReadme();
        return repositoryPage.isReadmeUpdated();
    }
}
