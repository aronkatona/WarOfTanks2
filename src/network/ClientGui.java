package network;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientGui extends JFrame{


	public ClientPanel tablePanel;
	public JTextField textField;
	public JTextArea messageArea;

	public ClientGui(){
		setTitle("UberGame");
		setSize(800, 700);
		setResizable(true);
		
		setLayout(new BorderLayout());
		tablePanel = new ClientPanel();
		tablePanel.setPreferredSize(new Dimension(800,330));
		add(tablePanel,BorderLayout.PAGE_START);
		
		messageArea = new JTextArea(8, 40);
	    messageArea.setEditable(false);
	    messageArea.setPreferredSize(new Dimension(800,300));
	    add(messageArea,BorderLayout.CENTER);

		textField = new JTextField(40);
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(800,30));
		add(textField,BorderLayout.PAGE_END);
		
       

       
		add(new JScrollPane(messageArea));
        setLocationRelativeTo(null);

          
        
	}

	void NotifySetTanks(int n){
		JOptionPane.showMessageDialog(this, "Rakdj be " + n + " tankot!");
	}

	void NotifyGameOver(String msg){
		JOptionPane.showMessageDialog(this, msg);
	}

	public String getName() {
        return JOptionPane.showInputDialog(
        		this,
            "Válassz egy nevet",
            "Névválasztás",
            JOptionPane.PLAIN_MESSAGE);
    }

}