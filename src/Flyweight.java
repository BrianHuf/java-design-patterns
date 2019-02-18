import java.util.*;

class Sentence {
    List<WordToken> words = new ArrayList<>();

    public Sentence(String plainText) {
        for (String word : plainText.split(" ")) {
            words.add(new WordToken(word));
        }
    }

    public WordToken getWord(int index) {
        return words.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (WordToken word : words) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(word.capitalize ? word.value.toUpperCase() : word.value);
        }
        return sb.toString();
    }

    class WordToken {
        public String value;
        public boolean capitalize;

        WordToken(String value) {
            this.value = value;
        }
    }
}

class DemoFlyWeight {
    public static void main(String[] args) {
        Sentence sentence = new Sentence("hello world");
        sentence.getWord(1).capitalize = true;
        System.out.println(sentence);
    }
}