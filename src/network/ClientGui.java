package network;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientGui extends JFrame{


	private ClientPanel tablePanel;
	private JPanel messagePanel;
	private JPanel inputPanel;

	public JTextField textField;
	public JTextArea messageArea;

	public ClientGui(){
		setTitle("UberGame");
		setSize(800, 700);
		
		setLayout(new BorderLayout());
		tablePanel = new ClientPanel();
		tablePanel.setPreferredSize(new Dimension(800,400));
		add(tablePanel,BorderLayout.PAGE_START);
		
		messageArea = new JTextArea(8, 40);
		add(new JScrollPane(messageArea));
	    messageArea.setEditable(false);
	    messageArea.setPreferredSize(new Dimension(800,230));
	    add(messageArea,BorderLayout.CENTER);

		textField = new JTextField(40);
		textField.setEditable(false);
		textField.setPreferredSize(new Dimension(800,50));
		add(textField,BorderLayout.PAGE_END);

       

       
        
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