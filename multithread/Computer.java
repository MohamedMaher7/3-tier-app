import java.net.*;
import java.io.*;
public class Computer {
public static void main(String[] args) throws IOException {
	
Socket s1 = new Socket("127.0.0.1",5000);
String received =null;

InputStream inputStream = s1.getInputStream();
DataInputStream dataInputStream = new DataInputStream(inputStream);
OutputStream outputStream = s1.getOutputStream();
DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

dataOutputStream.writeUTF("Hello Connect");
received = new String(dataInputStream.readUTF());

if(received.equals("Hello Connected"))
{
System.out.println("Server: "+received);
dataOutputStream.writeUTF("Send Recommendations"); //get recommendations from server
}
received = new String(dataInputStream.readUTF());
if(received.equals("Recommendations"))
{
System.out.println("Server: "+received);
dataOutputStream.writeUTF("Close");
dataInputStream.close();
inputStream.close();
dataOutputStream.close();
outputStream.close();
s1.close();
}

}
}