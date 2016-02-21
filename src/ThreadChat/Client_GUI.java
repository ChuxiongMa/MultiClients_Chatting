package ThreadChat;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.PrintWriter;
import java.net.*;


public class Client_GUI extends JFrame{
    
	private static Client ChatClient;
	public static JTextField userText;
	public static JTextArea chatWindow;//display message
	private JButton send = new JButton("SEND");
	private JPanel panel = new JPanel();
    
    public static String UserName = "Anonymous";
    
    public static JFrame LogInWindow = new JFrame();
    public static JTextField TF_UserNameBox = new JTextField(20);
    private static JButton B_ENTER = new JButton("ENTER");
    private static JLabel L_EnterUserName = new JLabel("Enter username: ");
    private static JPanel P_LogIn = new JPanel();
    
    
    //constructor
    public Client_GUI(){
    	super("Client Program!");
    	//IP = host;
    	userText = new JTextField();
    	//userText.setEditable(false);// before connected, cannot type anything
    	send.addActionListener(
        		new ActionListener(){
        			public void actionPerformed(ActionEvent event){
        				ChatClient.SEND(userText.getText());
        				userText.setText("");//set command back to empty
        				
        			}
        	    }   			
        	);  
    	
    	panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
    	panel.add(userText);
    	panel.add(send);
    	add(panel, BorderLayout.SOUTH);
    	chatWindow = new JTextArea();
    	add(new JScrollPane(chatWindow));
    	setSize(300,150);
    	setVisible(true);
    	Connect();
    }
    
    public static void main(String[] args) 
    {
    	 BuildLogInWindow();
	
    }
    
    public static void Connect()
    {
        try
        {
            final int PORT = 444;
            final String HOST = "localhost";
            Socket SOCK = new Socket(HOST, PORT);
            System.out.println("You connected to: "+ HOST);
            
            ChatClient = new Client(SOCK);
            
            PrintWriter OUT = new PrintWriter(SOCK.getOutputStream());
            OUT.println(UserName);
            OUT.flush();
            
            Thread X = new Thread(ChatClient);
            X.start();
            
        }
        catch(Exception e)
        {
            System.out.print(e);
            JOptionPane.showMessageDialog(null, "Server not responding.");
            System.exit(0);
        }
    }
    
    public static void BuildLogInWindow()
    {
        LogInWindow.setTitle("What's your name?");
        LogInWindow.setSize(400, 100);
        LogInWindow.setLocation(250, 200);
        LogInWindow.setResizable(false);
        P_LogIn = new JPanel();
        P_LogIn.add(L_EnterUserName);
        P_LogIn.add(TF_UserNameBox);
        P_LogIn.add(B_ENTER);
        LogInWindow.add(P_LogIn);
        
        Login_Action();
        LogInWindow.setVisible(true);
    }
    public static void Login_Action()
    {
        B_ENTER.addActionListener(
                new java.awt.event.ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ACTION_B_ENTER();
            }
        }
        );
    }
    
    public static void ACTION_B_ENTER()
    {
        if(!TF_UserNameBox.getText().equals(""))
        {
            UserName = TF_UserNameBox.getText().trim();
            
            Client_GUI client;
    		client = new Client_GUI();
    		client.setTitle(UserName+"'s Chat Box");
    		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            LogInWindow.setVisible(false);
           
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please enter a name!");
        }
    }
}
    
    
