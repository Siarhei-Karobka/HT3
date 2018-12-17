package com.epam.ta.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RepositoryPageSettings extends AbstractPage {

    private final Logger logger = LogManager.getRootLogger();
    private final String BASE_URL = "https://github.com/UserRepo/settings";

    @FindBy(xpath = "//summary[contains(text(),'Delete this repository')]")
    private WebElement deleteRepoButton;

    @FindBy(xpath = "//input[@name='verify']")
    private WebElement confirmDeleteTextBox;

    @FindBy(xpath = "//button[contains(text(),'I understand the consequences, delete this reposit')]")
    private WebElement confirmDeleteButton;

    public RepositoryPageSettings(WebDriver driver) {
        super(driver);
        PageFactory.initElements(this.driver, this);
    }

    public void clickDeleteButton() {
        deleteRepoButton.click();
    }

    public void confirmDelete(String deleteTextBox) {
        confirmDeleteTextBox.sendKeys(deleteTextBox);
        confirmDeleteButton.click();
        logger.info("repository deleted");
    }

    @Override
    public void openPage() {
        driver.navigate().to(BASE_URL);
    }
}
