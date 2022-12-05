import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String Fondo_RED = "\033[41m";
    public static final String Fondo_GREEN = "\033[42m";

    public static void main(String[] args) {
        Socket cliente;
        DataInputStream entrada;
        DataOutputStream salida;
        Scanner consola = new Scanner(System.in);
        String mensaje;
        try {
            cliente = new Socket(InetAddress.getLocalHost(), 5000);
            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());
            boolean reservado = false;

            while (reservado != true) {
                System.out.println("Introduce que asiento quieres reservar. (Fila),(Columna).");
                mensaje = consola.nextLine();
                salida.writeUTF(mensaje);
                mensaje = entrada.readUTF();
                if ("Reservado.".equals(mensaje)) {
                    System.out.println(ANSI_RESET + Fondo_GREEN + "Su Reserva se hizo correctamente." + ANSI_RESET);
                    reservado = true;
                } else if ("Completo.".equals(mensaje)) {
                    System.out.println(ANSI_RESET + Fondo_RED + "Esta completo todo, lo sentimos.");
                    reservado = true;
                } else if ("Ocupado.".equals(mensaje)) {
                    System.out.println(
                            ANSI_RESET + Fondo_RED + "Esta ocupado el sitio. Vuelva a intentarlo." + ANSI_RESET);
                } else if ("Error.".equals(mensaje)) {
                    System.out.println(ANSI_RESET + Fondo_RED + "No existe ese sitio en nuestro avion." + ANSI_RESET);
                }
            }
            entrada.close();
            salida.close();
            cliente.close();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
