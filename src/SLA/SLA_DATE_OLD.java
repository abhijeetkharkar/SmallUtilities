package SLA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class SLA_DATE_OLD
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		List<Date> dates = new ArrayList<Date>();
		ArrayList<String> datebase=new ArrayList<String>();
		
		String str_date ="01/02/2014";
		String end_date ="28/02/2014";

		DateFormat formatter ;
		Date startDate=null, endDate=null;

		formatter = new SimpleDateFormat("dd/MM/yyyy");
		try
		{
			startDate = (Date)formatter.parse(str_date); 
			endDate = (Date)formatter.parse(end_date);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		long interval = 24*1000 * 60 * 60;
		long endTime =endDate.getTime();
		long curTime = startDate.getTime();
		while (curTime <= endTime)
		{
		    dates.add(new Date(curTime));
		    curTime += interval;
		}
		for(int i=0;i<dates.size();i++)
		{
		    Date lDate =(Date)dates.get(i);
		    String ds = formatter.format(lDate);    
		    datebase.add(ds);
		}
		
		//System.out.println(datebase.size());
		
		File folder = new File("C:\\Users\\Abhijeet\\Desktop\\MIS_REPORT");
		File[] parentfolders=folder.listFiles();
		LinkedHashMap map[]=new LinkedHashMap[parentfolders.length];
		ArrayList<String> temp=null;
		for(int j=0;j<parentfolders.length;j++)
		{
			String trigger=parentfolders[j].getName().replace('.', '#').split("#")[1];
			//System.out.println(parentfolders[j].getName());
			  if (parentfolders[j].isFile()) 
			  {					  
				  temp=new ArrayList<String>();
				  try 
				  {
					  BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(parentfolders[j])));
					  String iniString;
					  while((iniString=br.readLine())!=null)
					  {
						  temp.add(iniString.replace(',', '#').replaceAll(" ", ""));
					  }
					  br.close();
					  //System.out.println(temp);
					  ArrayList<String> date=new ArrayList<String>();
					  ArrayList<String> time=new ArrayList<String>();
					  //HashMap<String, String> map=new HashMap<String, String>();
					  map[j]=new LinkedHashMap<String,String>();
					  int datecounter=0;
					  for(int i=0;i<temp.size()-1;i++)
					  {
						  String datevalue=temp.get(i).split("#")[0].replace('-', '/');
						  String datenext=temp.get(i+1).split("#")[0].replace('-', '/');
						  String timevalue=temp.get(i).split("#")[1];
						  //map[j].put(datevalue, timevalue);
						  if(datevalue.equals(datebase.get(datecounter)) && !datevalue.equals(datenext))
						  {
							  map[j].put(datevalue, timevalue);
							  datecounter++;
						  }
						  else if(datevalue.equals(datebase.get(datecounter)) && datevalue.equals(datenext))
						  {
							  //i++;
						  }
						  else if(!datevalue.equals(datebase.get(datecounter)))
						  {
							  map[j].put(datebase.get(datecounter), "NULL");
							  datecounter++;
							  i--;
						  }
						  /*else if(i==temp.size()-2 && !datevalue.equals(datenext))
						  {
							  map[j].put(datenext, temp.get(i+1).split("#")[1]);
						  }*/
					  }
					  map[j].put(temp.get(temp.size()-1).split("#")[0].replace('-', '/'), temp.get(temp.size()-1).split("#")[1]);
					  //System.out.println(map[j]);
				  }
				  catch(FileNotFoundException e1)
				  {
					  e1.printStackTrace();
					  break;
				  }
				  catch(IOException e2)
				  {
					  e2.printStackTrace();
					  break;
				  }
				  
			  }
			 temp.clear(); 
		}
		for(int i=0;i<map.length;i++)
		{
			System.out.println(map[i]);
		}
		/*ArrayList tempList[]=new ArrayList[map.length];
		for(int i=0;i<map.length;i++)
		{
			tempList[i]=new ArrayList(datebase.size());
			Set s=map[i].keySet();
			ArrayList set=new ArrayList(s);
			Collections.sort(set);
			Object o[]=set.toArray();
			String n[]=new String[o.length];
			
			for(int j=0;j<o.length;j++)
				n[j]=(String)o[j];			
			
			System.out.println(Arrays.toString(n));
			
			for(int j=0;j<n.length;j++)
			{
				int c=0;
				for(int k=0;k<=j;k++)
				{
					//datebase.get(k).equals(n[j])
					//n[j].equals(datebase.get(k))
					String check1=n[j];
					String check2=datebase.get(k);
					if(datebase.get(k).equals(n[j]) && k==c)
		        	{		        		
		        		//break;
						tempList[i].add(map[i].get(n[j]));
		        	}
		        	else
		        	{
		        		//++c;
		        		tempList[i].add("NULL");
		        	}
					++c;
				}
		        if(c==n.length)
		        {
		        	System.out.println("NULL=="+n[j]+"=="+c);
		        	tempList[i].add("NULL");
		        }
		        else
		        {
		        	System.out.println("NOT NULL=="+n[j]+"=="+c);
		        	tempList[i].add(map[i].get(n[j]));
		        }
			}
			
		}
		//System.out.println(tempList[0]);
		ArrayList<String> ATM=new ArrayList<String>(tempList[0]);
		ArrayList<String> POS=new ArrayList<String>(tempList[7]);
		ArrayList<String> IVR=new ArrayList<String>(tempList[5]);
		ArrayList<String> CHQMIS=new ArrayList<String>(tempList[1]);
		ArrayList<String> CHACCT=new ArrayList<String>(tempList[2]);
		ArrayList<String> CHNOBOOK=new ArrayList<String>(tempList[3]);
		ArrayList<String> CICUSTGRP=new ArrayList<String>(tempList[4]);
		ArrayList<String> NBMB=new ArrayList<String>(tempList[6]);
		System.out.println(ATM);
		System.out.println(POS);
		System.out.println(IVR);
		System.out.println(CHQMIS);
		System.out.println(CHACCT);
		System.out.println(CHNOBOOK);
		System.out.println(CICUSTGRP);
		System.out.println(NBMB);*/
	}
}
