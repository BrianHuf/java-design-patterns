import java.util.List;
import java.util.ArrayList;

// A builder is a separate component for building (constructing) complex objects
// Builders often employ the fluent style

class CodeBuilder {
    private final String className;

    private final List<CodeField> fields = new ArrayList<>();

    CodeBuilder(String className) {
        this.className = className;
    }

    CodeBuilder addField(String name, String type) {
        fields.add(new CodeField(name, type));
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("public class %s%n{%n", className));
        fields.stream().forEach(sb::append);
        sb.append("}");
        return sb.toString();
    }
}

class CodeField {
    private final String name, type;

    CodeField(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String toString() {
        return String.format("  public %s %s;%n", type, name);
    }
}

class DemoBuilder {
    public static void main(String args[]) {
        CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
        System.out.println(cb);
    }
}