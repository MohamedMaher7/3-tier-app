import java.net.*;
import java.io.*;

public class Sensor {
public static void main(String[] args) throws IOException {

ServerSocket s = new ServerSocket(1234);
String received=null;
while(true)
{
Socket s1=s.accept();
OutputStream outputStream = s1.getOutputStream();
DataOutputStream dataOutputStream = new DataOutputStream (outputStream);
InputStream inputStream = s1.getInputStream();
DataInputStream dataInputStream = new DataInputStream(inputStream);
received = new String (dataInputStream.readUTF());
if(received.equals("Hello Connect"))
{
System.out.println("Server: "+received);
dataOutputStream.writeUTF("Hello Connected");
}
received = new String (dataInputStream.readUTF());
if(received.equals("Send the collected data"))
{
System.out.println("Server: "+received);
dataOutputStream.writeUTF("DATA");
}
received = new String (dataInputStream.readUTF());
if(received.equals("Close"))
{
System.out.println("Server: "+received);
dataInputStream.close();
inputStream.close();
dataOutputStream.close();
outputStream.close();
s1.close();

}

}
}
}