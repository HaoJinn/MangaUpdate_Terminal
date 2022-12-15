import java.net.*;
import java.io.*;

import java.util.ArrayList;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

class Manga{
    String name;
    String urlLink;
    String htmlLine;
    Manga(String name, String urlLink, String htmlLine){
        this.name = name;
        this.urlLink = urlLink;
        this.htmlLine = htmlLine;
    }

    public String getName (){
        return name;
    }

    public String getUrlLink(){
        return urlLink;
    }

    public String getHtmlLine(){
        return htmlLine;
    }
}

public class Main{

    ArrayList<String> storedProgress;
    ArrayList<String> newProgress;
    ArrayList<String> names;

    public Main(){

    }

    public void getOldData(){
        try {
            int i = 1;
            File file = new File("C:\\Users\\nymph\\IdeaProjects\\MangaCheck\\current.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            while((temp = br.readLine()) != null){
                if(i % 2 == 0){
                    storedProgress.add(temp);
                }
                i++;
            }
            br.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void getNewData(){
        try{
            File file = new File("C:\\Users\\nymph\\IdeaProjects\\MangaCheck\\data.html");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            while((temp = br.readLine()) != null){
                newProgress.add(temp);
            }
            br.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public String obtainData(String link){
        String result = null;
        try{
            //DateTimeFormatter myDate = DateTimeFormatter.ofPatter("MMM-dd-yyyy");
            boolean taken = false;
            URL url = new URL(link);
            InputStream is = (InputStream) url.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //BufferedWriter bw = new BufferedWriter(new FileWriter("data.html"));
            String line = null;
            String updateLine = "<span><a href=";
            while((line = br.readLine()) != null){
                if(line.length() > 50 && line.substring(0,14).equals(updateLine) && !taken){
                    taken = true;
                    result = line;
                    //bw.write(line);
                    //bw.newLine();
                }

            }
            br.close();
            //bw.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    public void getNames(){

    }

    public boolean compareData(){
        return true;
    }
    public static void main(String[] args){

    }
}