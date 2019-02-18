import java.util.ArrayList;
import java.util.List;

class Token {
    public int value = 0;

    public Token(int value) {
        this.value = value;
    }
    
    public String toString() {
        return ""+value;
    }
}

class Memento {

    public List<Token> tokens = new ArrayList<>();

    public Memento(List<Token> tokens) {
        tokens.stream().map(t -> new Token(t.value)).forEach(this.tokens::add);
    }
}

class TokenMachine {
    public List<Token> tokens = new ArrayList<>();

    public Memento addToken(int value) {
        return addToken(new Token(value));
    }

    public Memento addToken(Token token) {
        tokens.add(token);
        return new Memento(tokens);
    }

    public void revert(Memento m) {
        tokens = m.tokens;
    }
    
    public String toString() {
        return tokens.toString() + System.lineSeparator();
    }
}

class DemoMemento {
    public static void main(String[] args) {
        TokenMachine tm = new TokenMachine();
        tm.addToken(10);
        
        Token t1 = new Token(20);
        tm.addToken(t1);
        Memento m1 = tm.addToken(t1);
        System.out.print("State 1 " + tm.toString());
        
        t1.value = 50;
        System.out.print("State 2 " + tm.toString());
        
        tm.revert(m1);
        System.out.print("State 1? " + tm.toString());
        
    }
}