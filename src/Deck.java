/*	
 * 	File:				Deck.java
 * 	Associated Files:	Main.java, CrazyEights.java, Card.java
 * 	Packages Needed:	java.util.ArrayList, java.util.Collections, java.util.Comparator,
 * 							java.util.Deque, java.util.LinkedList, java.util.Random
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/17/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Setup card structure to be used in card games
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

/*
 * Class:				Deck
 * Purpose:				Handle all card operations
 * Methods:				MakeDeck, DealCards, DrawCard, DisplayCards,
 * 							CalculateScore, CardScore, dblDigitSort
 */
public class Deck {

	// Different combinations for cards
	static final String[] suits = { "Clubs", "Hearts", "Spades", "Diamonds" };
	static final String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
	//static HashMap<String, Integer> letterValues = new HashMap<String, Integer>();
	static Random rng =  new Random(420);
	
	// Container to hold all cards
	public static ArrayList<Card> deck = new ArrayList<Card>();
	public static Deque<Card> discard = new LinkedList<Card>();

	/*
	 * Function:			Initialize
	 * Params: 				
	 * Purpose:				Initializes Rummy engine
	 * Returns: 			
	 */
	public Deck() {
		
		MakeDeck();
	}

	/*
	 * Function:			MakeDeck 
	 * Params: 
	 * Purpose:				Initialize central deck to hold all cards but the Queen of Hearts
	 * Returns: 			
	 */
	public static void MakeDeck() {
		for (int i = 0; i < suits.length; i++) {
			for (int j = 0; j < values.length; j++) {
				String label = values[j] + " of " + suits[i];
				Card temp = new Card(label, Card.CardEquivalent(values[j]), Card.SuitToChar(suits[i]));
				deck.add(temp);
			}
		}

		Collections.shuffle(deck, rng);
	}

	/*
	 * Function:			DealCards 
	 * Params:				Number of hands(int)
	 * Purpose: 			Evenly split every card from the center deck to each hand 
	 * Returns:				Each hand
	 */
	public static ArrayList<ArrayList<Card>> DealCards(int numPlayers) {
		ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
		
		for (int i = 0; i < numPlayers; i++) {
			hands.add(new ArrayList<Card>());
		}

		int player = 0;
		int handSize = 5;
		outer:
		while (!deck.isEmpty()) {
			if(hands.get(player).size() < handSize) {
				hands.get(player).add(deck.remove(0));
			}
			else {
				int playersDone = 0;
				for (ArrayList<Card> temp : hands){
					if (temp.size() >= handSize) {
						Collections.sort(temp, dblDigitSort);
						playersDone++;
					}
				}
				if (playersDone >= numPlayers) {
					break outer;
				}
			}
			player++;
			if (player >= numPlayers) {
				player = 0;
			}

		}
		discard.addFirst(deck.remove(0));
		System.out.println();
		return hands;
	}
	
	/*
	 * Function:			DrawCard
	 * Params: 				Where to draw a card from(String)
	 * Purpose:				Draw a card from either the deck or discard pile
	 * Returns: 			Drawn card(String)
	 */
	public static Card DrawCard() {
		if(deck.size() <= 0) {
			return null;
		}
        return deck.remove(0);
	}
	
	/*
	 * Function:			DisplayCards
	 * Params: 				A certain player's hand(ArrayList<Card>)
	 * Purpose:				Prints out a hand to the console to be viewed
	 * Returns: 			
	 */
	public static void DisplayCards(ArrayList<Card> hand) {
		Collections.sort(hand, dblDigitSort);
		for (int i = 0; i < hand.size(); i++) {
			if (i != 0) {
				System.out.print(", ");
			}
			System.out.print(hand.get(i).GetLabel());
		}
		System.out.println();
	}
	
	/*
	 * Function:			CalculateScore
	 * Params: 				A certain player's hand(ArrayList<Card>)
	 * Purpose:				Calculate the worth of a player's hand to be given
	 * 							to the winner
	 * Returns: 			Calculated score
	 */
	public static int CalculateScore(ArrayList<Card> hand) {
		int score = 0;
		for (Card card : hand) {
			score += CardScore(card);
		}
		return score;
	}
	
	/*
	 * Function:			CardScore
	 * Params: 				Specific card(String)
	 * Purpose:				Prints out a hand to the console to be viewed
	 * Returns: 			
	 */
	public static int CardScore(Card card) {
		int score = card.GetValue();
		if (score > 10) {
			score = 10;
		}
		if (score == 8) {
			score = 50;
		}
		return score;
	}
	
	/*
	 * Function:			dblDigitSort
	 * Params: 				
	 * Purpose:				Sorts based off of Card values: A is low, K is high.
	 * Returns: 			Comparator of two values
	 */
	public static Comparator<Card> dblDigitSort = new Comparator<Card>()
    {
        @Override
        public int compare(Card c1, Card c2)
        {
        	Integer val1;
        	Integer val2;
        	val1 = c1.GetValue();
        	val2 = c2.GetValue();
            return val1.compareTo(val2);
        }
    };
}