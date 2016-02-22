package ThreadChat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Server_Return implements Runnable
{
    
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
    
    public Server_Return(Socket X)
    {
        this.SOCK=X;
    }
    
    public void CheckConnection() throws IOException
    {
    	 for(int i = 0; i < Server.ConnectionArray.size(); i++)
         {
             if(Server.ConnectionArray.get(i).isClosed())
             {
                 Server.ConnectionArray.remove(i);
             }
         }
    	 
    	
    }
    
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                
                while(true)
                {
                    
                    if(!INPUT.hasNext())
                    {
                        return;
                    }
                    
                    MESSAGE = INPUT.nextLine();
                    
                    System.out.println("Client said: " + MESSAGE);
                    
                    CheckConnection();
                    
                    for(int i = 1; i <= Server.ConnectionArray.size(); i++)
                    {
                        Socket TEMP_SOCK = (Socket) Server.ConnectionArray.get(i-1);
                        PrintWriter TEMP_OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println(MESSAGE);
                        TEMP_OUT.flush();
                        System.out.println("Sent to: " + TEMP_SOCK.getLocalAddress().getHostName());
                    }
                    
                    
                }
            }
            finally
            {
                SOCK.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
}
