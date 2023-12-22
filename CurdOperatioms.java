package com.sanju;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CurdOperatioms {
private static Connection connection;
private static PreparedStatement prepStat;
private static ResultSet reSet;
  private static  ResultSetMetaData resultSetMetaData;
  private static Statement statement;
  static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) throws SQLException {
        String url = "JDBC:mysql://localhost:3306/jdbcclasses";
        String userName="root";
        String Password="Sanju@71";
	        try {
		 connection= DriverManager.getConnection(url, userName, Password);
		    do {
			   System.out.println("select the Query Number u want to Execute 1:Insert, 2:Update, 3:Delete,4:Select, 5:Exit..... ");
      int num = scan.nextInt();
	      if(num==1) {
			  Insert();
		 }else if (num==2) {
		    Update();
		 }else if (num==3) {
			 Delete();
		 }else if (num==4) {
			 Select();
		 } else {
			     System.out.println("Exit CURD Operations....");
		 }
			  System.out.println("Do u want to Continue CURD operations: Yes /No??");
		} while ( scan.next().equalsIgnoreCase("Yes") );
    System.out.println("Exit CURD operations...");
			
}

catch (SQLException e) {
e.printStackTrace();
}
finally {
CloseAll_P6();
}
}

        static void Insert() throws SQLException { 
		 String S1= "insert into employee (`Id,`Name`,`Email`,`Department`,`salary`)values(?,?,?,?,?)";
		 prepStat= connection.prepareStatement(S1);
		 do {
		  System.out.println("Enter Id");
	      int Id = scan.nextInt();
	      System.out.println("Enter E_name");
	      String E_name = scan.next();
	      System.out.println("Enter Email");
	      String Email = scan.next();
	      System.out.println("Enter Dept");
	      String Dept = scan.next();
	      System.out.println("Enter salary");
	      int salary = scan.nextInt();
	      prepStat.setInt(1, Id);
	      prepStat.setString(2, E_name);
	      prepStat.setString(3, Email);
	      prepStat.setString(4, Dept);
	      prepStat.setInt(5, salary);
	      prepStat.addBatch();
               int []  BR = prepStat.executeBatch();
              for(int i =1;i<BR.length;i++) {
    	       System.out.println(BR[i]);
		      }
             System.out.println("Do you want to  enter more Employee Details: yes/no");
        }while(scan.next().equalsIgnoreCase("Yes"));
       	   Program1.display(connection, prepStat, reSet);
	}
	           
//	if user enter S2=Delete..
	               static void Delete() throws SQLException{
	        	   Program1.display(connection, prepStat, reSet);
		    	   String S2 ="Delete from `employee` where `id`=?";
		    	   String S3 = "Delete from `employee` where `Department` = ?";
		    	   System.out.println("Enter Id or Department.....");
		    	   if(scan.next().equalsIgnoreCase("Id")) {
	               prepStat= connection.prepareStatement(S2);
		            System.out.println("Enter Id to delete");
	               int Id = scan.nextInt();
	                 prepStat.setInt(1, Id);
	                System.out.println(prepStat.executeUpdate());
	                  Program1.display(connection, prepStat, reSet);
		            }
		    	   else if(scan.next().equalsIgnoreCase("Department")) {
		    		   prepStat= connection.prepareStatement(S3);
		    		   System.out.println("Enter the Department u want to delete");
		    		   String dept = scan.next();
		    		   prepStat.setString(1, dept);
		    		   System.out.println(prepStat.executeUpdate());
		    		   Program1.display(connection, prepStat, reSet);
		    	   }
	           }
//		        if user enter S3=Update...
	                  static void   Update() throws SQLException{
	        	   String S4= "Update  `employee` set  `salary`=  salary + ? where `Id`= ?";
	        	   String S5= "Update  `employee` set  `Name`  = ? where `Id`=?";
	        	   String S6 = "Update  `employee` set  `Email`  = ? where `Id`=?";
	        	   System.out.println("Enter  details to update Salary or Name or Email");
	        	   String  Str = scan.next();
	        	      if( Str.equalsIgnoreCase("Salary")) {
	        		   prepStat= connection.prepareStatement(S4);
	        		   System.out.println("Enter Salary.....");
	        		   int Salary = scan.nextInt();
	        		   System.out.println("Enter Id...");
	        		   int Id = scan.nextInt();
	        		   prepStat.setInt(1, Salary);
	        		   prepStat.setInt(2, Id);
	                     System.out.println(prepStat.executeUpdate());
	                   }
	        	       else if(Str.equalsIgnoreCase("Name")) {
	        	       prepStat= connection.prepareStatement(S5);
	        		    System.out.println("Enter Name");
	        		    String E_Name = scan.next();
	        		     prepStat.setString(1,E_Name);
	        		     System.out.println("Enter Id...");
		        		 int Id = scan.nextInt();
		        		  prepStat.setInt(2, Id);
	        		     System.out.println(prepStat.executeUpdate());
	        	         }
//	        		   User enters Email......
	        	          else if( Str.equalsIgnoreCase("Email")) {
	               		   prepStat= connection.prepareStatement(S6);
	        	    	 System.out.println("Enter Email");
	      	              String Email = scan.next();
	      	            prepStat.setString(1, Email);
	      	               System.out.println("Enter Id...");
	        		      int Id = scan.nextInt();
	        		     prepStat.setInt(2, Id);
	      	            System.out.println(prepStat.executeUpdate());
	        	       }
	                     Program1.display(connection, statement, reSet);
	        	  }
	                   static void  Select() throws SQLException {
	        	        	String S7= "Select * from employee";
	        	            prepStat = connection.prepareStatement(S7);
                             reSet= prepStat.executeQuery();
                               resultSetMetaData = reSet .getMetaData();
                             for(int i =1 ;i<=resultSetMetaData.getColumnCount();i++) {
                            	   System.out.println(resultSetMetaData.getColumnName(i)+" ");
                               }
                               while(reSet.next()){
                            	 int id =reSet.getInt("Id");
                            	 String Name = reSet.getNString("Name");
                            	 String Email = reSet.getString("Email");
                            	 String Department = reSet.getString("Department");
                            	  int Salary = reSet.getInt("salary");
                            	   System.out.println("--------------------------------------------------------");
                               	   System.out.printf("%-3d|%-6s|%-16s|%-4s|%d \n" ,id, Name,Email,Department,Salary);
                                }
                                  System.out.println("---------------------------------------------------------");
                             }
	  static void CloseAll_P6() throws SQLException{
	                          if(connection != null) {
	                        	connection.close();
	                            }
	                            if(prepStat!= null) {
		                          prepStat.close();
	                              }
	                           if(reSet!= null) {
		                            reSet.close();
	                                }
		                           scan.close();
                                }
}
