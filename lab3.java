package lab3;

import java.io.IOException;

public class lab3 {

    public static void main(String[] Args) {
        try{
        Shell shell = new Shell();
        shell.run();}
        catch(IOException e){
            System.out.println(e);
        }
    }

}