// Java implementation of  Server side
// It contains two claSensorServeres : Server and IntermediateHandler
// Save file as Server.java
  
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;
  
// Server claSensorServer
public class SensorMultiThread 
{
    public static void main(String[] args) throws IOException 
    {
        // server is listening on port 1234
        ServerSocket SensorServer = new ServerSocket(1234);
          
        // running infinite loop for getting
        // client request
        while (true) 
        {
            Socket s = null;
              
            try 
            {
                // socket object to receive incoming client requests
                s = SensorServer.accept();
                  
                System.out.println("A new client is connected : " + s);
                  
                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                  
                System.out.println("Assigning new thread for this client");
  
                // create a new thread object
                Thread t = new IntermediateHandler(s, dis, dos);
  
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
  
// IntermediateHandler 
class IntermediateHandler extends Thread 
{
    
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
      
  
    // Constructor
    public IntermediateHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
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
                        System.out.println("Server: "+received);
                        dos.writeUTF("Hello Connected");
                        break;
                          
                    case "Send the collected data" :
                        System.out.println("Server: "+received);
                        dos.writeUTF("DATA");
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