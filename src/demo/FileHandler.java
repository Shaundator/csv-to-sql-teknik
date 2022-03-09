package demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    File file = new File("data/imdb-data.csv");

    //FileReaders
    public ArrayList<Movie> readImdbData() { //ArrayList<Movie>
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while (sc.hasNextLine()) {
                movies.add(stringToMovie(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return movies;
    } //Returns arraylist with movie object

    public String sqlManagerTables() {
        String tables = "";
        try {
            Scanner sc = new Scanner(file);
            tables = sc.nextLine();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tables;
    } //Returns the data for sql tables

    //FileWriters
    public void writeDataTables(String csv) {
        try {
            FileWriter fw = new FileWriter("data/ddl.sql");
            fw.write(csvToSQL(csv));
            fw.close();
            System.out.println("written to file");
        } catch (IOException e) {
            System.out.println("error...");
            e.printStackTrace();
        }

    }

    public void writeDataMovies(ArrayList<Movie> movies, String columns) {
        try {
            FileWriter fw = new FileWriter("data/dml.sql");
            fw.write(moviesToSQL(movies, columns));
            fw.close();
            System.out.println("written to file");
        } catch (IOException e) {
            System.out.println("error...");
            e.printStackTrace();
        }
    }


    //Services
    private Movie stringToMovie(String movie) { //Movie
        String[] data = movie.split(";");
        String year = data[0];
        String length = data[1];
        String title = data[2];
        String subject = data[3];
        String popularity = data[4];
        String awards = data[5];
        return new Movie(year, length, title, subject, popularity, awards);
    }

    private String csvToSQL(String scannerData) {
        String[] data = scannerData.split(";");
        String tables = "";
        tables = "CREATE TABLE imdb(\n";
        for (int i = 0; i < data.length; i++) {
            tables += data[i] + " varchar(255)\n";
        }
        tables += ")";
        return tables;
    }

    private String moviesToSQL(ArrayList<Movie> movies, String columns) {
        String result = "";
        String[] tables = columns.split(";");
        //Year;Length;Title;Subject;Popularity;Awards
        for (int i = 0; i < movies.size(); i++) {
            String temp = "INSERT INTO imdb (";
            for (int j = 0; j < tables.length; j++) {
                temp += tables[j];
                if (j == tables.length - 1) {
                    temp += ", ";
                }
            }
            temp += ")\n";
            temp += "VALUES (";
            for (int j = 0; j < tables.length; j++) {
                temp += "'" + movies.get(i).getYear() + "', ";
                temp += "'" + movies.get(i).getLength() + "', ";
                temp += "'" + movies.get(i).getTitle() + "', ";
                temp += "'" + movies.get(i).getSubject() + "', ";
                temp += "'" + movies.get(i).getPopularity() + "', ";
                temp += "'" + movies.get(i).isAwards() + "'";
            }
            temp += ")\n\n";
            result += temp;
        }
        return result;
    }

    public static void main(String[] args) {
        FileHandler fh = new FileHandler();
        ArrayList<Movie> movies = fh.readImdbData();
        String columns = fh.sqlManagerTables();
        System.out.println(columns);

        fh.writeDataTables(columns);
        fh.writeDataMovies(movies, columns);
    }
}
