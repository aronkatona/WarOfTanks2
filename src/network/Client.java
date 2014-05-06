package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;


public class Client {

	private static final int PORT = 9006;

    BufferedReader in;
    PrintWriter out;
    ClientGui gui ;
    
    static int State = 0;//tanklerakós state 0 , 1 amikor nincs lerakas
    
    static Integer[][] table;
    static Integer[][] shootTable;
    
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
        N = 10;
        table = new Integer[N][N];
        for(int i = 0 ; i < 10; ++i){
        	for(int j = 0; j < 10; ++j){
        		table[i][j] = 1;
        	}
        }
        
        shootTable = new Integer[N][N];
        for(int i = 0 ; i < 10; ++i){
        	for(int j = 0; j < 10; ++j){
        		shootTable[i][j] = 1;
        	}
        }
        
        gui.tablePanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e){
				int ClickedX = e.getX();
				int ClickedY = e.getY();
				
				
				if(getState() == 0){
					String tmp = gui.tablePanel.getIndex(ClickedX, ClickedY);
					Integer i = Integer.parseInt(tmp.substring(1,2));
					Integer j = Integer.parseInt(tmp.substring(3,4));
					if(!gui.tablePanel.getIndex(ClickedX, ClickedY).equals(" ") && table[i][j] != 0){
						out.println("PLACETANK"+gui.tablePanel.getIndex(ClickedX, ClickedY));
					}
				}
				else if(getState() == 1){
					if(!gui.tablePanel.getIndex(ClickedX, ClickedY).equals(" ")){
						out.println("FIRE"+gui.tablePanel.getIndex(ClickedX, ClickedY));
					}
				}
			}
		});
    }
    
    public Integer getElementInFiled(int x, int y){
    	return table[x][y];
    }
    
    public int getState(){
    	return State;
    }
    
    public void setState(int s){
    	this.State = s;
    }
    

    private void run() throws IOException {

        String serverAddress = "localhost";
        Socket socket = new Socket(serverAddress, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        
        /**
         * A kliens fõciklusa, fogadjuk a servertõl az üzeneteket és feldolgozzuk
         */
        while (true) {
            String line = in.readLine();
             if(line.startsWith("SENDID")){
            	 gui.setTitle("Client 0" + line.substring(6) );
            } else if (line.equals("SUBMITNAME")) {
                out.println(gui.getName());
            } else if (line.equals("NAMEACCEPTED")) {
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
            }else if(line.startsWith("SHOOT")){
            	Integer i = Integer.parseInt(line.substring(5,6));
            	Integer j = Integer.parseInt(line.substring(6,7));
            	Integer value = Integer.parseInt(line.substring(7,8));
            	shootTable[i][j] = value;
            	
            } else if(line.equals("TABLEDONE")){
            	 gui.repaint();
            } else if (line.startsWith("MESSAGE")) {
                gui.messageArea.append(line.substring(8) + "\n");
            } else if(line.equals("WON")){
            	gui.NotifyGameOver("Nyertél!");
            	gui.setVisible(false); 
            	gui.dispose(); 
            	System.exit(1);
            } else if(line.equals("LOST")){
            	gui.NotifyGameOver("Vesztettél!");
            	gui.setVisible(false); 
            	gui.dispose(); 
            	System.exit(1);
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