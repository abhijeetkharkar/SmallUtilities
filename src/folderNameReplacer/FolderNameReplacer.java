package folderNameReplacer;

import java.io.File;

public class FolderNameReplacer
{
	public static void main(String[] args) 
	{
		try
		{
			File folder=new File("F:\\VIDEOS\\ENGLISH");
			File moviesList[]=folder.listFiles();
			for(int i=0;i<moviesList.length;i++)
			{
				String movieName=moviesList[i].getName();
				if(movieName.contains("("))
				{
					String newName=movieName.replace('(', '[').replace(')', ']');
					moviesList[i].renameTo(new File(folder+"\\"+newName));
				}
			}
			
			for(int i=0;i<moviesList.length;i++)
			{
				String movieName=moviesList[i].getName();
				if(movieName.contains("]") && moviesList[i].isDirectory())
				{
					int location=movieName.indexOf(']');
					//System.out.println(location);
					if(movieName.length()!=location+1)
					{
						//System.out.println(movieName.substring(0, location+1));
						moviesList[i].renameTo(new File(folder+"\\"+movieName.substring(0, location+1)));
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
