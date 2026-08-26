import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * P = (C + H) * A   donde C, H, A son aleatorios de 1 a 100.
 *
 * Cada hilo:
 *   - crea su propio archivo
 *   - escribe m mensajes  (m aleatorio entre 1 y M)
 *   - duerme t milisegundos entre mensaje y mensaje (t aleatorio entre 10 y T)
 */
public class Formula implements Runnable {

    private final int id;
    private final int maxMensajes;   // M
    private final int maxDormir;     // T
    public double P = 0;

    private File archivo;
    private BufferedWriter escritor;

    public Formula(int id, int maxMensajes, int maxDormir) {
        this.id = id;
        this.maxMensajes = maxMensajes;
        this.maxDormir = maxDormir;
    }

    /** Aleatorio entre min y max, ambos incluidos. */
    private static int rand(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private int calculateP(int c, int h, int a) {
        return (c + h) * a;
    }

    /** Crea el archivo del hilo y abre el escritor. */
    private void createFile() throws IOException {
        this.archivo = new File("formula_" + id + "_" + rand(1, 10000) + ".txt");
        this.archivo.createNewFile();
        this.escritor = new BufferedWriter(new FileWriter(archivo));
    }

    /** Escribe una línea en el archivo del hilo. */
    private void write(String mensaje) throws IOException {
        escritor.write(mensaje);
        escritor.newLine();
        escritor.flush();   // para poder ver el archivo mientras corre
    }

    @Override
    public void run() {
        try {
            createFile();

            int m = rand(1, maxMensajes);
            write("=== Hilo " + id + " | " + m + " mensajes ===");

            for (int i = 1; i <= m; i++) {
                int c = rand(1, 100);
                int h = rand(1, 100);
                int a = rand(1, 100);
                int p = calculateP(c, h, a);
                this.P += p;

                write(String.format("[%d/%d] P = (%d + %d) * %d = %d", i, m, c, h, a, p));

                int t = rand(10, maxDormir);
                Thread.sleep(t);
            }

            double averageP = this.P/m;
            String apreciacion; 

            System.out.println("Hilo " + id + " terminó -> " + archivo.getName());
            System.out.println("Promedio de P: " + averageP);

            if(averageP < 5000){
                apreciacion = "mala";
            } else if(averageP > 15000){
                apreciacion = "buena";
            } else{
                apreciacion = "promedio";
            }

            System.out.println("Apreciación: " + apreciacion + ".");

        } catch (IOException e) {
            System.err.println("Hilo " + id + " error de E/S: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();   // buena práctica: restaurar la bandera
            System.err.println("Hilo " + id + " interrumpido");
        } finally {
            cerrar();
        }
    }

    private void cerrar() {
        if (escritor != null) {
            try {
                escritor.close();
            } catch (IOException ignored) {
                // nada que hacer al cerrar
            }
        }
    }

    // ---------------------------------------------------------------

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        int n = pedir(sc, "¿Cuántos threads? ", 1);
        int m = pedir(sc, "¿Cuántos mensajes máximo? ", 1);
        int t = pedir(sc, "¿Cuánto tiempo dormir máximo (ms)? ", 10);

        List<Thread> hilos = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Thread hilo = new Thread(new Formula(i, m, t), "formula-" + i);
            hilos.add(hilo);
            hilo.start();
        }

        Watcher w = new Watcher(hilos);
        w.start();

        for (Thread hilo : hilos) {
            hilo.join();   // esperar a que todos terminen
        }

        System.out.println("Listo: " + n + " archivos generados.");
        sc.close();
    }

    /** Lee un entero válido y mayor o igual al mínimo. */
    private static int pedir(Scanner sc, String texto, int minimo) {
        while (true) {
            System.out.print(texto);
            if (sc.hasNextInt()) {
                int valor = sc.nextInt();
                if (valor >= minimo) {
                    return valor;
                }
                System.out.println("Tiene que ser >= " + minimo);
            } else {
                System.out.println("Eso no es un número.");
                sc.next();
            }
        }
    }
}