import static org.junit.Assert.assertEquals;

abstract class ExpressionVisitor {
    abstract void visit(Value value);

    abstract void visit(AdditionExpression ae);

    abstract void visit(MultiplicationExpression me);
}

abstract class Expression {
    abstract void accept(ExpressionVisitor ev);
}

class Value extends Expression {
    public int value;

    public Value(int value) {
        this.value = value;
    }

    @Override
    void accept(ExpressionVisitor ev) {
        ev.visit(this);
    }
}

class AdditionExpression extends Expression {
    public Expression lhs, rhs;

    public AdditionExpression(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    void accept(ExpressionVisitor ev) {
        ev.visit(this);
    }
}

class MultiplicationExpression extends Expression {
    public Expression lhs, rhs;

    public MultiplicationExpression(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    void accept(ExpressionVisitor ev) {
        ev.visit(this);
    }
}

class ExpressionPrinter extends ExpressionVisitor {
    private StringBuilder sb = new StringBuilder();

    @Override
    public String toString() {
        return sb.toString();
    }

    @Override
    void visit(Value value) {
        sb.append(value.value);
    }

    @Override
    void visit(AdditionExpression ae) {
        visitBinary(ae.lhs, "+", ae.rhs);
    }

    @Override
    void visit(MultiplicationExpression me) {
        visitBinary(me.lhs, "*", me.rhs);
    }

    private void visitBinary(Expression lhs, String operator, Expression rhs) {
        sb.append("(");
        lhs.accept(this);
        sb.append(operator);
        rhs.accept(this);
        sb.append(")");
    }
}

class DemoVisitor {
    public static void main(String[] args) {
        AdditionExpression simple = new AdditionExpression(new Value(2), new Value(3));
        ExpressionPrinter ep = new ExpressionPrinter();
        ep.visit(simple);
        assertEquals("(2+3)", ep.toString());
    }
}