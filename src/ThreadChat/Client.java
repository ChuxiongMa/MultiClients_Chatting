package ThreadChat;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client implements Runnable{
    
    Socket SOCK;
    Scanner INPUT;
    Scanner SEND = new Scanner(System.in);
    PrintWriter OUT;

    public Client(Socket X) 
    {
        this.SOCK = X;
    }
    
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                OUT.flush();
                CheckStream();
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
    
    public void DISCONNECT() throws IOException
    {
        OUT.println(Client_GUI.UserName + " has disconnected and left.");
        OUT.flush();      
        SOCK.close();
        
        JOptionPane.showMessageDialog(null, "You are disconnected");
        System.exit(0);
    }
    
    public void CheckStream()
    {
        while(true)
        {
            RECEIVE();
        }
    }
    
    public void RECEIVE()
    {
        if(INPUT.hasNext())
        {
        	String MESSAGE = INPUT.nextLine();
        	
        	if(MESSAGE.contains("#?!"))
            {
                String TEMP1 = MESSAGE.substring(3);
                TEMP1 = TEMP1.replace("[", "");
                TEMP1 = TEMP1.replace("]", "");
                
                String[] CurrentUsers = TEMP1.split(", ");
                
            }
            else
            {
                Client_GUI.chatWindow.append(MESSAGE + "\n");
            }
        }
    }
    
    public void SEND(String x)
    {
        OUT.println(Client_GUI.UserName + ": " + x);
        OUT.flush();
        Client_GUI.userText.setText("");
    }
    
    
    
}