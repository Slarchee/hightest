package newpackage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class VerifReussite {
	
	public static void main(String[] args) {
    	//Déclaration et instantiation des objets et variables
		System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		driver.manage().window().maximize();
        
		//Déclaration des variables 
		String baseUrl = "https://hightest.nc/";
        String yopMail = "https://yopmail.com/fr/";
        String resultatBonneReponse = "Le mail reçu indique bien 100 % de bonne réponses";
        String resultatMauvaiseReponse = "Le mail reçu indique une valeur inférieur à 100 % de bonne réponses";
        String verificationReussite = "répondu à 20 question(s) sur 20, soit 100&nbsp;%";
        String yopMailAdresse = "charles.douradou@yopmail.com";
        String yopMailDebAdresse = "charles.douradou";
        //Reponses aux questions dans l'ordre dans un tableau
        int[] myArray = new int[]{1,2,1,2,2,3,2,4,1,3,4,2,3,2,4,3,3,1,2,2};
        
        //Script
        //Ouvrir la page d'accueil de hightest
        driver.get(baseUrl);
        
        //Cliquer sur le menu ToolBox
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[contains(@href,'https://hightest.nc/toolbox/')]")));
        
        //Cliquer sur le test "Quiz ISTQB niveau Foundation" en français
        executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[contains(@href,'https://hightest.nc/ressources/test-istqb.php')]")));
        
        //Ce positionner sur la nouvelle tab
        for(String qcmPage : driver.getWindowHandles()){
            driver.switchTo().window(qcmPage);
            driver.manage().window().maximize();
        }
        
        //Réponses aux questions
        for (int i=0; i<myArray.length; i++) {
            System.out.println(myArray[i]);
            executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//input[@name='"+i+"' and @value='"+myArray[i]+"']")));
        }
        
        //Valider le formulaire en cliquant sur le bouton "Terminé !"
        executor.executeScript("arguments[0].click();", driver.findElement(By.id("submit")));

        //Renseigner l'addresse mail dans le champ "Votre adresse e-mail"
        driver.findElement(By.id("email")).sendKeys(yopMailAdresse);
        
        //Valider le formulaire en cliquant sur le bouton "OK" 
        driver.findElement(By.id("submitMail")).click();
        
        //Ouvrir la page d'accueil de yopmail
        driver.get(yopMail);
        
        //Accepter les cookies
        driver.findElement(By.id("accept")).click();
        
        //Renseigner l'addresse mail dans le champ "Saisissez le mail jetable de votre choix"
        driver.findElement(By.id("login")).sendKeys(yopMailDebAdresse);
        
        //Appuyer sur Entrer pour valider l'adresse mail
        driver.findElement(By.id("login")).sendKeys(Keys.ENTER);
        
        //Appuyer sur le bouton refresh pour affichier le dernier mail reçu
        driver.findElement(By.id("refresh")).click();
        
        //Permet de ce concentrer sur la frame ifmail contenant les informations à vérifier
        driver.switchTo().frame(driver.findElement(By.name("ifmail")));
        
        //Condition qui permet de confirmer si le mail retourne 100% de réussite ou non
        if(driver.getPageSource().contains(verificationReussite))
        	System.out.println(resultatBonneReponse);
        else
        	System.out.println(resultatMauvaiseReponse);

        //Fermer les fenêtres Chrome
        driver.quit();
    }
}