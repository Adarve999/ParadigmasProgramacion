public class Kids extends Thread {
    KingerGarden kingerGarden;
    String name;

    public Kids(String name, KingerGarden kingerGarden) {
        this.name = name;
        this.kingerGarden = kingerGarden;
    }

    public void run() {
        int selector = 0;
        long tiempoJugando = 0;
        long tiempoEnComedero = 0;
        long tiempoEnBebedero = 0;
        long tiempoPaseo = 0;
        long tiempoEnCama = 0;

        while (true) {
            try {
                tiempoJugando = (long) 3000 + (int) ((7000 - 3000) * Math.random());
                tiempoEnComedero = (long) 2000 + (int) ((6000 - 2000) * Math.random());
                tiempoEnBebedero = (long) 1000 + (int) ((4000 - 1000) * Math.random());
                tiempoPaseo = (long) 5000 + (int) ((9000 - 5000) * Math.random());
                tiempoEnCama = (long) 15000 + (int) ((19000 - 15000) * Math.random());

                if (selector == 0) {
                    kingerGarden.cogerJuguete(name);// tiempo de Jugar
                    sleep(tiempoJugando);
                    kingerGarden.dejarJuguete(name);
                }

                selector = 0 + (int) ((5 - 0) * Math.random());

                if (selector == 1) {
                    kingerGarden.cogerComida(name);// tiempo de Comida
                    sleep(tiempoEnComedero);
                    kingerGarden.dejarComida(name);
                }
                if (selector == 2) {
                    kingerGarden.cogerAgua(name);// tiempo de Bebedero
                    sleep(tiempoEnBebedero);
                    kingerGarden.dejarAgua(name);
                }
                if (selector == 3) {
                    kingerGarden.cogerPaseo(name);// tiempo de Paseo
                    sleep(tiempoPaseo);
                    kingerGarden.dejarPaseo(name);
                }
                if (selector == 4) {
                    kingerGarden.cogerCama(name);// tiempo de Cama
                    sleep(tiempoEnCama);
                    kingerGarden.dejarCama(name);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
