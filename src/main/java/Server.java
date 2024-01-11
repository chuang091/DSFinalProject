import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Server {
	private InetAddress localhost;
	private ServerSocket serverSocket;
	
	private boolean terminate; 
	
	//Inner Class - ServerInstance - Use to connect client on a different thread (so multiple clients can be connected at the same time)
	protected class ServerInstance implements Runnable{
		//Final variables of the instance
		private final Socket clientSocket;
		private final DataInputStream dis;
		private final DataOutputStream dos;
		
		//Changing this class may be necessary
		//Possibly should be changed to the class which outputs the sorted list of websites
		//Possibly should be named as SortedList?
		//***** This part needs to be changed
		//private final FetchContent fetcher;
		private Algorithm algorithm;
		
		//Input and Output
		private String input;
		private ArrayList<String> output;
		
		//Instance Constructor
		public ServerInstance(Socket clientSocket) throws IOException {
			this.clientSocket = clientSocket;
			this.dis = new DataInputStream(clientSocket.getInputStream());
			this.dos = new DataOutputStream(clientSocket.getOutputStream());
			this.input = new String();
			this.output = new ArrayList<String>();
			//Possibly should be named in a different way
			//***** This part needs to be changed
			//this.fetcher = new FetchContent();
			algorithm = new Algorithm();
			
			//Check whether the client is still connected
			check();
		}
		
		//Start the actions of the instance (called by the Thread in the Server Constructor)
		@Override
		public void run() {
			connect();
		}
		
		//Connects with the client and read input from client
		private void connect() {
			try {
				while (!(clientSocket.isClosed())) {
					
					if (!(input = dis.readUTF()).toString().equals(null)) {
						String result = algorithm.getResult(input);
						
						String[] resultSet = result.split("\n");
						
						for (String string: resultSet) {
							output.add(string);
						}
						
						for (String s: output) {
							dos.writeUTF(s);
							dos.flush();
						}
					}
				}
			} catch (IOException e1) {
				try {
					//First type of closure - When client disconnected during data transfer OR ***JSOUP doesn't work
					dis.close();
					dos.close();
					clientSocket.close();
					System.out.println("Client Socket Closed");
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		//Checks whether the client is still connected to the server every 10 seconds
		//Runs on a different Thread do prevent blocking dis & dos
		private void check() {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					synchronized(this) {
						try {
							while (!(terminate || clientSocket.isClosed())) {
								try {
									this.wait(10000);
									dos.writeUTF("Test_Connection");
									dos.flush();
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								} catch (IOException e2) {
									System.out.println("Client Connection Lost");
									try {
										//Second type of closure - Connection Time Out (When Client is closed)
										dis.close();
										dos.close();
										clientSocket.close();
									} catch (IOException e3) {
										e3.printStackTrace();
									}
								} 
							}
							
							//Third type of closure - When Server has been stopped
							dis.close();
							dos.close();
							clientSocket.close();
						} catch (NullPointerException e4) {
							System.out.println("No Client was Ever Connected");
						} catch (IOException e5) {
							e5.printStackTrace();
						}
					}
				}
				
			});
			
			thread.start();
		}
	}
	//End of ServerInstance
	
	//These are the only two public methods of Server
	//Server Constructor
	public Server() {
		terminate = false;
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Start");
				try {
					localhost = InetAddress.getLocalHost();
					serverSocket = new ServerSocket(800);
					System.out.println("Server Started");
					System.out.printf("Please connect client to %s\n", localhost);
					
					while (!terminate) {
						Socket clientSocket = null;
						clientSocket = serverSocket.accept();
						
						new Thread(new ServerInstance(clientSocket)).start();
						System.out.println("Connected");
					}
				} catch (UnknownHostException e1) {
					System.out.println("Unknown Host");
				} catch (IOException e2) {
					System.out.println("Server Stopped");
				}
			}
		});
		
		thread.start();
	}
	
	//Closes the Server
	public void close() {
		try {
			serverSocket.close();
			terminate = true;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
