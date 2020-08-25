/*	
 * 	File:				Card.java
 * 	Associated Files:	Main.java, Deck.java, CrazyEights.java
 * 	Packages Needed:	
 * 	Author:            	Michael Ngo (https://github.com/yeeshue99)
 * 	Date Modified:      8/20/2020 by Michael Ngo
 * 	Modified By:        Michael Ngo
 * 
 * 	Purpose:			Abstraction for a playing card
 */

/*
 * Class:				Card
 * Purpose:				Abstract representation of playing card
 * Methods:				GetSuit, GetValue, GetLabel, SetSuit, 
 * 							GetStringValue, SuitToChar, CharToSuit
 */
public class Card{
	private String label;
	private int value;
	private char suit;
	
	/*
	 * Function:			Initialize 
	 * Params: 
	 * Purpose:				Initialize card to essentially null values;
	 * Returns: 			
	 */
	public Card() {
		label = "";
		value = -1;
		suit = '\000';
	}
	
	/*
	 * Function:			Initialize Overload
	 * Params: 				Pretty print card(String), card numeric value(int), suit of card(char)
	 * Purpose:				Initialize card to specific values
	 * Returns: 			
	 */
	public Card(String label, int value, char suit) {
		this.label = label;
		this.value = value;
		this.	suit = suit;
	}
	
	/*
	 * Function:			GetSuit 
	 * Params: 
	 * Purpose:				Getter for card suit
	 * Returns: 			Suit of card(char)
	 */
	public char GetSuit() {
		return suit;
	}
	
	/*
	 * Function:			GetValue 
	 * Params: 
	 * Purpose:				Getter for card int value
	 * Returns: 			Value of card(int)
	 */
	public int GetValue() {
		return value;
	}
	
	/*
	 * Function:			GetLabel 
	 * Params: 
	 * Purpose:				Getter for pretty print of card
	 * Returns: 			Pretty print of card(String)
	 */
	public String GetLabel() {
		return label;
	}
	
	/*
	 * Function:			SetSuit 
	 * Params: 				Suit to set card to(char)
	 * Purpose:				Replace 8 wild card with card of new suit
	 * Returns: 			
	 */
	public void SetSuit(char suit) {
		this.suit = suit;
		String[] splitLabel = this.label.split(" ");
		this.label = splitLabel[0] + " of " + CharToSuit(suit);
	}
	
	/*
	 * Function:			GetStringValue
	 * Params: 				
	 * Purpose:				Find instance's String representation of card value
	 * Returns: 			Value of card(String)
	 */
	public String GetStringValue() {
		switch(value) {
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		case 1:
			return "A";
		default:
			return Integer.toString(value);
		}
	}
	
	/*
	 * Function:			SuitToChar 
	 * Params: 				Suit to be converted(String)
	 * Purpose:				Switch from String to char representation of certain card suit
	 * Returns: 			Char representation of suit(char)
	 */
	public static char SuitToChar(String suit) {
		switch(suit) {
		case "Clubs":
			return 'C';
		case "Hearts":
			return 'H';
		case "Spades":
			return 'S';
		case "Diamonds":
			return 'D';
		default:
			return '\0';
		}
	}
	
	/*
	 * Function:			CharToSuit
	 * Params: 				Char to be converted(char)
	 * Purpose:				Switch from char to String representation of certain card suit
	 * Returns: 			String representation of suit(String)
	 */
	public static String CharToSuit(char suit) {
		switch(suit) {
		case 'C':
			return "Clubs";
		case 'H':
			return "Hearts";
		case 'S':
			return "Spades";
		case 'D':
			return "Diamonds";
		default:
			return "";
		}
	}
	
	/*
	 * Function:			CardEquivalent
	 * Params: 				Card value from construction(String)
	 * Purpose:				Find given card's numeric value
	 * Returns: 			Value of card(int)
	 */
	public static int CardEquivalent(String cardVal) {
		switch(cardVal) {
		case "J":
			return 11;
		case "Q":
			return 12;
		case "K":
			return 13;
		case "A":
			return 1;
		default:
			return Integer.parseInt(cardVal);
		}
	}
}