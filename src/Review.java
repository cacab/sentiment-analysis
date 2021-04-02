import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Review {

    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner (System.in);
        String fileName;
        File wordFile;
        Scanner wordScnr;
        String line = " ";
        String word1 = " ";
        String word2 = " ";
        String word3 = " ";
        int count = 0;
        int max = 0;

        FileReader surveyFile = new FileReader("SurveyResults.txt");
        File surveyFilee = new File("SurveyResults.txt");

        BufferedReader theReader = new BufferedReader(surveyFile);
        //read in the survey by each line
        ArrayList<String> data = new ArrayList<String>();
        while((line = theReader.readLine()) != null) {
            String[] string = line.toLowerCase().split(" ");
            data.addAll(Arrays.asList(string));
        }


        for(int i = 0; i < data.size(); i++){
            count = 1;

            for(int j = i+1; j < data.size(); j++){
                if(data.get(i).equals(data.get(j))){
                    count++;
                }
            }

            if(count > max){
                max = count;
                word1 = data.get(i);
                word2 = data.get(i+1);
                word3 = data.get(i+2);
            }
        }


        System.out.println("Top words used most commonly: " + word1 + ", " + word2);
        System.out.println("Sentiment analysis: ");
        fileName = "words.txt";
        wordFile = new File(fileName);
        wordScnr = new Scanner(wordFile);
        getSentiment(surveyFilee, wordScnr);
        theReader.close();
    }


    public static void getSentiment(File surveyFilee, Scanner wordScanner) throws IOException {

        String word;
        Scanner reviewScanner;
        int score;
        String text;
        int counter;
        double total;
        double totalAll = 0;
        int totalCount =  0;
        double average;

        while (wordScanner.hasNext()){
            word = wordScanner.next().toLowerCase();

            reviewScanner = new Scanner (surveyFilee);
            total = 0.0;
            counter = 0;

            while(reviewScanner.hasNext()) {

                score = reviewScanner.nextInt();
                text = reviewScanner.nextLine();
                text = text.toLowerCase();

                if(text.contains(word))
                {
                    counter += 1;
                    total += score;
                }
            }

            totalAll += total;
            totalCount += counter;

        }

        average = (totalCount > 0) ? totalAll/totalCount: 0;

        if (average > 0) {
            System.out.println("Average score: " + average);
            if (average > 2.01)  System.out.println("Positive sentiment");
            else if (average < 1.99) System.out.println("Negative sentiment");
            else System.out.println("Neutral sentiment");
        }
        else
            System.out.println("Retrain program- not enough words appeared");
    }
}