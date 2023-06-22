import java.util.ArrayList;

class Board {
	public class PeggingStack {
		public ArrayList<Card> peggingStack;
		public int peggingStackCount;

		public PeggingStack() {
			peggingStack = new ArrayList<Card>();
			peggingStackCount = 0;
		}

		@SuppressWarnings("unchecked")
		public PeggingStack(ArrayList<Card> cards) {
			peggingStack = (ArrayList<Card>) cards.clone();
			for (int i = 0; i < cards.size(); i++) {
				peggingStackCount += cards.get(i).getRank();
			}
		}

		public Card getCard(int i) {
			return peggingStack.get(i);
		}

		public int getCount() {
			return peggingStackCount;
		}

		public void addCard(Card c) {
			peggingStack.add(c);
			if (c.getRank() >= 9) {
				peggingStackCount += 10;
			} else {
				peggingStackCount += (c.getRank() + 1);
			}
		}

		public void discardCard(Card c) {
			peggingStack.remove(c);
			peggingStackCount -= c.getRank();
		}

		public void discardCard(int i) {
			int value;
			if (peggingStack.get(i).getRank() >= 9) {
				value = 10;
			} else {
				value = (peggingStack.get(i).getRank() - 1);
			}

			peggingStack.remove(i);
			peggingStackCount -= value;
		}

		public void resetCount() {
			for (int i = 0; i < peggingStack.size(); i++) {
				discardCard(i);
			}
			peggingStackCount = 0;
		}

		@SuppressWarnings("unchecked")
		public PeggingStack copy() {
			PeggingStack peggingStack = new PeggingStack();
			peggingStack.peggingStack = (ArrayList<Card>) this.peggingStack.clone();
			peggingStack.peggingStackCount = this.peggingStackCount;
			return peggingStack;
		}

		public ArrayList<Card> getCards() {
			return peggingStack;
		}

		public int size() {
			return peggingStack.size();
		}
	}

	public static int evaluatePeggingStack(ArrayList<Card> cards) {
		int score = 0;

		Card top = cards.get(cards.size() - 1);

		// pairs
		int pairDepthCount = 0;
		for (int i = cards.size() - 2; i >= 0; i--) {
			if (top.rank >= 9) {
				if (cards.get(i).rank == 10) {
					pairDepthCount++;
				} else {
					break;
				}
			}
			else {
				if (cards.get(i).rank == (top.rank + 1)) {
					pairDepthCount++;
				} else {
					break;
				}
			}
		}
		if (pairDepthCount == 1) {
			score += 2;
			System.out.printf("Pair: +2 points\n");
		} else if (pairDepthCount > 1) {
			score += 6 * (pairDepthCount - 1);
			if (pairDepthCount == 2) {
				System.out.printf("Trips: +6 points\n");
			} else {
				System.out.printf("Quad: +12 points\n");
			}
		}

		// count for 15 and 31
		int count = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getRank() >= 9) {
				count += 10;
			} else {
				count += (cards.get(i).getRank() + 1);
			}
		}
		if (count == 15) {
			score += 2;
			System.out.printf("Fifteen: +2 points\n");
		} else if (count == 31) {
			score += 2;
			System.out.printf("Thirty-one: +2 points\n");
		}
		// runs
		int runCount = 1;
		int topCardValue;
		if (top.rank >= 9) {
			topCardValue = 10; 
		}
		else {
			topCardValue = (top.rank + 1);
		}
		
		if (top.rank == 0)
			topCardValue = 14;
		int difference = 1;
		for (int i = cards.size() - 2; i >= 0; i--) {
			if (cards.get(i).rank >= 9) {
				if (topCardValue - 10 == difference)
					runCount++;
				else
					break;
				difference++;
			}
			else {
				if (topCardValue - (cards.get(i).rank + 1) == difference)
					runCount++;
				else
					break;
				difference++;
			}
			
		}
		if (runCount == 3) {
			score += runCount;
			System.out.printf("Run of Three: +3 points\n");
		}
		else if (runCount == 4) {
			score += runCount;
			System.out.printf("Run of Four: +4 points\n");
		}
		else if (runCount == 5) {
			score += runCount;
			System.out.printf("Run of Five: +5 points\n");
		}
		else if (runCount == 6) {
			score += runCount;
			System.out.printf("Run of Six: +6 points\n");
		}
		else if (runCount == 7) {
			score += runCount;
			System.out.printf("Run of Seven: +7 points\n");
		}
		runCount = 1;
		if (top.rank >= 9) {
			topCardValue = 10; 
		}
		else {
			topCardValue = (top.rank + 1);
		}
		difference = 1;
		for (int i = cards.size() - 2; i >= 0; i--) {
			if (-(topCardValue - cards.get(i).rank) == difference)
				runCount++;
			else
				break;
			difference++;
		}
		if (runCount == 3) {
			score += runCount;
			System.out.printf("Run of Three: +3 points\n");
		}
		else if (runCount == 4) {
			score += runCount;
			System.out.printf("Run of Four: +4 points\n");
		}
		else if (runCount == 5) {
			score += runCount;
			System.out.printf("Run of Five: +5 points\n");
		}
		else if (runCount == 6) {
			score += runCount;
			System.out.printf("Run of Six: +6 points\n");
		}
		else if (runCount == 7) {
			score += runCount;
			System.out.printf("Run of Seven: +7 points\n");
		}
		return score;
	}
}