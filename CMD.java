import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CMD {
  // all file reading related methods here.
  // It is very important to balance the methods between here and the main class.
  private String fname;//input file
  private String cmds[];//list of commands
  private String logs;
  private int size;//number of lines in file
  private PrintWriter out,log;//output 
  private BufferedReader br;

  public CMD(String fileName,boolean f_writ_to){
    size = 0;//set file size
    logs = "";
    fname = fileName;
    try{
      br = new BufferedReader(new FileReader(fileName));
    }
    catch(IOException e){
      System.out.println("Error Opening File");
    }
    try{
      log = new PrintWriter(new FileWriter("log.txt",f_writ_to));
    }
    catch(IOException e){
     System.out.println("Failed to open file output.txt");
    }
    catch(NullPointerException npr){
     System.out.println("Must first initiate the file writer");
    }
    if(fileName.indexOf("json")>=0){
      try {
        char head = (char) br.read(); //reads the first char
        if (head != '{') {
          System.out.println("Unexpected Start of JSON");
        }
      } 
      catch (IOException e) {
        System.out.println("Error Reading File");
      }  
    }
  }

  public void write(ArrayList list,String fileName){
    try{
      out = new PrintWriter(new FileWriter(fileName));
      for(int i=0;i<list.size();i++){
        //System.out.println(list.get(i).toString()+ " size is "+list.size());
        out.println(list.get(i).toString());
      }
      out.flush();
      out.close();
    } 
    catch (IOException e) {
      System.out.println("Failed to open file output.txt");
    } 
    catch (NullPointerException np){
      System.out.println("Must first initiate the file writer");
    }
  }

  public void readTextFile(){
    try { //open up file and increment size for each line
            String line = br.readLine();
            while (line != null) {                
                size++;
                line = br.readLine();
            }
            br.close();
        } 
    catch (IOException e) {
            System.out.println("Error Reading File");
      }
    cmds = new String[size];//create an array of length "size"
    try { //read through the file and save each line to the array
            FileReader fr = new FileReader(fname);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int y=0;//array position
            while (line != null) {                
                cmds[y] = line;
                y++;
                line = br.readLine();
            }
            br.close();
        } 
    catch (IOException e) {
            System.out.println("Error Reading File");
        }
  }

  public String getJsonInput(){
    try {
      String current = "";//create a string buffer
      boolean open = false;
      char c = (char) br.read();
      while (c > 0) {//read each {"command line"}, execute and repeat
        if (c == '{') {
          current = "";
          open = true;
        } 
        else if (c == '}') {//command line fully stored in buffer
          if (open) return current;
          else return null;
        } 
        else {
          current += c;
        }
        c = (char) br.read();
      }
    } 
    catch (IOException e) {
      System.out.println("Error Reading File");
    }
    return null;
  }

  public void log(String aString){
    logs = logs + (aString + "\n");
  }

  public void closeLog(){
     try{
      log.println(logs);
    }
    catch(NullPointerException npr){
       System.out.println("Must first initiate the file writer");
    } 
    log.flush();
    log.close();
  }

  public String getLine(int x) {
    if(size()==0) {return null;}
    else {return cmds[x];}
  }

  public int size() {
    return size;
  }
}