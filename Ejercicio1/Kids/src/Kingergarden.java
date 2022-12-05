import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Kingergarden {
    private ArrayList<String> juguetes;
    private Semaphore exCLSemaphore = new Semaphore(1);
    private Semaphore vacio = new Semaphore(0);
    private Semaphore LlenoJuguetes;
    private Semaphore LlenoComida;
    private Semaphore LlenoAgua;
    private Semaphore LlenoCama;

    public Kingergarden(int juguetes, int comida, int agua, int cama) {
        this.juguetes = new ArrayList<String>(juguetes);
        this.LlenoJuguetes = new Semaphore(juguetes);
        this.LlenoComida = new Semaphore(comida);
        this.LlenoAgua = new Semaphore(agua);
        this.LlenoCama = new Semaphore(cama);
    }

    public void cogerJuguete(String name) throws InterruptedException {
        try {
            LlenoJuguetes.acquire();
            exCLSemaphore.acquire();
            juguetes.add(name);
            System.out.println(name + " coge un juguete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            vacio.release();
            exCLSemaphore.release();

        }
    }

    public void dejarJuguete(String name) {
        try {
            vacio.acquire();
            exCLSemaphore.acquire();
            System.out.println(name + " deja un juguete");
            juguetes.remove(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LlenoJuguetes.release();
            exCLSemaphore.release();
        }
    }

    public void cogerComida(String name) {
        try {
            LlenoComida.acquire();
            exCLSemaphore.acquire();
            System.out.println(name + " coge comida");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exCLSemaphore.release();
        }
    }

    public void dejarComida(String name) {
        try {
            exCLSemaphore.acquire();
            System.out.println(name + " deja comida");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exCLSemaphore.release();
            LlenoComida.release();
        }
    }

    public void cogerAgua(String name) {
        try {
            LlenoAgua.acquire();
            exCLSemaphore.acquire();
            System.out.println(name + " coge agua");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exCLSemaphore.release();
        }
    }

    public void dejarAgua(String name) {
        try {
            exCLSemaphore.acquire();
            System.out.println(name + " deja agua");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exCLSemaphore.release();
            LlenoAgua.release();
        }
    }

    public void cogerPaseo(String name) {
        System.out.println(name + " esta en el paseo");
    }

    public void dejarPaseo(String name) {
        System.out.println(name + " ha terminado el paseo");
    }

    public void cogerCama(String name) {
        try {
            LlenoCama.acquire();
            exCLSemaphore.acquire();
            System.out.println(name + " coge cama");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exCLSemaphore.release();
        }
    }

    public void dejarCama(String name) {
        try {
            exCLSemaphore.acquire();
            System.out.println(name + " deja cama");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            exCLSemaphore.release();
            LlenoCama.release();
        }
    }

}
