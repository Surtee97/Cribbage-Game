import java.util.*;

public class Cribbage {
    public Cribbage() {
    };
    public static final Scanner INPUT = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.printf("Welcome to Cribbage!\nPlease Select 2 or 3 Players\n");
        int numPlayers = INPUT.nextInt();
        boolean match = true;
        if (numPlayers > 3 || numPlayers < 2) {
            System.out.printf("Incorrect Input Detected\nTerminating Game\n");
            match = false;
        }
        Cribbage newMatch = new Cribbage();
        while (match) {
            newMatch.game(numPlayers);
        }
    }

    public void game(int numPlayers) {
        
        Deck deck = new Deck();
        deck.generateDeck();
        deck.shuffle();
        Player playerOne = new Player(1);
        Player playerTwo = new Player(2);
        Player playerThree = new Player(3);
        Crib crib = new Crib();
        Board board = new Board();
        ScoreEvaluator score = new ScoreEvaluator();
        assignDealer(numPlayers, deck, playerOne, playerTwo, playerThree);
        createHands(numPlayers, deck, playerOne, playerTwo, playerThree);
        discardPhase(numPlayers, playerOne, playerTwo, playerThree, crib, deck);
        Card starter = deck.draw();
        playPhase(board, deck, numPlayers, playerOne, playerTwo, playerThree);
        showPhase(starter, deck, score, crib, numPlayers, playerOne, playerTwo, playerThree);
    }

    public void assignDealer(int numPlayers, Deck deck, Player playerOne, Player playerTwo, Player playerThree) {
        if (numPlayers == 2) {
            int firstCard = deck.draw().getRank();
            int secondCard = deck.drawIndexedCard(1).getRank();
            if (secondCard < firstCard) {
                playerTwo.setDealer();
                System.out.printf("Player 2 drew the lower card\n");
            } 
            else if (firstCard < secondCard) {
                playerOne.setDealer();
                System.out.printf("Player 1 drew the lower card\n");
            }
        } 
        else {
            int firstCard = deck.draw().getRank();
            int secondCard = deck.drawIndexedCard(1).getRank();
            int thirdCard = deck.drawIndexedCard(2).getRank();
            if (secondCard < firstCard && secondCard < thirdCard) {
                playerTwo.setDealer();
                System.out.printf("Player 2 drew the lowest card\n");
            } 
            else if (thirdCard < firstCard && thirdCard < secondCard) {
                playerThree.setDealer();
                System.out.printf("Player 3 drew the lowest card\n");
            } 
            else if (firstCard < secondCard && firstCard < thirdCard) {
                playerOne.setDealer();
                System.out.printf("Player 1 drew the lowest card\n");
            }
        }
    }

    public void createHands(int numPlayers, Deck deck, Player playerOne, Player playerTwo, Player playerThree) {
        deck.shuffle();
        if (numPlayers == 2) {
            for (int i = 0; i < 12; i++) {
                if (i % 2 == 1) {
                    playerOne.addToHand(deck.draw());
                } 
                else {
                    playerTwo.addToHand(deck.draw());
                }
            }
        } 
        else {
            for (int i = 0; i < 15; i++) {
                if (i % 3 == 1) {
                    playerOne.addToHand(deck.draw());
                } 
                else if (i % 3 == 2) {
                    playerTwo.addToHand(deck.draw());
                } 
                else {
                    playerThree.addToHand(deck.draw());
                }
            }
        }
    }

    public void discardPhase(int numPlayers, Player playerOne, Player playerTwo, Player playerThree, Crib crib, Deck deck) {
        int disc1;
        int disc2;
        if (numPlayers == 2) {
            int count = 1;
            while (count < 3) {

                if (count == 1) {
                    System.out.printf("Please choose 2 cards to discard\n");
                    System.out.printf("Player 1:\n");
                    displayCard(playerOne.getHand());
                    disc1 = INPUT.nextInt();
                    while (disc1 < 0 || disc1 > 5) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc1 = INPUT.nextInt();
                    }
                    disc2 = INPUT.nextInt();
                    while (disc2 < 0 || disc2 > 5 || disc2 == disc1) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc2 = INPUT.nextInt();
                    }
                    deck.transferCard(playerOne.hand.get(disc1), playerOne.hand, crib.getCrib());
                    if (disc2 > disc1) {
                        deck.transferCard(playerOne.hand.get(disc2 - 1), playerOne.hand, crib.getCrib());
                    }
                    else {
                        deck.transferCard(playerOne.hand.get(disc2), playerOne.hand, crib.getCrib());
                    }
                    count++;
                }
                else {
                    System.out.printf("Please choose 2 cards to discard\n");
                    System.out.printf("Player 2:\n");
                    displayCard(playerTwo.getHand());
                    disc1 = INPUT.nextInt();
                    while (disc1 < 0 || disc1 > 5) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc1 = INPUT.nextInt();
                    }
                    disc2 = INPUT.nextInt();
                    while (disc2 < 0 || disc2 > 5 || disc2 == disc1) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc2 = INPUT.nextInt();
                    }
                    deck.transferCard(playerTwo.hand.get(disc1), playerTwo.hand, crib.getCrib());
                    if (disc2 > disc1) {
                        deck.transferCard(playerTwo.hand.get(disc2 - 1), playerTwo.hand, crib.getCrib());
                    }
                    else {
                        deck.transferCard(playerTwo.hand.get(disc2), playerTwo.hand, crib.getCrib());
                    }
                    count++;

                }
            }

        } 
        else {
            int count = 1;
            crib.addToCrib(deck.draw());
            while (count < 4) {
                if (count == 1) {
                    System.out.printf("Please choose a card to discard\n");
                    System.out.printf("Player 1:\n");
                    displayCard(playerOne.getHand());
                    disc1 = INPUT.nextInt();
                    while (disc1 < 0 || disc1 > 5) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc1 = INPUT.nextInt();
                    }
                    deck.transferCard(playerOne.hand.get(disc1), playerOne.hand, crib.getCrib());
                    count++;
                }
                else if (count == 2) {
                    System.out.printf("Please choose a card to discard\n");
                    System.out.printf("Player 2:\n");
                    displayCard(playerTwo.getHand());
                    disc1 = INPUT.nextInt();
                    while (disc1 < 0 || disc1 > 5) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc1 = INPUT.nextInt();
                    }
                    deck.transferCard(playerTwo.hand.get(disc1), playerTwo.hand, crib.getCrib());
                    count++;
                }
                else {
                    System.out.printf("Please choose a card to discard\n");
                    System.out.printf("Player 3:\n");
                    displayCard(playerThree.getHand());
                    disc1 = INPUT.nextInt();
                    while (disc1 < 0 || disc1 > 5) {
                        System.out.printf("Incorrect Input, please try again\n");
                        disc1 = INPUT.nextInt();
                    }
                    deck.transferCard(playerThree.hand.get(disc1), playerThree.hand, crib.getCrib());
                    count++;
                }
            }
        }
    }

    public void playPhase(Board board, Deck deck, int numPlayers, Player playerOne, Player playerTwo, Player playerThree) {
        Board.PeggingStack stack = new Board().new PeggingStack();
        int play;
        if (numPlayers == 2) {
            if (playerOne.isDealer) {
                playerTwo.isTurn = true;
            } 
            else {
                playerOne.isTurn = true;
            }
            int goPoint = 0;
            while (!playerOne.hand.isEmpty() || !playerTwo.hand.isEmpty()) {
                boolean canPlay = false;
                if (playerOne.isTurn) {
                    if (playerOne.hand.isEmpty()) {
                        playerOne.isTurn = false;
                        playerTwo.isTurn = true;
                    } 
                    else {
                        for (int i = 0; i < playerOne.hand.size(); i++) {
                            if (stack.getCount() + playerOne.hand.get(i).getRank() <= 31) {
                                canPlay = true;
                            }
                        }
                        if (!canPlay) {
                            if (goPoint >= 1) {
                                playerOne.addPlayerScore(1);
                                System.out.printf("Player %d scored a GO: +1 point\n", playerOne.playerNum);
                                winCon(playerOne);
                                goPoint = 0;
                                System.out.printf("No one can play any cards that make a sum under 31. Count is reset.\n");
                                playerOne.isTurn = false;
                                playerTwo.isTurn = true;
                                stack.resetCount();
                                continue;
                            }
                            System.out.printf("Player 1 cannot play any cards that make a sum under 31. Go\n");
                            goPoint += 1;
                            playerOne.isTurn = false;
                            playerTwo.isTurn = true;
                        } 
                        else {
                            if (goPoint >= 1) {
                                goPoint = 0;
                            }
                            System.out.printf("Please pick a card to play\n");
                            System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                            displayCard(playerOne.getHand());
                            play = INPUT.nextInt();
                            while (play < 0 || play >= playerOne.hand.size()) {
                                System.out.printf("Incorrect Input, please try again\n");
                                play = INPUT.nextInt();
                            }
                            if (stack.getCount() == 31) {
                                stack.resetCount();
                            }
                            while (stack.getCount() + playerOne.hand.get(play).getRank() > 31) {
                                System.out.printf("Your input makes a sum greater than 31, please try again\n");
                                play = INPUT.nextInt();
                            }
                            deck.transferCard(playerOne.hand.get(play), playerOne.hand, playerOne.playedCards);
                            stack.addCard(playerOne.playedCards.get(playerOne.playedCards.size() - 1));
                            playerOne.addPlayerScore(Board.evaluatePeggingStack(stack.getCards()));
                            winCon(playerOne);
                            canPlay = false;
                            playerOne.isTurn = false;
                            playerTwo.isTurn = true;
                        }
                    }
                } 
                else {
                    if (playerTwo.hand.isEmpty()) {
                        playerTwo.isTurn = false;
                        playerOne.isTurn = true;
                    } 
                    else {
                        for (int i = 0; i < playerTwo.hand.size(); i++) {
                            if (stack.getCount() + playerTwo.hand.get(i).getRank() <= 31) {
                                canPlay = true;
                            }
                        }
                        if (!canPlay) {
                            if (goPoint >= 1) {
                                playerTwo.addPlayerScore(1);
                                System.out.printf("Player %d scored a GO: +1 point\n", playerTwo.playerNum);
                                winCon(playerTwo);
                                goPoint = 0;
                                System.out.printf("No one can play any cards that make a sum under 31. Count is reset.\n");
                                playerTwo.isTurn = false;
                                playerOne.isTurn = true;
                                stack.resetCount();
                                continue;
                            }
                            System.out.printf("Player 2 cannot play any cards that make a sum under 31. Go\n");
                            goPoint += 1;
                            playerTwo.isTurn = false;
                            playerOne.isTurn = true;
                        } 
                        else {
                            if (goPoint >= 1) {
                                goPoint = 0;
                            }
                            System.out.printf("Please pick a card to play\n");
                            System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                            displayCard(playerTwo.getHand());
                            play = INPUT.nextInt();
                            while (play < 0 || play >= playerTwo.hand.size()) {
                                System.out.printf("Incorrect Input, please try again\n");
                                play = INPUT.nextInt();
                            }
                            if (stack.getCount() == 31) {
                                stack.resetCount();
                            }
                            while (stack.getCount() + playerTwo.hand.get(play).getRank() > 31) {
                                System.out.printf("Your input makes a sum greater than 31, please try again\n");
                                play = INPUT.nextInt();
                            }
                            deck.transferCard(playerTwo.hand.get(play), playerTwo.hand, playerTwo.playedCards);
                            stack.addCard(playerTwo.playedCards.get(playerTwo.playedCards.size() - 1));
                            playerTwo.addPlayerScore(Board.evaluatePeggingStack(stack.getCards()));
                            winCon(playerTwo);
                            canPlay = false;
                            playerTwo.isTurn = false;
                            playerOne.isTurn = true;
                        }
                    }
                }
            }
        } 
        else {
            if (playerOne.isDealer) {
                playerTwo.isTurn = true;
            } 
            else if (playerTwo.isDealer) {
                playerThree.isTurn = true;
            } 
            else {
                playerOne.isTurn = true;
            }
            int goPoint = 0;
            while (!playerOne.hand.isEmpty() || !playerTwo.hand.isEmpty() || !playerThree.hand.isEmpty()) {
                boolean canPlay = false;
                if (playerOne.isTurn) {
                    if (playerOne.hand.isEmpty()) {
                        playerOne.isTurn = false;
                        playerTwo.isTurn = true;
                    } 
                    else {
                            
                        for (int i = 0; i < playerOne.hand.size(); i++) {
                            if (stack.getCount() + playerOne.hand.get(i).getRank() <= 31) {
                                canPlay = true;
                            }
                        }
                        if (!canPlay) {
                            if (goPoint >= 2) {
                                playerOne.addPlayerScore(1);
                                System.out.printf("Player %d scored a GO: +1 point\n", playerOne.playerNum);
                                goPoint = 0;
                                System.out.printf("No one can play any cards that make a sum under 31. Count is reset.\n");
                                playerOne.isTurn = false;
                                playerTwo.isTurn = true;
                                stack.resetCount();
                                continue;
                            }
                            System.out.printf("Player 1 cannot play any cards that make a sum under 31. Go\n");
                            goPoint += 1;
                            playerOne.isTurn = false;
                            playerTwo.isTurn = true;
                        } 
                        else {
                            if (goPoint >= 2) {
                                goPoint = 0;
                            }
                            System.out.printf("Please pick a card to play\n");
                            System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                            displayCard(playerOne.getHand());
                            play = INPUT.nextInt();
                            while (play < 0 || play >= playerOne.hand.size()) {
                                System.out.printf("Incorrect Input, please try again\n");
                                play = INPUT.nextInt();
                            }
                            if (stack.getCount() == 31) {
                                stack.resetCount();
                            }
                            while (stack.getCount() + playerOne.hand.get(play).getRank() > 31) {
                                System.out.printf("Your input makes a sum greater than 31, please try again\n");
                                play = INPUT.nextInt();
                            }
                            deck.transferCard(playerOne.hand.get(play), playerOne.hand, playerOne.playedCards);
                            stack.addCard(playerOne.playedCards.get(playerOne.playedCards.size() - 1));
                            playerOne.addPlayerScore(Board.evaluatePeggingStack(stack.getCards()));
                            winCon(playerOne);
                            canPlay = false;
                            playerOne.isTurn = false;
                            playerTwo.isTurn = true;
                        }
                    }
                } 
                else if (playerTwo.isTurn) {
                    if (playerTwo.hand.isEmpty()) {
                        playerTwo.isTurn = false;
                        playerThree.isTurn = true;
                    } 
                    else {
                        for (int i = 0; i < playerTwo.hand.size(); i++) {
                            if (stack.getCount() + playerTwo.hand.get(i).getRank() <= 31) {
                                canPlay = true;
                            }
                        }
                        if (!canPlay) {
                            if (goPoint >= 2) {
                                playerTwo.addPlayerScore(1);
                                System.out.printf("Player %d scored a GO: +1 point\n", playerTwo.playerNum);
                                winCon(playerTwo);
                                goPoint = 0;
                                System.out.printf("No one can play any cards that make a sum under 31. Count is reset.\n");
                                playerTwo.isTurn = false;
                                playerThree.isTurn = true;
                                stack.resetCount();
                                continue;
                            }
                            System.out.printf("Player 2 cannot play any cards that make a sum under 31. Go\n");
                            goPoint += 1;
                            playerTwo.isTurn = false;
                            playerThree.isTurn = true;
                        } 
                        else {
                            if (goPoint >= 2) {
                                goPoint = 0;
                            }
                            System.out.printf("Please pick a card to play\n");
                            System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                            displayCard(playerTwo.getHand());
                            play = INPUT.nextInt();
                            while (play < 0 || play >= playerTwo.hand.size()) {
                                System.out.printf("Incorrect Input, please try again\n");
                                play = INPUT.nextInt();
                            }
                            if (stack.getCount() == 31) {
                                stack.resetCount();
                            }
                            while (stack.getCount() + playerTwo.hand.get(play).getRank() > 31) {
                                System.out.printf("Your input makes a sum greater than 31, please try again\n");
                                play = INPUT.nextInt();
                            }
                            deck.transferCard(playerTwo.hand.get(play), playerTwo.hand, playerTwo.playedCards);
                            stack.addCard(playerTwo.playedCards.get(playerTwo.playedCards.size() - 1));
                            playerTwo.addPlayerScore(Board.evaluatePeggingStack(stack.getCards()));
                            winCon(playerTwo);
                            canPlay = false;
                            playerTwo.isTurn = false;
                            playerThree.isTurn = true;
                        }
                    }
                } 
                else {
                    if (playerThree.hand.isEmpty()) {
                        playerThree.isTurn = false;
                        playerOne.isTurn = true;
                    } 
                    else {
                        for (int i = 0; i < playerThree.hand.size(); i++) {
                            if (stack.getCount() + playerThree.hand.get(i).getRank() <= 31) {
                                canPlay = true;
                            }
                        }
                        if (!canPlay) {
                            if (goPoint >= 2) {
                                playerThree.addPlayerScore(1);
                                System.out.printf("Player %d scored a GO: +1 point\n", playerThree.playerNum);
                                winCon(playerThree);
                                goPoint = 0;
                                System.out.printf("No one can play any cards that make a sum under 31. Count is reset.\n");
                                playerThree.isTurn = false;
                                playerOne.isTurn = true;
                                stack.resetCount();
                                continue;
                            }
                            System.out.printf("Player 3 cannot play any cards that make a sum under 31. Go\n");
                            goPoint += 1;
                            playerThree.isTurn = false;
                            playerOne.isTurn = true;
                        } 
                        else {
                            if (goPoint >= 2) {
                                goPoint = 0;
                            }
                            System.out.printf("Please pick a card to play\n");
                            System.out.printf("Player 3: Score: %d\n", playerThree.getPlayerScore());
                            displayCard(playerThree.getHand());
                            play = INPUT.nextInt();
                            while (play < 0 || play >= playerThree.hand.size()) {
                                System.out.printf("Incorrect Input, please try again\n");
                                play = INPUT.nextInt();
                            }
                            if (stack.getCount() == 31) {
                                stack.resetCount();
                            }
                            while (stack.getCount() + playerThree.hand.get(play).getRank() > 31) {
                                System.out.printf("Your input makes a sum greater than 31, please try again\n");
                                play = INPUT.nextInt();
                            }
                            deck.transferCard(playerThree.hand.get(play), playerThree.hand, playerThree.playedCards);
                            stack.addCard(playerThree.playedCards.get(playerThree.playedCards.size() - 1));
                            playerThree.addPlayerScore(Board.evaluatePeggingStack(stack.getCards()));
                            winCon(playerThree);
                            canPlay = false;
                            playerThree.isTurn = false;
                            playerOne.isTurn = true;
                        }
                    }
                }
            }
        }
    }
    public void showPhase(Card starter, Deck deck, ScoreEvaluator score, Crib crib, int numPlayers, Player playerOne, Player playerTwo, Player playerThree) {
        if (numPlayers == 2) {
            System.out.printf("Showing cards + scoring hands\n");
            if (playerOne.isDealer) {
                playerTwo.isTurn = true;
            } 
            else {
                playerOne.isTurn = true;
            }
            while (!playerOne.playedCards.isEmpty() || !playerTwo.playedCards.isEmpty()) {
                if (playerOne.isTurn == true) {
                    
                    for (int i = 0; i < playerOne.playedCards.size(); i++) {
                        playerOne.addToHand(playerOne.playedCards.get(0));
                    }
                    playerOne.playedCards.add(starter);
                    System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                    displayCard(playerOne.playedCards);
                    playerOne.addPlayerScore(score.evaluateHand(playerOne.getHand(), starter));
                    System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                    winCon(playerOne);
                    if (playerOne.isDealer) {
                        crib.addToTemp(starter);
                        System.out.printf("Player 1 is the dealer, showing + scoring crib\n");
                        System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                        displayCard(crib.getTemp());
                        playerOne.addPlayerScore(score.evaluateHand(crib.getCrib(), starter));
                        System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                        winCon(playerOne);
                    }
                    playerOne.isTurn = false;
                    playerTwo.isTurn = true;
                    playerOne.playedCards.clear();
                }
                else {
                    for (int i = 0; i < playerTwo.playedCards.size(); i++) {
                        playerTwo.addToHand(playerTwo.playedCards.get(0));
                    }
                    playerTwo.playedCards.add(starter);
                    System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                    displayCard(playerTwo.playedCards);
                    playerTwo.addPlayerScore(score.evaluateHand(playerTwo.getHand(), starter));
                    System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                    winCon(playerTwo);
                    if (playerOne.isDealer) {
                        crib.addToTemp(starter);
                        System.out.printf("Player 2 is the dealer, showing + scoring crib\n");
                        System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                        displayCard(crib.getTemp());
                        playerTwo.addPlayerScore(score.evaluateHand(crib.getCrib(), starter));
                        System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                        winCon(playerTwo);
                    }
                    playerTwo.isTurn = false;
                    playerOne.isTurn = true;
                    playerTwo.playedCards.clear();
                }
            }
        }
        else if (numPlayers == 3) {
            System.out.printf("Showing cards + scoring hands\n");
            if (playerOne.isDealer) {
                playerTwo.isTurn = true;
            } 
            else if (playerTwo.isDealer) {
                playerThree.isTurn = true;
            }
            else {
                playerOne.isTurn = true;
            }
            while (!playerOne.playedCards.isEmpty() || !playerTwo.playedCards.isEmpty() || !playerThree.playedCards.isEmpty()) {
                if (playerOne.isTurn == true) {
                    for (int i = 0; i < playerOne.playedCards.size(); i++) {
                        playerOne.addToHand(playerOne.playedCards.get(0));
                    }
                    playerOne.playedCards.add(starter);
                    System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                    displayCard(playerOne.playedCards);
                    playerOne.addPlayerScore(score.evaluateHand(playerOne.getHand(), starter));
                    System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                    winCon(playerOne);
                    if (playerOne.isDealer) {
                        crib.addToTemp(starter);
                        System.out.printf("Player 1 is the dealer, showing + scoring crib\n");
                        System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                        displayCard(crib.getTemp());
                        playerOne.addPlayerScore(score.evaluateHand(crib.getCrib(), starter));
                        System.out.printf("Player 1: Score: %d\n", playerOne.getPlayerScore());
                        winCon(playerOne);
                    }
                    playerOne.isTurn = false;
                    playerTwo.isTurn = true;
                    playerOne.playedCards.clear();
                }
                else if (playerTwo.isTurn == true){
                    for (int i = 0; i < playerTwo.playedCards.size(); i++) {
                        playerTwo.addToHand(playerTwo.playedCards.get(0));
                    }
                    playerTwo.playedCards.add(starter);
                    System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                    displayCard(playerTwo.playedCards);
                    playerTwo.addPlayerScore(score.evaluateHand(playerTwo.getHand(), starter));
                    System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                    winCon(playerTwo);
                    if (playerTwo.isDealer) {
                        crib.addToTemp(starter);
                        System.out.printf("Player 2 is the dealer, showing + scoring crib\n");
                        System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                        displayCard(crib.getTemp());
                        playerTwo.addPlayerScore(score.evaluateHand(crib.getCrib(), starter));
                        System.out.printf("Player 2: Score: %d\n", playerTwo.getPlayerScore());
                        winCon(playerTwo);
                    }
                    playerTwo.isTurn = false;
                    playerThree.isTurn = true;
                    playerTwo.playedCards.clear();
                }
                else {
                    for (int i = 0; i < playerThree.playedCards.size(); i++) {
                        playerTwo.addToHand(playerThree.playedCards.get(0));
                    }
                    playerThree.playedCards.add(starter);
                    System.out.printf("Player 3: Score: %d\n", playerThree.getPlayerScore());
                    displayCard(playerThree.playedCards);
                    playerThree.addPlayerScore(score.evaluateHand(playerThree.getHand(), starter));
                    System.out.printf("Player 3: Score: %d\n", playerThree.getPlayerScore());
                    winCon(playerThree);
                    if (playerThree.isDealer) {
                        crib.addToTemp(starter);
                        System.out.printf("Player 3 is the dealer, showing + scoring crib\n");
                        System.out.printf("Player 3: Score: %d\n", playerThree.getPlayerScore());
                        displayCard(crib.getTemp());
                        playerThree.addPlayerScore(score.evaluateHand(crib.getCrib(), starter));
                        System.out.printf("Player 3: Score: %d\n", playerThree.getPlayerScore());
                        winCon(playerThree);
                    }
                    playerThree.isTurn = false;
                    playerTwo.isTurn = true;
                    playerThree.playedCards.clear();
                }
            }
        }
    }
    public void displayCard(ArrayList<Card> hand) {
        for(int i = 0; i < hand.size(); i++) {
            System.out.printf("[%d]: Rank: %d\tSuit: %s%n", i, hand.get(i).getRank(), hand.get(i).getSuit());
        }
    }
    public void winCon(Player playerCurrent) {
        if (playerCurrent.getPlayerScore() >= 121) {
            System.out.printf("Player %d wins! Terminating game\n", playerCurrent.playerNum);
            System.exit(0);
        }
    }
}