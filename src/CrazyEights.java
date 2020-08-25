/*	
 * 	File:				CrazyEights.java
 * 	Associated Files:	Main.java, Deck.java, Card.java
 * 	Packages Needed:	java.util.ArrayList, java.util.HashMap, java.util.Scanner
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/18/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Underlying structure for War card game
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * Class:				War
 * Purpose:				Handles War engine and game
 * Methods:				PlayGame, GetDiscardChoice, GetActionChoice,
 * 							GetSuitChoice, NextPlayer
 */
public class CrazyEights {

	int numPlayers = 2;
	int rounds = 0;
	//Deck deck;
	ArrayList<ArrayList<Card>> allHands;
	String[] actions = { "play", "table", "draw" };
	char[] suits = { 'C', 'H', 'S', 'D' };

	/*
	 * Function:			Initialize
	 * Params: 				Number of players(int)
	 * Purpose:				Initializes Rummy engine
	 * Returns: 			
	 */
	public CrazyEights(int numPlayers) {
		if (numPlayers <= 1) {
			numPlayers = 2;
			System.out.println("There has to be at least two players. I assume that's what you meant!");
		}
		if (numPlayers > 6) {
			numPlayers = 6;
			System.out.println("There can be at most six players. I assume that's what you meant!");
		}
		this.numPlayers = numPlayers;
		Deck.MakeDeck();
		System.out.println("Dealing the deck evenly to every player...");
		allHands = Deck.DealCards(numPlayers);
		for (int i = 0; i < numPlayers; i++) {
			System.out.printf("Player #%d, this is the hand you were dealt:%n", (i + 1));
			Deck.DisplayCards(allHands.get(i));
			System.out.println();
		}
	}

	/*
	 * Function:			PlayGame
	 * Params: 				Java command line input(Scanner)
	 * Purpose:				Run the game loop and communicate with user
	 * Returns: 			Player who won and the rounds the game took(int[])
	 */
	public int[] PlayGame(Scanner sc) {
		System.out.println("Welcome to the game of Crazy Eights!");

		int player = 0;

		while (true) {
			ArrayList<Card> hand = allHands.get(player);
			Card discardCard = Deck.discard.getFirst();
			System.out.println("======================================");
			System.out.printf("Player #%d, these are your cards:%n", (player + 1));
			Deck.DisplayCards(allHands.get(player));
			System.out.printf("The card at the top of the discard pile is %s%n", discardCard.GetLabel());
			String action = "";
			
			while (true) {

				System.out.println("What do you want to do now? (To see list of actions type \"help\"):");
				action = GetActionChoice(sc);
				//Play a card
				if (action.equalsIgnoreCase(actions[0])) {
					ArrayList<Card> cardsToPlay = new ArrayList<Card>();
					for (Card playerCard : hand) {
						if (playerCard.GetValue() == discardCard.GetValue() || playerCard.GetSuit() == discardCard.GetSuit()) {
							cardsToPlay.add(playerCard);
						}
						if (playerCard.GetValue() == 8) {
							cardsToPlay.add(playerCard);
						}
					}
					if(cardsToPlay.size() > 0) {
						System.out.println("These are the cards that you can play:");
						Deck.DisplayCards(cardsToPlay);
						Card chosenCard = GetDiscardChoice(sc, cardsToPlay);
						if (chosenCard == null) {
							continue;
						}
						System.out.printf("Playing your %s...%n", chosenCard.GetLabel());
						hand.remove(chosenCard);
						if(chosenCard.GetValue() == 8) {
							System.out.println("You played an 8! Choose the suit: ");
							chosenCard.SetSuit(GetSuitChoice(sc));
						}
						Deck.discard.addFirst(chosenCard);
						break;

					}
					else {
						System.out.println("You don't have any cards that you can play!");
					}
				}
				//Look at table
				else if (action.equalsIgnoreCase(actions[1])) {
					System.out.printf("The card at the top of the discard pile is %s%n", discardCard.GetLabel());
				}
				//Draw cards
				else if(action.equalsIgnoreCase(actions[2])){
					int i = 0;
					for (i = 0; i < 3; i++) {
						Card chosenCard = Deck.DrawCard();
						if (chosenCard == null) {
							System.out.println("The deck is empty! Passing turn...");
							break;
						}
						System.out.printf("From the deck you drew %s%n", chosenCard.GetLabel());
						if(!(chosenCard.GetValue() == discardCard.GetValue() || chosenCard.GetSuit() == discardCard.GetSuit())) {
							System.out.println("Adding it to your hand...");
							hand.add(chosenCard);
						}
						else {
							System.out.println("Playing the card...");
							Deck.discard.addFirst(chosenCard);
							break;
						}
					}
					if(i == 3) {
						System.out.println("After drawing three cards you pass your turn!");
					}
					break;
				}
				else {
					System.out.println("The actions you can take are: ");
					System.out.println(Arrays.toString(actions));
				}
			}
			if (allHands.get(player).size() <= 0) {
				break;
			}
			player = NextPlayer(player);
		}
		System.out.printf("Someone has no more cards! The game lasted %d rounds!%n", rounds);
		int score = 0;
		for (ArrayList<Card> hand : allHands) {
			score += Deck.CalculateScore(hand);
		}
		int[] playerAndScore = new int[2];
		playerAndScore[0] = player;
		playerAndScore[1] = score;
		return playerAndScore;
		// return rounds;
	}
	
	/*
	 * Function:			GetDiscardchoice
	 * Params: 				Java command line input(Scanner), Current player(int)
	 * Purpose:				Input validate card to discard
	 * Returns: 			Card to discard(int)
	 */
	private Card GetDiscardChoice(Scanner sc, ArrayList<Card> hand) {
		System.out.printf("Choose a card to discard (1-%d) or \"-1\" to return: ", hand.size());
		int chosenCard = -1;

		chosenCard = sc.nextInt();
		if(chosenCard == -1) {
			return null;
		}
		while (!(chosenCard >= 1 && chosenCard <= hand.size())) {
			System.out.println(
					"Invalid card number. Please enter integer between 1 and " + hand.size() + ": ");
			chosenCard = sc.nextInt();
			if(chosenCard == -1) {
				return null;
			}
		}
		return hand.get(chosenCard - 1);
	}

	/*
	 * Function:			GetActionChoice
	 * Params: 				Java command line input(Scanner)
	 * Purpose:				Input validate action to do
	 * Returns: 			Player's chosen action(String)
	 */
	private String GetActionChoice(Scanner sc) {
		String action;
		action = sc.next();
		boolean reDoChoice = true;
		for (int i = 0; i < actions.length; i++) {
			if (action.equalsIgnoreCase(actions[i])) {
				reDoChoice = false;
			}
		}
		while (reDoChoice) {
			System.out.println("The actions you can take are: ");
			System.out.println(Arrays.toString(actions));
			action = sc.next();
			for (int i = 0; i < actions.length; i++) {
				if (action.equalsIgnoreCase(actions[i])) {
					reDoChoice = false;
				}
			}
		}
		return action;
	}
	
	/*
	 * Function:			GetSuitChoice
	 * Params: 				Java command line input(Scanner)
	 * Purpose:				Input validate suit to change 8 to
	 * Returns: 			Player's chosen suit(String)
	 */
	private char GetSuitChoice(Scanner sc) {
		char suit;
		suit = sc.next().charAt(0);
		boolean reDoChoice = true;
		for (int i = 0; i < actions.length; i++) {
			if (suit == suits[i]) {
				reDoChoice = false;
				break;
			}
		}
		while (reDoChoice) {
			System.out.println("The suits you can choose: ");
			System.out.println(Arrays.toString(suits));
			suit = sc.next().charAt(0);
			for (int i = 0; i < actions.length; i++) {
				if (suit == suits[i]) {
					reDoChoice = false;
				}
			}
		}
		return suit;
	}

	/*
	 * Function:			NextPlayer
	 * Params: 				Current player(int)
	 * Purpose:				Calculates the position of the next player
	 * Returns: 			Position of next player(int)
	 */
	private int NextPlayer(int player) {
		player++;
		if (player >= numPlayers) {
			player = 0;
			rounds++;
		}
		return player;
	}
}
