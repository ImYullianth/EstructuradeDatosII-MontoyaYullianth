import java.util.ArrayList;
import java.util.List;

/**
 * Arbol binario de busqueda (BST) de claves enteras.
 * Regla: izquierda < nodo < derecha. No admite claves repetidas.
 * Incluye insertar, buscar y eliminar (con sus tres casos).
 */
public class ArbolBST {

    private NodoArbol<Integer> raiz;

    public NodoArbol<Integer> getRaiz() {
        return raiz;
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    // ---------------- Insertar y buscar ----------------

    /** Inserta una clave. Devuelve false si ya existia (no se duplica). */
    public boolean insertar(int clave) {
        if (buscar(clave)) {
            return false;
        }
        raiz = insertar(raiz, clave);
        return true;
    }

    private NodoArbol<Integer> insertar(NodoArbol<Integer> n, int clave) {
        if (n == null) {
            return new NodoArbol<>(clave);
        }
        if (clave < n.getDato()) {
            n.setIzquierdo(insertar(n.getIzquierdo(), clave));
        } else {
            n.setDerecho(insertar(n.getDerecho(), clave));
        }
        return n;
    }

    public boolean buscar(int clave) {
        NodoArbol<Integer> actual = raiz;
        while (actual != null) {
            int dato = actual.getDato();
            if (clave == dato) {
                return true;
            }
            actual = (clave < dato) ? actual.getIzquierdo() : actual.getDerecho();
        }
        return false;
    }

    // ---------------- Eliminar ----------------

    /**
     * Describe que caso de eliminacion aplicaria a una clave (sin eliminarla).
     */
    public String tipoDeCaso(int clave) {
        NodoArbol<Integer> actual = raiz;
        while (actual != null && actual.getDato() != clave) {
            actual = (clave < actual.getDato()) ? actual.getIzquierdo() : actual.getDerecho();
        }
        if (actual == null) {
            return "la clave no existe en el arbol";
        }
        if (actual.esHoja()) {
            return "CASO 1: nodo hoja";
        }
        if (actual.getIzquierdo() == null || actual.getDerecho() == null) {
            return "CASO 2: nodo con un hijo";
        }
        return "CASO 3: nodo con dos hijos";
    }

    /**
     * Elimina una clave del BST sin destruir el resto del arbol.
     * Devuelve true si la clave existia y fue eliminada.
     */
    public boolean eliminar(int clave) {
        if (!buscar(clave)) {
            return false;
        }
        raiz = eliminar(raiz, clave);
        return true;
    }

    /**
     * Version recursiva. Devuelve la nueva raiz del subarbol, de modo que el
     * padre re-enlace su referencia al resultado.
     */
    private NodoArbol<Integer> eliminar(NodoArbol<Integer> n, int clave) {
        if (n == null) {
            return null;
        }

        if (clave < n.getDato()) {
            n.setIzquierdo(eliminar(n.getIzquierdo(), clave));
        } else if (clave > n.getDato()) {
            n.setDerecho(eliminar(n.getDerecho(), clave));
        } else {
            // Se encontro el nodo a eliminar

            // CASO 1: nodo hoja -> el padre apuntara a null
            if (n.esHoja()) {
                return null;
            }

            // CASO 2: un solo hijo -> el hijo ocupa el lugar del nodo
            if (n.getIzquierdo() == null) {
                return n.getDerecho();
            }
            if (n.getDerecho() == null) {
                return n.getIzquierdo();
            }

            // CASO 3: dos hijos -> se reemplaza por el sucesor inorden
            // (el minimo del subarbol derecho) y luego se elimina ese sucesor
            NodoArbol<Integer> sucesor = minimo(n.getDerecho());
            n.setDato(sucesor.getDato());
            n.setDerecho(eliminar(n.getDerecho(), sucesor.getDato()));
        }
        return n;
    }

    private NodoArbol<Integer> minimo(NodoArbol<Integer> n) {
        while (n.getIzquierdo() != null) {
            n = n.getIzquierdo();
        }
        return n;
    }

    // ---------------- Eliminar el arbol completo ----------------

    /**
     * Libera todo el arbol usando POSTORDEN: primero los hijos y al final el
     * padre. Asi nunca se pierde la referencia a un subarbol sin liberar.
     * Devuelve el orden en que se liberaron las claves.
     */
    public List<Integer> vaciar() {
        List<Integer> orden = new ArrayList<>();
        liberar(raiz, orden);
        raiz = null;
        return orden;
    }

    private void liberar(NodoArbol<Integer> n, List<Integer> orden) {
        if (n == null) {
            return;
        }
        liberar(n.getIzquierdo(), orden);
        liberar(n.getDerecho(), orden);
        orden.add(n.getDato());
        n.setIzquierdo(null);
        n.setDerecho(null);
    }

    // ---------------- Utilidades ----------------

    public int tamano() {
        return contar(raiz);
    }

    private int contar(NodoArbol<Integer> n) {
        if (n == null) {
            return 0;
        }
        return 1 + contar(n.getIzquierdo()) + contar(n.getDerecho());
    }

    public int altura() {
        return altura(raiz);
    }

    private int altura(NodoArbol<Integer> n) {
        if (n == null) {
            return 0;
        }
        return 1 + Math.max(altura(n.getIzquierdo()), altura(n.getDerecho()));
    }

    /**
     * Dibujo en texto del arbol girado: la raiz queda a la izquierda,
     * los hijos derechos arriba y los izquierdos abajo.
     */
    public String dibujar() {
        if (raiz == null) {
            return "  (arbol vacio)\n";
        }
        StringBuilder sb = new StringBuilder();
        dibujar(raiz, 0, sb);
        return sb.toString();
    }

    private void dibujar(NodoArbol<Integer> n, int nivel, StringBuilder sb) {
        if (n == null) {
            return;
        }
        dibujar(n.getDerecho(), nivel + 1, sb);
        sb.append("  ");
        for (int i = 0; i < nivel; i++) {
            sb.append("      ");
        }
        sb.append(n.getDato()).append("\n");
        dibujar(n.getIzquierdo(), nivel + 1, sb);
    }
}
