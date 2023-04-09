import java.net.*;
import java.io.*;
import java.util.*;

public class Main{
    ArrayList<String> urlLinks = new ArrayList<String>();
    ArrayList<String> storedProgress = new ArrayList<String>();
    ArrayList<String> newProgress = new ArrayList<String>();
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Integer> chNum = new ArrayList<Integer>();

    public Main(){

    }

    public void setOldData(){
        try {
            File file = new File("C:\\Users\\nymph\\IdeaProjects\\MangaCheck\\current.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            br.readLine();
            br.readLine();
            while((temp = br.readLine()) != null){
                storedProgress.add(temp);
                br.readLine();
                br.readLine();
            }
            br.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void obtainData(String link){
        try{
            //DateTimeFormatter myDate = DateTimeFormatter.ofPatter("MMM-dd-yyyy");
            //int temp = newProgress.size();
            URL url = new URL(link);
            InputStream is = (InputStream) url.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            //BufferedWriter bw = new BufferedWriter(new FileWriter("data.html"));
            String line;
            String updateLine = "<span><a href=";
            String updateLine2 = "chapter-name";
            while((line = br.readLine()) != null){
                line = line.trim();
                if(line.length() > 150 && (line.substring(0,14).equals(updateLine) || line.substring(25,37).equals(updateLine2))){

                    newProgress.add(line);
                    break;
                }

            }
            /*
            if(temp == newProgress.size()){
                System.out.println("FUUUUUUCK");
            }
            else{
                System.out.println("1");
            }
            */

            br.close();

            //bw.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void setNames(){
        try {
            File file = new File("C:\\Users\\nymph\\IdeaProjects\\MangaCheck\\current.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            while((temp = br.readLine()) != null){
                names.add(temp);
                br.readLine();
                br.readLine();
            }
            br.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void setLinks(){
        try {
            File file = new File("C:\\Users\\nymph\\IdeaProjects\\MangaCheck\\current.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            br.readLine();
            while((temp = br.readLine()) != null){
                urlLinks.add(temp);
                br.readLine();
                br.readLine();
            }
            br.close();
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public ArrayList getName(){
        return names;
    }

    public ArrayList getLinks(){
        return urlLinks;
    }
    public ArrayList getOldData(){
        return storedProgress;
    }
    public ArrayList getNewData(){
        return newProgress;
    }
    public ArrayList getChap(){
        return chNum;
    }
    public void incNum(int i){
        chNum.add(i);
    }
    public boolean compareData(int i){
        if(!newProgress.get(i).toString().equals(storedProgress.get(i).toString())){
            return true;
        }
        return false;
    }

    public int checkChapters(int i){
        int num = 0;
        try {
            String link = urlLinks.get(i).toString();
            URL url = new URL(link);
            InputStream is = (InputStream) url.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            String updateLine = "<span><a href=";
            String updateLine2 = "chapter-name";
            while((line = br.readLine()) != null){
                line = line.trim();
                if(line.length() > 150 && (line.substring(0,14).equals(updateLine) || line.substring(25,37).equals(updateLine2))){
                    if(line.equals(storedProgress.get(i).toString())){
                        break;
                    }
                    num++;
                }

            }
            br.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        return num;
    }
    public void printManga(){
        int listNum = 1;
        for(int i = 0; i < names.size(); i++){
            if(chNum.get(i) > 0) {
                System.out.println(listNum + ". " + names.get(i).toString() + " --- " + chNum.get(i) + " new chapters.");
                listNum++;
            }
        }
    }
    public void updateFile(){
        try{
            File file = new File("C:\\Users\\nymph\\IdeaProjects\\MangaCheck\\current.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i = 0;
            int cnt = 0;
            String line;
            ArrayList<String> temp = new ArrayList<String>();
            while((line = br.readLine()) != null){
                line.trim();
                if(cnt == 2){
                    if(i == newProgress.size()){
                        break;
                    }
                    line = newProgress.get(i).toString();
                    i++;
                    cnt -= 3;
                }
                temp.add(line);
                cnt++;
            }
            /*
            br.close();
            for(int j = 0; j < temp.size(); j++){
                System.out.println(temp.get(j));
            }
            */
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for(int j = 0; j < temp.size(); j++){
                bw.write(temp.get(j).toString());
                bw.newLine();
            }

            bw.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        Main mangas = new Main();
        mangas.setOldData();
        mangas.setLinks();
        mangas.setNames();

        for (int i = 0; i < mangas.getName().size(); i++) {
            //System.out.print(mangas.getName());
            //System.out.println(i);
            mangas.obtainData(mangas.getLinks().get(i).toString());
        }
        /*
        for (int i = 0; i < mangas.getName().size(); i++) {
            System.out.println(mangas.getName().get(i).toString());
        }
        for (int i = 0; i < mangas.getOldData().size(); i++) {
            System.out.println(mangas.getOldData().get(i).toString());
        }
        for (int i = 0; i < mangas.getLinks().size(); i++) {
            System.out.println(mangas.getLinks().get(i).toString());
        }
        for (int i = 0; i < mangas.getNewData().size(); i++) {
            System.out.println(mangas.getNewData().get(i).toString());
        }
        */

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("Updates.txt"));
            Scanner read = new Scanner(System.in);
            String input;
            boolean updates = false;
            for(int i = 0; i < mangas.getName().size(); i++){
                if(mangas.compareData(i)){
                    updates = true;
                }
                int temp = mangas.checkChapters(i);
                mangas.incNum(temp);

            }
            mangas.printManga();
            if(updates) {
                System.out.println("There are updates!");
                System.out.println("Do you want to update the progress?");
                input = read.nextLine();
                if (input.equalsIgnoreCase("yes")) {
                    mangas.updateFile();
                    System.out.println("Progress Updated!");
                }
            }
            else{
                System.out.println("No updates!");
            }
            bw.close();
            read.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}