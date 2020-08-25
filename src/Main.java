/*	
 * 	File:				Main.java
 * 	Associated Files:	Deck.java, CrazyEights.java, Card.java
 * 	Packages Needed:	
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/17/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Run a simple War game in console
 */

import java.util.ArrayList;
import java.util.Scanner;

/*
 * Class:				Main
 * Purpose:				Run the code
 * Methods:				main
 */
public class Main {
	
	/*
	 * Function:			main 
	 * Params: 				commandLineArguments(String[]) {Not used}
	 * Purpose:				Handle overhead components to structure Rummy game
	 * Returns: 			
	 */
	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Welcome to Crazy Eights. Let's get ready to play! ");
        System.out.print("Enter number of players: ");
        int numPlayers = sc.nextInt();
        
        CrazyEights crazyEightsGame = new CrazyEights(numPlayers);

        int[] winner = crazyEightsGame.PlayGame(sc);
        winner[0] = winner[0] + 1;
        System.out.printf("Conratulations, player #%d, you won!", (winner[0]));
        System.out.printf("Your score was: %d%n", (winner[1]));
    }
}
