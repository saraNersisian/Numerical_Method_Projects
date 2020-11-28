/**
 * 
 */

import java.util.Scanner;
import java.io.*;
public class P2_iterative_method
{


	public static void main(String[] args) throws IOException 
	{
		Scanner kb = new Scanner(System.in);
        int n; //number of equations
        double EPSILON;

		do
		{
		   System.out.print("Enter the number of equations (n <= 10): ");
		   n = kb.nextInt();
		   if(n > 10)
			   System.out.println("Invalid input for n! Try again.");
		} while(n > 10);
		kb.nextLine();

		System.out.println("\nMake your selection on how you would like to input the coefficients: \n1) Using Command Line \n2) Using Files ");
		int selection = kb.nextInt();
		
		while (selection!=1 && selection!=2)
		{
			System.out.println("Invalid selection! Try again.");
			System.out.print("Enter your selection: ");
			selection = kb.nextInt();
		}

		 
		double[][] A = new double[n][n];   //coefficient matrix
		double[] x = new double[n];  
		double[] B = new double[n];// B vector
		
		
		if(selection == 1)
			getMatrix(n,A,B);
		if(selection == 2)
			getMatrix_Files(n,A,B);
		display_A(A,n);
		display_B(B,n);
		
		/*  diagonally dominant
		for (int i=0;i<n;i++)                    
		{
			for (int k=i+1;k<n;k++)
	            if (Math.abs(A[i][i])<Math.abs(A[k][i]))
	            {
	            	System.out.println("Matrix is not diagonally dominant! Swapping the rows ...");
	                for (int j=0;j<=n;j++)
	                {
	                    double temp=A[i][j];
	                    A[i][j]=A[k][j];
	                    A[k][j]=temp;
	             
	                }
	                System.out.println("Displayimg new matrix...");
	        		display_A(A,n);
	        		display_B(B,n);
	            }
		}
		*/
		System.out.print("\nWhat is the Stopping ERROR? ");
		EPSILON=kb.nextDouble();
		System.out.println("EPSILON= "+ EPSILON);
		System.out.println();
		
		System.out.print("Enter the starting solution: ");
		for(int i=0; i<n; i++)
		{
		   x[i]=kb.nextDouble();	
		}
	    display_sol(x,n);
	    System.out.println("------------------ STARTING JACOBI METHOd ------------------------");
	  
	   double[] x_previous=new double [n];
	   double[] x_current= new double [n];
	   double L2;
	   int count=0;
	   
	  do
	    {	
		    x_previous=iteration_Jacobi(A,B,x,n);
		    x=x_previous;	
		    x_current= iteration_Jacobi(A,B,x,n);		  
	    	System.out.print(" X_previous[]= ");
	    	for(int i=0; i<n; i++)
	    	{
	    		System.out.print( x_previous[i]+ "  ");	
	    	}
	    	 System.out.println();
	    	 System.out.print(" X_current[]= ");
		    for(int i=0; i<n; i++)
		    {
		    	System.out.print( x_current[i]+ "  ");	
		    }
	    	 L2 = L2_norm(x_current,x_previous,n);
	    	 count++;
	    	 if(count >= 50)
	    	 {
	    		  System.out.println("The error was not reached!\nTerminating the pogram...");
	    		  System.exit(0);
	      	 }
	     } while(L2 >= EPSILON); 
	  
	  
	  System.out.print("\n\n\n******************** FINAL SOLUTION ***************************");
	  System.out.println("\nThe solution was found in " + count + "-th iterations.");
	  System.out.print("Final Solution Using Jacobi Method: ");
	  for(int i=0; i<n; i++)
	  {
		  System.out.printf("%-8.3f" , x_current[i], "    ");	
	  }
	    	
		  	
	 System.out.println("\n\n\n------------------ STARTING GAUSS-SEIDEL METHOD ------------------------");
 	 System.out.print("Enter the starting solution: ");
	 for(int i=0; i<n; i++)
	 {
	    x[i]=kb.nextDouble();	
	 }
		  
	 display_sol(x,n);
	 double  x0;
	 count=0;
	 int flag =0;
	  	  do                            //Perform iterations to calculate x1,x2,...xn
	      {
	  		 System.out.print("x["+(count+1)+"]=");
	          for (int i=0;i<n;i++)                //Loop that calculates x1,x2,...xn
	          {
	              x0=x[i];
	              x[i]=B[i];
	              for (int j=0;j<n;j++)
	              {
	                  if (j!=i)
	                  x[i]=x[i]-A[i][j]*x[j];
	              }
	              x[i]=x[i]/A[i][i];
	                if (Math.abs(x[i]-x0)<=EPSILON)            //Compare the ne value with the last value
	                   flag++;
	                
	              System.out.print(x[i]+"       ");
	          }
	        //   System.out.print("x["+i+"]=");
	          System.out.println();
	          count++;   
	      }while(flag<n);                        //If the values of all the variables don't differ from their previious values with error more than eps then flag must be n and hence stop the loop
	  	  System.out.print("\n\n******************** FINAL SOLUTION ***************************");
		  System.out.println("\nThe solution was found in " + count + "-th iterations.");
		  System.out.print("Final Solution Using Gauss_Seidel Method: ");
	      for (int i=0;i<n;i++)
	          System.out.printf("%-8.3f", x[i], "   ");
 
	}
	
	public static void getMatrix_Files(int n, double[][] A, double[] B) throws IOException
	{
		 File data = new File("Coefficients.txt");
	     Scanner inputFile = new Scanner(data);
	     
	     for(int i=0; i<n; i++)
			{
				for(int j=0; j<n; j++)
				{
					A[i][j]=inputFile.nextDouble();
				}
				B[i]=inputFile.nextDouble();			
			}

	     inputFile.close();
	}
	
	
	
	public static void getMatrix(int n, double[][] A, double[] B)
	{
		Scanner kb = new Scanner(System.in);
		String equations;
		
		System.out.println("Enter your " + n + " equations bellow:");
		for(int i=1; i<=n; i++)
		{
			System.out.print("Equation " + i +": ");
			equations=kb.nextLine();			
		}		
		System.out.println("Enter the coefficients incluing b values row by row:");
		System.out.println("Example for [3x3]: \n\t\t  A B C b1\n\t\t  D E F b2\n\t\t  G H I b3");
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
			{
				A[i][j]=kb.nextDouble();
			}
			B[i]=kb.nextDouble();	
		}
	}
	
	
	public static void display_A(double[][] A, int n)
	{
		System.out.print("A[]=\n");
		for(int i=0; i<n; i++)
		{
			for(int j=0; j<n; j++)
				System.out.format("%6.2f   ", A[i][j]);
			System.out.println();
		}
	}
	
	public static void display_B(double[] B, int n)
	{
		System.out.print("\nB[]= ");
		for(int i=0; i<n; i++)
		{
			System.out.print(B[i]+"   ");
		}
		System.out.println();
	}
	
	public static void display_sol(double[] x, int n)
	{
		System.out.print("X0[]= ");
		for(int i=0; i<n; i++)
		{	
			System.out.print(x[i]+ " ");
		}
		System.out.println();
	}
	
	
	public static double[] iteration_Jacobi(double[][] A, double[] B, double[] x, int n)
	{
		double []sum=new double [n];// to store the sum of coefficients from right side
		//making the sum zero to start
		
		for(int i=0;i<n;i++)
		{
			sum[i]=0;
		}
		
		System.out.println();
		double fake;
		for(int i=0; i<n; i++)
		{
			sum[i]=0;
			fake=0;
			for(int j=0; j<n;j++)
			{
				fake=0;
				if(i==j)
					fake=A[i][j]*x[i]; 
				
				
					sum[i] += (A[i][j] * x[j]) - fake ;
				//	System.out.println("sum row #"+i+": "+sum[i]);
					
			}
			
			//System.out.println("sum row #"+i+": "+sum);
		}
		
		double x1[]=finding_x(A,B,sum,n);
		return x1;
	}
	
	
	public static double[] finding_x(double[][] A,double[] B, double[] sum, int n)
	{
		double [] x = new double [n];
		System.out.println();
		for(int i=0; i<n;i++)
		{
			x[i] = (B[i]/(A[i][i])) + ((-1/(A[i][i])) * sum[i]);
			//System.out.println("x["+i+"]= "+x[i]);
		}
		return x;
	}
	 
	
	public static double L2_norm(double[] x_current, double[] x_previous, int n)
	{
		double error=0;
		
		for(int i=0; i<n; i++)
		{
			error += Math.sqrt(Math.pow((x_current[i] - x_previous[i]),2))/(Math.sqrt(Math.pow(x_current[i],2)));
		 //   System.out.println("Error #" + (i+1) + ": " + error);
		    
		}
		System.out.print("\n Error: " + error);
		return error;
	}   
	
	

}
