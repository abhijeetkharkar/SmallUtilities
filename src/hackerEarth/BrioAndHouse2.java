package hackerEarth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BrioAndHouse2
{
	public static void main(String[] args)
	{
		System.out.println("Enter number of testcases:");
		BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
		try
		{
			int numberOfTestCases=0,radius=0;
			try
			{
				numberOfTestCases=Integer.parseInt(input.readLine());
			}
			catch (NumberFormatException e)
			{
				System.out.println("Please enter an Integral value for radius");
			}
			//System.out.println(numberOfTestCases);
			System.out.println("Enter co-ordinates:");
			for(int i = 0;i<numberOfTestCases; i++)
			{
				String coordinatesLine=input.readLine();
				try
				{
					radius=Integer.parseInt(input.readLine());
				}
				catch (NumberFormatException e)
				{
					System.out.println("Please enter an Integral value for radius.");
				}
				
				String coordinatesArray[]=coordinatesLine.split(coordinatesLine.replace(' ', ','));
				
				if(coordinatesArray.length!=4)
				{
					System.out.println("Please input 2 pairs of co-ordinates.");
					System.exit(0);
				}
				else
				{
					int x1=0,y1=0,x2=0,y2=0;
					try
					{
						x1=Integer.parseInt(coordinatesArray[0]);
						y1=Integer.parseInt(coordinatesArray[1]);
						x2=Integer.parseInt(coordinatesArray[2]);
						y2=Integer.parseInt(coordinatesArray[3]);						
					}
					catch (NumberFormatException e)
					{
						System.out.println("Please enter an Integral value for radius.");
					}
					
					double distance1=Math.sqrt((x1*x1)+(y1*y1));
					double distance2=Math.sqrt((x2*x2)+(y2*y2));
					
					if(distance1>radius && distance2>radius)
						System.out.println("");
				}
				
			}
			input.close();
		}
		catch(IOException e)
		{
			
		}		
	}
}
