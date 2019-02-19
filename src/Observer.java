import static org.junit.Assert.assertEquals;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class Event<T> {
    private int count = 0;

    private Map<Integer, Consumer<T>> handlers = new HashMap<>();

    public Subscription addHandler(Consumer<T> handler) {
        int i = count;
        handlers.put(count++, handler);
        return new Subscription(this, i);
    }

    public void fire(T args) {
        for (Consumer<T> handler : handlers.values())
            handler.accept(args);
    }

    public class Subscription implements AutoCloseable {
        private Event<T> event;

        private int id;

        public Subscription(Event<T> event, int id) {
            this.event = event;
            this.id = id;
        }

        @Override
        public void close() /* throws Exception */
        {
            event.handlers.remove(id);
        }

        public void fire(T args) {
            Event.this.fire(args);
        }
    }
}

class Game {
    Event<Rat> eventEnter = new Event<>();

    Event<Rat> eventLeave = new Event<>();

    Event<Rat> eventPing = new Event<>();
}

class Rat implements Closeable {
    public int attack = 1;

    private Event<Rat>.Subscription addHandler;

    private Event<Rat>.Subscription leaveHandler;

    private Event<Rat>.Subscription pingHandler;

    public Rat(Game game) {
        addHandler = game.eventEnter.addHandler(o -> {
            if (o != this) {
                ++attack;
            }
        });

        leaveHandler = game.eventLeave.addHandler(o -> {
            if (o != this) {
                --attack;
            }
        });


        pingHandler = game.eventPing.addHandler(o -> {
            if (o != this) {
                o.attack++;
            }
        });

        pingHandler.fire(this);
        addHandler.fire(this);
    }

    @Override
    public void close()
        throws IOException {
        leaveHandler.fire(this);

        addHandler.close();
        leaveHandler.close();
    }
}

class DemoObserver {
    public static void main(String[] args)
        throws IOException {
        Game game = new Game();

        try (Rat rat1 = new Rat(game)) {
            assertEquals(1, rat1.attack);

            try (Rat rat2 = new Rat(game)) {
                assertEquals(2, rat1.attack);
                assertEquals(2, rat2.attack);

                try (Rat rat3 = new Rat(game)) {
                    assertEquals(3, rat1.attack);
                    assertEquals(3, rat2.attack);
                    assertEquals(3, rat3.attack);
                }

                assertEquals(2, rat1.attack);
                assertEquals(2, rat2.attack);
            }

            assertEquals(1, rat1.attack);
        }

        System.out.println("success");
    }
}