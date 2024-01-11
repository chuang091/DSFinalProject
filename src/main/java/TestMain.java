import java.util.Scanner;

public class TestMain {
	FetchContent fetcher;

	public static void main(String[] args) {
		//Server Runs on a different Thread, so simply construct a server and that's it
		Server server = new Server();
		Scanner scanner = new Scanner(System.in);
		
		while (!scanner.next().toLowerCase().equals("end")) {
			System.out.println("Pause");
		}
		System.out.println("Operation Succeed");
		
		//Please remember to close the server when you wish to
		server.close();
		scanner.close();
		System.out.println("Main Thread Stopped");
		System.out.println("Please Wait Until Program Terminates");
	}

}
