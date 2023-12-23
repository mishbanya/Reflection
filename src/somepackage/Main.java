package somepackage;

public class Main {
    public static void main(String[] args) {
        try {
            Injector injector = new Injector();
            SomeBean someBean1 = injector.inject(new SomeBean());
            System.out.println("Тест 1:");
            someBean1.foo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}