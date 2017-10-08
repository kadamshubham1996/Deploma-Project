import java.awt.*;
import javax.swing.* ;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class Client extends JFrame
implements ActionListener,ItemListener
{

	JButton butcipher,butquit,butsend,butconnect;
	JLabel lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7;
	JRadioButton chksub,chktrans;
	ButtonGroup bgroup;
	JTextArea txtplanetxt,txtciphertxt;
	JTextField txtsubkey,txttranskey,txtipadd;
	JScrollPane jscrollpane1,jscrollpane2;
	String cryptmeth="Substitution";
	String subciphertxt,transciphertxt,ptxt,subkeytxt,transkeytxt,addtxt;
	int  subkey=0;

	Socket sc;
	PrintStream out=null;
	BufferedReader in=null;

	public  Client()

	{
			this.setSize(790,600);
		    this.setBackground(new Color(190,200,255));
		    Container contentPane=getContentPane();

			contentPane.setBackground(new Color(135,162,252));

			lbl6=new JLabel("SecuredEncryptedMessagingSystem");
			lbl6.setBounds(100,15,500,30);
			lbl6.setFont(new Font("Monotype Corsiva",Font.BOLD,30));
			lbl6.setForeground(new Color(255,255,255));
			contentPane.add(lbl6);

			lbl7=new JLabel("(User1)");
			lbl7.setBounds(550,15,200,30);
			lbl7.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl7);

			lbl1=new JLabel("Enter Plane Text - ");
			lbl1.setBounds(75,40,200,30);
			lbl1.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl1);

			txtplanetxt=new JTextArea(5,25);
			JScrollPane jscrollpane1=new JScrollPane(txtplanetxt,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jscrollpane1.setBounds(75,75,650,100);
			txtplanetxt.setFont(new Font("",Font.BOLD,14));
			contentPane.add(jscrollpane1);


			chksub=new JRadioButton("Substitution Method",true);
			chksub.setBounds(170,190,200,30);
			chksub.setFont(new Font("",Font.BOLD,14));
			contentPane.add(chksub);
			chksub.addItemListener(this);

			chktrans=new JRadioButton("Transposition Method");
			chktrans.setBounds(480,190,200,30);
			chktrans.setFont(new Font("",Font.BOLD,14));
			contentPane.add(chktrans);
			chktrans.addItemListener(this);

			bgroup=new ButtonGroup();
			bgroup.add(chksub);
			bgroup.add(chktrans);

			lbl2=new JLabel("Enter Key - ");
			lbl2.setBounds(75,230,100,20);
			lbl2.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl2);


			txtsubkey=new JTextField(15);
			txtsubkey.setBounds(170,230,200,30);
			txtsubkey.setFont(new Font("",Font.BOLD,14));
			contentPane.add(txtsubkey);

			lbl3=new JLabel("Enter Key - ");
			lbl3.setBounds(390,230,100,20);
			lbl3.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl3);

			txttranskey=new JTextField(15);
			txttranskey.setBounds(480,230,200,30);
			txttranskey.setFont(new Font("",Font.BOLD,14));
			contentPane.add(txttranskey);

			lbl4=new JLabel("Cipher Text - ");
			lbl4.setBounds(75,270,200,30);
			lbl4.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl4);

			txtciphertxt=new JTextArea(5,25);
//			txtciphertxt.enable(false);
			JScrollPane jscrollpane2=new JScrollPane(txtciphertxt,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			jscrollpane2.setBounds(75,300,650,100);
			txtciphertxt.setFont(new Font("",Font.BOLD,14));
			contentPane.add(jscrollpane2);

			lbl5=new JLabel("Enter User2(IP Address) - ");
			lbl5.setBounds(230,420,300,20);
			lbl5.setFont(new Font("",Font.BOLD,14));
			contentPane.add(lbl5);

			txtipadd=new JTextField(15);
			txtipadd.setBounds(450,420,150,30);
			txtipadd.setFont(new Font("",Font.BOLD,14));
			contentPane.add(txtipadd);


			butcipher=new JButton("Cipher");
			butcipher.setBounds(200,480,90,30);
			butcipher.addActionListener(this);
			contentPane.add(butcipher);


			butsend=new JButton("Send")	;

	        butsend.setBounds(350,480,90,30);
	        butsend.addActionListener(this);
	        contentPane.add(butsend);

			butquit=new JButton("Quit")	;
			butquit.setBounds(500,480,90,30);
			butquit.addActionListener(this);
			contentPane.add(butquit);

			butconnect=new JButton("Connect")	;
		    butconnect.setBounds(630,480,90,30);
			butconnect.addActionListener(this);
			contentPane.add(butconnect);

			txtplanetxt.setFocusable(true);

		contentPane.setLayout(null);
		this.show();
	//butsend.enable(false);

	}
	 public static void main(String[] args)
	 	{
	 		Client client;
	 		client =  new Client();

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

		if(ae.getActionCommand()=="Cipher")
		{
	     	txtciphertxt.setText("");
		 	ptxt=new String(txtplanetxt.getText());
		 	ptxt=ptxt.toUpperCase();
		 	if(ptxt.length()<1)
		 	{
            	JOptionPane.showMessageDialog((Component)null,"Please Enter Plain Text",
    		                            "Input Message",JOptionPane.WARNING_MESSAGE);
    		    return ;
		 	}
			if(cryptmeth.equals("Substitution"))
			{
				subkeytxt=txtsubkey.getText();
				try
				{
					subkey=Integer.parseInt(subkeytxt);

					if(subkey>=0 & subkey<=26)
					{
						subEncrypt();
						txtciphertxt.setText(subciphertxt);
						subkey=0;
					}
					else
					{
					    JOptionPane.showMessageDialog((Component)null,"Invalid Key",
    				            "Subtitution Method",JOptionPane.WARNING_MESSAGE);
    				}
				}
				catch(NumberFormatException ne)
				{
						JOptionPane.showMessageDialog((Component)null,"Invalid Key",
    				            "Subtitution Method",JOptionPane.WARNING_MESSAGE);
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

				transEncrypt();
				txtciphertxt.setText(transciphertxt);
			}

		}
		else if(ae.getActionCommand()=="Send")

		{
		    try
		    {
		      String st,cp;
			  InetAddress add=InetAddress.getLocalHost();
			  st=add.toString();
			  int ind=st.indexOf("/");

		 	 if(ind<0)
		 	  ind=st.length();

		 	  cp=st.substring(ind+1,st.length());

		        if(cryptmeth.equals("Substitution"))
				{
					subciphertxt=subciphertxt+"//"+cp;
					out.println(subciphertxt);
				  }
				if(cryptmeth.equals("Transposition"))
				  {
				  	transciphertxt=transciphertxt+"//"+cp;
					out.println(transciphertxt);
				   }
				  subciphertxt="";
			}
			catch (Exception ioe)
			{
             	JOptionPane.showMessageDialog(this,"couldn't get I/O for "
                               + "the connection to: server.");
          	}
		}
		else if(ae.getActionCommand()=="Quit")
		{

		    int n = JOptionPane.showConfirmDialog(this,"Do you want to quit","",
    				JOptionPane. YES_NO_OPTION);

    		if(n==JOptionPane.YES_OPTION)
    		{

       		System.exit(-1);
       	    }

		}
		else if(ae.getActionCommand()=="Connect")
		{   addtxt="";
			addtxt=new String(txtipadd.getText());
			try
			{
			  System.out.print("IP Address: "+addtxt);
			  sc=new Socket(addtxt,4546);
              System.out.println("Successfuly connected to: "+addtxt);
	          out = new PrintStream(sc.getOutputStream());
              in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
         	 }
         	 catch(Exception e)
         	 {
         	 	System.out.print(e);
         	 	JOptionPane.showMessageDialog(this,"Incorrect Ip address or "
                               + "server is not active.");
                return;
         	 }
          // butconnect.setVisible(false);

		}
	}

    //casear substitution

void subEncrypt()
{
        char[] planetxt=new char[100];
        char[] ctxt=new char[100];
        char c,vv;
        planetxt=ptxt.toCharArray();

    	for(int i=0;i<ptxt.length();i++)
    	{
    		c=planetxt[i];
    		vv=(char)(subkey+(int)c);
    		if(vv>'Z')
		   	{
				int rkey=subkey-((int)('Z')-(int)c);
					vv=(char)((int)('A')+(rkey-1));
		   	}
		   	ctxt[i]=vv;
    	}
    	subciphertxt=new String(ctxt,0,ptxt.length());
    	subciphertxt=subciphertxt.toUpperCase();
}
//transposition
void transEncrypt()
{
	char[] keytxt=new char[100];
	char[] planetxt=new char[100];
    char[] ctxt=new char[100];
    char[] ctxt1=new char[100];
    char c;
    int i,j,k;

    transkeytxt=transkeytxt.toUpperCase();
    keytxt=transkeytxt.toCharArray();

    for(i=0;i<transkeytxt.length();i++)
    {
    	c=keytxt[i];
    	if(c >= 'A' & c <='Z')
    	{

    		{
    		    for( k=0;k<i;k++)
    		     {
    		     	if(c==keytxt[k])
    		     	 {
    		     	   JOptionPane.showMessageDialog((Component)null,"Charactor Repeatatin Not Allowed",
    						      "Transpotion Method",JOptionPane.WARNING_MESSAGE);
    		     	   return ;
    		     	  }
    		  	 }

    		 }
    	}
    	else
    	{
    		JOptionPane.showMessageDialog((Component)null,"Invalid Key",
    				      "Transpotion Method",JOptionPane.WARNING_MESSAGE);
    		return ;
    	}
	}

	int seq[]=new int[transkeytxt.length()];

    for( i=0;i<transkeytxt.length();i++)
    {
    	int cn=1;

      	for( j=0;j<transkeytxt.length();j++)
       	{
        	if(keytxt[i]>keytxt[j])
           	{
          		cn++;
          	}
       	}
       	seq[i]=cn;
    }


    int rem=ptxt.length() % transkeytxt.length();

    if(rem!=0)
    {
    	rem=transkeytxt.length()-rem;
    	String str="abcdefghijklmnopqrstuvwxyz";
    	ptxt=ptxt+(str.substring(0,rem));
    }

       ctxt=ptxt.toCharArray();
       int tt=-1;

    for(i=1;i<=transkeytxt.length();i++)
    {
      	for(j=0;j<transkeytxt.length();j++)
      	{
      	  	if(seq[j]==i)
      	  	  break;
      	}
      	for(k=j;k<ptxt.length();)
      	{
      	 char ct=ctxt[k];
      	 ctxt1[++tt]=ct;
      	 k=k+transkeytxt.length();
      	}
    }

      transciphertxt=new String(ctxt1,0,ptxt.length());
      transciphertxt=transciphertxt.toUpperCase();

 }

}
