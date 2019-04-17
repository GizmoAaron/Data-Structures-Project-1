public class Member {
  // Member object
  private String name;
  private String CUNYID;
  private String major;
  private boolean vip;
  public Member(String n, String ID){
    this.name = n;
    this.CUNYID = ID;
  }
  public String getname() {return name;}
  public String getID() {return CUNYID;}
  public String toString() {return name + " " + CUNYID;}
  public void setname(String n) {this.name = n;}
  public void setID(String ID) {this.CUNYID = ID;}
  public void setMajor(String m) {major = m;}
  public void setVIP(boolean status) {vip = status;}
  public String getFirstName() {
    return name.substring(0,name.indexOf(" "));
  }
  public String getLastName() {
    return name.substring(name.indexOf(" ")+1);
  }
}