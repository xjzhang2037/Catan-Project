package catanNodes;
import java.util.ArrayList;
import player.player;

public class psuedoLobby_Test {
	public static void main(String[] args) {
		
		/** Random data Entries **/
		player bob = new player("irsok00l" , 88888 );
		player dan = new player("catanProDudeXXX" , 2234 );
		player fred = new player("pwnsaur" , 200100 );
		player jon = new player("l33t4lyfe" , 13333337 );
		
		ArrayList<player> lobby = new ArrayList<player>();
		
		lobby.add( bob );
		lobby.add(dan);
		lobby.add(fred);
		lobby.add(jon);
		
		/** Make the game **/
		
		CatanBoard_vServer game = new CatanBoard_vServer( lobby );
		
		
		
		
		
	}
}
