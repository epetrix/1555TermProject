import java.util.Scanner;
import java.io.Console;

public class Prompter {
  private static final Scanner input = new Scanner(System.in);
  private static final Console console = System.console();

	public static String getSecret() {
		if(console == null) return input.nextLine();

		char[] password = console.readPassword();
		String secret = new String(password);
		java.util.Arrays.fill(password, '\0');
		return secret;
	}

  public static boolean getBoolean() {
    return input.nextLine().toLowerCase().equals("y");
  }

  public static int getInt(String prompt) {
    int answer;
    while(true) {
      System.out.print(prompt);
      String line = input.nextLine();

      try {
        answer = Integer.parseInt(line);
        break;
      } catch(NumberFormatException ex) {
        System.out.println("Answer must be an integer!");
      }
    }
    return answer;
  }

  public static char getChoice(char start, char end) {
    char answer;
    start = Character.toLowerCase(start);
    end = Character.toLowerCase(end);

    while(true) {
      System.out.print("Choice: ");
      String line = input.nextLine();

      if(line.length() == 1) {
        answer = Character.toLowerCase(line.charAt(0));
        if(answer >= start && answer <= end) break;
      }

      System.out.println("Answer must fall between '" + start + "' and '" + end + "'");
    }
    return answer;
  }
}
