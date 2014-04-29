package network;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ClientGui extends JFrame{
	
	private JPanel panel1;
	private JPanel panel2;
	
	public List<JButton> buttons;
	
	public JTextField textField;
	
	public JTextArea messageArea;
	
	public ClientGui(){
		setTitle("UberGame");
		setSize(400, 800);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		
		panel1 = new JPanel();
		panel1.setSize(400, 400);
		panel1.setLayout(new GridLayout(10,10));
		
		buttons = new ArrayList<>();
		for(int i = 0; i < 10; ++i){
			for(int j = 0; j < 10; ++j){
				JButton btn = new JButton();
				btn.setText(" " + Integer.toString(i) + ","+ Integer.toString(j));
				buttons.add(btn);
				panel1.add(btn);
			}
		}
		
		add(panel1,BorderLayout.PAGE_START);
		
		panel2 = new JPanel();
		panel2.setSize(400,400);
		
		textField = new JTextField(40);
	    messageArea = new JTextArea(8, 40);
	    
	    textField.setEditable(false);
        messageArea.setEditable(false);
        panel2.setLayout(new BorderLayout());
        panel2.add(textField,BorderLayout.PAGE_START);
        panel2.add(messageArea,BorderLayout.PAGE_END);
       
        add(panel2,BorderLayout.PAGE_END);
       
        add(new JScrollPane(messageArea));
        setLocationRelativeTo(null);
        pack();
          
        
	}
	
	void NotifySetTanks(int n){
		JOptionPane.showMessageDialog(this, "Rakdj be " + n + " tankot!");
	}
	
	public String getName() {
        return JOptionPane.showInputDialog(
        		this,
            "Válassz egy nevet",
            "Névválasztás",
            JOptionPane.PLAIN_MESSAGE);
    }

}

