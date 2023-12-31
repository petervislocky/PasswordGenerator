# PasswordGenerator
This is a simple password generator I am writing in java to practice coding, also just for fun. It runs on the command line and can be used to create secure passwords given that the passwords it generates are generated randomly using the SecureRandom class. You can adjust whether you want to include uppercase letters, lowercase letters, numbers, or symbols in the settings option in the main menu. The settings will save to a .properties file created and stored in the user's home directory in a folder called "PasswordGenConfig", and the file will be called "passwordSettings.properties". This way the settings applied will persist even after the program is closed. I will continue to add more features overtime to try to make it as user friendly and useful as possible. Also if you choose to use this I recommend writing the passwords down somewhere or adding them to your password manager, because your passwords that are generated are not saved anywhere.

*To run*  
You must have java installed (Version: Java SE17 or newer is what I validated it for but older versions should work as well).  
**To download this program** just download the jar file in the releases tab in this repository. Once you downloaded the jar archive just navigate to the directory you downloaded it into in the terminal and run **java -jar PasswordGenerator.jar**     

If you want to compile from source or use the pre-compiled .class files in the repository below are instructions on how to do that.

*To compile from source*  
1. Clone git repository.  
2. In the terminal navigate to the directory you installed the project to and run **javac com\PasswordSettings.java com\UserInputHandler.java com\passwdGen.java com\passwdGenMain.java**.   
3. Then run **java com.passwdGenMain**.

*To run the pre-compiled .class files*
1. In the terminal navigate within the project file to the bin directory **PasswordGenerator\bin**
2. Then simply run **java com.passwdGenMain**

*Alternatively you can also run inside Eclipse IDE*
