import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Nodo de un arbol general (cualquier cantidad de hijos).
 * Guarda un dato, una referencia al padre y una lista de hijos.
 * La referencia al padre permite calcular profundidad y caminos.
 */
public class NodoGeneral<T> {

    private T dato;
    private NodoGeneral<T> padre;
    private final List<NodoGeneral<T>> hijos = new ArrayList<>();

    public NodoGeneral(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoGeneral<T> getPadre() {
        return padre;
    }

    /** Lista de solo lectura: los hijos solo se agregan con agregarHijo(). */
    public List<NodoGeneral<T>> getHijos() {
        return Collections.unmodifiableList(hijos);
    }

    /** Crea un nodo con ese dato, lo enlaza como ultimo hijo y lo devuelve. */
    public NodoGeneral<T> agregarHijo(T dato) {
        NodoGeneral<T> hijo = new NodoGeneral<>(dato);
        hijo.padre = this;
        hijos.add(hijo);
        return hijo;
    }

    /** Grado de un nodo = numero de hijos. */
    public int getGrado() {
        return hijos.size();
    }

    /** La raiz es el unico nodo sin padre. */
    public boolean esRaiz() {
        return padre == null;
    }

    /** Una hoja es un nodo sin hijos. */
    public boolean esHoja() {
        return hijos.isEmpty();
    }

    /** Un nodo interno tiene al menos un hijo (la raiz tambien lo es si tiene hijos). */
    public boolean esInterno() {
        return !hijos.isEmpty();
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}
