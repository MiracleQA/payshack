package payshack_qa;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Schedule_Killer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		for (int executionCount = 1; executionCount <= 4; executionCount++) {
            WebDriver driver = new ChromeDriver();
            System.out.println("Starting execution #" + executionCount);
            
            try {
                // Configure browser settings
                driver.get("https://dashboard.payshack.in/login");
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                // Login process
                WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
                email.sendKeys("admin-user@indigate.in");
                
                WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
                password.sendKeys("User@12345");

                driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();
                
                // Create explicit wait for the menu item
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement menuItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("/html/body/div/div/div[1]/div[3]/div[12]/a")));
                
                // Scroll to the element
                ((JavascriptExecutor)driver).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                    menuItem);
                
                // Wait briefly for scroll to complete
                Thread.sleep(500);
                
                // Click the element
                menuItem.click();
                
                // Wait for page to load
                Thread.sleep(2000);
                
                // Scroll to bottom of the table container
                WebElement tableContainer = driver.findElement(By.xpath("//div[contains(@style, 'height: 76vh')]"));
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight)", tableContainer);
                
                // Find all rows in the table
                List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
                
                boolean buttonFound = false;
                
                // Iterate through rows from bottom to top
                for (int i = rows.size() - 1; i >= 0; i--) {
                    WebElement row = rows.get(i);
                    
                    // Check if this row has a second button in Actions column
                    try {
                        WebElement button2 = row.findElement(By.xpath(".//td[7]//button[2]"));
                        
                        // Scroll the button into view
                        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", button2);
                        
                        // Highlight the button
                        ((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", button2);
                        
                        // Click the button
                        button2.click();
                        System.out.println("Execution #" + executionCount + ": Successfully clicked button 2 in row " + (i+1));
                        buttonFound = true;
                        
                        // Wait for action to complete
                        Thread.sleep(300);
                        break;
                    } catch (Exception e) {
                        // Button not found in this row, continue searching
                        continue;
                    }
                }
                
                if (!buttonFound) {
                    System.out.println("Execution #" + executionCount + ": No button 2 (Deactivate) found in any row's Actions column");
                }
                
            } catch (Exception e) {
                System.err.println("Execution #" + executionCount + " error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Close the browser for this execution
                driver.quit();
                System.out.println("Completed execution #" + executionCount);
                
                // Add delay between executions (optional)
                if (executionCount < 4) {
                    try {
                        Thread.sleep(2000); // 2 second delay between executions
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        System.out.println("All 4 executions completed");
		
	}

}
