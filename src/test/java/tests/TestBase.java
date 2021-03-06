package tests;

import com.codeborne.selenide.Configuration;
import config.DeviceHost;
import config.ProjectData;
import drivers.AndroidMobileDriver;
import helpers.AllureAttachments;
import helpers.BrowserStackHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import pages.SearchFieldSteps;
import pages.SettingsScreensSteps;
import pages.StartScreensSteps;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AllureAttachments.getSessionId;

public class TestBase {
    StartScreensSteps startScreensSteps = new StartScreensSteps();
    SearchFieldSteps searchFieldSteps = new SearchFieldSteps();
    SettingsScreensSteps settingsScreensSteps = new SettingsScreensSteps();

    @BeforeAll
    public static void setup() {
        addListener("AllureSelenide", new AllureSelenide());

        Configuration.browser = AndroidMobileDriver.class.getName();
        Configuration.startMaximized = false;
        Configuration.browserSize = null;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void startDriver() {
        open();
    }

    @AfterEach
    public void afterEach() {
        AllureAttachments.screenshotAs("Last screenshot");

        if (!ProjectData.deviceHost().equals(DeviceHost.EMULATION)) {
            String sessionId = getSessionId();
            AllureAttachments.attachVideo(sessionId);

            if (ProjectData.deviceHost().equals(DeviceHost.BROWSERSTACK)) {
                AllureAttachments.attachAsText(
                        "Browserstack build link",
                        BrowserStackHelper.getBrowserstackVideoUrl(sessionId)
                );
            }
        }
        closeWebDriver();
    }
}
