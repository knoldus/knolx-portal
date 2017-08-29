package seltest

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.{By, WebDriver}
import org.scalatest._

/**
  * Created by vimalesh-mishra on 29/8/17.
  */
class ForgotSpec extends FlatSpec with ShouldMatchers  {

  "The Knolx-Portal Login page" should "have the correct login" in {
    System.setProperty("webdriver.chrome.driver", "/home/vimalesh-mishra/Pictures/chromedriver")
    val capabilities = DesiredCapabilities.chrome()
    val driver: WebDriver = new ChromeDriver(capabilities)
    val host = "http://knolx.knoldus.com/"
    /*--- Open Home Page ---*/
    driver.get(host)
    driver.manage().window().maximize()
    Thread.sleep(5000)
    /*--- Login Page ---*/
    driver.findElement(By.xpath("/html/body/div[2]/nav/div[2]/ul/li[2]/a")).click()
    Thread.sleep(5000)
    /*--- Forgot password page with invalid email ---*/
    driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[2]/div[2]/div[2]/a")).click()
    driver.findElement(By.name("email")).clear()
    driver.findElement(By.name("email")).sendKeys("jasdkjah@jfhkjsdf.com")
    driver.findElement(By.xpath("//*[@id=\"forgot-Password-Form\"]/div[2]/div[2]/input")).click()
    Thread.sleep(5000)
    /*--- Forgot password page with valid email ---*/
    driver.findElement(By.name("email")).clear()
    driver.findElement(By.name("email")).sendKeys("vimalesh@knoldus.com")
    driver.findElement(By.xpath("//*[@id=\"forgot-Password-Form\"]/div[2]/div[2]/input")).click()


    Thread.sleep(10000)
    driver.close()

  }

}
