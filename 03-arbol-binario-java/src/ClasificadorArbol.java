import java.util.LinkedList;
import java.util.Queue;

/**
 * Metodos utilitarios para medir y clasificar arboles binarios:
 * lleno, completo, perfecto y degenerado.
 * Todos validan null para evitar NullPointerException.
 */
public class ClasificadorArbol {

    private ClasificadorArbol() {
    }

    public static <T> int contarNodos(NodoArbol<T> n) {
        if (n == null) {
            return 0;
        }
        return 1 + contarNodos(n.getIzquierdo()) + contarNodos(n.getDerecho());
    }

    /** Altura medida en niveles: arbol vacio = 0, solo raiz = 1. */
    public static <T> int altura(NodoArbol<T> n) {
        if (n == null) {
            return 0;
        }
        return 1 + Math.max(altura(n.getIzquierdo()), altura(n.getDerecho()));
    }

    /** Lleno: cada nodo tiene 0 o 2 hijos. */
    public static <T> boolean esLleno(NodoArbol<T> n) {
        if (n == null) {
            return true;
        }
        if (n.esHoja()) {
            return true;
        }
        if (n.getIzquierdo() != null && n.getDerecho() != null) {
            return esLleno(n.getIzquierdo()) && esLleno(n.getDerecho());
        }
        return false;
    }

    /** Completo: todos los niveles llenos de izquierda a derecha (recorrido por niveles). */
    public static <T> boolean esCompleto(NodoArbol<T> raiz) {
        if (raiz == null) {
            return true;
        }
        Queue<NodoArbol<T>> cola = new LinkedList<>();
        cola.add(raiz);
        boolean huecoVisto = false;

        while (!cola.isEmpty()) {
            NodoArbol<T> actual = cola.poll();

            if (actual.getIzquierdo() != null) {
                if (huecoVisto) {
                    return false;
                }
                cola.add(actual.getIzquierdo());
            } else {
                huecoVisto = true;
            }

            if (actual.getDerecho() != null) {
                if (huecoVisto) {
                    return false;
                }
                cola.add(actual.getDerecho());
            } else {
                huecoVisto = true;
            }
        }
        return true;
    }

    /** Perfecto: lleno y con todas las hojas en el mismo nivel (nodos = 2^h - 1). */
    public static <T> boolean esPerfecto(NodoArbol<T> raiz) {
        if (raiz == null) {
            return false;
        }
        int h = altura(raiz);
        return esLleno(raiz) && contarNodos(raiz) == (1 << h) - 1;
    }

    /** Degenerado: cada nodo tiene un solo hijo (parece una lista enlazada). */
    public static <T> boolean esDegenerado(NodoArbol<T> raiz) {
        if (raiz == null) {
            return false;
        }
        NodoArbol<T> actual = raiz;
        while (actual != null) {
            if (actual.getIzquierdo() != null && actual.getDerecho() != null) {
                return false;
            }
            if (actual.getIzquierdo() != null) {
                actual = actual.getIzquierdo();
            } else {
                actual = actual.getDerecho();
            }
        }
        return true;
    }

    /** Devuelve todas las clasificaciones que cumple (no son excluyentes). */
    public static <T> String clasificar(NodoArbol<T> raiz) {
        if (raiz == null) {
            return "arbol vacio";
        }
        StringBuilder sb = new StringBuilder();
        if (esLleno(raiz)) {
            sb.append("lleno, ");
        }
        if (esCompleto(raiz)) {
            sb.append("completo, ");
        }
        if (esPerfecto(raiz)) {
            sb.append("perfecto, ");
        }
        if (esDegenerado(raiz)) {
            sb.append("degenerado, ");
        }
        if (sb.length() == 0) {
            return "binario general (ninguna clasificacion especial)";
        }
        return sb.substring(0, sb.length() - 2);
    }
}
