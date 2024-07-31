package homework.Wordle;

import java.util.*;

public class Wordle {
    static final int ALPHABET_SIZE = 26; // The size of the alphabet
    static final int WORD_LENGTH = 5; // The length of words
    static final int TOTAL_CHANCES = 6; // The chances in total

    // Guess `word` at state `s`
    public static State guess(State s) {
        // TODO begin

        for (int i = 0; i < s.wordState.length; i++) {
            s.wordState[i] = Color.GRAY;
        }

        Map<Character, Integer> map = new HashMap<>(); // record the times of each character in the answer

        for (int i = 0; i < s.answer.length(); i++) {
            char c = s.answer.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }

        for (int i = 0; i < s.answer.length(); i++) {
            if (s.answer.charAt(i) == s.word.charAt(i)) {
                s.wordState[i] = Color.GREEN;
                s.alphabetState[s.word.charAt(i) - 'A'] = Color.GREEN;

                map.put(s.word.charAt(i), map.get(s.word.charAt(i)) - 1);
            }
        }

        for (int i = 0; i < s.answer.length(); i++) {
            char c = s.word.charAt(i);
            if (s.wordState[i] != Color.GREEN && map.containsKey(c)) {
                if (map.get(c) > 0) {
                    s.wordState[i] = Color.YELLOW;
                    if (s.alphabetState[c - 'A'] == Color.GRAY || s.alphabetState[c - 'A'] == Color.RED) {
                        s.alphabetState[c - 'A'] = Color.YELLOW;
                    }
                    map.put(c, map.get(c) - 1);
                } else {
                    s.wordState[i] = Color.RED;
                    if (s.alphabetState[c - 'A'] == Color.GRAY) {
                        s.alphabetState[c - 'A'] = Color.RED;
                    }
                }
            } else if (s.wordState[i] == Color.GRAY) {
                s.wordState[i] = Color.RED;
                if (s.alphabetState[c - 'A'] == Color.GRAY) {
                    s.alphabetState[c - 'A'] = Color.RED;
                }
            }
        }

        s.chancesLeft--;
        if (s.word.equals(s.answer)) {
            s.status = GameStatus.WON;
        } else if (s.chancesLeft == 0) {
            s.status = GameStatus.LOST;
        }

        // TODO end
        return s;
    }

    public static void main(String[] args) {
        // Read word sets from files
        WordSet wordSet = new WordSet("assets/wordle/FINAL.txt", "assets/wordle/ACC.txt");

        Scanner input = new Scanner(System.in);
        // Keep asking for an answer if invalid
        String answer;
        do {
            System.out.print("Enter answer: ");
            answer = input.nextLine().toUpperCase().trim();
            if (wordSet.isNotFinalWord(answer)) {
                System.out.println("INVALID ANSWER");
            }
        } while (wordSet.isNotFinalWord(answer));

        State state = new State(answer);
        while (state.status == GameStatus.RUNNING) {
            // Keep asking for a word guess if invalid
            String word;
            do {
                System.out.print("Enter word guess: ");
                word = input.nextLine().toUpperCase().trim();
                if (wordSet.isNotAccWord(word)) {
                    System.out.println("INVALID WORD GUESS");
                }
            } while (wordSet.isNotAccWord(word));
            // Try to guess a word
            state.word = word;
            state = guess(state);
            state.show();
        }
        if (state.status == GameStatus.LOST) {
            System.out.println("You lost! The correct answer is " + state.answer + ".");
        } else {
            System.out.println("You won! You only used " + (TOTAL_CHANCES - state.chancesLeft) + " chances.");
        }
    }
}
