import static org.junit.Assert.assertEquals;

// Using aggregation, create a component that looks like (adapts to) another interface

class Square0 {
    public int side;

    public Square0(int side) {
        this.side = side;
    }
}

interface Rectangle {
    int getWidth();

    int getHeight();

    default int getArea() {
        return getWidth() * getHeight();
    }
}

class SquareToRectangleAdapter implements Rectangle {
    Square0 square;

    public SquareToRectangleAdapter(Square0 square) {
        this.square = square;
    }

    public int getWidth() {
        return square.side;
    }

    public int getHeight() {
        return square.side;
    }
}

class DemoAdapter {
    public static void main(String[] args) {
        Square0 s = new Square0(5);
        Rectangle r = new SquareToRectangleAdapter(s);
        assertEquals(5, r.getWidth());
        assertEquals(5, r.getHeight());
    }
}