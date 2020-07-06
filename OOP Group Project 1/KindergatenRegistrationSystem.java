//This Program store information of a child age between 4 - 7 years old
//It also will store the child's parent information
//This program can at least store up to 20 record
import java.util.Scanner;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

public class KindergatenRegistrationSystem {
   public static void main(String args[]) throws ParseException{
      int registerNo = 1, counter =  1, age = 0, userChoice = 0, userRegisterNo = 0;
      String userInput;
      char ch;
      Scanner scan = new Scanner(System.in);
      Child Record1[] = new Child[22];//can hold at least 20 record 
      Father Record2[] = new Father[22];
      Mother Record3[] = new Mother[22];
      System.out.println("Do you want to register? (yes / no)");
      userInput = scan.next();
      ch = userInput.charAt(0);
      while(ch == 'y' || ch == 'Y')
      {
         if(counter == 21)
         {
            System.out.println("Sorry all Class is full");
            break;
         }
         System.out.println("Your registration number is: " + registerNo );//important for checking record of registration
         Record1[counter] = new Child();//can hold at least 20 record 
         Record2[counter] = new Father();
         Record3[counter] = new Mother();
         Record1[counter].InputChildData(); 
         age = Record1[counter].child_age;
         if(age < 4 || age > 7)
         {
            System.out.println("Sorry we only accepting child age between 4 - 7 years old");
            Record2[counter] = null;
            Record3[counter] = null;
            Record1[counter] = null;
            break;
         }
         Record2[counter].InputFatherData();
         Record3[counter].InputMotherData();
         System.out.println("Registered Successfull!");
         System.out.println("What do you want to do? [1.Register, 2.Delete Registration, 3.Exit]");
         userChoice = scan.nextInt();
         if(userChoice == 1)
         {
            System.out.println("Do you want to register someone else? (yes / no)");
            userInput = scan.next();
            ch = userInput.charAt(0);  
         }
         else if(userChoice == 2)
         {
            System.out.print("Enter your register no: ");
            userRegisterNo = scan.nextInt();
            Record2[userRegisterNo] = null;
            Record3[userRegisterNo] = null;
            Record1[userRegisterNo] = null;
            ch = 'n';
         } 
         else
         {
            ch = 'n';
         }         
         registerNo++;
         counter++;
      }
      
      if((age <= 7 && age >= 4) || registerNo > 1)
      {
         String respond; int inputNumber = 0; boolean flag = true, loop = true; char ch2, exit;
         System.out.println("Show registered record? (yes / no)");
         respond = scan.next();
         ch2 = respond.charAt(0);
         if(ch2 == 'y' || ch2 == 'Y')
         {
            loop = true;
            while(loop)
            {
               while(flag)
               {
                  System.out.println("Enter your registration number(between 1 - 20): ");
                  inputNumber = scan.nextInt();
                  if(inputNumber != 0 && inputNumber <= 20)
                     flag = false;
               }
               if(Record2[inputNumber] != null && Record3[inputNumber] != null && Record1[inputNumber] != null)
               {
                  System.out.println("====================================");
                  Record2[inputNumber].ShowFatherData(); 
                  System.out.println("====================================");
                  Record3[inputNumber].ShowMotherData();
                  System.out.println("====================================");
                  Record1[inputNumber].ShowChildData();
                  System.out.println("====================================");
               }
               else
               {
                  System.out.println("No Registration");
               }
               
               System.out.println("Show another registered record? (yes / no)");
               respond = scan.next();
               exit = respond.charAt(0);
               flag = true;
               if(exit == 'n' || exit == 'N')
                  loop = false;
            }
         }
      }
      else
      { 
         System.out.print("");
      }
   }
}
   
class Find_Age{//Determine the child age
   public static int setAge(int year, int month, int date)throws ParseException{
      LocalDate l1 = LocalDate.of(year, month, date);
      LocalDate now1 = LocalDate.now();
      Period different1 = Period.between(l1, now1);
      return different1.getYears();
   }
}

class Child {// Stored children Information 
   private  String childName;
   private  String childID;
   private  String childbirth;
   private  String childgender;
   int child_age;
   
   public void InputChildData()throws ParseException{
   
      int age;
      int year;
      int month;
      int date;
      Scanner scan = new Scanner(System.in);
      System.out.println("Child name:");
      childName = scan.nextLine();
      System.out.println("Child ID:");
      childID = scan.nextLine();
      System.out.println("Gender");
      childgender = scan.nextLine();
      System.out.print("Date of Birth(year):");
      year = scan.nextInt();
      System.out.print("Date of Birth(month):");
      month = scan.nextInt();
      System.out.print("Date of Birth(Day):");
      date = scan.nextInt();
      child_age = Find_Age.setAge(year,month,date);
      String str_y = String.valueOf(year); String str_m = String.valueOf(month); String str_d = String.valueOf(date);
      childbirth = (str_y + "/" + str_m + "/" + str_d);
   }
   
    
   public  void ShowChildData(){
      if(child_age >= 4 && child_age <= 5)
      {
         System.out.println("Children age between 4 - 5 years  old");
         System.out.println("Teacher: Maclaren Yusof");
         
         System.out.println("Child name: " + childName);
         System.out.println("Child ID: " + childID);
         System.out.println("Child Age: " + child_age);
         System.out.println("Gender: " + childgender);
         System.out.println("Date of Birth(yyyy/MM/dd): " + childbirth);   
      }
      else if (child_age >= 6 && child_age <= 7)
      {
         System.out.println("Children age between 6 - 7 years  old");
         System.out.println("Teacher: Ikmal");
         System.out.println("Child name: " + childName);
         System.out.println("Child ID: " + childID);
         System.out.println("Gender: " + childgender);
         System.out.println("Date of Birth(yyyy/MM/dd): " + childbirth);   
      }  
   }
}
    
class Mother{//Stored parent information
   private  String M_name;
   private  String M_contNo;
   private  String M_address;
   
   public void InputMotherData(){
   
      Scanner scan = new Scanner(System.in);
      System.out.println("Mother's name:");
      M_name= scan.nextLine();
      System.out.println("Contact Number:");
      M_contNo = scan.nextLine();
      System.out.println("Address:");
      M_address = scan.nextLine();
   }
   
   public void ShowMotherData(){
      System.out.println("Mother Name: " + M_name);
      System.out.println("Contact Number: " + M_contNo);
      System.out.println("Address: " + M_address);
   }

}

class Father {//Stored parent information
   private String F_name;
   private String F_contNo;
   private String F_address;
   
   public void InputFatherData(){
   
      Scanner scan = new Scanner(System.in);
      System.out.println("Father's name:");
      F_name = scan.nextLine();
      System.out.println("Contact Number:");
      F_contNo = scan.nextLine();
      System.out.println("Address:");
      F_address = scan.nextLine();
   }
   
   public  void ShowFatherData(){
      System.out.println("Father Name: " + F_name);
      System.out.println("Contact Number: " + F_contNo);
      System.out.println("Address: " + F_address);
   }
}