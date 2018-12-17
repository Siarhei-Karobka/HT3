package com.epam.ta.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainPage extends AbstractPage
{
	WebElement element;

	private final String BASE_URL = "https://github.com/";

	@FindBy(xpath = "//summary[@aria-label='Create new…']")
	private WebElement buttonCreateNew;

	@FindBy(xpath = "//a[contains(text(), 'New repository')]")
	private WebElement linkNewRepository;

	@FindBy(xpath = "//input[@placeholder='Search or jump to…']")
	private WebElement searchField;

	@FindBy(xpath = "//input[@id='dashboard-repos-filter-sidebar']")
	private WebElement findField;

	@FindBy(xpath = "//a[contains(@class,'js-selected-navigation-item selected reponav-item')]")
	private WebElement settingsButton;

	@FindBy(xpath = "//summary[contains(text(),'Delete this repository')]")
	private WebElement deleteButton;

	public MainPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(this.driver, this);
	}

	public void clickOnCreateNewRepositoryButton()
	{
		buttonCreateNew.click();
		linkNewRepository.click();
	}

	//*************************************

	public void findRepo (String repoName){
		searchField.sendKeys(repoName);
		searchField.submit();
	}

	public void forkRepo (String repoName){
		element = driver.findElement(By.xpath("//a[@href = '/" + repoName + "']"));
		element.click();

		RepositoryPage repositoryPage = new RepositoryPage(driver);
		repositoryPage.clickForkButton(repoName);

		element = driver.findElement(By.xpath("//button[@title='Fork your own copy of " + repoName + " to your account']"));
		element.click();
	}

	public String getRepositoryDescription(){
		element = driver.findElement(By.xpath("//span[@class='text']"));
		return element.getText();
	}

	public void findUserRepo (String userRepoName){
		findField.sendKeys(userRepoName);
		element = driver.findElement(By.xpath("//a/span[@title='" + userRepoName + "']/.."));
		element.click();
	}

	public boolean checkUserRepo (String userRepoName){
		try {
			findField.sendKeys(userRepoName);
			element = driver.findElement(By.xpath("//a/span[@title='" + userRepoName + "']/.."));
			return true;
		} catch (NoSuchElementException e){
			return false;
		}
	}

	@Override
	public void openPage()
	{
		driver.navigate().to(BASE_URL);
	}
}
