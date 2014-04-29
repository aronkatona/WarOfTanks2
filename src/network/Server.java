package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;

import logic.Field;
import logic.ScoutTank;


public class Server {


    private static final int PORT = 9006;


    private static int currentPlayer = 1;
    
    public int getCurrentPlayer(){
    	return currentPlayer;
    }
    
    public void setCurrentPlayer(int c){
    	currentPlayer = c;
    }
    
    private static HashSet<String> players = new HashSet<String>();
    
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);
        try {
            while (true) {
                Player player1 = new Player(listener.accept(),1);
                Player player2 = new Player(listener.accept(),2);
                player1.setOpponent(player2);
                player2.setOpponent(player1);
                player1.start();
                player2.start();
                
            }
        } finally {
            listener.close();
        }
    }
    
    public static class Player extends Thread{
    	private Integer id;
    	private Socket socket;
    	private BufferedReader in;
    	private PrintWriter out;
    	private String name;
    	private Player opponent;
    	private int State;
    	
    	private Field[][] table;
    	private int N;
    	
    	public Player(Socket socket,int id){
    		this.socket = socket;
    		this.id = id;
    		N = 10;
    		table = new Field[N][N];
    		for(int i = 0; i < N; ++i){
    			for(int j = 0; j < N;++j){
    				table[i][j] = new Field(i, j);
    				//TODO: tankbrerakás
    			}
    		}
    	}
    	 	
    	public void setOpponent(Player opponent){
    		this.opponent = opponent;
    	}
    	
    	 public void run() {
             try {
                 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 out = new PrintWriter(socket.getOutputStream(), true);

                 while (true) {
                     out.println("SUBMITNAME");
                     name = in.readLine();
                     if (name == null) {
                         return;
                     }
                     synchronized (players) {
                         if (!players.contains(name)) {
                             players.add(name);
                             break;
                         }
                     }
                 }

         
                 out.println("NAMEACCEPTED");
                 writers.add(out);
                 out.println("SENDID" + id);
                 
                 out.println("INIT" + N);

                 sendTable();
                 
                 
                 /**
                  * SETTTANKS
                  */
                 out.println("SETTANKS"+ N/2);
                 int tmp = 0;
                 while(tmp < N/2){
                	 
                	 String input = in.readLine();
                	 Integer i = Integer.parseInt(input.substring(10,11));
                	 Integer j = Integer.parseInt(input.substring(12,13));
                	 table[i][j].setTank(new ScoutTank());
                	 sendTable();
                	 tmp++;
                 }
                 out.println("SETSTATE" + 1);
                 this.State = 1;
                 while(opponent.State == 0){
                	 System.out.println("waiting with oS: "+opponent.State);
                 }
                 
                 
                 while (true) {
                     String input = in.readLine();
                     if (input == null) {
                         return;
                     } else if(input.startsWith("CHAT")){
                    	 for (PrintWriter writer : writers) {
                             writer.println("MESSAGE " + name + ": " + input.substring(4));
                         }
                     } else if(input.startsWith("FIRE") && Server.currentPlayer == id){
                    	 Integer i = Integer.parseInt(input.substring(5,6));
	                     Integer j = Integer.parseInt(input.substring(7,8));
	                     if(opponent.table[i][j].getTank() != null){
	                    	 opponent.table[i][j].setTank(null);
	                     }else{
	                    	 opponent.table[i][j].setTank(null);
	                    	 opponent.table[i][j].setIsDestroyed(true);
	                     }
	                     Server.currentPlayer =  opponent.id;
	                     opponent.sendTable();
	                     sendTable();
                     } 
                     
                 }
                 
             } catch (IOException e) {
                 System.out.println(e);
             } finally {
          
                 if (name != null) {
                     players.remove(name);
                 }
                 if (out != null) {
                     writers.remove(out);
                 }
                 try {
                     socket.close();
                 } catch (IOException e) {
                 }
             }
         }
    	 
    	 
    	 public void sendTable(){
    		 for(int i = 0; i < N; ++i){
            	 for(int j = 0; j < N; ++j){
            		 /**
            		  * 0 tank
            		  * 1 nem tank
            		  * 2 megsemmisítve
            		  */
            		 if(table[i][j].getTank() != null){
            			 out.println("FILL" + i + j + 0);
            		 }else if(table[i][j].getIsDestroyed()){
        				 out.println("FILL" + i + j + 2);
        			 }else{
        				 out.println("FILL" + i + j + 1);
            		 }
            	 }
             }
            
             out.println("TABLEDONE");
    	 }
    }
}