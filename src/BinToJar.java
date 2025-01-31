import java.io.File;

import helpers.*;

public class BinToJar {
    public static void main(String[] args) {
        // Takes the current number from the BTJ-List.info file to be used to pick the path in the file
        String infoFile[] = FileH.fileToArray(FileH.getFilePath("BTJ-List.info"));
        int infoFileNum = Integer.valueOf(infoFile[0].split(" ")[1]);

        // Finds the corresponding path located in the info file
        String curFilePath = infoFile[infoFileNum].split(" ")[1];
        
        // If an BTJ file exists
        try {
            // Gets the BTJ file path
            String btjFilePath =  curFilePath + "\\BTJ.info";

            // Converts the BTJ.info file into the main class name and jar name
            String [] stcInfoStrings = FileH.fileToArray(btjFilePath);
            String mainClassName = stcInfoStrings[0].split(" ")[1];
            String jarName = stcInfoStrings[1].split(" ")[1];

            // Set the directory to the project to turn into a jar file
            File directory = new File(curFilePath);

            // Creates a text file that is used to contain all of the class files
            //UtilH.runCommand(directory, "dir /s /b src\\*.java > sources.txt");

            // Uses the created sources.txt file and compiles all the java files into classes
            //UtilH.runCommand(directory, "javac -d classes @sources.txt");

            // Creates a manifest.MF file that will be used for creating the jar file
            FileH.arrayToFile(curFilePath + "\\manifest.MF", new String[] {"Main-Class: " + mainClassName, ""});

            // If the jar file already exists delete it
            File jarFile = new File(curFilePath + "\\" + jarName + ".jar");
            if (jarFile.exists() == true) {
                UtilH.runCommand(directory, "del " + jarName + ".jar");
                while (jarFile.exists() == true) {}
            }

            // Creates a jar file with the given name as {jarName.jar}
            UtilH.runCommand(directory, "jar cmf manifest.MF " + jarName + ".jar -C bin/ .");

            // Waits for the jar file to be created
            while (jarFile.exists() == false) {}

            // Cleans up files no longer needed
            //UtilH.runCommand(directory, "del sources.txt");
            UtilH.runCommand(directory, "del manifest.MF");

        } 
        // If an BTJ file does not already exist
        catch (Exception e) {
            // Creates a BTJ.info file if one does not already exist
            FileH.arrayToFile(curFilePath + "\\BTJ.info", new String[] {"Main-Java-File: ", "Jar-Name: ", "This-Path: " + curFilePath});
        }
        

    }

    
}
