import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Los cuatro recorridos de un arbol binario.
 * Cada metodo devuelve una lista con la secuencia de visita, lo que permite
 * imprimirla y tambien verificarla en las pruebas.
 *
 * DFS (profundidad, recursivos): preorden, inorden, postorden.
 * BFS (amplitud, con cola): porNiveles.
 */
public class Recorridos {

    private Recorridos() {
    }

    /** Preorden: raiz - izquierda - derecha. */
    public static <T> List<T> preorden(NodoArbol<T> raiz) {
        List<T> salida = new ArrayList<>();
        preorden(raiz, salida);
        return salida;
    }

    private static <T> void preorden(NodoArbol<T> n, List<T> salida) {
        if (n == null) {
            return;
        }
        salida.add(n.getDato());
        preorden(n.getIzquierdo(), salida);
        preorden(n.getDerecho(), salida);
    }

    /** Inorden: izquierda - raiz - derecha. En un BST produce las claves ordenadas. */
    public static <T> List<T> inorden(NodoArbol<T> raiz) {
        List<T> salida = new ArrayList<>();
        inorden(raiz, salida);
        return salida;
    }

    private static <T> void inorden(NodoArbol<T> n, List<T> salida) {
        if (n == null) {
            return;
        }
        inorden(n.getIzquierdo(), salida);
        salida.add(n.getDato());
        inorden(n.getDerecho(), salida);
    }

    /** Postorden: izquierda - derecha - raiz. */
    public static <T> List<T> postorden(NodoArbol<T> raiz) {
        List<T> salida = new ArrayList<>();
        postorden(raiz, salida);
        return salida;
    }

    private static <T> void postorden(NodoArbol<T> n, List<T> salida) {
        if (n == null) {
            return;
        }
        postorden(n.getIzquierdo(), salida);
        postorden(n.getDerecho(), salida);
        salida.add(n.getDato());
    }

    /** Por niveles (BFS): usa una cola y visita por profundidad creciente. */
    public static <T> List<T> porNiveles(NodoArbol<T> raiz) {
        List<T> salida = new ArrayList<>();
        if (raiz == null) {
            return salida;
        }
        Queue<NodoArbol<T>> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            NodoArbol<T> actual = cola.poll();
            salida.add(actual.getDato());
            if (actual.getIzquierdo() != null) {
                cola.add(actual.getIzquierdo());
            }
            if (actual.getDerecho() != null) {
                cola.add(actual.getDerecho());
            }
        }
        return salida;
    }

    /** Convierte una lista en texto separado por espacios, ej: "20 30 35". */
    public static String formato(List<?> lista) {
        StringBuilder sb = new StringBuilder();
        for (Object o : lista) {
            sb.append(o).append(" ");
        }
        return sb.toString().trim();
    }
}
