import static org.junit.Assert.assertEquals;

// A factory method creates other objects
// In this case the factory automates the setting of auto-id int

class Person0 {
    public int id;

    public String name;

    public Person0(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class PersonFactory {
    int currId = 0;

    public Person0 createPerson(String name) {
        return new Person0(currId++, name);
    }
}

class DemoFactory {
    public static void main(String[] args) {
        PersonFactory factory = new PersonFactory();
        assertEquals(0, factory.createPerson("bob").id);
        assertEquals(1, factory.createPerson("john").id);
        assertEquals(2, factory.createPerson("tim").id);
    }
}