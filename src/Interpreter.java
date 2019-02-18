import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

interface Element {
    int evaluate();
}

class BadVariableException extends RuntimeException {
    
}

class IntegerElement implements Element {
    private int value;

    IntegerElement(LexToken t) {
        this.value = Integer.parseInt(t.text);
    }

    public int evaluate() {
        return value;
    }
}

abstract class BinaryOperationElement implements Element {
    Element lhs;

    Element rhs;

    BinaryOperationElement(Element lhs) {
        this.lhs = lhs;
    }
}

class AdditionElement extends BinaryOperationElement {
    AdditionElement(Element lhs) {
        super(lhs);
    }

    public int evaluate() {
        return lhs.evaluate() + rhs.evaluate();
    }
}

class SubtractionElement extends BinaryOperationElement {
    SubtractionElement(Element lhs) {
        super(lhs);
    }

    public int evaluate() {
        return lhs.evaluate() - rhs.evaluate();
    }
}

class VariableElement implements Element {
    private String name;

    private ExpressionProcessor p;

    VariableElement(LexToken t, ExpressionProcessor p) {
        this.name = t.text;
        this.p = p;
    }

    public int evaluate() {
        if (name.length() > 1) {
            throw new BadVariableException();
        }

        return p.variables.get(name.charAt(0));
    }
}

class LexToken {
    enum Type {
        NUMBER, PLUS, MINUS, VARIABLE, DONE
    }

    Type type;

    String text;

    LexToken(Type type, char c) {
        this.type = type;
        this.text = Character.toString(c);
    }

    void append(char c) {
        text = text + c;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", type, text);
    }
}

class Lexer {
    LexToken current = null;

    Stream<LexToken> tokenize(Character c) {
        if (c == '+') {
            return addToken(LexToken.Type.PLUS, c);
        }

        if (c == '-') {
            return addToken(LexToken.Type.MINUS, c);
        }

        if (Character.isLetter(c)) {
            return addToken(LexToken.Type.VARIABLE, c);
        }

        if (Character.isDigit(c)) {
            return addToken(LexToken.Type.NUMBER, c);
        }

        if (c == '\n') {
            return Stream.of(current, new LexToken(LexToken.Type.DONE, c));
        }

        throw new RuntimeException("Illegal expression character " + c);
    }

    Stream<LexToken> addToken(LexToken.Type type, char c) {
        if (current != null && current.type == type) {
            current.append(c);
            return Stream.empty();
        }

        LexToken lastToken = current;
        current = new LexToken(type, c);
        return lastToken == null ? Stream.empty() : Stream.of(lastToken);
    }

    Optional<LexToken> flush() {
        return Optional.empty();
    }
}

class Parser {
    Element current = null;

    private ExpressionProcessor p;

    Parser(ExpressionProcessor p) {
        this.p = p;
    }

    void parse(LexToken token) {
        switch (token.type) {
            case NUMBER:
                Element t = new IntegerElement(token);
                if (current == null) {
                    current = t;
                } else if (current instanceof BinaryOperationElement) {
                    ((BinaryOperationElement) current).rhs = t;
                } else {
                    throw new RuntimeException();
                }
                break;
            case VARIABLE:
                Element vt = new VariableElement(token, p);
                if (current == null) {
                    current = vt;
                } else if (current instanceof BinaryOperationElement) {
                    ((BinaryOperationElement) current).rhs = vt;
                } else {
                    throw new RuntimeException();
                }
                break;
            case PLUS:
                current = new AdditionElement(current);
                break;
            case MINUS:
                current = new SubtractionElement(current);
                break;
            case DONE:
                break;
            default:
                throw new RuntimeException();
        }
    }
}

class ExpressionProcessor {
    public Map<Character, Integer> variables = new HashMap<>();

    public int calculate(String expression) {
        Lexer lexer = new Lexer();
        Parser parser = new Parser(this);

        int ret;
        try {
            ret = evaluate(expression + "\n", lexer, parser);
        } catch(BadVariableException e) {
            ret = 0;
        }
                
        System.out.println("Evaluate " + expression + " = " + ret);
        return ret;
    }

    private int evaluate(String expression, Lexer lexer, Parser parser) {
        // @formatter:off
        expression
            .chars()
            .mapToObj(c -> (char) c)
            .flatMap(lexer::tokenize)
            .forEach(t -> parser.parse(t));
        // @formatter:on 
        
        return parser.current.evaluate();
    }
}

class DemoInterpreter {
    public static void main(String[] args) {
        ExpressionProcessor p = new ExpressionProcessor();
        p.variables.put('x', 3);

        p.calculate("1+2+3");
        p.calculate("1+2+xy");
        p.calculate("10-2-x");
    }
}