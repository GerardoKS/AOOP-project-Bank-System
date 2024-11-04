package src;
import java.io.File; 
import java.io.IOException;
import java.io.FileWriter;

/**
 * The Files class provides methods to create a text file and write content to it.
 */
public class Files {
  
  /**
   * The name of the file (without extension).
   */
  public String name;

  /**
   * Default constructor for the Files class.
   */
  public Files(){
  }

  /**
   * Constructs a Files object with a specified name.
   * 
   * @param n The name of the file.
   */
  public Files(String n){
    name = n;
  }

  /**
   * Creates a new text file with the specified name.
   * If the file already exists, it notifies the user.
   */
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

  /**
   * Writes the provided input to the file. 
   * Appends the text to the file if it already exists.
   * 
   * @param input The text to be written to the file.
   */
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
