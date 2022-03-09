package demo;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileHandler fh = new FileHandler();
        ArrayList<Movie> movies = fh.readImdbData();


    }

    public static void printArray(ArrayList<Movie> movies){
        for (int i = 0; i < movies.size(); i++) {
            System.out.println(movies.get(i));
        }
    }
}
