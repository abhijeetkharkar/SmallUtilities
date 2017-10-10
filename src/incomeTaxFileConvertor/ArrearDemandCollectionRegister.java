package incomeTaxFileConvertor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ArrearDemandCollectionRegister
{
	static ArrayList<String> tempList;
	static int maxLeftlineSize=0;
	static int maxRightlineSize=0;
	
	static int getNextLeftFirstRow(int rightPageStart)
	{
		for(int i=rightPageStart+1;i<tempList.size();i++)
		{
			if(tempList.get(i).startsWith(" ") && tempList.get(i+2).matches(".*\\bPage\\b.*\\b-1\\b"))
				return i+11;
		}
		return -1;
	}
	
	static int getNextRightFirstRow(int leftPageStart)
	{
		for(int i=leftPageStart+1;i<tempList.size();i++)
		{
			if(tempList.get(i).startsWith(" ") && tempList.get(i+1).matches(".*\\bPage\\b.*\\b-2\\b"))
				return i+11;
		}
		return -1;
	}
	
	static int getMaximumLeftLength(String line)
	{
		if(line.length()>maxLeftlineSize)
			maxLeftlineSize=line.length();
		
		return maxLeftlineSize;
	}
	
	static int getMaximumRightLength(String line)
	{
		if(line.length()>maxRightlineSize)
			maxRightlineSize=line.length();
		
		return maxRightlineSize;
	}
	
	static String getPadding(int spacesCount)
	{
		String spaces="";
		for(int i=0;i<spacesCount;i++)
			spaces+=" ";
		return spaces;
	}
	
	public static void main(String args[])
	{
		tempList=new ArrayList<String>();
		try 
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("D:\\CnPm_MIS\\lrcdcr14SEP15134958.lis")));
			String iniString;
			while((iniString=br.readLine())!=null)
			{
				tempList.add(iniString);
			}
			br.close();
		}
		catch(FileNotFoundException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		int firstLeftRow=0,firstRightRow=0;
		
		for(int i=0;i<tempList.size();i++)
		{
			if(tempList.get(i).startsWith(" ") && tempList.get(i+2).matches(".*\\bPage\\b.*\\b-1\\b"))
			{
				firstLeftRow=i;
				break;
			}
		}
		
		for(int i=firstLeftRow+1;i<tempList.size();i++)
		{
			if(tempList.get(i).startsWith(" ") && tempList.get(i+1).matches(".*\\bPage\\b.*\\b-2\\b"))
			{
				firstRightRow=i;
				break;
			}
		}
		
		//System.out.println("Left="+firstLeftRow+",Right="+firstRightRow);
		
		
		ArrayList<String> left=new ArrayList<String>();
		ArrayList<String> right=new ArrayList<String>();
		
		int countLeft=firstLeftRow+11;
		while(countLeft<tempList.size())
		{
			//System.out.println(countLeft);
			if(tempList.get(countLeft).startsWith(" "))
			{
				int tempCount=ArrearDemandCollectionRegister.getNextLeftFirstRow(countLeft);
				if(tempCount>=0)
					countLeft=tempCount;
				else
					break;
			}
			else
			{
				//System.out.println(tempList.get(countLeft));
				left.add(tempList.get(countLeft)+getPadding(getMaximumLeftLength(tempList.get(countLeft))-tempList.get(countLeft).length()));
				countLeft++;
			}
		}
		
		int countRight=firstRightRow+11;
		while(countRight<tempList.size())
		{
			if(tempList.get(countRight).startsWith(" "))
			{
				int tempCount=ArrearDemandCollectionRegister.getNextRightFirstRow(countRight);
				if(tempCount>=0)
					countRight=tempCount;
				else
					break;
			}
			else
			{
				right.add(tempList.get(countRight)+getPadding(getMaximumRightLength(tempList.get(countRight))-tempList.get(countRight).length()));
				countRight++;
			}
		}
		
		ArrayList<String> intermediary=new ArrayList<String>();
		//System.out.println("Left Size="+left.size()+",Right Size="+right.size());
		File f=new File("D:\\CnPm_MIS\\lrcdcr14SEP15134958_OUTPUT.txt");
		BufferedWriter bw=null;
		try
		{
			bw=new BufferedWriter(new FileWriter(f.getAbsolutePath(),true));
			for(int i=0;i<left.size();i++)
			{
				if(!left.get(i).startsWith(" ---"))
				{
					intermediary.add(left.get(i)+right.get(i));
					bw.write(left.get(i)+right.get(i)+"\n");
				}
			}
			bw.close();
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}
}
