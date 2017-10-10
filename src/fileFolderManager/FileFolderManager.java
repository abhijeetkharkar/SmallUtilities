package fileFolderManager;

import java.io.File;

public class FileFolderManager
{
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		FileFolderManager ffm=new FileFolderManager();
		File f=new File("D:\\ANIME\\Naruto Shippuden");
		//ffm.directoryReorg(f);
		ffm.fileRename(f);
	}
	
	public void fileRename(File f)
	{
		if(f.isDirectory())
		{
			File insideArray[]=f.listFiles();
			for(int i=0;i<insideArray.length;i++)
			{
				//System.out.println(insideArray[i].getName());
				/*if(insideArray[i].isFile() && insideArray[i].getName().contains("_."))
				{
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+insideArray[i].getName().replace("_.", ".")));
				}
				if(insideArray[i].isFile() && insideArray[i].getName().contains("_eng."))
				{
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+insideArray[i].getName().replace("_eng.", ".")));
				}*/
				if(insideArray[i].isFile() && insideArray[i].getName().contains("Naruto Shippuuden Episode "))
				{
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+insideArray[i].getName().replace("Naruto Shippuuden Episode ", "Naruto Shippuden ")));
				}
				if(insideArray[i].isFile() && insideArray[i].getName().contains("naruto shippuuden"))
				{
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+insideArray[i].getName().replace("naruto shippuuden", "Naruto Shippuden ")));
				}
				if(insideArray[i].isFile() && insideArray[i].getName().contains(" English Subbed - Chia-animecom"))
				{
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+insideArray[i].getName().replace(" English Subbed - Chia-animecom", "")));
				}
				if(insideArray[i].isFile() && insideArray[i].getName().contains("Naruto Shippuuden"))
				{
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+insideArray[i].getName().replace("Naruto Shippuuden", "Naruto Shippuden ")));
				}
				if(insideArray[i].isFile() && Character.isDigit(insideArray[i].getName().charAt(0)))
				{
					//System.out.println(insideArray[i].getName().charAt(0));
					//insideArray[i].getName().replace("Naruto Shippuuden", "Naruto Shippuden ")
					insideArray[i].renameTo(new File(f.getAbsolutePath()+"\\"+"Naruto Shippuden "+insideArray[i].getName()));
				}				
			}
		}
		System.out.println("Renaming Completed Successfully!!!");
	}
	
	public void directoryReorg(File f)
	{
		if(f.isDirectory())
		{
			File insideArray[]=f.listFiles();
			for(int i=0;i<insideArray.length;i++)
			{
				if(insideArray[i].isDirectory())
				{
					File files[]=insideArray[i].listFiles();
					for(int j=0;j<files.length;j++)
					{
						if(files[j].isFile()&&(!files[j].getName().contains("_swe.srt")))
						{
							files[j].renameTo(new File(f.getAbsolutePath()+"\\"+files[j].getName()));
						}
					}
					removeDirectory(insideArray[i]);
					//System.out.println("Folder \""+insideArray[i].getName()+"\" Done");
				}
			}
		}
	}
	
	public static boolean removeDirectory(File directory)
	{
		if (directory == null)
			return false;
		if (!directory.exists())
			return true;
		if (!directory.isDirectory())
			return false;

		String[] list = directory.list();
		if (list != null)
		{
			for (int i = 0; i < list.length; i++)
			{
				File entry = new File(directory, list[i]);

				if (entry.isDirectory())
				{
					if (!removeDirectory(entry))
					return false;
				}
				else
				{
					if (!entry.delete())
					return false;
				}
			}
		}
		return directory.delete();
	}
}
