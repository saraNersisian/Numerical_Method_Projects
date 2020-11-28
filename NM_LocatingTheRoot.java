import java.util.Scanner;
public class Card
{
   private int rank;
   private char suit;
   public Card(int r, char s)
   {
	   rank=r;
	   suit=s;
   }
   public Card(Card card2)
   {
	   this.rank=card2.rank;
	   this.suit=card2.suit;
   }
   public boolean equals(Card card2)
   {
	   if (card2 == null)
	      return false;
	   else
		   return (this.rank==card2.rank) && (this.suit == card2.suit);
   }
   public String toString()
   {
	   String name="";
	   switch (suit)
	   {
	      case 'd':
		     name="dimonds";
		     break;
	      case 'h':
	         name="hearts";
	    	 break;
	      case 's':
	    	 name="spades";
	    	 break;
	      case 'c':
	    	 name="clubs";
	    	 break;
	      default:
	         System.out.println("INVALID SELECTION");
	   }
	   return ("(" + name + ", " + suit + ")"); 
   }
   public static Card read()
   {   int  r;
       char s;
	   Scanner kb = new Scanner(System.in);
	   System.out.println("enter rank(1-13): ");
	   r=kb.nextInt();
	   System.out.println("enter suit(d=diamonds, h=hearts, s=spades, c=clubs): ");
	   s=kb.nextLine().charAt(0);
	   Card newCard = new Card(r,s) ;
	   return newCard;
	   
   }
}
