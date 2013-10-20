package codeGenerators;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Xin Zhang
 */
public class nodeGenerator_standAlone {

	public static void main(String[] args) {
		FileWriter fOpen;
		try { /**-------------------------------**/			
			fOpen = new FileWriter("lazy.txt");
			BufferedWriter out = new BufferedWriter(fOpen);
			String[] xNodes = {"X1","X2","X3","X4","X5","X6","X7","X8","X9","X10","X11","X12"};
			/** Y1,12 | X4,6,8
			 * Y2,3,10,11 | X3,5,7,9
			 * Y4,5,8,9 | X2,4,6,8,10
			 * Y6,7 | X1,3,5,7,9,11			 */
			
			
			/** Y1 | X4,6,8 **/
			for( int x = 4-1; x < 8; x+=2){
				out.write("junctionNode Y1" + xNodes[x] + " = new junctionNode(" + "1," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			/* Y[2],3,10,11 | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("junctionNode Y2" + xNodes[x] + " = new junctionNode(" + "2," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			/* Y2,[3],10,11 | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("junctionNode Y3" + xNodes[x] + " = new junctionNode(" + "3," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			/*Y[4],5,8,9 | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("junctionNode Y4" + xNodes[x] + " = new junctionNode(" + "4," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			/*Y4,[5],8,9 | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("junctionNode Y5" + xNodes[x] + " = new junctionNode(" + "5," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			/* Y[6],7 | X1,3,5,7,9,11*/
			for( int x = 1-1; x < 11; x+=2){
				out.write("junctionNode Y6" + xNodes[x] + " = new junctionNode(" + "6," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			/* Y6,[7] | X1,3,5,7,9,11*/
			for( int x = 1-1; x < 11; x+=2){
				out.write("junctionNode Y7" + xNodes[x] + " = new junctionNode(" + "7," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");			
			
			/*Y4,5,[8],9 | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("junctionNode Y8" + xNodes[x] + " = new junctionNode(" + "8," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			/*Y4,5,8,[9] | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("junctionNode Y9" + xNodes[x] + " = new junctionNode(" + "9," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			/* Y2,3,[10],11 | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("junctionNode Y10" + xNodes[x] + " = new junctionNode(" + "10," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			/* Y2,3,10,[11] | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("junctionNode Y11" + xNodes[x] + " = new junctionNode(" + "11," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			/**Y12 | X4,6,8 **/ 
			for( int x = 4-1; x < 8; x+=2){
				out.write("junctionNode Y12" + xNodes[x] + " = new junctionNode(" + "12," + (x+1) + ");\t"); // Write Stuff
			} out.write("\n");
			
			
			
			out.close();
		} catch (IOException e) { System.err.println( "THE SKY IS FALLING" );	} // ticket writer
		/**-------------------------------**/

	}
}
