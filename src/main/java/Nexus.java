import java.util.Scanner;

public class Nexus {
    public static void main(String[] args) {
        String line = "____________________________________________________________";
        String banner = " _   _  _____  __  __  _   _  _____\n"
                + "| \\ | || ____| \\ \\/ / | | | ||  ___|\n"
                + "|  \\| ||  _|    \\  /  | | | || |___ \n"
                + "| |\\  || |___   /  \\  | |_| | ___| |\n"
                + "|_| \\_||_____| /_/\\_\\  \\___/ |____/ ";
        System.out.println(banner);
        System.out.println("Hello! I'm Nexus.");
        System.out.println("What can I do for you?");
        System.out.println(line);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            if (command.equals("bye")) {
                System.out.println(line);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(line);
                break;
            }
            System.out.println(line);
            System.out.println(command);
            System.out.println(line);
        }
        scanner.close();
    }
}