import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import entities.user;
import service.userService;
import service.URLShortener;

public class App {
    public static void main(String[] args) {
        System.out.println("-------------------Running-------------------");
        Scanner scanner = new Scanner(System.in);
        int option=0;
        userService userService;
        URLShortener URLShortener;
        try{
            userService = new userService();
            URLShortener=new URLShortener();
        }catch(IOException ex){
            System.out.println("-------------------There is something wrong-------------------");
            return;
        }
        while (option <=3){
            System.out.println("-------------------Choose option-------------------");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. URL-Shortner");
            System.out.println("4. exit");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Enter the username to signup");
                    String nameToSignUp = scanner.next();
                    System.out.println("Enter the password to signup");
                    String passwordToSignUp = scanner.next();
                    user userToSignup = new user(nameToSignUp, passwordToSignUp);
                    userService.signUp(userToSignup);
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = scanner.next();
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = scanner.next();
                    user userToLogin = new user(nameToLogin, passwordToLogin);
                    if (userService.loginUser(userToLogin)) {
                        System.out.println("-------------------Login successful-------------------");
                    } else {
                        System.out.println("-------------------Login failed-------------------");
                    }
                    break;
                case 3:
                    if (userService.isLogged()) {
                        System.out.println("Enter the long URL to shorten:");
                        String longURL = scanner.next();
                        try {
                            String shortURL = URLShortener.shortenURL(longURL);
                            System.out.println("Shortened URL: " + shortURL);
                            System.out.println("Opening the URL in your default browser...");
                            openURLInBrowser(longURL);
                        } catch (IOException e) {
                            System.out.println("Error occurred while shortening the URL.");
                        }
                    } else {
                        System.out.println("-------------------You need to log in first to use the URL shortener.-------------------");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    break;
            }

        }
    }

    private static void openURLInBrowser(String url) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                System.out.println("Failed to open the URL in the browser.");
            }
        } else {
            System.out.println("Desktop is not supported. Cannot open the URL in the browser.");
        }
    }
}
