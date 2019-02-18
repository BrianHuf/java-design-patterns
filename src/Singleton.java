import java.util.function.Supplier;

// A singleton is an object that is only created once
// Shown here is a tester for single.  func is the accessor to a singleton

class SingletonTester {
    public static boolean isSingleton(Supplier<Object> func) {
        return func.get() == func.get();
    }
}
