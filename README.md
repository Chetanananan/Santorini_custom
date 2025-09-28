Name - Chetan Arjun Somuse 

Files:
docs - Has the uml diagram and pdf of the report for sprint 3 covering all the documentation.
src - Has all the source code responsible for the working of this game.

Steps on how to create executable on mac:
To create a JAR file in IntelliJ IDEA on macOS,
1. Access Project Structure: Go to File > Project Structure or use the shortcut ⌘; (Command + semicolon).
2. Select Artifacts: In the Project Structure dialog, navigate to Artifacts.
3. Add Artifact: Click the "+" button and select JAR > From modules with dependencies.
4. Choose Main Class: Select the class containing your main method. This is the entry point for your application.
5. Build Artifacts: In the main menu, go to Build > Build Artifacts and select the JAR artifact you just created.
6. the JAR: The generated JAR file will be in the out/artifacts folder of your project. 

After creating the JAR file:
1. Go to https://github.com/dante-biase/jar2app 
2. Install the jar2app either through homebrew or manually. 
3. If installed through Homebrew: 
   1. direct (cd) to the current directory or the jar file 
   2. run the command  jar2app JAR_FILE -n <<Name the file you want>>
4. Similar step for the Manual installation:
   1. direct (cd) to the current directory or the jar file
   2. run the command  jar2app JAR_FILE -n <<Name the file you want>>
That is the whole process to create an executable on MAC!
