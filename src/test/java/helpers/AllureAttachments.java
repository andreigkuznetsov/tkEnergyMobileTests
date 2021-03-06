package helpers;

import config.DeviceHost;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static config.ProjectData.deviceHost;

public class AllureAttachments {
    @Attachment(value = "{attachName}", type = "text/plain")
    public static String attachAsText(String attachName, String message) {
        return message;
    }

    @Attachment(value = "{attachName}", type = "image/png")
    public static byte[] screenshotAs(String attachName) {
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Video", type = "text/html", fileExtension = ".html")
    public static String attachVideo(String sessionId) {
        return "<html><body><video width='100%' height='100%' controls autoplay><source src='"
                + getVideoUrl(sessionId)
                + "' type='video/mp4'></video></body></html>";
    }

    private static String getVideoUrl(String sessionId) {
        if (deviceHost().equals(DeviceHost.BROWSERSTACK)) {
            return BrowserStackHelper.getBrowserstackVideoUrl(sessionId);
        } else if (deviceHost().equals(DeviceHost.SELENOID)) {
            return SelenoidHelper.getSelenoidVideoUrl(sessionId);
        }
        return null;
    }

    public static String getSessionId() {
        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }
}
