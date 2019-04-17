import java.util.StringTokenizer;

class Main {
  // Only the main routine. Do everything based on the input file
  public static void main(String[] args) {
    CMD reader = new CMD("input.txt",false);
    //load inputfile into string array
    reader.readTextFile();
    //create list to store members
    ArrayList list = new ArrayList();
    //create Hashmap to have O(1) search
    HashIndex map = new HashIndex();
    //parse commands and execute
    Parse(reader,list,map);
    //write the member list to txt
    CMD jsonreader = new CMD("input.json",true);
    ParseJson(jsonreader,list, map);
    reader.write(list, "output.txt");
    reader.closeLog();
    jsonreader.closeLog();
  }
  public static void Parse(CMD cmd,ArrayList list,HashIndex map){
    String cd;//command
    String name,id,vip;
    boolean status;
    if(cmd.size()==0) {
    //log: failed to parse an empty text file.
    cmd.log("Failed to parse an empty text file.");
    return;
    }
    for(int i=0;i<cmd.size();i++){
      //if(cmd.getLine(i)==null) break;
      String line = cmd.getLine(i);//works up to here
      StringTokenizer s = new StringTokenizer(line);
      cd = s.nextToken();
      if(cd.equals("ADD")) {
        id = s.nextToken();
        name = s.nextToken() + " " + s.nextToken();
        if(Verify(id,map)){
          //log: failure to add, ID already exists
          System.out.println("fail " +name);
          cmd.log("Failure to add, "+name+" already exists");
        }
        else{
          Member mem = new Member(name,id);//create member object
          list.Add(mem);//add user
          cmd.log(name+" added to Member Database");//log memeber added
          map.put(id,list.size()-1);//add id/index pair to map
        }
      }
      else if(cd.equals("MOD")){ 
        id = s.nextToken();
        vip = s.nextToken();
        if(vip.equals("true")) status = true;
        else status = false;
        if(Verify(id,map)){
          list.Mod(id,status);
        }
        else{
          //log: failure to modify, member doesn't exist
          cmd.log("Failure to modify, member doesn't exist.");
        }
      }
      else if(cd.equals("DEL")){ 
        id = s.nextToken();
        if(Verify(id,map)){
          list.Rem(map.get(id));
          map.remove(id);
        }
        else{
          //log: falure to remove, member doesn't exist
          cmd.log("Failed to remove member ID" + id + " doesn't exist.");
        }
      }
    }
    //cmd.closeLog();

  }
  public static void ParseJson(CMD cmd,ArrayList list, HashIndex map){
    String cd = cmd.getJsonInput();
    while(true) {
      if(cd!=null){
        String ary[] = cd.split(",");
        String type="";
        int arySize = 0;
        for(String s:ary){
          if(s.indexOf("type")!=-1){
            type = s.substring(s.indexOf(":")+1);
          }
        }
        type = type.replaceAll("\"","");
        execute(type,ary,cmd,list,map);
        cd = cmd.getJsonInput();
      }
      else break;
    }
    //cmd.closeLog();
  }
  public static void execute(String cmdType,String cmdAry[],CMD cmd,ArrayList list, HashIndex map){
    if(cmdType.equals("ADD")){
      String id = cmdAry[1].substring(cmdAry[1].indexOf(":")+1);
      String name = cmdAry[2].substring(cmdAry[2].indexOf(":")+1) + " " + cmdAry[3].substring(cmdAry[3].indexOf(":")+1);
      String major = cmdAry[4].substring(cmdAry[4].indexOf(":")+1);
      id = id.replaceAll("\"","");
      name = name.replaceAll("\"","");
      major = major.replaceAll("\"","");
      if(Verify(id,map)){
          //log: failure to add, ID already exists
          cmd.log("Failure to add, "+name+" already exists");
        }
      else{
          Member mem = new Member(name,id);//create member object
          mem.setMajor(major);
          list.Add(mem);//add user
          cmd.log(name+" added to Member Database");//log memeber added
          map.put(id,list.size()-1);//add id/index pair to map
        }
    }
    else if(cmdType.equals("MOD")){
      boolean vip;
      String id = cmdAry[1].substring(cmdAry[1].indexOf(":")+1);
      String status = cmdAry[2].substring(cmdAry[2].indexOf(":")+1);
      if(status.equals("true")) vip = true;
      else vip = false;
      id = id.replaceAll("\"","");
      status = status.replaceAll("\"","");
        if(Verify(id,map)){
          list.Mod(id,vip);
          cmd.log("Member with ID "+id+"has been modified");
        }
        else{
          //log: failure to modify, member doesn't exist
          cmd.log("Failure to modify, member doesn't exist.");
        }
      }
      else if(cmdType.equals("DEL")){
        String id = cmdAry[1].substring(cmdAry[1].indexOf(":")+1);
        id = id.replaceAll("\"","");
        if(Verify(id,map)){
          list.Rem(map.get(id));
          map.remove(id);
          cmd.log(id+" was removed from database");
        }
        else{
          //log: falure to remove, member doesn't exist
          cmd.log("Failed to remove member, ID " + id + " doesn't exist.");
        }
      }
  }
  public static boolean Verify(String str, HashIndex map){
    return map.containsKey(str);
  }
}
