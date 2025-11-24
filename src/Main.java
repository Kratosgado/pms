import utils.ConsoleMenu;

public class Main {

  public static void main(String[] args) {
    ConsoleMenu menu = new ConsoleMenu();
    menu.printMenu();
    int choice = menu.getChoice();

    while (choice != 5) {
      switch (choice) {
        case 1:
          menu.setTitle("Add Project");
          break;
        case 2:
          System.out.println("View Projects");
          break;
        case 3:
          System.out.println("Add Task");
          break;
        case 4:
          System.out.println("View Tasks");
          break;
        case 5:
          System.out.println("Exit");
          break;
        default:
          System.out.println("Invalid Choice");
      }
      choice = menu.getChoice();
    }
  }
}
