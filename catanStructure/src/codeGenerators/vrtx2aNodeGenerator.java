package codeGenerators;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class vrtx2aNodeGenerator {
	
	public static void main(String[] args) {
		
		FileWriter fOpen;
		FileReader fRead;
		FileReader fRead2;
		
		try {
			fRead = new FileReader("jNodeCoordList.txt");
			fOpen = new FileWriter("vaAssociation.txt");
			BufferedReader coordStream = new BufferedReader( fRead );	
//			fRead2 = new FileReader("jNodeList.txt");
//			BufferedReader vertexStream = new BufferedReader( fRead2 );	
			BufferedWriter out = new BufferedWriter(fOpen);
			
			/** Read and "parse" all possibilities of Y#X# **/
			int[] xjCoords = new int[70];
			int[] yjCoords = new int[70];
			int xInit = 0;
			int yInit = 0;
			int xMax = 0;
			int yMax = 0;
			int count = 0;
			String input = "RAWR";
			
			
			while(!input.equals("exit")){ // parse coords
				input = coordStream.readLine();
				String[] dStr = input.split("X");
				if( dStr.length == 2 ){
					System.out.println(dStr[0] + " | " + dStr[1] + " count: " + count);
					yjCoords[count] = Integer.valueOf( dStr[0] );
					xjCoords[count] = Integer.valueOf( dStr[1] );
					count++;
				}
			}
			
			/** UNNEEDED AFTER THOUGHT**/
//			input = "RAWR";	count = 0;
//			String[] jNodes = new String[70];
//			//Parse jNode names
//			while( !input.equals("exit") ) {
//				input = vertexStream.readLine();
//				jNodes[count] = input;
//				count++;
//			}
			
			
			//A
			out.write("case( 'A' ): \t");
			yInit = 1; yMax = 4;
			xInit = 3; xMax = 5;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//B
			out.write("case( 'B' ): \t");
			yInit = 1; yMax = 4;
			xInit = 5; xMax = 7;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//C
			out.write("case( 'C' ): \t");
			yInit = 1; yMax = 4;
			xInit = 7; xMax = 9;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//L
			out.write("case( 'L' ): \t");
			yInit = 3; yMax = 6;
			xInit = 2; xMax = 4;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//M
			out.write("case( 'M' ): \t");
			yInit = 3; yMax = 6;
			xInit = 4; xMax = 6;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//N
			out.write("case( 'N' ): \t");
			yInit = 3; yMax = 6;
			xInit = 6; xMax = 8;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//D
			out.write("case( 'D' ): \t");
			yInit = 3; yMax = 6;
			xInit = 8; xMax = 10;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//K
			out.write("case( 'K' ): \t");
			yInit = 5; yMax = 8;
			xInit = 1; xMax = 3;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//R
			out.write("case( 'R' ): \t");
			yInit = 5; yMax = 8;
			xInit = 3; xMax = 5;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//S
			out.write("case( 'S' ): \t");
			yInit = 5; yMax = 8;
			xInit = 5; xMax = 7;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//O
			out.write("case( 'O' ): \t");
			yInit = 5; yMax = 8;
			xInit = 7; xMax = 9;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//E
			out.write("case( 'E' ): \t");
			yInit = 5; yMax = 8;
			xInit = 9; xMax = 11;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//I
			out.write("case( 'I' ): \t");
			yInit = 9; yMax = 12;
			xInit = 3; xMax = 5;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//H
			out.write("case( 'H' ): \t");
			yInit = 9; yMax = 12;
			xInit = 5; xMax = 7;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//G
			out.write("case( 'G' ): \t");
			yInit = 9; yMax = 12;
			xInit = 7; xMax = 9;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//J
			out.write("case( 'J' ): \t");
			yInit = 7; yMax = 10;
			xInit = 2; xMax = 4;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//Q
			out.write("case( 'Q' ): \t");
			yInit = 7; yMax = 10;
			xInit = 4; xMax = 6;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//P
			out.write("case( 'P' ): \t");
			yInit = 7; yMax = 10;
			xInit = 6; xMax = 8;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			//F
			out.write("case( 'F' ): \t");
			yInit = 7; yMax = 10;
			xInit = 8; xMax = 10;
			for( int idx = 0; idx < xjCoords.length; idx++){				
				if( yInit <= yjCoords[idx] && yjCoords[idx] <= yMax  && xInit <= xjCoords[idx] && xjCoords[idx] <= xMax){ // If it is between the range
						out.write("temp.add2List( Y" + yjCoords[idx] + "X" + xjCoords[idx] + " );\n");	
			}} out.write("break;\n");
			
			
			
			
			
			
			
			
			out.close();
			coordStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		
		
	}
}
