package network;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientGui extends JFrame{


	private ClientPanel tablePanel;

	public JTextField textField;
	public JTextArea messageArea;

	public ClientGui(){
		setTitle("UberGame");
		setSize(800, 700);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		

		tablePanel = new ClientPanel();
		c.ipady = 400;
		c.weightx = 0.1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		add(tablePanel, c);
		
		
		messageArea = new JTextArea(8, 40);
		//add(new JScrollPane(messageArea));
	    messageArea.setEditable(false);
	    c.ipady = 150;
	    c.weightx = 0.1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		add(messageArea, c);


		textField = new JTextField(40);
		textField.setEditable(false);
		c.ipady = 40;
		c.weightx = 0.1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		add(textField, c);
       

       
        
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