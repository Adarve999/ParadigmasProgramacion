import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String Fondo_BLUE = "\033[44m";
    public static final String Fondo_YELLOW = "\033[47m";
    public static final String ANSI_WHITE = "\033[37m";
    public static boolean matriz[][] = new boolean[10][4];

    private static void mostrarReservas() {
        System.out.println(ANSI_RESET + Fondo_BLUE + "---Reservas de Asientos---" + ANSI_RESET);
        for (int i = 0; i < 10; i++) {
            for (int y = 0; y < 4; y++) {
                if (matriz[i][y] == false) {
                    System.out.print(ANSI_RESET + ANSI_RED + matriz[i][y] + ANSI_RESET + "|");
                } else {
                    System.out.print(ANSI_RESET + ANSI_GREEN + matriz[i][y] + ANSI_RESET + "|");
                }
            }
            System.out.println();
        }
    }

    private static boolean AsientosLibres() {
        int contador = 0;
        boolean completo = false;
        for (int i = 0; i < 10; i++) {
            for (int y = 0; y < 4; y++) {
                if (matriz[i][y] == true) {
                    contador++;
                }
            }
        }
        if (contador == matriz.length) {
            completo = true;
        }
        return completo;
    }

    private static boolean confirmarReserva(int x, int y) {
        boolean reservado = false;
        if (matriz[x - 1][y - 1] == false) {
            // seteamos a true porque esta vacio el asiento y decimos que si se puede
            // reservar
            reservado = true;
            matriz[x - 1][y - 1] = true;
        }
        return reservado;
    }

    public static void main(String[] args) {
        ServerSocket servidor;
        Socket conexion;
        DataInputStream entrada;
        DataOutputStream salida;
        String aux;
        int n = 0;
        try {
            servidor = new ServerSocket(5000);
            System.out.println("Servidor Arrancado....");
            while (true) {
                conexion = servidor.accept();
                Boolean reservado = false;
                entrada = new DataInputStream(conexion.getInputStream()); // Abrimos los canales de E/S
                salida = new DataOutputStream(conexion.getOutputStream());
                System.out.println(ANSI_RESET + Fondo_YELLOW + ANSI_WHITE + "Conexión nº " + n + " desde: "
                        + conexion.getInetAddress().getHostName() + ANSI_RESET);
                mostrarReservas();// mostrar la tabla de reservas
                while (reservado != true) {
                    String mensaje = entrada.readUTF();// Leemos el mensaje del cliente
                    String[] parts = mensaje.split(",");
                    if (parts.length <= 1) {// si introduce un solo numero, o algo que no tiene ese formato vuelve a
                                            // pedirselo al cliente
                        aux = "Error.";
                        salida.writeUTF(aux);
                    } else {
                        // Separamos el mensaje en 2 partes
                        int filas = Integer.parseInt(parts[0]);
                        int columnas = Integer.parseInt(parts[1]);

                        if (filas <= 10 && columnas <= 4) {
                            if (confirmarReserva(filas, columnas)) {
                                System.out.println("Fila: " + parts[0] + " Columna: " + parts[1]);
                                aux = "Reservado.";
                                salida.writeUTF(aux);
                                reservado = true;
                            } else {
                                aux = "Ocupado.";
                                salida.writeUTF(aux);
                            }
                            if (AsientosLibres()) {
                                aux = "Completo.";
                                salida.writeUTF(aux);
                            }
                        } else {
                            aux = "Error.";
                            salida.writeUTF(aux);
                        }
                    }

                }
                n++;
                mostrarReservas();
                entrada.close();
                salida.close();
                conexion.close();
            }
        } catch (IOException ex) {
        }

    }
}
