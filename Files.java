import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class


public class Files {
  public String name;

  public Files(){

  }

  public Files(String n){
    name = n;
  }

  public void createFile(){
    try {
      File file = new File(name + ".txt");
      if (file.createNewFile()) {
        System.out.println("File created: " + file.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public void writeFile(String input){
    try {
      FileWriter myWriter = new FileWriter(name + ".txt", true);
      myWriter.write(input + "\n");
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }
}