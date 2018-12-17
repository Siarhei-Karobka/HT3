package com.epam.ta.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RepositoryPage extends AbstractPage{

    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://github.com/UserRepo";
    private final String COMMIT = "Test commit";
    private final String TEXT_FOR_CHANGES = "some text";

    @FindBy(xpath = "//*[@id='js-repo-pjax-container']/div/nav/a[4]")
    private WebElement settingsButton;

    @FindBy(xpath = "//a[.='README.md']")
    private WebElement readme;

    @FindBy(xpath = "//button[@aria-label='Edit this file']")
    private WebElement editFileButton;

    @FindBy(xpath = "//div[contains(@class,'CodeMirror-code')]/div[last()]")
    private WebElement editFileTextField;

    @FindBy(xpath = "//input[@id='commit-summary-input']")
    private WebElement commitFileTextField;

    @FindBy(xpath = "//a[contains(@data-pjax,'#js-repo-pjax-container')]")
    private WebElement usedRepo;

    public RepositoryPage(WebDriver driver)
    {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public void clickSettingsButton()
    {
        settingsButton.click();
    }

    public void clickForkButton(String repoName){
        WebElement forkButton;
        forkButton = driver.findElement(By.xpath("//button[@title='Fork your own copy of " + repoName + " to your account']"));
        forkButton.click();
    }

    public void findReadme(){
        readme.click();
        logger.info("Readme founded");
    }

    public void commitReadme(){
        editFileButton.click();
        editFileTextField.click();
        (new Actions(driver)).sendKeys(TEXT_FOR_CHANGES).build().perform();
        commitFileTextField.sendKeys(COMMIT);
        commitFileTextField.submit();
        logger.info("Readme commited");
    }

    public boolean isReadmeUpdated(){
        usedRepo.click();
        WebElement element;
        element = driver.findElement(By.xpath("//a[contains(text(),'" + COMMIT + "')]"));
        return element.isDisplayed();
    }

    @Override
    public void openPage()
    {
        driver.navigate().to(BASE_URL);
    }
}
