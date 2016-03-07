import java.io.*;
import java.util.*;

public class Crawler {
	
	
	//main thread================================================================================
	public static void main(String[] args){
		
		Crawler crawler = new Crawler();
		
		try {
			crawler.traverseDirectories();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//traverse directories function===============================================================
	public String traverseDirectories() throws Exception
	{
		String rootDir = "E:\\";
		int recDirToTraverse = 20; //recursive directories to traverse
		int currentDepth = 0;
		
		String fileSearch = "Waqas"; //folder to go inside 
		String txtSearch = "a"; //text to search
		
		File[] rootFiles = new File(rootDir).listFiles();
		Map<String, String> fileIndex = new HashMap<String, String>();
		Map<String, String> textIndex = new HashMap<String, String>();
		
		for(File f: rootFiles){
			System.out.println(f.getPath());
			if(f.isHidden()){
				continue;
			}else{
				fileIndex.put(f.getName(), f.getPath());
				if(f.getName().contains(".txt")){
					textIndex.put(f.getName(), f.getAbsolutePath());
				}
			}
			
		}
		
		//The actual crawling begins
		Map<String, String> temp = new HashMap<String, String>();
		
		for(Map.Entry<String, String> it : fileIndex.entrySet()){
			
			//if max depth has been reached break===============================================
			if(currentDepth==recDirToTraverse)
				break;	
			
			String p = it.getKey(); //Current key
			if(p.contains(".txt")){
				textIndex.put(p, fileIndex.get(p));
			}else{
				File[] localFiles = new File(fileIndex.get(p)).listFiles();
				
				for(File f : localFiles){
					temp.put(f.getName(), f.getAbsolutePath());
				}
				currentDepth++;
			}
		}
		
		fileIndex.putAll(temp); //put back all the temporary entries into the main index
		
		
		//Searching file ==========================================================================
		boolean fileFound = false;
		for(String str : fileIndex.keySet()){
			if(fileIndex.get(str).contains(fileSearch)){
				System.out.println("New Folder: " +fileIndex.get(str));
				fileFound = true;
			}
		}
		
		
		if(fileFound==false){
			System.out.println("File not found...");
		}
		
		//Searching text in the files ======================================================================
		boolean txtFound = false;
		for(String str : textIndex.keySet()){
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new FileReader(textIndex.get(str))); //Open each text file to search
				
				String buffer;
				while ((buffer = bufferedReader.readLine()) != null) {
					if(buffer.contains(txtSearch)){
						System.out.println("Text found in file: " +textIndex.get(str));
						txtFound = true;
						return textIndex.get(str);
						
					}
				}
				if(txtFound == false){
					System.out.println("Text not found....");
					
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		return "Not found";
	}
}
