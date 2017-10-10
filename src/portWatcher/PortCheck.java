package portWatcher;

import java.io.IOException;
import java.net.Socket;

public class PortCheck
{
	public static void main(String[] args) throws Exception
	{
		Socket socket;
		try
		{
			socket = new Socket("",3306);
			System.out.println("In use "+socket.toString());
			socket.close();
		}
		catch (IOException e)
		{
			System.out.println("Not in use");
		}
	}
}