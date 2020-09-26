package core;

import static core.Kid.changeKidData;
import static core.Kid.deleteKid;
import static core.Kid.enterKidData;
import static core.Kid.generateGroups;
import static core.Kid.getKidsByFile;
import static core.Kid.printNames;
import static core.Teacher.changeTeacherAgeAndExp;
import static core.Teacher.changeTeacherGroup;
import static core.Teacher.deleteTeacher;
import static core.Teacher.getTeachersByFile;
import static core.Teacher.printNames;
import static files.FileUtil.loadFile;
import static files.FileUtil.loadSampleFile;
import static files.FileUtil.overwriteInFile;
import static files.FileUtil.writeInFile;
import static util.AppUtil.getKeyboard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.Kid;

public class AppMenu {

	public static void main(String[] args) {

		FileReader kidsFile;
		FileReader teachersFile;
		FileReader smallGroupRead;
		FileReader bigGroupRead;
		FileWriter kidsFileOut;
		FileWriter teachersFileOut;
		FileWriter bigGroup;
		FileWriter smallGroup;
		File fileKidsOld;
		File fileTeachersOld;
		File file2ndTeachersOld;
		File file3rdTeachersOld;
		File tempFile;
		File temp2ndFile;
		File temp3rdFile;
		File temp4thFile;
		List<Kid> kidsList = new ArrayList<Kid>();
		List<Teacher> teachersList = new ArrayList<Teacher>();

		int endProgram = 1;
		Scanner keyboard = getKeyboard();	

		do {

			System.out.println("=========================KidsManagement===========================");
			System.out.printf("%55s %6s %3s", "See all Kids", "=====>", "1\n");
			System.out.printf("%55s %6s %3s", "Register a new Kid", "=====>", "2\n");
			System.out.printf("%55s %6s %3s", "Edit age for a Kid", "=====>", "3\n");
			System.out.printf("%55s %6s %3s", "Delete a Kid", "=====>", "4\n");
			System.out.printf("%55s %6s %3s", "See groups", "=====>", "5\n");
			System.out.printf("%55s %6s %3s", "See all Teachers", "=====>", "6\n");
			System.out.printf("%55s %6s %3s", "Register a Teacher", "=====>", "7\n");
			System.out.printf("%55s %6s %3s", "Delete a Teacher", "=====>", "8\n");
			System.out.printf("%55s %6s %3s", "Edit age and years of experience for a teacher", "=====>", "9\n");
			
			System.out.print("\n>> ");
			endProgram = keyboard.nextInt();
			System.out.println();

			switch(endProgram) {
			case 1:
				try {
					kidsFile = loadFile("kids.txt");
					kidsList = getKidsByFile(kidsFile);
					printNames(kidsList);
					System.out.println();
					kidsFile.close();
					kidsFile = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					
				}
				break;
			case 2: 	
				Kid newOne = enterKidData();
				try {
					kidsFile = loadFile("kids.txt");
					kidsFileOut = writeInFile("kids.txt");
					newOne.registerKid(kidsFileOut, kidsFile);
				}
				catch(IOException e) {
					e.printStackTrace();
				} finally {
					
				}
				break;			
			case 3:	
				try {
				fileKidsOld = loadSampleFile("kids.txt");
				tempFile = loadSampleFile("temp.txt");
				System.out.print("Enter the CNP of the Kid: ");
				keyboard = getKeyboard();
				String tempCnp = keyboard.nextLine();
				System.out.print("Enter the new age: ");
				String tempAge = keyboard.nextLine();
				changeKidData(fileKidsOld, tempFile, tempCnp, tempAge);
				} catch (Exception e) {
					
				} finally {
					
				}
				break;
			case 4:
				try {
				fileKidsOld = loadSampleFile("kids.txt");
				tempFile = loadSampleFile("temp.txt");
				System.out.print("Enter the CNP of the Kid: ");
				keyboard = getKeyboard();
				String tempCnp2 = keyboard.nextLine();
				deleteKid(fileKidsOld, tempFile, tempCnp2);
				} catch (Exception e) {
					
				} finally {
					
				}
				break;
			case 5: 
				try {
					kidsFile = loadFile("kids.txt");				
					smallGroup = overwriteInFile("small_group.txt");
					bigGroup = overwriteInFile("big_group.txt");
					generateGroups(kidsFile, smallGroup, bigGroup);
					smallGroupRead = loadFile("small_group.txt");
					kidsList = getKidsByFile(smallGroupRead);
					printNames(kidsList);
					bigGroupRead = loadFile("big_group.txt");
					kidsList = getKidsByFile(bigGroupRead);
					printNames(kidsList);
					System.out.println();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					
				}
				break;
			case 6:
				try {
					teachersFile = loadFile("teachers.txt");
					teachersList = getTeachersByFile(teachersFile);
					printNames(teachersList);
					teachersFile.close();
					teachersFile = null;
					System.out.println();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					
				}
				break;
			case 7:
				Teacher newEdu = Teacher.enterTeacherData();
				try {
					teachersFile = loadFile("teachers.txt");
					teachersFileOut = writeInFile("teachers.txt");
					newEdu.registerTeacher(teachersFileOut, teachersFile);
				}
				catch(IOException e) {
					e.printStackTrace();
				} finally {
					
				}
				break;	
			case 8:
				
				try {
					fileTeachersOld = loadSampleFile("teachers.txt");
					temp2ndFile = loadSampleFile("temp.txt");
					System.out.print("Enter the CNP of the Teacher : ");
					keyboard = getKeyboard();
					String temp2ndCnp2 = keyboard.nextLine();
					deleteTeacher(fileTeachersOld, temp2ndFile, temp2ndCnp2);
				} catch (Exception e) {
					
				} finally {
					
				}
				break;
			
			case 9:
				
				try {
					file2ndTeachersOld = loadSampleFile("teachers.txt");
					temp3rdFile = loadSampleFile("temp.txt");
					System.out.print("Enter the CNP of the Teacher : ");
					keyboard = getKeyboard();
					String temp3rdCnp = keyboard.nextLine();
					System.out.print("Enter the new age : ");
					String temp3rdAge = keyboard.nextLine();
					System.out.print("Enter new Years Of Experience : ");
					String temp3rdYOE = keyboard.nextLine();
					changeTeacherAgeAndExp(file2ndTeachersOld, temp3rdFile, temp3rdCnp, temp3rdAge, temp3rdYOE);
				} catch (Exception e) {
					
				} finally {
					
				}
				break;
				
			case 10:
				
				try {
					file3rdTeachersOld = loadSampleFile("teachers.txt");
					temp4thFile = loadSampleFile("temp.txt");
					System.out.print("Enter the CNP of the Teacher : ");
					keyboard = getKeyboard();
					String temp4thCnp = keyboard.nextLine();
					System.out.print("Enter the new group : ");
					String tempGroup = keyboard.nextLine();
					changeTeacherGroup(file3rdTeachersOld, temp4thFile, temp4thCnp, tempGroup);
				} catch (Exception e) {
					
				} finally {
					
				}
				break;
			
			default:
				System.out.println("Choose a correct option!");
				break;
			}

			System.out.print("Do you want to continue? 1 - Yes / 0 - No\n\n>> ");
			endProgram = keyboard.nextInt();
			System.out.println();

		} while (endProgram != 0);
	}

}
