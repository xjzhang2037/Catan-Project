package codeGenerators;
import java.io.*;
import java.io.FileWriter;
/**
 * @author Xin Zhang
 */
public class edgeNameGenerator {
	public static void main(String[] args) {
		FileWriter fOpen;
		FileReader fRead;

		try { /**-------------------------------**/						
			fOpen = new FileWriter("edgeNameLog.txt");
			BufferedWriter out = new BufferedWriter(fOpen);
			fRead = new FileReader("edge coords.txt");
			BufferedReader iStream = new BufferedReader( fRead );
			String input = "INITIAL BECAUSE JAVA IS LAME";
			System.out.println( "Entering Loops" );
			
			while( !input.equals("exit") ){
				
				input = iStream.readLine();
				
				
				String coords[] = input.split(" "); // (y,x) (y,x)
				if( coords.length == 4 ){ // If greater than 4 entries skips.
					System.out.println("Entry were points (Y" + Integer.parseInt(coords[0]) + " , X" + Integer.parseInt(coords[1]) + ") | (Y" + Integer.parseInt(coords[2]) + " ,X" + Integer.parseInt(coords[3]) + ")");
					int point1[] = {Integer.parseInt(coords[0]) ,Integer.parseInt(coords[1]) };
					int point2[] = {Integer.parseInt(coords[2]) ,Integer.parseInt(coords[3]) };
					System.out.println("Wrote: Y" + point1[0] +"X" + point1[1] + "cY" + point2[0] +"X" + point2[1]  +" to File");
					
					//pathEdge ____ = new pathEdge( point1 , point2 );
					out.write("Y" + point1[0] +"X" + point1[1] + "cY" + point2[0] +"X" + point2[1] + "\n");
					
				} else {
					System.out.println( "Did not add last entry");
				}

			}	
			
			out.close();
			iStream.close();
		}catch ( Exception e){
			System.out.println( e );
		};
		
	}
}