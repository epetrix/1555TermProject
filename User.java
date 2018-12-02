import java.util.scanner;

public abstract class User {
  public final Connection connection;
  public final Scanner input;

  public User(connection) {
    this.connection = connection;
    this.input = new Scanner(System.in);
  }

  public abstract void openMenu();

  public char getChoice(char start, char end) {
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
}
