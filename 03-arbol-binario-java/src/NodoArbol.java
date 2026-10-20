/**
 * Nodo generico de un arbol binario.
 * Guarda un dato de tipo T y dos referencias: izquierdo y derecho.
 * Si una referencia vale null, significa que ese hijo no existe.
 */
public class NodoArbol<T> {

    private T dato;
    private NodoArbol<T> izquierdo;
    private NodoArbol<T> derecho;

    public NodoArbol(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    public void setIzquierdo(NodoArbol<T> n) {
        izquierdo = n;
    }

    public void setDerecho(NodoArbol<T> n) {
        derecho = n;
    }

    /** Un nodo hoja es un nodo REAL cuyas dos referencias son null. */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /**
     * Imprime la raiz (este nodo) y sus hijos.
     * Valida null antes de llamar getDato() para evitar NullPointerException.
     */
    public void imprimirRaizYHijos() {
        System.out.println("Raiz: " + dato);

        if (izquierdo != null) {
            System.out.println("  Hijo izquierdo: " + izquierdo.getDato());
        } else {
            System.out.println("  Hijo izquierdo: (no tiene, referencia null)");
        }

        if (derecho != null) {
            System.out.println("  Hijo derecho:   " + derecho.getDato());
        } else {
            System.out.println("  Hijo derecho:   (no tiene, referencia null)");
        }
    }
}
