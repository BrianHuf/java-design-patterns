import static org.junit.Assert.assertEquals;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface State {
    State enterDigit(int i);

    String getStatus();
}

class LockedState implements State {
    private int[] combination;

    private int currentIndex = 0;

    LockedState(int[] combination) {
        this.combination = combination;
        this.currentIndex = 0;
    }

    public State enterDigit(int digit) {
        if (digit == combination[currentIndex]) {
            if (currentIndex == combination.length - 1) {
                return new UnlockedState();
            } else {
                currentIndex++;
                return this;
            }
        }
        return new ErrorState();
    }

    public String getStatus() {
        if (currentIndex == 0) {
            return "LOCKED";
        }

        return IntStream.range(0, currentIndex).mapToObj(i -> "" + combination[i]).collect(Collectors.joining());
    }
}

class UnlockedState implements State {
    public State enterDigit(int i) {
        return new ErrorState();
    }

    public String getStatus() {
        return "OPEN";
    }
}

class ErrorState implements State {
    public State enterDigit(int i) {
        return this;
    }

    public String getStatus() {
        return "ERROR";
    }
}

class CombinationLock {
    int[] combination;

    public String status;

    State state;

    public CombinationLock(int[] combination) {
        this.combination = combination;
        this.state = combination.length == 0 ? new UnlockedState() : new LockedState(combination);
        status = state.getStatus();
    }

    public void enterDigit(int digit) {
        state = state.enterDigit(digit);
        status = state.getStatus();
    }
}

class DemoState {
    public static void main(String[] args) {
        CombinationLock cl = new CombinationLock(new int[] { 1, 2, 3, 4 });
        assertEquals("LOCKED", cl.status);
        cl.enterDigit(1);
        assertEquals("1", cl.status);
        cl.enterDigit(2);
        assertEquals("12", cl.status);
        cl.enterDigit(3);
        assertEquals("123", cl.status);
        cl.enterDigit(4);
        assertEquals("OPEN", cl.status);
        cl.enterDigit(0);
        assertEquals("ERROR", cl.status);
    }
}