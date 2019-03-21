package reuven.java.interviewquestions;

/**
 * Singleton class means only one object is created for the given class. The constructor is made private
 * to restrict the creation of the object. A static method is used to get the instance of the object, so
 * the object creation can be handled inside the class only. A static block was used to create the object.
 * 
 * @author Reuven
 */
 
public class Singleton {
	
	private static Singleton newObj;					//class variable
	
	static{												//class instantiation
        newObj = new Singleton();
    }
	
	private Singleton(){								//constructor with private access modifier
	     
    }
	
	public static Singleton getInstance(){
        return newObj;
    }
	
	public void test(){
        System.out.println("Test Successful!");
    }

	public static void main(String[] args) {
		Singleton s = getInstance();
        s.test();
	}
	
}
