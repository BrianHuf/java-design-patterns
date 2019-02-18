import java.util.ArrayList;
import java.util.List;

class Participant {
    static int currIndex = 0;
    
    int value = 0;

    Mediator mediator;

    int index = currIndex++;

    public Participant(Mediator mediator) {
        this.mediator = mediator.register(this);
    }

    public void say(int n) {
        mediator.publish(index, n);
    }
}

class Mediator {
    List<Participant> participants = new ArrayList<>();

    public void publish(int index, int n) {
        for (int i = 0; i < participants.size(); i++) {
            if (i != index) {
                participants.get(i).value += n;
            }
        }
        
        System.out.print(String.format("Participant %d broadcasts the value %d. We now have ", index+1, n));        
        for (int i = 0; i < participants.size(); i++) {
            System.out.print(String.format("Participant %d value = %d, ", i + 1, participants.get(i).value));
        }
        System.out.print(System.lineSeparator());
    }

    public Mediator register(Participant p) {
        participants.add(p);
        return this;
    }
}

class DemoMediator {
    public static void main(String[] args) {
        Mediator m = new Mediator();
        Participant p1 = new Participant(m);
        Participant p2 = new Participant(m);
        
        p1.say(3);
        p2.say(2);
    }
}