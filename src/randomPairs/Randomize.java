package randomPairs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

public class Randomize {
	
	
	private HashMap<String, String> taMap;
	private HashMap<String, String> emailMap;

	public Randomize() {
		BufferedReader br=null;
		try {
			br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\abhij\\Google Drive\\UIowa\\Fall'17\\RA\\Pairings\\Input\\TA_List.txt"))));
			String line="";
			taMap=new HashMap<String, String>();
			emailMap=new HashMap<String, String>();
			while((line=br.readLine())!=null) {
				String split[]=line.split(",");
				//System.out.println(line);
				taMap.put(split[0]+split[1], split[3]);
				emailMap.put(split[0]+split[1], split[4]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		selectFolder();
	}
	
	private void selectFolder() {
		File folder = new File(PairConstant.INPUT_FOLDER);
		File files [] = folder.listFiles();
		for(int i =0; i<files.length;i++) {
			readFile(files[i]);
		}
	}

	private void readFile(File file) {
		BufferedReader br = null;
		String line = "";
		//String filename = "C:\\Users\\abhij\\Google Drive\\UIowa\\Fall'17\\RA\\Pairings\\Input\\Week2\\cs1020_2017-08-24.csv";
		Date reportDate = null;
		String semester = "";
		TreeSet<String> sections = new TreeSet<String>();
		ArrayList<String> masterData = new ArrayList<String>();
		int i = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			while ((line = br.readLine()) != null) {
				// System.out.println(line);
				if (i == 0) {

				} else if (i == 1) {
					reportDate = new SimpleDateFormat("yyyyMMdd").parse(line
							.split(",")[1]);
					semester = line.split(",")[3];
					masterData.add(line);

				} else {
					sections.add(line.split(",")[4]);
					masterData.add(line);
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/*System.out.println("ReportDate=" + reportDate);
		System.out.println("Sections=" + sections);
		System.out.println("Master Data Size=" + masterData.size());*/

		ArrayList<String> courseSections = new ArrayList<String>(sections);

		for (i = 0; i < courseSections.size(); i++) {
			ArrayList<String> temp = new ArrayList<String>();
			for (int j = 0; j < masterData.size(); j++) {
				String thisCourseSection = masterData.get(j).split(",")[4];
				if (courseSections.get(i).equals(thisCourseSection)) {
					String studentName = masterData.get(j).split(",")[7]
							.replace("\"", "").trim()
							+ " "
							+ masterData.get(j).split(",")[6].replace("\"", "")
									.trim();
					String studentID = masterData.get(j).split(",")[5].replace(
							"\"", "").replace("=", "");
					temp.add(studentID + "," + studentName);
				}
			}
			if("Paired".equals(taMap.get(courseSections.get(i).replace(":", "")))) {
				generatePairs(courseSections.get(i), temp);
			}
		}
	}

	private void generatePairs(String courseSection, ArrayList<String> students) {
		int max = students.size();
		int size = students.size();
		int randomIndex = 0;
		String extra = "";
		ArrayList<String> even = new ArrayList<String>();
		ArrayList<String> odd = new ArrayList<String>();
		Random rand = new Random();
		for (int i = 0; i < size; i++) {
			if (size % 2 != 0) {
				if (i != size - 1) {
					randomIndex = rand.nextInt(max);
					// System.out.println("Max="+max+",Random="+randomIndex);
					if (i % 2 == 0) {
						even.add(students.get(randomIndex));
						students.remove(randomIndex);
					} else {
						odd.add(students.get(randomIndex));
						students.remove(randomIndex);
					}
					max--;
				} else {
					// System.out.println("namesList="+students);
					extra = students.get(0);
				}
			} else {
				if (i % 2 == 0) {
					even.add(students.get(randomIndex));
					students.remove(randomIndex);
				} else {
					odd.add(students.get(randomIndex));
					students.remove(randomIndex);
				}
				max--;
			}
		}
		// System.out.println("Even="+even.size());
		// System.out.println("Odd="+odd.size());
		//System.out.println("Extra=" + extra);
		ArrayList<String> finalPairs = new ArrayList<String>();
		for (int i = 0; i < even.size(); i++) {
			if (i == even.size() - 1) {
				finalPairs.add(even.get(i) + "," + odd.get(i) + "," + extra);
			} else {
				finalPairs.add(even.get(i) + "," + odd.get(i));
			}
		}
		generateOutput(courseSection, finalPairs, extra);
		//System.out.println("Final Pairs=" + finalPairs);
	}

	private void generateOutput(String courseSection,
			ArrayList<String> finalPairs, String extra) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		String filename = PairConstant.INPUT_FOLDER.replace("Input", "Output")
				+ courseSection.replace(":", "") + ".csv";		
		File file = new File(filename);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			String content = "";			
			if("".equals(extra)) {
				content="SID1,SName1,SID2,SName2\n";
			} else {
				content="SID1,SName1,SID2,SName2,SID3,SName3\n";
			}
			for (String line : finalPairs) {
				content += line + "\n";
			}
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);
			System.out.println(courseSection+" Done!!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}