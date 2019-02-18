import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.StreamSupport;

interface ValueContainer extends Iterable<Integer> {
}

class SingleValue implements ValueContainer {
    public int value;

    // please leave this constructor as-is
    public SingleValue(int value) {
        this.value = value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(value).iterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {
}

class MyList extends ArrayList<ValueContainer> {
    int mySum = 0;
    // please leave this constructor as-is
    public MyList(Collection<? extends ValueContainer> c) {
        super(c);
    }

    public int sum() {
        return stream().flatMap(vc -> StreamSupport.stream(vc.spliterator(), false)).mapToInt(i -> i).sum();        
    }
}

class DemoComposite {
    public static void main(String[] args) {
        SingleValue v1 = new SingleValue(10);
        SingleValue v2 = new SingleValue(30);
        ManyValues v3 = new ManyValues();
        v3.add(300);
        v3.add(400);

        MyList ml = new MyList(Arrays.asList(v1, v2, v3));
        System.out.println("" + ml.sum());
    }

    public static void test(int i) {

    }
}
