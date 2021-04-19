package grip_task_6;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;


public class test_case {
	
	public static void main(String[] args) throws IOException, InterruptedException {

		String homePage = "https://www.thesparksfoundationsingapore.org/";
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\kaush\\Desktop\\selenium\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
	
		driver.get(homePage);
		driver.manage().window().maximize();

//=============================Checking Logo of the Website=================================================

		WebElement logo = driver.findElement(By.className("navbar-brand").tagName("img"));
		System.out.print("1. Checking logo image: ");
		if (logo != null) {
			System.out.println("image is present!");
		} else {
			System.out.println("image is NOT present!");
		}

//=============================Checking Navbar of the Website========================================================

		WebElement navbar = driver.findElement(By.tagName("nav"));
		System.out.print("2. Checking Navbar: ");
		if (navbar != null) {
			System.out.println("Navigation bar is present!");
		} else {
			System.out.println("Navigation bar is NOT present!");
		}

//=============================Checking Footer of the Website========================================================
		
		System.out.println("3. Checking Map in contact page Website: ");

		WebElement footer = driver.findElement(By.className("footer"));

		File f1 = footer.getScreenshotAs(OutputType.FILE);
		
	    FileUtils.copyFile(f1, new File("screenshots/footer.png"));
	    
	    System.out.println("   SCREENSHOT IS SAVED!");
	    
	    //js.executeScript("window.scrollBy(0,-10000)", "");

//==============================Checking for broken links in Website=================================================

		System.out.println("4. Checking all the links(a-tags) in the Website: ");
		String url = "";
		HttpURLConnection huc = null;
		int respCode = 200;

		List<WebElement> links = driver.findElements(By.tagName("a"));

		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			url = it.next().getAttribute("href");

			System.out.println(url);

			if (url == null || url.isEmpty()) {
				System.out.println("URL is either not configured for anchor tag or it is empty");
				continue;
			}

			if (!url.startsWith(homePage)) {
				System.out.println("URL belongs to another domain, skipping it.");
				continue;
			}

			try {
				huc = (HttpURLConnection) (new URL(url).openConnection());

				huc.setRequestMethod("HEAD");

				huc.connect();

				respCode = huc.getResponseCode();

				if (respCode >= 400) {
					System.out.println(url + " is a broken link");
				} else {
					System.out.println(url + " is a valid link");
				}

			} catch (MalformedURLException e) {

				e.printStackTrace();

			} catch (IOException e) {

				e.printStackTrace();

			}
						
		}
		
//============================Checking if map is appearing in Website================================================
		
		System.out.println("5. Checking Map in contact page Website: ");
		
		driver.findElement(By.tagName("a").linkText("Contact Us")).click();
		
		js.executeScript("window.scrollBy(0,600)");
		Thread.sleep(3000);
		
		WebElement map = driver.findElement(By.className("map-agileits"));

		File f2 = map.getScreenshotAs(OutputType.FILE);
		
	    FileUtils.copyFile(f2, new File("screenshots/map.png"));
	    
	    System.out.println("   SCREENSHOT IS SAVED!");
		
	}
}
