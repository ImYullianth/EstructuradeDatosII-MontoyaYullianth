import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Metricas de un arbol general: altura, profundidad, nivel, grado,
 * hojas, nodos internos, caminos, hermanos y subarboles.
 *
 * Convenciones usadas en este proyecto:
 *  - profundidad: numero de aristas desde la raiz hasta el nodo (raiz = 0).
 *  - nivel: profundidad + 1 (la raiz esta en el nivel 1).
 *  - altura de un nodo: aristas del camino mas largo hasta una hoja (hoja = 0).
 *  - altura del arbol: altura de la raiz. Numero de niveles = altura + 1.
 *  - longitud de un camino: numero de aristas (= nodos - 1).
 */
public class MetricasArbol {

    private MetricasArbol() {
    }

    // ---------------- Altura (metodo recursivo de la guia) ----------------

    /**
     * Altura de un nodo: longitud del camino mas largo desde el hasta una hoja.
     * Caso base: una hoja tiene altura 0. Un arbol vacio (null) devuelve -1.
     */
    public static int altura(NodoGeneral<?> nodo) {
        if (nodo == null) {
            return -1;
        }
        if (nodo.getHijos().isEmpty()) {
            return 0;
        }
        int max = 0;
        for (NodoGeneral<?> hijo : nodo.getHijos()) {
            max = Math.max(max, altura(hijo));
        }
        return 1 + max;
    }

    // ---------------- Profundidad, nivel y grado ----------------

    /** Distancia (en aristas) desde la raiz hasta el nodo. */
    public static int profundidad(NodoGeneral<?> nodo) {
        int p = 0;
        NodoGeneral<?> actual = nodo.getPadre();
        while (actual != null) {
            p++;
            actual = actual.getPadre();
        }
        return p;
    }

    /** Nivel = profundidad + 1. */
    public static int nivel(NodoGeneral<?> nodo) {
        return profundidad(nodo) + 1;
    }

    /** Grado del arbol: el mayor grado entre todos sus nodos. */
    public static int gradoArbol(NodoGeneral<?> raiz) {
        if (raiz == null) {
            return 0;
        }
        int max = raiz.getGrado();
        for (NodoGeneral<?> hijo : raiz.getHijos()) {
            max = Math.max(max, gradoArbol(hijo));
        }
        return max;
    }

    // ---------------- Conteos ----------------

    public static int contarNodos(NodoGeneral<?> raiz) {
        if (raiz == null) {
            return 0;
        }
        int total = 1;
        for (NodoGeneral<?> hijo : raiz.getHijos()) {
            total += contarNodos(hijo);
        }
        return total;
    }

    public static int contarHojas(NodoGeneral<?> raiz) {
        if (raiz == null) {
            return 0;
        }
        if (raiz.esHoja()) {
            return 1;
        }
        int total = 0;
        for (NodoGeneral<?> hijo : raiz.getHijos()) {
            total += contarHojas(hijo);
        }
        return total;
    }

    /** Nodos con al menos un hijo (incluye la raiz si tiene hijos). */
    public static int contarInternos(NodoGeneral<?> raiz) {
        return contarNodos(raiz) - contarHojas(raiz);
    }

    // ---------------- Listados ----------------

    /** Todos los nodos en preorden (raiz primero, luego cada subarbol). */
    public static <T> List<NodoGeneral<T>> preorden(NodoGeneral<T> raiz) {
        List<NodoGeneral<T>> salida = new ArrayList<>();
        preorden(raiz, salida);
        return salida;
    }

    private static <T> void preorden(NodoGeneral<T> n, List<NodoGeneral<T>> salida) {
        if (n == null) {
            return;
        }
        salida.add(n);
        for (NodoGeneral<T> hijo : n.getHijos()) {
            preorden(hijo, salida);
        }
    }

    public static <T> List<NodoGeneral<T>> hojas(NodoGeneral<T> raiz) {
        List<NodoGeneral<T>> hojas = new ArrayList<>();
        for (NodoGeneral<T> n : preorden(raiz)) {
            if (n.esHoja()) {
                hojas.add(n);
            }
        }
        return hojas;
    }

    public static <T> List<NodoGeneral<T>> internos(NodoGeneral<T> raiz) {
        List<NodoGeneral<T>> internos = new ArrayList<>();
        for (NodoGeneral<T> n : preorden(raiz)) {
            if (n.esInterno()) {
                internos.add(n);
            }
        }
        return internos;
    }

    /** Datos de los hijos de un nodo. */
    public static <T> List<T> hijos(NodoGeneral<T> nodo) {
        List<T> lista = new ArrayList<>();
        for (NodoGeneral<T> h : nodo.getHijos()) {
            lista.add(h.getDato());
        }
        return lista;
    }

    /** Hermanos: nodos con el mismo padre (sin incluir al propio nodo). */
    public static <T> List<T> hermanos(NodoGeneral<T> nodo) {
        List<T> lista = new ArrayList<>();
        if (nodo.getPadre() == null) {
            return lista;
        }
        for (NodoGeneral<T> h : nodo.getPadre().getHijos()) {
            if (h != nodo) {
                lista.add(h.getDato());
            }
        }
        return lista;
    }

    /** Busca un dato en el arbol (recorrido en profundidad). Devuelve null si no existe. */
    public static <T> NodoGeneral<T> buscar(NodoGeneral<T> raiz, T dato) {
        if (raiz == null) {
            return null;
        }
        if (raiz.getDato().equals(dato)) {
            return raiz;
        }
        for (NodoGeneral<T> hijo : raiz.getHijos()) {
            NodoGeneral<T> r = buscar(hijo, dato);
            if (r != null) {
                return r;
            }
        }
        return null;
    }

    // ---------------- Caminos ----------------

    /** Nodos desde el nodo dado subiendo hasta la raiz (incluye ambos extremos). */
    private static <T> List<NodoGeneral<T>> haciaLaRaiz(NodoGeneral<T> nodo) {
        List<NodoGeneral<T>> lista = new ArrayList<>();
        NodoGeneral<T> actual = nodo;
        while (actual != null) {
            lista.add(actual);
            actual = actual.getPadre();
        }
        return lista;
    }

    /** Camino desde la raiz hasta el nodo, ej: [A, B, E, K]. */
    public static <T> List<T> caminoDesdeRaiz(NodoGeneral<T> nodo) {
        List<T> camino = new ArrayList<>();
        for (NodoGeneral<T> n : haciaLaRaiz(nodo)) {
            camino.add(n.getDato());
        }
        Collections.reverse(camino);
        return camino;
    }

    /** Camino entre dos nodos cualquiera: sube hasta el ancestro comun y baja. */
    public static <T> List<T> caminoEntre(NodoGeneral<T> a, NodoGeneral<T> b) {
        List<NodoGeneral<T>> subidaA = haciaLaRaiz(a);
        List<NodoGeneral<T>> subidaB = haciaLaRaiz(b);
        Set<NodoGeneral<T>> ancestrosA = new HashSet<>(subidaA);

        NodoGeneral<T> comun = null;
        for (NodoGeneral<T> n : subidaB) {
            if (ancestrosA.contains(n)) {
                comun = n;
                break;
            }
        }

        List<T> camino = new ArrayList<>();
        for (NodoGeneral<T> n : subidaA) {
            camino.add(n.getDato());
            if (n == comun) {
                break;
            }
        }
        List<T> bajada = new ArrayList<>();
        for (NodoGeneral<T> n : subidaB) {
            if (n == comun) {
                break;
            }
            bajada.add(n.getDato());
        }
        Collections.reverse(bajada);
        camino.addAll(bajada);
        return camino;
    }

    /** Longitud de un camino = numero de aristas = nodos - 1. */
    public static int longitudCamino(List<?> camino) {
        return camino.size() - 1;
    }

    // ---------------- Presentacion ----------------

    /** Imprime el arbol con sangria segun la profundidad. */
    public static <T> void imprimirIndentado(NodoGeneral<T> raiz) {
        for (NodoGeneral<T> n : preorden(raiz)) {
            StringBuilder sb = new StringBuilder("  ");
            for (int i = 0; i < profundidad(n); i++) {
                sb.append("    ");
            }
            sb.append(profundidad(n) == 0 ? "" : "+-- ").append(n.getDato());
            System.out.println(sb);
        }
    }

    /** Tabla con las metricas de todos los nodos. */
    public static <T> void imprimirTabla(NodoGeneral<T> raiz) {
        String formato = "  %-24s %-24s %5s %5s %5s %6s  %s%n";
        System.out.printf(formato, "Nodo", "Padre", "Grado", "Nivel", "Prof.", "Altura", "Tipo");
        System.out.println("  " + "-".repeat(88));
        for (NodoGeneral<T> n : preorden(raiz)) {
            String padre = (n.getPadre() == null) ? "-" : String.valueOf(n.getPadre().getDato());
            String tipo = n.esRaiz() ? "RAIZ" : (n.esHoja() ? "HOJA" : "INTERNO");
            System.out.printf(formato, n.getDato(), padre, n.getGrado(), nivel(n),
                    profundidad(n), altura(n), tipo);
        }
    }
}
