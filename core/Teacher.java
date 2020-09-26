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

public class Teacher {
	
	private String cnp;
	private String firstName;
	private String lastName;
	private int age;
	private int yearsOfExperience;
	private String group;

	public Teacher(String cnp, String firstName, String lastName, int age, int yearsOfExperience, String group) {
		
		this.cnp = cnp;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.yearsOfExperience = yearsOfExperience;
		this.group = group;
		
	}
	
	public Teacher() {
		
	}

	public static List<Teacher> getTeachersByFile(FileReader file) throws IOException {
		
		List<Teacher> totalTeachers = new ArrayList<Teacher>();
		String line = null;
		String[] splitLine = null;
		BufferedReader bufferedReader = new BufferedReader(file);
		
		try {
			
			while((line = bufferedReader.readLine()) != null ) {
				
				splitLine = line.split(",");
				
				Teacher teacher = new Teacher(splitLine[0],splitLine[1],splitLine[2], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]),splitLine[5]);
				
				totalTeachers.add(teacher);
			}
		} catch (IOException e) {
			
			e.printStackTrace();	
			
		} catch (NumberFormatException e) {
			
			System.out.println("You are trying to parse something that is not a number!!! ");
			
			e.printStackTrace();
			
		}
		
		return totalTeachers;
		
	}
	
	public static void printNames(List<Teacher> teachers) {
		
		teachers.sort(Comparator.comparing(Teacher::getFirstName));
		
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.printf("%10s %15s %5s %23s %20s %17s", "First Name", "Last Name", "Age", "Years of experience", "CNP", "Group");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------------------");
		
		for (int i = 0; i< teachers.size(); i++) {
			
			Teacher currentTeacher = teachers.get(i);
			
			System.out.format("%10s %15s %5d %23s %20s %17s", currentTeacher.getFirstName(), currentTeacher.getLastName(), 
					currentTeacher.getAge(), currentTeacher.getYearsOfExperience(), currentTeacher.getCnp(), currentTeacher.getGroup());
			System.out.println();
			
		}
		
	}
	
	public void registerTeacher(FileWriter file, FileReader fileRead) throws IOException {

		if(checkCNP(fileRead)) {
			System.out.println("Already a teacher with this CNP: " + this.getCnp() + " exists! Please check record!");
			return;
		}

		String entryLine = this.getCnp() + "," + this.getFirstName() + "," + this.getLastName() + "," //
				+ this.getAge() + "," + this.getYearsOfExperience() + "," + this.getGroup();

		BufferedWriter bufferedWriter = new BufferedWriter(file);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);

		printWriter.println(entryLine);
		printWriter.flush();

		file.close();
		fileRead.close();
	}	
	
	public static Teacher enterTeacherData() {

		Scanner keyboard = getKeyboard();

		Teacher newTeacher = new Teacher();

		System.out.print("Enter CNP : ");
		newTeacher.setCnp(keyboard.nextLine());
		System.out.println();
		System.out.print("Enter First Name : ");
		newTeacher.setFirstName(keyboard.nextLine());
		System.out.println();
		System.out.print("Enter Last Name : ");
		newTeacher.setLastName(keyboard.nextLine());
		System.out.println();
		System.out.print("Enter Age : ");
		newTeacher.setAge(Integer.parseInt(keyboard.nextLine()));
		System.out.println();
		System.out.print("Enter Years Of Experience : ");
		newTeacher.setYearsOfExperience(Integer.parseInt(keyboard.nextLine()));
		System.out.println();
		System.out.print("Enter Group : ");
		newTeacher.setGroup(keyboard.nextLine());
		System.out.println();

		return newTeacher;
	}
	
	public static void deleteTeacher(File originalFile, File tempFile, String CNP) {

		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {

				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.contains(CNP)) continue;
				writer.write(currentLine + System.getProperty("line.separator"));
				writer.flush();
			}
			writer.close();
			writer = null;
			reader.close();
			reader = null;
			
			originalFile.delete();

			boolean successful = tempFile.renameTo(originalFile);

			if(successful) {
				System.out.println("The teacher with the CNP " + CNP + " has been deleted!");
			} else {
				System.out.println("Something went wrong! The teacher was not deleted!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			
		}
	}
	
	public static void changeTeacherAgeAndExp(File originalFile, File tempFile, String CNP, String age, String yoe) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {

				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.contains(CNP)) {
					
					String[] splitLine = trimmedLine.split(",");

					Teacher teacher = new Teacher(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), splitLine[5]);
					
					teacher.setAge(Integer.parseInt(age));
					teacher.setYearsOfExperience(Integer.parseInt(yoe));
					
					currentLine = teacher.getCnp() + "," + teacher.getFirstName() + "," + teacher.getLastName() + "," + teacher.getAge()
									+ "," + teacher.getYearsOfExperience() + "," + teacher.getGroup();
				}
				writer.write(currentLine + System.getProperty("line.separator"));
				writer.flush();
			}
			writer.close();
			writer = null;
			reader.close();
			reader = null;

			originalFile.delete();

			boolean successful = tempFile.renameTo(originalFile);

			if(successful) {
				System.out.println("The age and years of experience of the teacher with the CNP " + CNP + " has been changed!");
			} else {
				System.out.println("Something went wrong! The teacher age and years of experience was not altered!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			
		}
	}
	
	public static void changeTeacherNames(File originalFile, File tempFile, String CNP, String firstName, String lastName) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {

				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.contains(CNP)) {
					
					String[] splitLine = trimmedLine.split(",");

					Teacher teacher = new Teacher(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), splitLine[5]);
					
					teacher.setFirstName(firstName);
					teacher.setLastName(lastName);
					
					currentLine = teacher.getCnp() + "," + teacher.getFirstName() + "," + teacher.getLastName() + "," + teacher.getAge()
									+ "," + teacher.getYearsOfExperience() + "," + teacher.getGroup();
				}
				writer.write(currentLine + System.getProperty("line.separator"));
				writer.flush();
			}
			writer.close();
			writer = null;
			reader.close();
			reader = null;

			originalFile.delete();

			boolean successful = tempFile.renameTo(originalFile);

			if(successful) {
				System.out.println("The names of the teacher with the CNP " + CNP + " has been changed!");
			} else {
				System.out.println("Something went wrong! The teacher names was not altered!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			
		}
	}
	
	public static void changeTeacherGroup(File originalFile, File tempFile, String CNP, String group) {

		try {

			BufferedReader reader = new BufferedReader(new FileReader(originalFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while((currentLine = reader.readLine()) != null) {

				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if(trimmedLine.contains(CNP)) {
					
					String[] splitLine = trimmedLine.split(",");

					Teacher teacher = new Teacher(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), splitLine[5]);
					
					teacher.setGroup(group);
					
					currentLine = teacher.getCnp() + "," + teacher.getFirstName() + "," + teacher.getLastName() + "," + teacher.getAge()
									+ "," + teacher.getYearsOfExperience() + "," + teacher.getGroup();
				}
				writer.write(currentLine + System.getProperty("line.separator"));
				writer.flush();
			}
			writer.close();
			writer = null;
			reader.close();
			reader = null;

			originalFile.delete();

			boolean successful = tempFile.renameTo(originalFile);

			if(successful) {
				System.out.println("The group of the teacher with the CNP " + CNP + " has been changed!");
			} else {
				System.out.println("Something went wrong! The teacher group was not altered!");
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			
		}
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

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

}
