public class ArrayList {
  // Implement this structure to store all Member Objects
   static final int defaultCapacity = 30;
    int capacity;     //max number of elements this can hold
    int currentSize;  //number of elements this is currently holding
    Member[] array;

    public ArrayList() {
        capacity = defaultCapacity;
        array = new Member[capacity];
    }

    public ArrayList(int specifiedCapacity) {
        capacity = specifiedCapacity;
        array = new Member[capacity];
    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }
    
    public void set(int i, Member m) {
        array[i] = m;
    }

    public Member get(int i) {
        if (i < currentSize) {
            return array[i];
        } 
        else {
            return null;
        }
    }
    
    public void Add(Member m) {
        if (currentSize < capacity) { //still got space
            array[currentSize++] = m;
        } 
        else {
            capacity += defaultCapacity;
            Member[] newArray = new Member[capacity];
            for (int i = 0; i < currentSize; i++) {
                newArray[i] = array[i];
            }
            newArray[currentSize++] = m;
            array = newArray;
        }
    }
    public void Mod(String id,boolean vipS){
        //search array for existing Element ID
        boolean found = false;
        for(int i=0;i<currentSize;i++){
          if(array[i].getID().equals(id)){
            array[i].setVIP(vipS);
            found = true;
          }
        }
        //if not found print error to log
        if(!found){
          System.out.println("ID not found for ID: " + id);
        }
        else{
          //print modification to log
        }
    }
    
    public void Rem(int index) {//invalid name and ID
        for (int i = index; i < currentSize; i++) {
            array[i] = array[i+1];
        }
        currentSize--;
    }
    
    public void printAll() {
        System.out.println("Current size = " + currentSize + "/" + capacity);
        for (int i = 0; i < currentSize; i++) {
            System.out.println(i + "\t" + array[i].toString());
        }
    }
}