@SuppressWarnings("rawtypes")
interface BuildWithName<S extends BuildWithName> {
    S setName(String name);
}

interface BuildWithNameAndDesc extends BuildWithName<BuildWithNameAndDesc> {
    BuildWithNameAndDesc setDescription(String description);
}

interface BuildWithNameAndColor extends BuildWithName<BuildWithNameAndColor> {
    BuildWithNameAndColor setColor(String description);
}

@SuppressWarnings("rawtypes")
class BuildWithNameImpl<I extends BuildWithName, S extends I> implements BuildWithName<I> {
    @SuppressWarnings("unchecked")
    protected S self() {
        return (S) this;
    }

    @Override
    public S setName(String name) {
        System.out.println(String.format("setName(%s)", name));
        return self();
    }
}

class BuildWithNameAndDescImpl extends BuildWithNameImpl<BuildWithNameAndDesc, BuildWithNameAndDescImpl> implements BuildWithNameAndDesc {
    @Override
    public BuildWithNameAndDescImpl setDescription(String description) {
        System.out.println(String.format("setDescription(%s)", description));
        return self();
    }
}

class BuildWithNameAndColorImpl extends BuildWithNameImpl<BuildWithNameAndColor, BuildWithNameAndColorImpl> implements BuildWithNameAndColor {
    @Override
    public BuildWithNameAndColorImpl setColor(String color) {
        System.out.println(String.format("setColor(%s)", color));
        return self();
    }
}

class DemoFluentInheritance {
    public static void main(String args[]) {
        BuildWithNameAndDesc o1 = new BuildWithNameAndDescImpl();
        o1.setName("foo").setDescription("bar");

        BuildWithNameAndColor o2 = new BuildWithNameAndColorImpl();
        o2.setName("foo").setColor("bar");
    }
}