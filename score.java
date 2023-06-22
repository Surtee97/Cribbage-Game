import java.util.*;
class ScoreEvaluator{
	
	static double[] dealProbabilities = {0.0196, 0.0196, 0.0209, 0.013, 0.0155, 0.0179, 0.0233, 0.0266, 0.0207, 0.0146, 0.0172, 0.0192, 0.0177, 0.0172, 0.0208, 0.0189, 0.0183, 0.0167, 0.0189, 0.0237, 0.0252, 0.0203, 0.0184, 0.017, 0.0183, 0.0171, 0.0199, 0.0204, 0.0185, 0.0158, 0.0154, 0.0219, 0.0217, 0.023, 0.0221, 0.0186, 0.0167, 0.0191, 0.0164, 0.0183, 0.0212, 0.0214, 0.015, 0.0157, 0.02, 0.0246, 0.0226, 0.023, 0.0174, 0.0189, 0.0166, 0.0192};
	static double[] nonDealProbabilities = {0.0199, 0.0202, 0.0168, 0.0133, 0.0017, 0.0169, 0.0179, 0.0212, 0.0191, 0.0227, 0.0175, 0.0244, 0.0301, 0.0193, 0.0216, 0.0159, 0.0151, 0.0012, 0.0143, 0.0209, 0.0227, 0.0203, 0.0269, 0.0163, 0.0226, 0.0309, 0.0224, 0.0227, 0.0163, 0.0173, 0.0015, 0.0165, 0.0197, 0.0198, 0.0212, 0.0277, 0.0164, 0.0248, 0.0327, 0.0203, 0.0203, 0.0177, 0.0146, 0.0014, 0.0166, 0.0189, 0.0177, 0.0225, 0.027, 0.0171, 0.0216, 0.0356};
	
	static double[] dealKeepProbabilities = {0.0187, 0.0157, 0.0206, 0.0205, 0.0165, 0.0194, 0.017, 0.0159, 0.0178, 0.0206, 0.0215, 0.0192, 0.0172, 0.0221, 0.0179, 0.0201, 0.0219, 0.0224, 0.0199, 0.0172, 0.0175, 0.0209, 0.0215, 0.0188, 0.0186, 0.0188, 0.0186, 0.018, 0.0178, 0.0193, 0.0208, 0.0191, 0.0172, 0.0199, 0.0194, 0.0188, 0.02, 0.0174, 0.0218, 0.0203, 0.0156, 0.0182, 0.0199, 0.0234, 0.0183, 0.0172, 0.0177, 0.0192, 0.0212, 0.0206, 0.0202, 0.0219};
	static double[] nonDealKeepProbabilities = {0.0177, 0.0157, 0.0217, 0.021, 0.0242, 0.0206, 0.0169, 0.0176, 0.0176, 0.0174, 0.0211, 0.0167, 0.0116, 0.0203, 0.0186, 0.021, 0.0223, 0.0304, 0.0221, 0.0199, 0.0196, 0.0205, 0.0172, 0.0181, 0.0164, 0.0123, 0.0181, 0.0178, 0.0189, 0.0203, 0.0273, 0.0218, 0.0183, 0.0217, 0.0192, 0.0137, 0.0207, 0.0147, 0.0164, 0.0193, 0.0158, 0.0193, 0.0205, 0.0305, 0.0188, 0.019, 0.0187, 0.0192, 0.0178, 0.0213, 0.0176, 0.0148};

	public static void probabilityCheck()
	{
		double a=0;
		double b=0;
		for (int i = 0; i < dealProbabilities.length; i++)
		{
			a += dealProbabilities[i];
			b += nonDealProbabilities[i];
		}
		System.out.println(a + ", " + b);
	}
	
	@SuppressWarnings("unchecked")
	public int evaluateHand(ArrayList<Card> hand, Card c)
	{
		ArrayList<Card> handCopy;
		handCopy = hand;
		Collections.copy(handCopy, hand);
		
		int score = 0;
		
		if (handCopy.size() != 4)
			return score;
		//Check for flush
		boolean flush = true;
		ArrayList<Card> cardSet = handCopy;
		String flushSuit = cardSet.get(0).getSuit();
		for (int i = 1; i < cardSet.size(); i ++)
		{
			if (cardSet.get(i).getSuit() != flushSuit)
				flush = false;
		}
		if (flush)
		{
			score += 4;
			if (c.getSuit() == flushSuit) {
				score ++;
				System.out.printf("Flush w/ Starter Card: +5 points\n");
			}
			else {
				System.out.printf("Flush: +4 points\n");
			}
		}
		
		//Check for right Jack
		for (int i = 0; i < cardSet.size(); i ++)
		{
			if (cardSet.get(i).rank == 11 && cardSet.get(i).getSuit() == c.getSuit())
			{
				score += 1;
				System.out.printf("One for his nobs: +1 point\n");
				break;
			}
		}
		
		//Check points for subsets of 5 cards
		boolean fiveStraitFound = false;
		boolean fourStraitFound = false;
		cardSet.add(c);
		Collections.sort(cardSet);
		boolean strait = true;
		int sum = 0;
		ArrayList<Card> cardSubset = new ArrayList<Card>();
		for (int i = 0; i < cardSet.size(); i ++)
		{
			if (cardSet.get(i).getRank() >= 9) {
				sum += 10;
			}
			else {
				sum += (cardSet.get(i).getRank() + 1);
			}
			
			if (i > 0 && cardSet.get(i).rank - cardSet.get(i-1).rank != 1)
				strait = false;
		}
		//handle ace special case in strait calculation
		if (!strait && cardSet.get(0).rank == 0)
		{
			strait = true;
			while (cardSet.get(0).rank == 0)
			{
				cardSet.add(cardSet.get(0));
				cardSet.remove(0);
			}
			for (int i = 0; i < cardSet.size(); i ++)
			{
				int cardRank = cardSet.get(i).rank;
				if (cardSet.get(i).rank == 0)
					cardRank = 14;
				if (i > 0 && cardRank - cardSet.get(i-1).rank != 1)
					strait = false;
			}
		}
		if (strait)
		{
			score += 5;
			System.out.printf("Run of five: +5 points\n");
			fiveStraitFound = true;
		}
		if (sum == 15) {
			score += 2;
			System.out.printf("Fifteen: +2 points\n");
		}
		strait = true;
		sum = 0;
		
		Collections.sort(cardSet);
		//Check points for subsets of 4 cards (straits, 15s)
		for (int i = 0; i < cardSet.size(); i ++)
		{
			cardSubset = (ArrayList<Card>) cardSet.clone();
			cardSubset.remove(cardSet.get(i));

			for (int j = 0; j < cardSubset.size(); j ++)
			{
				if (cardSubset.get(j).getRank() >= 9) {
					sum += 10;
				}
				else {
					sum += (cardSubset.get(j).getRank() + 1);
				}
				if (!fiveStraitFound && j > 0 && cardSubset.get(j).rank - cardSubset.get(j-1).rank != 1)
					strait = false;
			}
			//handle ace special case in strait calculation
			if (!strait && !fiveStraitFound && cardSubset.get(0).rank == 0)
			{
				strait = true;
				int count = 0;
				while (cardSubset.get(0).rank == 0)
				{
					cardSubset.add(cardSubset.get(0));
					cardSubset.remove(0);
					count ++;
					if (count > 4)
						break;
				}
				for (int j = 0; j < cardSubset.size(); j ++)
				{
					int cardRank = cardSubset.get(j).rank;
					if (cardSubset.get(j).rank == 0)
						cardRank = 14;
					if (j > 0 && cardRank - cardSubset.get(j-1).rank != 1)
						strait = false;
				}
			}
			if (!fiveStraitFound && strait)
			{
				score += 4;
				System.out.printf("Run of Four: +4 points\n");
				fourStraitFound = true;
			}
			if (sum == 15)
				score += 2;
				System.out.printf("Fifteen: +2 points\n");
		
			strait = true;
			sum = 0;
		}
		
		Collections.sort(cardSet);
		//Check points for subsets of 3 cards (straits, 15s) and 2 cards (15s, pairs)
		for (int i = 0; i < cardSet.size() - 1; i ++)
		{
			for (int j = i + 1; j < cardSet.size(); j ++)
			{
				cardSubset = (ArrayList<Card>) cardSet.clone();
				cardSubset.remove(cardSet.get(i));
				cardSubset.remove(cardSet.get(j));
				
				for (int k = 0; k < cardSubset.size(); k ++)
				{
					if (cardSubset.get(k).getRank() >= 9) {
						sum += 10;
					}
					else {
						sum += (cardSubset.get(k).getRank() + 1);
					}
					if (!fiveStraitFound && !fourStraitFound && k > 0 && cardSubset.get(k).rank - cardSubset.get(k-1).rank != 1)
						strait = false;
				}
				//handle ace special case in strait calculation
				if (!strait && !fiveStraitFound && !fourStraitFound && cardSubset.get(0).rank == 0)
				{
					strait = true;
					int count = 0;
					while (cardSubset.get(0).rank == 1)
					{
						cardSubset.add(cardSubset.get(0));
						cardSubset.remove(0);
						count ++;
						if (count == 3)
							break;
					}
					for (int k = 0; k < cardSubset.size(); k ++)
					{
						int cardRank = cardSubset.get(k).rank;
						if (cardSubset.get(k).rank == 0)
							cardRank = 14;
						if (k > 0 && cardRank - cardSubset.get(k-1).rank != 1)
							strait = false;
					}
				}
				if (!fiveStraitFound && !fourStraitFound && strait)
					score += 3;
					System.out.printf("Run of three: +3 points\n");
				if (sum == 15)
					score += 2;
					System.out.printf("Fifteen: +2 points\n");
			
				strait = true;
				sum = 0;
				
				cardSubset.clear();
				cardSubset.add(cardSet.get(i));
				cardSubset.add(cardSet.get(j));
				if (cardSubset.get(0).getRank() >= 9) {
					if (10 + (cardSubset.get(1).getRank() + 1) == 15) {
						score += 2;
						System.out.printf("Fifteen: +2 points\n");
					}
				}
				else if (cardSubset.get(1).getRank() >= 9) {
					if (10 + (cardSubset.get(0).getRank() + 1) == 15) {
						score += 2;
						System.out.printf("Fifteen: +2 points\n");
					}
				}
				else {
					if ((cardSubset.get(0).getRank() + 1) + (cardSubset.get(1).getRank() + 1) == 15) {
						score += 2;
						System.out.printf("Fifteen: +2 points\n");
					}
				}
				if (cardSubset.get(0).rank == cardSubset.get(1).rank) {
					score += 2;
					System.out.printf("Pair: +2 points\n");
				}
			}
		}
		
		return score;
	}
}