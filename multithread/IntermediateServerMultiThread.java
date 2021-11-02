// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java
  
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
  
// Server class
public class IntermediateServer 
{
    public static void main(String[] args) throws IOException 
    {
        // server is listening on port 5000
        ServerSocket ss = new ServerSocket(5000);
          
        // running infinite loop for getting
        // client request
        while (true) 
        {
            Socket s = null;
              
            try 
            {
                // socket object to receive incoming client requests
                s = ss.accept();
                  
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                  
                System.out.println("Assigning new thread for this client");
  
                // create a new thread object
                Thread t = new ClientHandler(s, dis, dos);
  
                // Invoking the start() method
                t.start();
                  
            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }
}
  
// ClientHandler class
class ClientHandler extends Thread 
{
    
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
      
  
    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
    }
  
    @Override
    public void run() 
    {
        String received;
        while (true) 
        {
            try {
                  
                // receive the answer from client
                received = dis.readUTF();
                  
                if(received.equals("Close"))
                { 
                    System.out.println("Client " + this.s + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.s.close();
                    System.out.println("Connection closed");
                    break;
                }
                  
                
                  
                // write on output stream based on the
                // answer from the client
                switch (received) {
                  
                    case "Hello Connect" :
                        System.out.println("Computer: "+received);
						dos.writeUTF("Hello Connected");
						
                        break;
                          
                    case "Send Recommendations" :
                        Socket scl = new Socket("127.0.0.1",1234);
						InputStream inputStreamc = scl.getInputStream();
						DataInputStream dataInputStreamc = new DataInputStream(inputStream);
						OutputStream outputStreamc = scl.getOutputStream();
						DataOutputStream dataOutputStreamc = new DataOutputStream(outputStream);
						//connect to sensors and ask for data
						dataOutputStreamc.writeUTF("Hello Connect");
						string received2 = new String(dataInputStreamc.readUTF());

						if(received2.equals("Hello Connected"))
						{
						System.out.println("Sensor: "+received);
						dataOutputStreamc.writeUTF("Send the collected data"); //get data from sensor
						}
						received2 = new String(dataInputStreamc.readUTF());
						if(received2.equals("DATA"))
						{
						System.out.println("Sensor: "+received);
						dataOutputStreamc.writeUTF("Close");
						dataInputStreamc.close();
						inputStreamc.close();
						outputStreamc.close();
						scl.close();
						/*
						do some processing on data before sending recommendations to computers
						*/
						dos.writeUTF("Recommendations");
                        break;
                          
						default:
                        dos.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
          
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
              
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}