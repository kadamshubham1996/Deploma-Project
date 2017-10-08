import java.awt.*;
import javax.swing.* ;
import java.awt.event.*;
import java.net.*;
import java.io.*;


class Sok extends Thread
{
	String cryptmeth="Substitution";
	String subciphertxt,transciphertxt,ptxt,subkeytxt,transkeytxt;
	int  subkey=0;

    ServerSocket serverSocket;
    PrintStream toclient;
	BufferedReader frmclient;
    Server r;

Sok(Server rr)
{
 	r=rr;
	try
	{
	 	serverSocket = new ServerSocket(4546);
	 	System.out.println("Server Started, connected to port 8088");
	 	System.out.println("Waiting for client connection......");
    }
    catch (IOException e)
    {
      	System.err.println("Error - Accept Con failed....." + e);
       	System.exit(1);
    }
	this.start();
}


public void run()
{
	String txt;
   	try{
    		while(true)
       		{
     			Socket client= serverSocket.accept();
     			Conection con=new Conection(client,r);
	   		}
    	}
 		catch (IOException e)
       	{
       		System.err.println("Error - Not Listning...." + e);
       		System.exit(1);
    	}
}

}

class Conection extends Thread
{
	Socket netClient;
	PrintStream toclient;
	BufferedReader frmclient;
	Server r;
    String txt=new String();

  Conection(Socket clientSocket,Server rr)
	{   r=rr;
		netClient=clientSocket;
		  try
		  {
			 toclient = new PrintStream( netClient.getOutputStream());
             frmclient = new BufferedReader(new InputStreamReader(netClient.getInputStream()));
          }
          catch(Exception e)
          {
          		System.err.println("Error - Stram...." + e);
       			System.exit(1);
          }
          this.start();
     }

     public void run()
     {
     	try
     	{
     		for(;;)
     	    {
     	  		txt=frmclient.readLine();
     	  		if(txt==null)
     	  		{
     	  			System.out.println("quit");
     	  			return;
     	  		}
     	  		else
     	  		{
    	  			System.out.println(txt);
    	  		    r.show1(txt);
     	  		}
     		}
     	 }
     	 catch(Exception i) {}
     	 finally
     	 {
     	 	try{
     	 		toclient.close();
     	 		frmclient.close();
//     		    netClien.close();
     			}
     		catch(Exception i) {}
     	 }
     }
}


public class Server  extends JFrame implements ActionListener,ItemListener
{
	Container contentPane;
	JButton butplain,butquit;
	JLabel lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7;
	JRadioButton chksub,chktrans;
	ButtonGroup bgroup;
    JTextArea txtplanetxt,txtciphertxt;
	JTextField txtsubkey,txttranskey,txtipadd;
	JScrollPane jscrollpane1,jscrollpane2;

	String cryptmeth="Substitution";
	String ciphertxt,plaintxt,ptxt,subkeytxt,transkeytxt;
	Server mr;
	int  subkey=0;

	Server()
	{

	        this.setSize(790,600);
			contentPane=getContentPane();
			contentPane.setBackground(new Color(135,162,252));
			contentPane.setLayout(null);

			lbl6=new JLabel(" Secured Encrypted messaging system");
			lbl6.setBounds(100,15,500,30);
			lbl6.setFont(new Font("Monotype Corsiva",Font.BOLD,30));
			lbl6.setForeground(new Color(255,255,255));
			contentPane.add(lbl6);

			lbl7=new JLabel("(User2)");
			lbl7.setBounds(570,15,200,30);
			lbl7.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl7);

			lbl1=new JLabel("Cipher Text - ");
			lbl1.setBounds(75,40,200,30);
			lbl1.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl1);

			txtplanetxt=new JTextArea(5,25);
//			txtplanetxt.enable(false);
			JScrollPane jscrollpane1=new JScrollPane(txtplanetxt,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jscrollpane1.setBounds(75,300,650,100);
				txtplanetxt.setFont(new Font("",Font.BOLD,14));
			contentPane.add(jscrollpane1);


			chksub=new JRadioButton("Substitution Method",true);
			chksub.setBounds(170,190,200,30);
			chksub.setFont(new Font("",Font.BOLD,14));
			contentPane.add(chksub);
			chksub.addItemListener(this);

			chktrans=new JRadioButton("Transposition Method");
			chktrans.setBounds(500,190,200,30);
			chktrans.setFont(new Font("",Font.BOLD,14));
			contentPane.add(chktrans);
			chktrans.addItemListener(this);

			bgroup=new ButtonGroup();
			bgroup.add(chksub);
			bgroup.add(chktrans);

			lbl2=new JLabel("Enter Key - ");
			lbl2.setBounds(90,235,100,20);
			lbl2.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl2);


			txtsubkey=new JTextField(15);
			txtsubkey.setBounds(170,230,200,30);
			txtsubkey.setFont(new Font("",Font.BOLD,14));
			contentPane.add(txtsubkey);

			lbl3=new JLabel("Enter Key - ");
			lbl3.setBounds(420,235,100,20);
			lbl3.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl3);

			txttranskey=new JTextField(15);
			txttranskey.setBounds(500,230,200,30);
			txttranskey.setFont(new Font("",Font.BOLD,14));
			contentPane.add(txttranskey);

			lbl4=new JLabel("Plain Text - ");
			lbl4.setBounds(75,270,200,30);
			lbl4.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl4);

			txtciphertxt=new JTextArea(5,25);
			JScrollPane jscrollpane2=new JScrollPane(txtciphertxt,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jscrollpane2.setBounds(75,75,650,100);
			txtciphertxt.setFont(new Font("",Font.BOLD,14));
			contentPane.add(jscrollpane2);

			lbl5=new JLabel("From User1 (IP Address) - ");
			lbl5.setBounds(180,420,200,20);
			lbl5.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl5);

			txtipadd=new JTextField(15);
//			txtipadd.enable(false);
			txtipadd.setBounds(390,420,150,30);
			txtipadd.setFont(new Font("",Font.BOLD,14));
			contentPane.add(txtipadd);


			butplain=new JButton("Plain Text");
			butplain.setBounds(275,480,90,30);
			butplain.addActionListener(this);
			contentPane.add(butplain);


			butquit=new JButton("Quit")	;
			butquit.setBounds(390,480,90,30);
			butquit.addActionListener(this);
			contentPane.add(butquit);


			txtplanetxt.setFocusable(true);
			this.show();
		}


	public void show1(String str)
        {

	        txtipadd.setText("");

             String cp,ad;
             int ind=str.indexOf("//");

		 	 if(ind<0)
		 	  ind=str.length();

		 	  cp=str.substring(0,ind);
		 	  ad=str.substring(ind+2,str.length());

	          txtciphertxt.setText(cp);
	          txtipadd.setText(ad);
	      }

	public static void main(String str[] )
	  {
		   Server s=new Server();
			Sok obj=new Sok(s);

       }


public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getItemSelectable()==chksub)
			cryptmeth="Substitution";
		if(ie.getItemSelectable()==chktrans)
			cryptmeth="Transposition";
	}

public void actionPerformed(ActionEvent ae)
	{

	if(ae.getActionCommand()=="Plain Text")
		{
		    ciphertxt=txtciphertxt.getText();

	      if(ciphertxt.length()<1)
		 	{
            JOptionPane.showMessageDialog((Component)null,"Cipher Text is not avilable",
    		                            "Input Message",JOptionPane.WARNING_MESSAGE);
    		     return;
		 	}


		 	 if(cryptmeth.equals("Substitution"))
				{
				subkeytxt=txtsubkey.getText();
				try
				   {
					 subkey=Integer.parseInt(subkeytxt);
					 subdecrypt();
					 txtplanetxt.setText(plaintxt);
				       }
				catch(NumberFormatException ne)
				   {
						JOptionPane.showMessageDialog((Component)null,"Invalid Key",
    				            "Subtitution Method",JOptionPane.WARNING_MESSAGE);
    				    return;
				     }
				  }

			 if(cryptmeth.equals("Transposition"))
				  {
				  	transkeytxt=txttranskey.getText();
				    if(transkeytxt.length()<1)
		 		    {
            		  JOptionPane.showMessageDialog((Component)null,"Invalid Key",
    		                            "Transposition Method",JOptionPane.WARNING_MESSAGE);
    		        	return ;
		 		     }

			        transdecrypt();
				    txtplanetxt.setText(plaintxt);
			      }


		}
		else if(ae.getActionCommand()=="Quit")
		{

		    int n = JOptionPane.showConfirmDialog((Component)null,"Do you want to quit","",
    				JOptionPane.YES_NO_OPTION
    				);

					if(n==JOptionPane.YES_OPTION)
					System.exit(1);
		}
	}

  void subdecrypt()
      {
      	int l=ciphertxt.length();
      	char[] cp1=new char[100];
      	char[] rt=new char[100];
      	char vv,c;
      	cp1=ciphertxt.toCharArray();
      	for(int i=0;i<l;i++)
      	{
      		c=cp1[i];
    		vv=(char)((int)c-subkey);
    		if(vv<'A')
		   	   {
				   vv=(char)((int)('Z') -(subkey-((int)c-(int)'A')-1));
		   	   }
		   	rt[i]=vv;
      	 }
      	plaintxt=new String(rt,0,ciphertxt.length());


      	}

 void transdecrypt()
      {
      	int r,l,len,i,j,tc;
      	char[] keytxt=new char[100];
      	char  tem[][]=new char[100][100];
      	char[] ctxt=new char[100];
      	len=transkeytxt.length();
      	l=ciphertxt.length();
      	tc=l/len;
      	r=0;

      transkeytxt=transkeytxt.toUpperCase();
      keytxt=transkeytxt.toCharArray();

    int seq[]=new int[transkeytxt.length()];

    for( i=0;i<transkeytxt.length();i++)
       {
    	int cn=0;

      	for( j=0;j<transkeytxt.length();j++)
       	{
        	if(keytxt[i]>keytxt[j])
           	{
          		cn++;
          	}
       	}
       	seq[i]=cn;
     }

      	i=0;j=tc;
      	for(r=0;r<len;r++)
      	{
      	  tem[r]=(ciphertxt.substring(i,j)).toCharArray();
      	   i=i+tc;
      	   j=j+tc;
      		}
      	int u=0;

      	for(i=0;i<tc;i++)
      	  {
      	 	for(j=0;j<len;j++)
      	 	 {
      	 	 ctxt[u++]=tem[seq[j]][i];
      	 	 	}
      	   }
      	plaintxt=new String(ctxt,0,u);
      	}
}