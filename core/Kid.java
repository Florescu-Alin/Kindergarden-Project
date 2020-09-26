package core;

import static util.AppUtil.getKeyboard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Kid {

	private String cnp;
	private String firstName;
	private String lastName;
	private int age;
	private String phoneNumber;

	public Kid(String cnp, String firstName, String lastName, int age, String telephoneNumber) {

		this.cnp = cnp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.phoneNumber = telephoneNumber;
	}

	public Kid() {
		//initialize with the default values
	}

	public static List<Kid> getKidsByFile(FileReader file) throws IOException {

		List<Kid> totalKids = new ArrayList<Kid>();
		String line = null;
		String[] splitLine = null;
		BufferedReader bufferedReader = new BufferedReader(file);

		try {

			while((line = bufferedReader.readLine()) != null) {

				splitLine = line.split(",");

				Kid kid = new Kid(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]), splitLine[4]);

				totalKids.add(kid);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NumberFormatException e) {

			System.out.println("You are trying to parse someting that is not a number!!!");

			e.printStackTrace();
		}
		file.close();
		return totalKids; 
	}

	public static void printNames(List<Kid> kids) {

		kids.sort(Comparator.comparing(Kid::getFirstName));

		System.out.println("-----------------------------------------------------------------------------");
		System.out.printf("%10s %15s %5s %20s %20s", "First Name", "Last Name", "Age", "CNP", "Phone");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------");

		for (int i = 0; i < kids.size(); i++) {

			Kid currentKid = kids.get(i);

			System.out.format("%10s %15s %5d %20s %20s", currentKid.getFirstName(), currentKid.getLastName(), //
					currentKid.getAge(), currentKid.getCnp(), currentKid.getTelephoneNumber());
			System.out.println();
		}
	}

	public void registerKid(FileWriter file, FileReader fileRead) throws IOException {

		if(checkCNP(fileRead)) {
			System.out.println("Already a kid with this CNP: " + this.getCnp() + " exists! Please check!");
			return;
		}

		String entryLine = this.getCnp() + "," + this.getFirstName() + "," + this.getLastName() + "," //
				+ this.getAge() + "," + this.getTelephoneNumber();

		BufferedWriter bufferedWriter = new BufferedWriter(file);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);

		printWriter.println(entryLine);
		printWriter.flush();

		file.close();
		fileRead.close();
	}

	public static void deleteKid(File originalFile, File tempFile, String CNP) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {

				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.contains(CNP)) continue;
				writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close(); 

			originalFile.delete();

			boolean successful = tempFile.renameTo(originalFile);

			if(successful) {
				System.out.println("The kid with the CNP " + CNP + " has been deleted!");
			} else {
				System.out.println("Something went wrong! The Kid was not deleted!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void changeKidData(File originalFile, File tempFile, String CNP, String age) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {

				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.contains(CNP)) {
					
					String[] splitLine = trimmedLine.split(",");

					Kid kid = new Kid(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]), splitLine[4]);
					
					kid.setAge(Integer.parseInt(age));
					
					currentLine = kid.getCnp() + "," + kid.getFirstName() + "," + kid.getLastName() + "," + kid.getAge() + "," + kid.getTelephoneNumber();
				}
				writer.write(currentLine + System.getProperty("line.separator"));
			}
			writer.close(); 
			reader.close(); 

			originalFile.delete();

			boolean successful = tempFile.renameTo(originalFile);

			if(successful) {
				System.out.println("The age of the kid with the CNP " + CNP + " has been changed!");
			} else {
				System.out.println("Something went wrong! The Kid age was not altered!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public static void generateGroups(FileReader fileReader, FileWriter fileWriter, FileWriter fileWriter2) throws IOException {
		
		BufferedReader buffRead = new BufferedReader(fileReader);
		BufferedWriter buffWrite = new BufferedWriter(fileWriter);
		BufferedWriter buffWrite2 = new BufferedWriter(fileWriter2);
		String currentLine;
		String[] splitLine;
		
		while((currentLine = buffRead.readLine()) != null) {
			
			splitLine = currentLine.split(",");
			if (Integer.parseInt(splitLine[3]) >= 3 && Integer.parseInt(splitLine[3]) <= 5) {
				buffWrite.write(currentLine + System.getProperty("line.separator"));
			} else if (Integer.parseInt(splitLine[3]) > 5 && Integer.parseInt(splitLine[3]) <= 7) {
				buffWrite2.write(currentLine + System.getProperty("line.separator"));
			}
		}
		buffRead.close();
		buffWrite.close();
		buffWrite2.close();
	}

	public static Kid enterKidData() {

		Scanner keyboard = getKeyboard();

		Kid newKid = new Kid();

		System.out.print("Enter CNP : ");
		newKid.setCnp(keyboard.nextLine());
		System.out.println();
		System.out.print("Enter First Name : ");
		newKid.setFirstName(keyboard.nextLine());
		System.out.println();
		System.out.print("Enter Last Name : ");
		newKid.setLastName(keyboard.nextLine());
		System.out.println();
		System.out.print("Enter Age : ");
		newKid.setAge(Integer.parseInt(keyboard.nextLine()));
		System.out.println();
		System.out.print("Enter Phone Number : ");
		newKid.setTelephoneNumber(keyboard.nextLine());
		System.out.println();

//		keyboard.close();

		return newKid;
	}

	public boolean checkCNP(FileReader file) throws IOException {

		BufferedReader bufferedReader = new BufferedReader(file);
		String line = null;

		while((line = bufferedReader.readLine()) != null) {
			if(line.contains(this.getCnp())) {
				return true;
			}
		}

		return false;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getTelephoneNumber() {
		return phoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.phoneNumber = telephoneNumber;
	}

}
