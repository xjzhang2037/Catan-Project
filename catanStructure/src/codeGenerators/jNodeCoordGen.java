package codeGenerators;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @author Xin Zhang
 */
public class jNodeCoordGen {

	public static void main(String[] args) {
		FileWriter fOpen;
		try { /**-------------------------------**/			
			fOpen = new FileWriter("jNodeCoordList.txt");
			BufferedWriter out = new BufferedWriter(fOpen);
			String[] xNodes = {"X1","X2","X3","X4","X5","X6","X7","X8","X9","X10","X11","X12"};
			/** Y1,12 | X4,6,8
			 * Y2,3,10,11 | X3,5,7,9
			 * Y4,5,8,9 | X2,4,6,8,10
			 * Y6,7 | X1,3,5,7,9,11			 */
			
			
			/** Y1 | X4,6,8 **/
			for( int x = 4-1; x < 8; x+=2){
				out.write("1" + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			/* Y[2],3,10,11 | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("2"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			/* Y2,[3],10,11 | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("3"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			/*Y[4],5,8,9 | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("4"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			/*Y4,[5],8,9 | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("5"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			/* Y[6],7 | X1,3,5,7,9,11*/
			for( int x = 1-1; x < 11; x+=2){
				out.write("6"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			/* Y6,[7] | X1,3,5,7,9,11*/
			for( int x = 1-1; x < 11; x+=2){
				out.write("7"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");			
			
			/*Y4,5,[8],9 | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("8"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			/*Y4,5,8,[9] | X2,4,6,8,10*/
			for( int x = 2-1; x < 10; x+=2){
				out.write("9"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			/* Y2,3,[10],11 | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("10"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			/* Y2,3,10,[11] | X3,5,7,9*/
			for( int x = 3-1; x < 9; x+=2){
				out.write("11"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			/**Y12 | X4,6,8 **/ 
			for( int x = 4-1; x < 8; x+=2){
				out.write("12"  + xNodes[x]  +"\n"); // Write Stuff
			} out.write("\n");
			
			
			
			out.close();
		} catch (IOException e) { System.err.println( "THE SKY IS FALLING" );	} // ticket writer
		/**-------------------------------**/

	}
}

