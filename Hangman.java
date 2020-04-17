//.......1.........2.........3.........4..........5..........6.........7.........8
import java.util.Random;//For creating a random generator
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
/**
 * A simple hangman like game that shares similar features and goal.
 *
 * @author (Raphael Juco)
 * @version (3/3/18)
 */
public class Hangman
{
    /*
     * These instance variables are used in multiple methods in this class that 
     * are static, but not outside of the class so they are private.
     */
    private static char guessedLetter;
    private static char[] wordTemplate;
    private static char[] guessedTemplate;
    private static int length;
    private static int index = 0;
    private static char[] allGuessedLetters;
    private static int lives;

    /**
     * In the main method, we print out the instructions, initialize our 
     * variables and call all of our methods to accomplish the task for 
     * operating the game.
     */
    public static void main(String[] args){
        Scanner keyboard = new Scanner(System.in);
        Scanner input = null;
        String word = "";
        boolean endWholeGame = false;
        boolean endCurrentGame = false;
        boolean checkWord;

        System.out.println("Welcome to Hangman!");
        System.out.println("In this game, you will try to figure out the word ");
        System.out.println("by guessing a letter that it consists. "); 
        System.out.println("You will have ten lives. Good luck!");

        /*
         * This block of code has two loops, one for the current game
         * and one for the whole game. The whole game/loop gets the word from 
         * the library and sets up the template/array for guessing. It also 
         * prompts the user to play again in the end. The current game/loop
         * takes care of the checking and filling up the template.
         */
        while(endWholeGame == false){
            int list = randomGenerator();//Returns a random int
            allGuessedLetters = new char[50];//For storing already guessed char
            lives = 10;//You start with ten lives
            try{
                input = new Scanner(new FileInputStream("words.txt"));
                for(int i = 0; i < list ;i++){
                    word = input.next();//Gets word from the library
                }
            }
            catch(IOException e){
                System.out.println("Cannot Open File!");
                System.exit(0);
            }

            length = word.length();
            wordTemplate = new char[length];//Array for storing the word
            guessedTemplate = new char[length];//Array for storing guessed char
            /*
             * The next loop is for filling the guessed template
             * with spaces, that way everytime it is printed on the 
             * screen, it shows that the char has not been guessed yet.
             */
            for(int a = 0; a < length; a++){
                guessedTemplate[a] = ' ';
            }
            /*
             * In the next block of code, is the actual game that prompts the 
             * user to enter a letter and does all the checking if it's in the 
             * word or not and returns a message back to the window. The game
             * or loop ends when the word is either completed or the user runs
             * out of lives.
             */
            while(endCurrentGame == false){
                System.out.print("Guess a letter: ");
                guessedLetter = keyboard.next().charAt(0);//reads next char
                index++;//Moves to next index of storage for all guessed char
                for(int s = 0; s < length; s++){
                    wordTemplate[s] = word.charAt(s);
                }
                checkLetterMatching();
                allGuessedLetters[index] = guessedLetter;//puts char in storage
                printingWordTemplate();
                checkWord = checkWordMatching();
                if(checkWord == true){
                    System.out.println("Congratulations! You won!");
                    break;//Exits the loop
                }
                else if(lives <= 0){
                    System.out.println("Sorry! You lose!");
                    break;
                }
            }
            /*
             * The next block of code prompts the user to play again.
             * It reads in the next entered char and repeats the game
             * or loop if 'y' is entered and ends the class for other.
             */
            System.out.println("Want to play again? (y/n)");
            char yesOrNo = keyboard.next().charAt(0);
            if(yesOrNo == 'y'){
                System.out.println("Okay");
            }
            else{
                System.out.println("Thanks for playing!");
                System.exit(0);
            }
        }
        input.close();//Closes the file
    }

    /**
     * In this method, we check if the contents of the array for the 
     * guessed template is the same for the array of the word template
     * or if the word is completely guessed then it returns true.
     */
    public static boolean checkWordMatching(){
        int count = 0;
        for(int i = 0; i < length; i++){
            if(guessedTemplate[i] == wordTemplate[i]){
                count++;
            }
        }
        if(count == length){
            return true;
        }
        return false;
    }

    /**
     * In this method, we check if the char that is guessed is in
     * the word, if it is then it puts the word in the template, if
     * not then it decreases the number of lives and prints it out.
     * This also checks if you have already previously guessed the char.
     */
    public static void checkLetterMatching(){
        int count = 0;
        boolean guessed = alreadyGuessed();
        for(int j = 0; j < length; j++){
            if(guessed == true){
                System.out.println("Already guessed!");
                break;
            }
            if(guessedLetter == wordTemplate[j]){
                guessedTemplate[j] = guessedLetter;
                count++;
            }
        }
        if(count < 1){
            System.out.println("Try Again.");
            lives--;
            System.out.println("Lives left: " + lives);
        }
    }

    /**
     * This method prints out the current guessed template
     * with the incomplete word showing what you have guessed
     * and what you still need to guess.
     */
    public static void printingWordTemplate(){
        for(int k = 0; k < length;k++){
            System.out.print(guessedTemplate[k]);
        }
        System.out.println();
        for(int i = 0; i < length;i++){
            System.out.print("_");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * This method is for another method, it goes through the 
     * array storage for all guessed char and if it finds it in 
     * there then it returns true, meaning if you have already 
     * previously guessed the char it then tells the other method so.
     */
    public static boolean alreadyGuessed(){
        int allLength = allGuessedLetters.length;
        for(int j = 0; j < allLength; j++){
            if(guessedLetter == allGuessedLetters[j]){
                return true;
            }
        }
        return false;
    }

    /**
     * This method is a random generator for int, it returns an int 
     * between 0 and 50, which is the number of words that is inside
     * of our library text that is called in main.
     */
    public static int randomGenerator(){
        Random rand = new Random();
        int max = 50;
        int min = 0;
        int n = rand.nextInt((max - min) + 1) + min;
        return n;
    }

}
//.......1.........2.........3.........4.........5.........6.........7.........8