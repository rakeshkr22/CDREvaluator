package com.rakesh.major;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

	/**
	 * @author Rakesh KR [July-2k13]
	 */

public class FileOperations {
	
	/*
	 * Operation : File Read
	 * Input     : File Name (String) 
	 * Output    : Returns an ArrayList contains all the data
	*/
	public ArrayList<String> fileRead(String fileName){
		File           f;
		String         s;
		FileReader     fr = null;
		BufferedReader br = null;
		ArrayList<String>   sl = new ArrayList<String>();
		try {
			f  = new File(fileName); 
			fr = new FileReader(f);
			br = new BufferedReader(fr);
			while((s=br.readLine())!=null){
				sl.add(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
				try {
					if(br!=null)
						br.close();
					if(fr!=null)
						fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sl;
	}
	
	/*
	 * Operation : File Write
	 * Input     : File Name (String) and An ArrayList
	 * Output    : Data's in the ArrayList enter into the File
	*/
	public void fileWrite(String fileName,ArrayList<String> str){
		File           f ;
		FileWriter     fw = null;
		BufferedWriter bw = null;
		try {
			f  = new File(fileName);
			fw = new FileWriter(f.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			for(String i:str){
				bw.write(i);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bw!=null)
					bw.close();
				if(fw!=null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/*
	 * Operation : File Rename
	 * Input     : File Name (File) and New File Name (String)
	 * Output    : File Name Changed
	*/
	public void fileRename(File f,String newName){
		if (f.exists()) {
			File nf = new File(newName);
			f.renameTo(nf);
		}
		else
			System.err.println("File "+f.getName()+" Not Found");
	}
	
	/*
	 * Operation : File Copy
	 * Input     : File Name (String) and Copy File Name
	 * Output    : File Should Copied with new name
	*/
	public void fileCopy(File f,String copyName){
		if(f.exists()){
			fileWrite(copyName,fileRead(f.getAbsolutePath()));
		}
		else
			System.err.println("File "+f.getName()+" Not Found");
	}
	
	public void fileAppend(String fileName,String appStr){
		File f = new File(fileName);
		if(f.exists()){
			try {
				ArrayList<String> strList = new ArrayList<String>();
				strList = fileRead(fileName);
				strList.add(appStr);
				fileWrite(fileName,strList);
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally{
			}
		}
		else
			System.err.println("File "+fileName+" Not Found");
	}
	
}
