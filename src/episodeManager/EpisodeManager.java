/*
 * Give folder name
 * Create a temp folder if not already present
 * Search inside the parent folder if file names contain numbers 1-3 digits
 * if the filename contains more than 2 numbers or a single number of more than 3 digits, move the file to temp folder
 * else rename the file to "folder name_episode_digit extracted from the file name.extension"
 */
//TODO If 2 files have same episode number, move both to ambiguous
//TODO Allow a list of common text (which can be skipped in a search) to be sent as a parameter instead of a single strings
package episodeManager;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpisodeManager {

	private String folderPath;
	private String skippedCharacters;

	public EpisodeManager(String folderPath, String skippedCharacters) {
		this.folderPath = folderPath;
		this.skippedCharacters = skippedCharacters;
	}

	public void manage() {
		File f = new File(folderPath);
		if (f.exists()) {
			if (f.isDirectory()) {
				if (createTemp(f)) {
					if (moveAmbiguous(f)) {
						if (renameFiles(f)) {
							System.out.println("Processing is complete");
						} else {
							System.out.println("Renaming files failed");
						}
					} else {
						System.out.println("Moving ambiguous file to Temp folder failed");
					}
				} else {
					System.out.println("Temporary folder creation failed");
				}

			} else {
				System.out.println("This is not a folder");
			}
		} else {
			System.out.println("Invalid folder path");
		}

	}

	private boolean createTemp(File f) {
		File[] folderList = f.listFiles(new FileFilter() {
			@Override
			public boolean accept(File dir) {
				if (dir.isDirectory() && "temp".equals(dir.getName().toLowerCase()))
					return true;
				else
					return false;
			}
		});
		if (folderList.length != 0) {
			System.out.println("Temporary folder already exists");
			//File toBeDeleted = new File(f.getAbsoluteFile() + "\\"	+ folderList[0]);
			if (deleteFolder(folderList[0])) {
				System.out.println("Temporary folder deleted");
				folderList[0].mkdir();
				return true;
			} else {
				return false;
			}
		} else {
			System.out.println("Temporary folder does not exist. Creating...");
			if (new File(f.getAbsolutePath()+"\\Temp").mkdir()) {
				System.out.println("Temporary folder created");
				return true;
			} else {
				System.out.println("Temporary folder created");
				return false;
			}			
		}
	}

	private boolean deleteFolder(File toBeDeleted) {
		File[] l = toBeDeleted.listFiles();
		for (File f : l) {
			if (f.isDirectory())
				deleteFolder(f);
			else
				f.delete();
		}
		return toBeDeleted.delete();
	}

	private boolean moveAmbiguous(File f) {
		File [] ambiguousFileList = f.listFiles(new FilenameFilter() {			
			@Override
			public boolean accept(File dir, String name) {
				File temp=new File(dir.getAbsolutePath()+"\\"+name);
				if(temp.isFile()) {
					if(!name.contains(".")) {
						return true;
					} else {
						String ext = name.substring(name.lastIndexOf('.')+1, name.length()).toLowerCase();
						if(!("mkv".equals(ext) || "mp4".equals(ext) || "avi".equals(ext) || "flv".equals(ext) || "wmv".equals(ext) || "rmvb".equals(ext))) {
							return true;
						} else {
							String newName=name.substring(0,name.lastIndexOf('.')).replace(skippedCharacters, "");
							if(!(newName.matches("[^\\d]*[0-9]{1,3}[^\\d]*"))) {
								return true;
							} else {
								return false;
							}
						}
					}
				} else {
					return false;
				}				
			}
		});
		System.out.println("No. of Ambiguous Files = "+ambiguousFileList.length);
		for(File ambiguousFile : ambiguousFileList) {
			if(!ambiguousFile.renameTo(new File(ambiguousFile.getParent()+"\\Temp\\"+ambiguousFile.getName()))) {
				return false;
			}
		}
		System.out.println("Ambiguous files moved to Temp");
		return true;
	}

	private boolean renameFiles(File f) {
		File filesToBeRenamed[] = f.listFiles(new FileFilter() {			
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile())
					return true;
				else
					return false;
			}
		});
		System.out.println("No. of Files to be renamed = "+filesToBeRenamed.length);
		for(File temp : filesToBeRenamed) {
			System.out.println("Renaming file..."+temp.getName());
			String ext = temp.getName().substring(temp.getName().lastIndexOf('.')+1, temp.getName().length()).toLowerCase();
			String filename=temp.getName().substring(0,temp.getName().lastIndexOf('.')).replace(skippedCharacters, "");
			Pattern p=Pattern.compile("([^\\d]*)([0-9]{1,3})([^\\d]*)");
			Matcher m=p.matcher(filename);
			if(m.find()) {				
				if(!(temp.renameTo(new File(temp.getParent()+"\\"+temp.getParentFile().getName().replace(" ", "_")+"_Episode_"+m.group(2)+"."+ext)))) {
					return false;
				}
			} else {
				return false;
			}
		}
		System.out.println("Files have been renamed");
		return true;
	}

	public static void main(String args[]) {
		EpisodeManager em = new EpisodeManager("E:\\VIDEOS\\ANIME\\New folder\\One Piece","(480P - mp4)");
		em.manage();
	}

}
