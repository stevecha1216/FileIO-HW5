package cha.MAIN;
import java.util.Random;
import java.io.*;
public class HW5 {
	
	public static String DIR = "C:\\Users\\Izabelle\\eclipse-workspace\\HW5\\src\\";
	public static String[] Letters = {"A","B","C","D","E","F","G","H","I","J","K",
			"L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	public static String[] Digits = {"0","1","2","3","4","5","6","7","8","9"};
	
	
	public static int getRandNum(int min, int max) {
		Random r = new Random();
		int randomNum = r.nextInt((max - min) + 1) + min;
		return(randomNum);
	}
	
	
	
	public static void generateAlphaNumericCode(int numLines, int numCodes, String fileName) {
		File F = new File(DIR + fileName);
		try {
			F.createNewFile();
		}
		catch (IOException e) {
			System.out.println("Error with file");
			e.printStackTrace();
		}
		try {
			PrintWriter pw = new PrintWriter(F);
			String[] row = new String[numCodes];
			String alphNumCode = "";
			int x = 0;
			int y = 0;
			int z = 0;
			while(z < numLines) {
				for(String i : row) {
					while(x < 4) {
						int numOrLet = getRandNum(1,2);
						switch(numOrLet) {
							case 1: alphNumCode += Digits[getRandNum(0,9)]; break;
							case 2: alphNumCode += Letters[getRandNum(0,25)]; break;
						}
						x += 1;
					}
					
					row[y] = alphNumCode;
					x = 0;
					y += 1;
					alphNumCode = "";
				}
				for(int j = 0; j < row.length; j ++) {
					pw.print(row[j] + " ");
				}
				pw.print("\n");	
				z += 1;
				y = 0;
			}
			pw.close();
		}
		catch (FileNotFoundException e){
			System.out.println("Error with file");
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void extractNumbersLettersToFile(String codeFileName, String numFileName, String letFileName) throws IOException {
		FileInputStream input = null;
		FileOutputStream numOutput = null;
		FileOutputStream letOutput = null;
		File inputFile = new File(DIR + codeFileName);
		File numOutputFile = new File(DIR + numFileName);
		File letOutputFile = new File(DIR + letFileName);
		byte[] inputData = new byte[(int) inputFile.length()];
		char c;
		int d;
		try {
			input = new FileInputStream(inputFile);
			numOutput = new FileOutputStream(numOutputFile);
			letOutput = new FileOutputStream(letOutputFile);
			input.read(inputData);
			int x = 0;
			int y = 0;
			int sumNum = 0;
			int hCount = 0;
			int gCount = 0;
			int lCount = 0;
			for(byte b:inputData) {
				c = (char) b;
				d = (int) b;
				switch (d) {
				case 72:hCount += 1;break;
				case 71:gCount += 1;break;
				case 76:lCount += 1;break;
				}
				if(47 < d) {
					if(d < 58) {
						if(x == 20) {
							numOutput.write(10);
							x = 0;
						}
							numOutput.write(c);
							numOutput.write(32);
							x += 1;
							sumNum += d;
					}
				}
				if(64 < d) {
					if(d < 91) {
						if(y == 20) {
							letOutput.write(10);
							y = 0;
						}
							letOutput.write(c);	
							letOutput.write(32);
							y += 1;
					}
				}
			}
			numOutput.close();
			letOutput.close();
			FileWriter fwn = new FileWriter(numOutputFile, true);
			FileWriter fwl = new FileWriter(letOutputFile, true);
			fwn.write("\n");
			fwl.write("\n");
			fwn.write("The Sum of the Numbers in this file is:" + sumNum + "\n");
			fwl.write("There are " + hCount + " Hs in this file\n");
			fwl.write("There are " + gCount + " Gs in this file\n");
			fwl.write("There are " + lCount + " Ls in this file\n");
			fwn.close();
			fwl.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error with file");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args)throws IOException{
		generateAlphaNumericCode(10,10,"AlphaCode");
		extractNumbersLettersToFile("AlphaCode","AlphaNumbers","AlphaLetters");
	}
}
