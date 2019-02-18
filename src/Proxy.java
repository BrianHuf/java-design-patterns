class Person {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String drink() {
        return "drinking";
    }

    public String drive() {
        return "driving";
    }

    public String drinkAndDrive() {
        return "driving while drunk";
    }
}

class ResponsiblePerson {
    private Person person;
    
    public ResponsiblePerson(Person person) {
        this.person = person;
    }
    
    public int getAge() {
        return person.getAge();
    }

    public void setAge(int age) {
        person.setAge(age);
    }

    public String drink() {
        if (person.getAge() < 18) {
            return "too young";
        }
        return person.drink();
    }

    public String drive() {
        if (person.getAge() < 16) {
            return "too young";
        }
        return person.drive();
    }

    public String drinkAndDrive() {
        return "dead";
    }
}