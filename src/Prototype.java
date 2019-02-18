import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

// Is the prototype of (deep) copying an object
// This can be done with hand coding or using serialization

class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point deepCopy() {
        return new Point(x, y);
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line deepCopy() {
        return new Line(start.deepCopy(), end.deepCopy());
    }
}

class DemoPrototype {
    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Line l1 = new Line(p1, p2);

        Line l2 = l1.deepCopy();

        assertNotEquals(l1, l2);
        assertNotEquals(l1.start, l2.start);
        assertEquals(l1.start.x, l2.start.x);
        assertEquals(l1.start.y, l2.start.y);
    }
}