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
import java.util.List;

public class SLA_DATE 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		
		List<Date> dates = new ArrayList<Date>();
		ArrayList<String> datebase=new ArrayList<String>();
		
		String str_date ="01/04/2014";
		String end_date ="30/04/2014";

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
		
		File folder = new File("D:\\MIS_REPORT");
		File[] parentfolders=folder.listFiles();
		@SuppressWarnings("unchecked")
		ArrayList<String> map[]=new ArrayList[parentfolders.length];
		ArrayList<String> temp=null;
		for(int j=0;j<parentfolders.length;j++)
		{
			if (parentfolders[j].isFile())
			{
				temp=new ArrayList<String>();
				try
				{
					BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(parentfolders[j])));
					String iniString;
					while((iniString=br.readLine())!=null)
					{
						if(iniString.contains(new SimpleDateFormat("MM-yyyy").format(startDate)+","))
						{
							temp.add(iniString.replace(',', '#').replaceAll(" ", ""));
						}
					}
					br.close();
					map[j]=new ArrayList<String>();
					int datecounter=0;
					for(int i=0;i<temp.size()-1;i++)
					{
						String datevalue=temp.get(i).split("#")[0].replace('-', '/');
						String datenext=temp.get(i+1).split("#")[0].replace('-', '/');
						String timevalue=temp.get(i).split("#")[1];
						if(datevalue.equals(datebase.get(datecounter)) && !datevalue.equals(datenext))
						{
							map[j].add(timevalue);
							datecounter++;
						}
						else if(datevalue.equals(datebase.get(datecounter)) && datevalue.equals(datenext))
						{
						}
						else if(!datevalue.equals(datebase.get(datecounter)))
						{
							map[j].add("NULL");
							datecounter++;
							i--;
						}
					}
					map[j].add(temp.get(temp.size()-1).split("#")[1]);
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
		//System.out.println("DATE"+"\t"+"ATM"+"\t"+"POS"+"\t"+"IVR"+"\t"+"CHQMIS"+"\t"+"CH_ACCT_MAST"+"\t"+"CH_NOBOOK"+"\t"+"CI_CUST_GRP_MAST"+"\t"+"NBMB");
		//System.out.println(datebase.size()+"-"+map[0].size()+"-"+map[1].size()+"-"+map[2].size()+"-"+map[3].size()+"-"+map[4].size()+"-"+map[5].size()+"-"+map[6].size()+"-"+map[7].size());
		for(int i=0;i<datebase.size();i++)
		{
			String dateformatchanged=datebase.get(i).split("/")[1]+"/"+datebase.get(i).split("/")[0]+"/"+datebase.get(i).split("/")[2];
			System.out.println(dateformatchanged+","+map[0].get(i)+","+map[7].get(i)+","+map[5].get(i)+","+map[1].get(i)+","+map[2].get(i)+","+map[3].get(i)+","+map[4].get(i)+","+map[6].get(i));
		}
	}
}

/*FILE READING ORDER
 * MIS_ATM.txt
 * MIS_CHQMIS.txt
 * MIS_CH_ACCT_MAST.txt
 * MIS_CH_NOBOOK.txt
 * MIS_CI_CUST_GRP.txt
 * MIS_IVR.txt
 * MIS_NBMB.txt
 * MIS_POS.txt
*/