package com.tgt.ludo.problems;

public class LudoMarkovChain {// arm of the ludo board
	public static final int DIMENSION = 6;

	// final home & rest squares are added
	// home squares = (DIMENSION - 1)
	public static final int TOTAL_NUM_SQUARES = (DIMENSION * 4 * 2) + 4 + (DIMENSION - 1) + 2;

	public static void main(String[] args) {

		// the state transition matrix
		double[][] transition = new double[TOTAL_NUM_SQUARES][TOTAL_NUM_SQUARES];
		for (int i = 0; i < TOTAL_NUM_SQUARES; i++)
			for (int j = 0; j < TOTAL_NUM_SQUARES; j++) {
				transition[i][j] = 0f;
			}
		for (int i = 0; i < TOTAL_NUM_SQUARES; i++) {
			for (int j = i + 1; j <= i + 6 && j < TOTAL_NUM_SQUARES; j++) {
				transition[i][j] = 1 / 6f;
			}
		}

		// transition[0][0]=1;
		// for the rest square add probability
		// jail indexes
		 int jail[]= {2,3,4,5,6,8};
		 for(int i=0;i<jail.length;i++){
		   //  transition[jail[i]][0] = 1;
		 }
		
		 //home
		 //transition[7][TOTAL_NUM_SQUARES-1] = 1;
		 
		//vector matrix for first input
		double vec[] = new double[TOTAL_NUM_SQUARES];
		for(int i=0;i<TOTAL_NUM_SQUARES;i++){
			vec[i]=1;
		}
		
		//get first prob dist
		double[][] res1 = multMatrix(vec,transition);
		double[][] temp = res1;
		
		for (int y = 0; y < TOTAL_NUM_SQUARES; y++)
			   System.out.print(res1[y][TOTAL_NUM_SQUARES-1]+ " ");
			 System.out.println("");
			 
		for (int i = 0; i < 100; i++) {
			temp = multMatrix(temp,transition);
			 for (int y = 0; y < TOTAL_NUM_SQUARES; y++)
			   System.out.print(temp[y][TOTAL_NUM_SQUARES-1]+ " ");
			 System.out.println("");
		}
		
	}

	private static double[][] multMatrix(double[][] a, double[][] b) {
		int n = TOTAL_NUM_SQUARES;
		double[][] c = new double[n][n];
		 for (int i = 0; i < n; i++)
	        {
	            for (int j = 0; j < n; j++)
	            {
	                for (int k = 0; k < n; k++)
	                {
	                    c[i][j] = c[i][j] + a[i][k] * b[k][j];
	                }
	            }
	        }
		 return c;
	}

	private static double[][] multMatrix(double[] a, double[][] b) {
		int n = TOTAL_NUM_SQUARES;
		double[][] c = new double[n][n];
		 for (int i = 0; i < n; i++)
	        {
	            for (int j = 0; j < n; j++)
	            {
	               
	                    c[i][j] = c[i][j] + a[i] * b[j][i];
	               
	            }
	        }
		 return c;
	}
}
