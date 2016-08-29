import java.util.*;  
import package1.*;  
import package2.*;  
public class Test{  
  
    public static void main(String[] args){  
  
        A a = new A();  
        B b = new B();  
        C c = new C();  
          
        a.setValue(3);  
        b.setName("Java");  
        c.setName("Test");  
          
        System.out.println(a.getValue());  
        System.out.println(b.getName());  
        System.out.println(c.getName());  
    }  
}