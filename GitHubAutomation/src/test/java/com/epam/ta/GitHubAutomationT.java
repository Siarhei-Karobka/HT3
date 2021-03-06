package com.epam.ta;

import com.epam.ta.steps.Steps;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GitHubAutomationT {
    private Steps steps;
    private final String USERNAME = "testautomationuser";
    private final String PASSWORD = "Time4Death!";
    private final int REPOSITORY_NAME_POSTFIX_LENGTH = 6;
    private final String FIND_REPOSITORY = "vitalliuss/automation-samples";
    private final String DELETE_REPOSITORY = "automation-samples";
    private final String COMMIT_REPOSITORY = "automation-samples";

    @BeforeMethod(description = "Init browser")
    public void setUp()
    {
        steps = new Steps();
        steps.openBrowser();
    }

    @Test
    public void oneCanCreateProject()
    {
        steps.loginGithub(USERNAME, PASSWORD);
        String repositoryName = steps.generateRandomRepositoryNameWithCharLength(REPOSITORY_NAME_POSTFIX_LENGTH);
        steps.createNewRepository(repositoryName, "auto-generated test repo");

        Assert.assertEquals(steps.getCurrentRepositoryName(), repositoryName);
    }

    @Test(description = "Login to Github")
    public void oneCanLoginGithub()
    {
        steps.loginGithub(USERNAME, PASSWORD);
        Assert.assertEquals(USERNAME, steps.getLoggedInUserName());
    }

    //fork existing repository
    @Test
    public void forkProject(){
        steps.loginGithub(USERNAME,PASSWORD);
        steps.forkRepository(FIND_REPOSITORY);
        Assert.assertEquals(steps.getRepositoryDescription(), "forked from " + FIND_REPOSITORY);
    }

    //delete existing repository
    @Test
    public void deleteRepository(){
        steps.loginGithub(USERNAME, PASSWORD);
        steps.findUserRepository(DELETE_REPOSITORY);
        steps.clickSettings();
        steps.clickAndDelete(DELETE_REPOSITORY);
        Assert.assertFalse(steps.checkUserRepository(DELETE_REPOSITORY));
    }

    //try to commit readme file in user repository and check changes
    @Test
    public void commitFile(){
        steps.loginGithub(USERNAME, PASSWORD);
        steps.findUserRepository(COMMIT_REPOSITORY);
        steps.findReadmeFile();
        Assert.assertTrue(steps.commitReadmeFile());
    }

    @AfterMethod(description = "Stop Browser")
    public void stopBrowser()
    {
        steps.closeBrowser();
    }
}
