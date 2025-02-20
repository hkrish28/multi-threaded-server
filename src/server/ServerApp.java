package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ServerApp is the entry point for starting the server.
 */
public class ServerApp {

  private static final int SERVER_COUNT = 5;

  /**
   * The main method starts the server by initializing and binding the RMI server implementation.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {

    List<Integer> serverPorts = new ArrayList<>();
    if (args.length == SERVER_COUNT) {
      try {
        for (int i = 0; i < SERVER_COUNT; i++) {
          serverPorts.add(Integer.parseInt(args[i]));
        }
      } catch (NumberFormatException e) {
        System.out.println("Invalid port provided. Reverting to defaults...");
      }
    } else
      serverPorts = Arrays.asList(5000, 5001, 5002, 5003, 5004);

    Coordinator coordinator = new CoordinatorImpl(serverPorts);
    ServerOperator serverOperator = new ServerOperator(coordinator);
    serverOperator.start();
    try {
      serverOperator.join(); // Wait for ServerOperator to complete
    } catch (InterruptedException e) {
      System.out.println("Main thread interrupted while waiting for server operator to close.");
    }
    System.out.println("Main thread exiting");
    System.exit(0);
    }

}
