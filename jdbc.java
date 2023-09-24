import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class jdbc{
	public static void main(String[] args) throws SQLException{
    	
		Connection con=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/user","root","tamil");
        Scanner scanner = new Scanner(System.in);
        
        HashMap<String,String> userCredentials = new HashMap<>(); //username, password
        HashMap<String,String> forget = new HashMap<>(); //question,answer
        HashMap<String,String> confirm = new HashMap<>(); //username,question
        HashMap<String,String> mid=new HashMap<>(); //username,email
        
        System.out.println("Welcome to the login/signup system!!");
        System.out.println("Greetings from Tamilselvan <3");
	        
        	Statement st= con.createStatement();
	        String query="select * from login";
	        
	        ResultSet rs=st.executeQuery(query);
	        while(rs.next()) {
	        	String s1=rs.getString("username");
	        	String s2=rs.getString("email"); 
	        	String s3=rs.getString("password"); 
	        	String s4=rs.getString("question");
	        	String s5=rs.getString("answer");
	        	
	        	userCredentials.put(s1, s2);
	        	forget.put(s4, s5);
	        	confirm.put(s1, s4);
	        	mid.put(s1, s3);
	        }
	        
        while (true){
            System.out.println("Enter 1 to sign up, 2 to log in, or any other number to exit : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            if (choice == 1) {// signup process takes place
                System.out.print("Enter username : ");
                String username = scanner.nextLine();
                while(uservalid(username,userCredentials)){// validating the username
                    System.out.println("The username is already present in DataBase. Please, enter valid username.");
                    username=scanner.nextLine();
                }
                System.out.print("Enter your email id : ");
                String mail=scanner.nextLine();
                while(!mailverify(mail)){// validating mail id
                    System.out.println("Please, enter the vaild email id for login");
                    mail=scanner.nextLine();
                }
                mid.put(username,mail);
                System.out.print("Enter your desired password : ");
                String password = scanner.nextLine();
                while(!validation(password)){// validating the password 
                    System.out.println("Please, enter the valid password with the atleast one special character, one number ,one captial letter, one small charater without spaces");
                    password=scanner.nextLine();
                }
                userCredentials.put(username, password);
                System.out.println("Suppose if you forget your password, give some hints that your the same person who logged the page.");
                System.out.print("The Question is : ");
                String qn=scanner.nextLine();
                System.out.print("Answer for above question : ");
                String ans=scanner.nextLine();
                confirm.put(username,qn);
                forget.put(qn,ans);
                System.out.println("You have successfully signed up!!!");
                PreparedStatement ps= con.prepareStatement("Insert into login values(?,?,?,?,?)");
                ps.setString(1,username);
                ps.setString(2,password);
                ps.setString(3,mail);
                ps.setString(4,qn);
                ps.setString(5,ans);
                ps.execute();
            } 
            
            else if (choice == 2) 
            {
                System.out.print("Enter username : ");
                String username = scanner.nextLine();
                System.out.print("Enter email : ");
                String mailid = scanner.nextLine();
                System.out.print("Enter password : ");
                String password = scanner.nextLine();
                if (userCredentials.containsKey(username) && mid.get(username).equals(mailid)) 
                {
                    if(!userCredentials.get(username).equals(password))
                    {
                         System.out.println("Incorrect username or password! enter 1 to start process again / enter 5 for forget password");
                         int qu=scanner.nextInt();
                         scanner.nextLine();
                         if(qu==1)
                            continue;
                          else if(qu==5)
                          {
                              String temp=confirm.get(username); //question
                              System.out.println(temp);
                              System.out.print("Enter the answer : ");
                              String answer=forget.get(temp);
                              String givenans=scanner.nextLine();
                              if(givenans.equals(answer))
                              {
                                  System.out.print("Enter the new password :");
                                  String pass = scanner.nextLine();
                                  while(!validation(pass))
                                  {// validating the password 
                                           System.out.println("Enter the valid password with atleast one special character, number, captial letter, small charater without spaces");
                                           pass=scanner.nextLine();
                                  } 
                                  userCredentials.put(username,pass);
                                  PreparedStatement st3= con.prepareStatement("update login set password = ? where username = ?;");
                                  st3.setString(1,pass);
                                  st3.setString(2, username);
                                  st3.executeUpdate();
                                 System.out.println("Start the process once Again!..");
                                 //break;
                              }
                              else
                              {
                                 System.out.println("Sorry, your given details are wrong please try again.");
                                 //break;
                              }
                          }  
                    }
                    else
                    {
                        System.out.println("Successfully logged in!!!");
                    }
                } 
                else
                {
                     System.out.println("Incorrect username or password");
                }
            }
            else{
                System.out.println("finshed over...");
                break;
            }
        }
    }
	
	// password validation function
    static boolean validation(String str){
        int capital=0;
        int small=0;
        int special=0;
        int num=0;
        int space=0;
        
        for(int i=0;i<str.length();i++){
            char a=str.charAt(i);
            if(a>='A' && a<='Z') capital++;
            else if(a>='a' && a<='z') small++;
            else if(a>='0' && a<='9') num++;
            else if(a==' ') space++;
            else special++;
        }
        if(space==0 && num>=1 && special>=1 && small>=1 && capital>=1)
           return true;
        return false;
    }
    
    //username validation function
    static boolean uservalid(String str, HashMap<String, String> userCredentials){
        String ans=str.trim();
        if(userCredentials.containsKey(ans))
            return true;
        else if(ans.equals(""))
            return true;
        return false;    
    }
    
    // user email validation function
    static boolean mailverify(String mail){
        String ans="moc.liamg@";
        int j=0;
        for(int i=mail.length()-1;i>=0;i--){
            if(j>=ans.length())break;
            else if(mail.charAt(i)!=ans.charAt(j))
               return false;
            j++;   
        }
        for(int i=mail.length()-11;i>=0;i--){
            //System.out.print(mail.charAt(i)+" ");
            if(mail.charAt(i)==' ')
                return false;
        }
        return true;
    }
}
