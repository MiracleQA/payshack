package payshack_qa;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Payout_OderCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 List<String> orderIds = List.of(
				 "00006797836201",
				    "00006795161601",
				    "00006912797301",
				    "00006914690301",
				    "00006892353701",
				    "00006892353501",
				    "00006892345501",
				    "00006892647701",
				    "00006893148601",
				    "00006892352301",
				    "00006892352701",
				    "00006890989401",
				    "00006892645601",
				    "00006892352801",
				    "00006892426101",
				    "00006892672901",
				    "00006892673301",
				    "00006892674201",
				    "00006892645401",
				    "00006892352601",
				    "00006913877101",
				    "00006795695401",
				    "00006912214801",
				    "00006796106101",
				    "00006801600501",
				    "00006921424301",
				    "00006923412601",
				    "00006901836001",
				    "00006886356801",
				    "00006892646101",
				    "00006892646001",
				    "00006892344701",
				    "00006892345301",
				    "00006893284801",
				    "00006892352101",
				    "00006892549501",
				    "00006892344401",
				    "00006892344501",
				    "00006892299301"     		    
     		);

     WebDriver driver = new ChromeDriver();
     
     try {
         // Login process
         driver.get("https://dashboard.payshack.in/login");
         driver.manage().window().maximize();
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

         WebElement email = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[1]/div/input"));
         email.sendKeys("admin-user@indigate.in");
         
         WebElement password = driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/div[2]/div/div[1]/input"));
         password.sendKeys("User@12345");

         driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div[3]/button")).click();  // login
         
         // Navigate to orders page
         driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/a")).click();
         System.out.println("fghjk");
         driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/div/a[2]")).click();
         
         
         Thread.sleep(1000);
//         
         driver.findElement(By.xpath("/html/body/div/div/div[1]/div[3]/div[3]/div[2]/a[2]")).click();
//         
//         // Create a list to store results
         List<String> results = new ArrayList<>();
         
         for (String orderId : orderIds) {
             try {
//                 // Open search dialog
                 driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[2]/header/div/div/div[1]/div[2]/div/button")).click();
//                 
//                 // Enter order ID and search
                 WebElement searchInput = driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[7]/div/input"));
                 searchInput.clear();
                 searchInput.sendKeys(orderId);
                 
                 driver.findElement(By.xpath("/html/body/div[2]/div[3]/ul/li/div/div[2]/div[10]/button[2]")).click();
//                 
//                 // Wait for results to load
                 Thread.sleep(2000);
//                 
        // Get status
                 String status = driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/div[3]/table/tbody/tr/td[11]/div")).getText();
//                 
           // Store result
                 results.add("Order ID: " + orderId + " - Status: " + status);
                 System.out.println("Order ID: " + orderId + " - Status: " + status);
                 
             } catch (Exception e) {
                 results.add("Order ID: " + orderId + " - Error: " + e.getMessage());
                 System.out.println("Failed to check Order ID: " + orderId);
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
