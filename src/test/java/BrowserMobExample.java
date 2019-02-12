import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

public class BrowserMobExample {

    @Test
    public void test() throws Exception {
        System.setProperty("webdriver.chrome.driver", "drivers//chromedriver.exe");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        BrowserMobProxy proxy = getProxyServer(); //getting browsermob proxy

        Proxy seleniumProxy = getSeleniumProxy(proxy);

        ChromeOptions chromeOptions=new ChromeOptions();
        chromeOptions.setProxy(seleniumProxy);

        WebDriver driver = new ChromeDriver(chromeOptions);

        proxy.newHar("HTTest");

        driver.get("http://www.google.com");

        Har har = proxy.getHar();
        File harFile = new File("HTTest.har");
        har.writeTo(harFile);

        List<HarEntry> entries = proxy.getHar().getLog().getEntries();
        for (HarEntry entry : entries) {
            System.out.println(entry.getRequest().getUrl());
        }
        proxy.stop();
        driver.close();
    }

    public Proxy getSeleniumProxy(BrowserMobProxy proxyServer) {
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxyServer);
        try {
            String hostIp = Inet4Address.getLocalHost().getHostAddress();
            seleniumProxy.setHttpProxy(hostIp + ":" + proxyServer.getPort());
            seleniumProxy.setSslProxy(hostIp + ":" + proxyServer.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Assert.fail("invalid Host Address");
        }
        return seleniumProxy;
    }
    public BrowserMobProxy getProxyServer() {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start();
        return proxy;
    }
}

