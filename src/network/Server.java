package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


public class Server {


    private static final int PORT = 9006;


    private static int currentPlayer = 1;
    
    public int getCurrentPlayer(){
    	return currentPlayer;
    }
    
    public void setCurrentPlayer(int c){
    	currentPlayer = c;
    }
    
    private static HashSet<String> names = new HashSet<String>();
    
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
    	
    	private Integer[][] table;
    	private int N;
    	
    	public Player(Socket socket,int id){
    		this.socket = socket;
    		this.id = id;
    		N = 10;
    		table = new Integer[N][N];
    		for(int i = 0; i < N; ++i){
    			for(int j = 0; j < N;++j){
    				table[i][j] = 0;
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
                     synchronized (names) {
                         if (!names.contains(name)) {
                             names.add(name);
                             break;
                         }
                     }
                 }

         
                 out.println("NAMEACCEPTED");
                 writers.add(out);
                 out.println("SENDID" + id);
                 
                 out.println("INIT" + N);

                 sendTable();
                 
                 
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
	                     opponent.table[i][j] = 1;
	                     Server.currentPlayer =  opponent.id;
	                     opponent.sendTable();
	                     sendTable();
                     } 
                     
                 }
                 
             } catch (IOException e) {
                 System.out.println(e);
             } finally {
          
                 if (name != null) {
                     names.remove(name);
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
            		 out.println("FILL" + i + j + table[i][j]);
            	 }
             }
            
             out.println("TABLEDONE");
    	 }
    }
}