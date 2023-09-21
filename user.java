import java.util.HashMap;
import java.util.Scanner;

public class Main{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> userCredentials = new HashMap<>();
        HashMap<String,String> forget = new HashMap<>();
        HashMap<String,String> confirm = new HashMap<>();
        HashMap<String,String> mid=new HashMap<>();
        System.out.println("Welcome to the login/signup system!");
        while (true){
            System.out.println("Enter 1 to sign up, 2 to log in, or any other number to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice == 1) {// login access takes place
                System.out.print("Enter your desired username: ");
                String username = scanner.nextLine();
                while(uservalid(username,userCredentials)){// validating the username
                    System.out.println("The username is already present.Plz... entyer the valid username.");
                    username=scanner.nextLine();
                }
                System.out.print("Enter your email id: ");
                String mail=scanner.nextLine();
                while(!mailverify(mail)){// validating mail id
                    System.out.println("Plz... enter the vaild email id for login");
                    mail=scanner.nextLine();
                }
                mid.put(username,mail);
                System.out.print("Enter your desired password: ");
                String password = scanner.nextLine();
                while(!validation(password)){// validating the password 
                    System.out.println("Plz.. enter the valid password with the one special char, one number ,one captial letter, one small charater without spaces");
                    password=scanner.nextLine();
                }
                userCredentials.put(username, password);
                System.out.println("Suppose if you forget your password.Plz.. give some hints that your the same person who logged the page.");
                System.out.print("The Question is: ");
                String fp=scanner.nextLine();
                System.out.print("Answer for above question: ");
                String pass=scanner.nextLine();
                confirm.put(username,fp);
                forget.put(fp,pass);
                System.out.println("You have successfully signed up!");
                
            } 
            else if (choice == 2) 
            {// login process is takesplace
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your mailid: ");
                String mailid = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();
                if (userCredentials.containsKey(username) && mid.get(username).equals(mailid)) 
                {
                    if(!userCredentials.get(username).equals(password))
                    {
                         System.out.println("Incorrect username or password! enter 1 to start process again / enter 5 for forget password");
                         int qu=scanner.nextInt();
                         scanner.nextLine();
                         if(qu==1)
                            break;
                          else if(qu==5)
                          {
                              String temp=confirm.get(username);
                              System.out.println(temp);
                              System.out.print("Enter the answer: ");
                              String answer=forget.get(temp);
                              String givenans=scanner.nextLine();
                              if(givenans.equals(answer))
                              {
                                  System.out.print("Enter the new password :");
                                  String pass = scanner.nextLine();
                                  while(!validation(pass))
                                  {// validating the password 
                                           System.out.println("Plz.. enter the valid password with the one special char, one number ,one captial letter, one small charater without spaces");
                                           pass=scanner.nextLine();
                                  } 
                                  userCredentials.put(username,pass);
                                 System.out.println("Start the process onec Again!..");
                                 //break;
                              }
                              else
                              {
                                 System.out.println("Sorry, your given details are wrong plz once again start the process.");
                                 //break;
                              }
                          }  
                    }
                    else
                    {
                        System.out.println("Successfully logged in...");
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
    static boolean validation(String str){// password validation function
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
    static boolean uservalid(String str,HashMap<String, String> userCredentials){// username validation function
        String ans=str.trim();
        if(userCredentials.containsKey(ans))
            return true;
        else if(ans.equals(""))
            return true;
        return false;    
    }
    static boolean mailverify(String mail){// user mail id validation function
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
