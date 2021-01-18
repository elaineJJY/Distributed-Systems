import java.io.BufferedInputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;


public class TimeClient {
	private static String hostUrl = "127.0.0.1";
	private static int PORT = 27780;
	private Double minD;
	private Double minOffset;
	private NTPRequest minNTPrequest;
	private Socket socket;

	private double offset;  
	
	
	public TimeClient() throws ClassNotFoundException {

		try {
			System.out.println("----Begin----");
			
			// 10 times measurement
			for (int i = 0; i < 10; i++) {
				socket = new Socket(InetAddress.getByName(hostUrl), PORT);
				System.out.print("[Messure: "+(i+1)+" ]   ");
				
				//send request
				minNTPrequest = new NTPRequest();
				sendNTPRequest(minNTPrequest);
				socket.close();
				
				//wait 350ms
				threadSleep(350);
			}
			
			System.out.println("Offset:"+ minOffset + ", Accuracy bound:"+ minD/2);
			System.out.println("----Ende----\n");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void sendNTPRequest(NTPRequest request) throws IOException, ClassNotFoundException {
		ObjectOutputStream os = null;
		ObjectInputStream is = null;;
		
		
		//send request
		request.setT1(getLocalClock());
		
		os = new ObjectOutputStream(socket.getOutputStream());
		os.writeObject(request);
		os.flush();
		
		
		//receive answer
		is = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		request = (NTPRequest)is.readObject();
		
		//delay
		long randomDelay = (long) (10.0+Math.random()*90);    // 10 ~ 100 ms;
		threadSleep(randomDelay);
		
		request.setT4(getLocalClock());
		
		//estimate the offset correspond to the minD
		request.calculateOandD();
		if(minD == null) {
			minD = request.d;
			minOffset = request.o;
		}
		else {
			if(minD > request.d) {
				minD = request.d;
				minOffset = request.o;
			}
		}
		System.out.println("offset Oi:"+request.o+",  Delay di:"+ request.d);
		
		// set local Clock
		offset = request.o;
		
		//close the resource
		is.close();
		os.close();
		

	}
	
	private long getLocalClock() {
		return (long) (new Date().getTime() + offset);
	}

	private void threadSleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		new TimeClient();
	}

}
