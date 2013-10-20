package codeGenerators;
import java.io.*;
import java.io.FileWriter;
/**
 * @author Xin Zhang
 */
public class edge2vertexAssociationGenerator {
	public static void main(String[] args) {
		FileWriter fOpen;
		FileReader fRead;
		FileReader fRead2;

		try { /**-------------------------------**/						
			fOpen = new FileWriter("evAssociation.txt");
			BufferedWriter out = new BufferedWriter(fOpen);
			fRead = new FileReader("edgeNameLog.txt");
			BufferedReader edgeStream = new BufferedReader( fRead );
			fRead2 = new FileReader("jNodeList.txt");
			BufferedReader vertexStream = new BufferedReader( fRead2 );			
			System.out.println( "Entering Loops" );
			
			String vInput = "INITIAL BECAUSE JAVA IS LAME";
			String eInput = "RAWR";
			
			String[] eLog = new String[80];
			int counter = 0;
			
			//Load edge to array
			while( !eInput.equals("exit") ) {
				eInput = edgeStream.readLine();
				eLog[counter] = eInput;
				counter++;
			}
			
			
			while( !vInput.equals("exit") ){
				String[] test = new String[2];
				vInput = vertexStream.readLine();
				counter = 0;
				for( int x = 0; !eLog[x].equals("exit"); x++ ){
					test = delimEdge(eLog[x]);
					if( vInput.equals(test[0]) || vInput.equals(test[1]) ){
						out.write(vInput + ".addPath(" + eLog[x] +");\t");
						counter++;
					}				
				}
				out.write("\n");
				System.out.println( "Found: " + counter + " edges for jNode: " + vInput);
			}	
			
			out.close();
			vertexStream.close();
			edgeStream.close();
		}catch ( Exception e){
			System.out.println( e );
		};
		
		
		
	}
	/** Delimits the edge to the proper coords **/
	public static String[] delimEdge(String input ){
		String[] ret = new String[2];
		ret = input.split("c");		
		return ret;
	}
	
}