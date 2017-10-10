package fileSizeViewer;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileSize
{
	public static void main(String[] args)
	{
		//File root=new File("F:\\VIDEOS\\ENGLISH\\1.2012\\Skyfall [2012]\\Skyfall [2012]-720p-BRrip-x264-StyLishSaLH (StyLish Release).mkv");
		File root=new File("E:\\VIDEOS\\ENGLISH");
		new FileSize().findFiles(root);
	}
	
	public void findFiles(File root)
	{
		Map<String, Double> filesSize=new HashMap<String, Double>();
		String rootExtension=root.getName().substring(root.getName().lastIndexOf('.')+1);
		if(root.isFile() && (rootExtension.equals("mkv")||rootExtension.equals("mp4")||rootExtension.equals("avi")||rootExtension.equals("flv")||rootExtension.equals("mpeg")))
		{
			String x=new DecimalFormat(".##").format((double)root.length()/1024/1024);
			double sizeInMB=Double.parseDouble(x);
			filesSize.put(massagedName(root.getName().substring(0,root.getName().lastIndexOf('.'))), sizeInMB);
			//System.out.println(massagedName(root.getName().substring(0,root.getName().lastIndexOf('.')))+","+sizeInMB);
		}
		else
		{
			File drillDown[]=root.listFiles();
			//System.out.println("Length:"+drillDown.length);
			for(int i=0;i<drillDown.length;i++)
			{
				//System.out.println(drillDown[i].getName());
				if(drillDown[i].isFile())
				{
					String extension=drillDown[i].getName().substring(drillDown[i].getName().lastIndexOf('.')+1);
					if(extension.equals("mkv")||extension.equals("mp4")||extension.equals("avi")||extension.equals("flv")||extension.equals("mpeg"))
					{
						String x=new DecimalFormat(".##").format((double)drillDown[i].length()/1024/1024);
						double sizeInMB=Double.parseDouble(x);
						filesSize.put(massagedName(drillDown[i].getName().substring(0,drillDown[i].getName().lastIndexOf('.'))), sizeInMB);
						//System.out.println(massagedName(drillDown[i].getName().substring(0,drillDown[i].getName().lastIndexOf('.')))+","+sizeInMB);						
					}
				}
				else
				{
					findFiles(drillDown[i]);					
				}
			}				
		}
		
		Map<String, Double> finalMap=new TreeMap<String, Double>();
		finalMap=sortByComparator(filesSize);
		printMap(finalMap);
	}
	
	private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap)
	{ 
		// Convert Map to List
		List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Double>>()
		{
			public int compare(Map.Entry<String, Double> o1,Map.Entry<String, Double> o2)
			{
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Iterator<Map.Entry<String, Double>> it = list.iterator(); it.hasNext();)
		{
			Map.Entry<String, Double> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	public static void printMap(Map<String, Double> map)
	{
		for (Map.Entry<String, Double> entry : map.entrySet())
		{
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
	}
	
	public String massagedName(String fileName)
	{
		if(fileName.contains("_")||fileName.contains("."))
		{
			String massagedFile=fileName.replace('_', ' ').replace('.', ' ');
			return massagedFile;
		}
		else
		{
			return fileName;
		}
	}
}
