import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class TimeServer {
	private static int PORT = 27780;
	private ServerSocket serverSocket;
	
	private long offset = 1100;  

	public TimeServer() {
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("Server started on port: " + PORT);
			Socket socket;
			while(true){
				 socket = serverSocket.accept();
				 NTPRequestHandler  sthread= new NTPRequestHandler(socket);
				 sthread.run();
			}

		} catch (IOException e) {
			e.printStackTrace();
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	private void threadSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TimeServer();
	}

	private class NTPRequestHandler implements Runnable {
		private Socket client;
		private ObjectInputStream is = null;
		private ObjectOutputStream os = null;
		private long randomDelay;
		
		public NTPRequestHandler(Socket client) {
			this.client = client;
		}

		@Override
		public void run() {
			try {
				is = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
				os = new ObjectOutputStream(client.getOutputStream());
				
				//receive the reqeust
				Object obj = is.readObject();
				NTPRequest request = (NTPRequest) obj;
				
				sendNTPAnswer(request);
				
				//close the stream
				is.close();
				os.close();
				client.close();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			

		}

		private void sendNTPAnswer(NTPRequest request) throws IOException, ClassNotFoundException {
	
			// write t2
			request.setT2(getLocalClock());
		
			// delay
			randomDelay = (long) (10+Math.random()*90);    // 10 ~ 100 ms;
			threadSleep(randomDelay);
			
			// write t3
			request.setT3(getLocalClock());
			os.writeObject(request);
			os.flush();
			
		}
		
		private long getLocalClock() {
			return (long) (new Date().getTime() + offset);
		}

	}

}
