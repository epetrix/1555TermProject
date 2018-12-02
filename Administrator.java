import java.util.Scanner;

public class Administrator {
  private static final Scanner input = new Scanner(System.in);

  private static void displayMenu() {
    System.out.println("***************************");
    System.out.println("* Administrator Interface *");
    System.out.println("***************************");
    System.out.println("a) New customer registration");
    System.out.println("b) Update system date");
    System.out.println("c) Product statistics");
    System.out.println("d) Exit menu");
    System.out.println();
  }

  private static char getChoice(char start, char end) {
    char answer;

    System.out.print("Choice: ");
    while(true) {
      String line = input.nextLine();
      if(line.length() == 1) {
        answer = line.charAt(0);
        if(answer >= start && answer <= end) break;
      }

      System.out.println("Answer must fall between '" + start + "' and '" + end + "'");
      System.out.print("Choice: ");
    }
    return answer;
  }

  public static void openMenu() {
    char answer;
    do {
      displayMenu();
      answer = getChoice('a', 'd');
      switch(answer) {
        case 'a':
        registerCustomer();
        break;

        case 'b':
        updateSysDate();
        break;

        case 'c':
        getProductStats();
        break;

        case 'd':
        System.out.println("Exiting admin interface");
        break;
      }
      System.out.println();
    } while(answer != 'd');
  }

  private static void registerCustomer() {
    System.out.println("Registering customer...");
  }

  private static void updateSysDate() {
    System.out.println("Updating system date...");
  }

  private static void getProductStats() {
    System.out.println("Getting product statistics...");
  }
}
