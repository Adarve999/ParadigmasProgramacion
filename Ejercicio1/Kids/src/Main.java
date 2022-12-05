
public class Main {
    public static void main(String[] args) throws Exception {
        int limit = 53;
        Kingergarden kingerGarden = new Kingergarden(10, 4, 8, 10);
        for (int i = 1; i < limit; i++) {// creacion en bucle de los niños
            String name = "Kid" + i;
            new Kids(name, kingerGarden).start();
            Thread.sleep(200);
        }
    }

}
