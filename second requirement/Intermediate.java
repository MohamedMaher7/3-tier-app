import java.net.*;
import java.io.*;

public class IntermediateServer {
public static void main(String[] args) throws IOException {

ServerSocket s = new ServerSocket(5000);
Socket scl = new Socket("127.0.0.1",1234);
InputStream inputStreamc = scl.getInputStream();
DataInputStream dataInputStreamc = new DataInputStream(inputStream);
OutputStream outputStreamc = scl.getOutputStream();
DataOutputStream dataOutputStreamc = new DataOutputStream(outputStream);
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
System.out.println("Computer: "+received);
dataOutputStream.writeUTF("Hello Connected");
}
received = new String (dataInputStream.readUTF());
if(received.equals("Send Recommendations"))
{
System.out.println("Computer: "+received);
//connect to sensors and ask for data
dataOutputStreamc.writeUTF("Hello Connect");
string received = new String(dataInputStreamc.readUTF());

if(received.equals("Hello Connected"))
{
System.out.println("Sensor: "+received);
dataOutputStreamc.writeUTF("Send the collected data"); //get data from sensor
}
received = new String(dataInputStreamc.readUTF());
if(received.equals("DATA"))
{
System.out.println("Sensor: "+received);
dataOutputStreamc.writeUTF("Close");
dataInputStreamc.close();
inputStreamc.close();
outputStreamc.close();
scl.close();

}

/*
do some processing on data before sending recommendations to computers
*/

dataOutputStream.writeUTF("Recommendations");

}

received = new String (dataInputStream.readUTF());
if(received.equals("Close"))
{
System.out.println("Computer: "+received);
dataOutputStream.writeUTF("Close");
dataInputStream.close();
inputStream.close();
dataOutputStream.close();
outputStream.close();
s1.close();

}

}
}