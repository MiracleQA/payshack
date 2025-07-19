package payshack_qa;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class OrderIdCheck {

	public static void main(String[] args) {
		
		
		// List of order IDs to check
		List<String> orderIds = List.of(
			    "6680670702", "6695627902", "6718905002", "6718896802", "6719643202",
			    "6719253602", "6719373802", "6719643002", "6721440102", "6720848002",
			    "6720016502", "6719833902", "6721724902", "6721035702", "6720391702",
			    "6723327002", "6722670702", "6720744202", "6720400902", "6722953902",
			    "6722657402", "6721907902", "6722945502", "6723093102", "6723102702",
			    "6720398702", "6720399402", "6720849902", "6724100902", "6724095002",
			    "6724042202", "6739991001", "6740054701", "6739662401", "6741427701",
			    "6770018301", "6771129701", "6785494401", "6787153301", "6796018601",
			    "6816509101", "6816747601", "6805965201", "6816597701", "6837173402"
			);
	        WebDriver driver = new ChromeDriver();
	        try {
	            // Login process
	            driver.get("https://dashboard.payshack.in/login");
	            driver.manage().window().maximize();
	            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	            WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
	            email.sendKeys("daniil.s@monetix.pro");
	            
	            WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
	            password.sendKeys("MONEclt@123");
	            
	            driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();  // login button
	            
	            Thread.sleep(500);
	            // Navigate to orders page
	            driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/a")).click();
	            
	            // Create a list to store results
	            List<String> results = new ArrayList<>();
	            
	            for (String orderId : orderIds) {
	                try {
	                    // Open search dialog
	                    driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[2]/header/div/div/div[1]/div[3]/div/button")).click(); //filter button in the payyin
	                    
	                    // Enter order ID and search
//	                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/div/input")); //enter order id
	                   
	                    WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[5]/div/input")); //enter order id
	                    searchInput.clear();
	                    searchInput.sendKeys(orderId);
	                    
	                    driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/button[2]")).click();
	                    
	                    // Wait for results to load
	                    Thread.sleep(5000);
	                    
	                    // Get status and date
	                    String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[8]/div")).getText();
	                    String dateOfTransaction = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[2]")).getText();
	                    
//	                    String clientname = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr[1]/td[4]")).getText();
	                    String trxnid = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[4]")).getText();
	                    String amount = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[7]")).getText();
	                    
	                    
	                    
	                    // Store result
	                    String result = String.format("Order ID: %-20s | Date: %-15s | Status: %s", 
	                            orderId, dateOfTransaction, status);
	                    results.add(result);
	                    
	                    // Print current result
	                    System.out.println(result);
	                    
	                } catch (Exception e) {
	                    String errorResult = "Order ID: " + orderId + " - Error: " + e.getMessage();
	                    results.add(errorResult);
	                    System.out.println(errorResult);
	                    e.printStackTrace();
	                }
	            }
	            
	            // Print all results at the end
	            System.out.println("\nFinal Results:");
	            for (String result : results) {
	                System.out.println(result);
	            }
	        
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            driver.quit();
	        }
	    }
	}
		
		

	


