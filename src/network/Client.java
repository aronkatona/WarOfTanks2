package network;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Client {

	private static final int PORT = 9006;

    BufferedReader in;
    PrintWriter out;
    ClientGui gui ;
    
    int State = 0;//tanklerak�s state 0 , 1 amikor nincs lerakas
    
    Integer[][] table ;
    Integer N;
    
    String name;

    
    
    public Client() {

        gui = new ClientGui(); 

        gui.textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println("CHAT" + gui.textField.getText());
                gui.textField.setText("");
            }
        });

        for(final JButton btn: gui.buttons){
        	btn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

						if(getState() == 0){
							out.println("PLACETANK" + btn.getText());

						}else if(getState() == 1){
							out.println("FIRE" + btn.getText());
						}
				}
			});
        }
    }
    
    public int getState(){
    	return State;
    }
    
    public void setState(int s){
    	this.State = s;
    }
    
    
    public void sendMsg(){
    	
    }


    private void run() throws IOException {

        String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        

        while (true) {
            String line = in.readLine();
             if(line.startsWith("SENDID")){
            	 gui.setTitle("Client 0" + line.substring(6) );
            } else if (line.startsWith("SUBMITNAME")) {
                out.println(gui.getName());
            } else if (line.startsWith("NAMEACCEPTED")) {
                gui.textField.setEditable(true);
            } else if (line.startsWith("INIT")){
            	String tmp = line.substring(4, 6);
            	N = Integer.parseInt(tmp);
            	table = new Integer[N][N];
            } else if(line.startsWith("FILL")){
            	Integer i = Integer.parseInt(line.substring(4,5));
            	Integer j = Integer.parseInt(line.substring(5,6));
            	Integer value = Integer.parseInt(line.substring(6,7));
            	table[i][j] = value;
            } else if(line.startsWith("SETTANKS")){
            	int N = Integer.parseInt(line.substring(8));
            	gui.NotifySetTanks(N);      	
            } else if(line.startsWith("SETSTATE")){
            	int s = Integer.parseInt(line.substring(8));
            	setState(s);    	
            } else if(line.startsWith("TABLEDONE")){
            	 for(int i = 0; i < N; ++i){
                 	for(int j = 0; j < N; ++j){
                 		if(table[i][j] == 0){
                 			setBtnColor(i,j,Color.BLUE);
                 		} 
                 		if(table[i][j] == 1){
                 			setBtnColor(i,j,Color.WHITE);
                 		}
                 		if(table[i][j] == 2){
                 			setBtnColor(i,j,Color.RED);
                 		} 
                 	}
                 }
            } else if (line.startsWith("MESSAGE")) {
                gui.messageArea.append(line.substring(8) + "\n");
            } else if(line.startsWith("WON")){
            	gui.NotifyGameOver("Nyert�l!");
            	//TODO: fix this shit
            	gui.setVisible(false); 
            	gui.dispose(); 
            	System.exit(1);
            } else if(line.startsWith("LOST")){
            	gui.NotifyGameOver("Vesztett�l!");
            	//TODO:
            	gui.setVisible(false); 
            	gui.dispose(); 
            	System.exit(1);
            }
        }
        
    }
    
    public void setBtnColor(int i,int j, Color color){
    	String tmp = " " + Integer.toString(i) + "," + Integer.toString(j); 
			for(JButton btn: gui.buttons){
				if(btn.getText().equals(tmp)){
					btn.setBackground(color);
					break;
				} 
			}
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.gui.setVisible(true);
        client.run();
    }
}