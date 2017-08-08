package com.bhanu.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.bhanu.dataclass.*;

public class Server {
    int port=4545;
    Socket clients=null;
    ServerSocket sc=null;
    public Server(int port)
    {
    	this.port=port;
    }
	public static void main(String[] args) {
		System.out.println("Enter server port");
		int p =Integer.parseInt(System.console().readLine());
		Server s=new Server(p);
		try {
			s.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void startServer() throws IOException
	{
		
		try
		{
			 sc= new ServerSocket(port);
			while(true)
			{
		        clients = sc.accept();
		        Server.this.createThread();
		        }
				
			
		}catch(IOException e) {
			e.printStackTrace();	
		}
		finally{
			sc.close();
			clients.close();
		}
	}
	private synchronized void createThread()
	{
	    new ClientThread(clients).run();
		
	}
	private class ClientThread extends Thread implements Runnable
	{
        private Socket client=null;
		protected ClientThread(Socket cl)
		{
			this.client=cl;
		}
		
		@Override
		public void run() {
			try(ObjectInputStream input = new ObjectInputStream(client.getInputStream())){
			DataClass dc = (DataClass)input.readObject();
			System.out.println(dc.getClass().getName());
			System.out.println(dc.Name);
			System.out.println(dc.Address);
			System.out.println(dc.Problem);
			System.out.println("------------------------");
			Connection con=MySQLConn.getConnection();
			PreparedStatement ps = con.prepareStatement("insert into problems values(?,?,?)");
			ps.setString(1, dc.Name);
			ps.setString(2, dc.Address);
			ps.setString(3, dc.Problem);
		    int i=ps.executeUpdate();
		    ps.close();
		    if(i!=0)
		    {
		    	System.out.println("Success");
		    }
		    else
		    {
		    	System.out.println("Failure");
		    }
			client.close();
			}catch(Exception e)
			{
				System.out.println(e);
				
			}
			finally
			{
			  try {
				client.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			}
			
		
		}
	}
}
