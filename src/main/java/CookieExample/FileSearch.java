package CookieExample;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileSearch {
	
	ParseCookie pc;

    public void parseFile(String fileName,String searchStr) throws FileNotFoundException{
        Scanner scan = new Scanner(new File(fileName));
        while(scan.hasNext()){
            String line = scan.nextLine().toString();
            if(line.contains(searchStr)){
                System.out.println("the line is " +line);
                String newline = line;
                String[] ss;
                ss= newline.split("\\;");
                ss = ss[1].split("\\;");

                System.out.println("the GA visit Token is "+ss[0]);
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException{
        FileSearch fileSearch = new FileSearch();
        fileSearch.parseFile("C:\\Users\\Avani\\workspace\\Cookie\\Cookies.data", "Cookie_VisitToken");
    }

}
