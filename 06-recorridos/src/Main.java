import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Guia 6: Recorridos en arboles binarios
 * Laboratorio: "Eliminar sin destruir el arbol"
 * Estructura de Datos II - Ingenieria de Software
 *
 * Las claves del BST son codigos de producto.
 */
public class Main {

    /** Orden en que se insertan los 10 codigos de producto. */
    static final int[] ORDEN_INSERCION = {50, 30, 70, 40, 20, 60, 80, 35, 90, 65};

    static final List<Integer> ORDEN_INSERCION_LISTA = new ArrayList<>();
    static final Map<Integer, String> CATALOGO = new LinkedHashMap<>();

    static {
        for (int c : ORDEN_INSERCION) {
            ORDEN_INSERCION_LISTA.add(c);
        }
        CATALOGO.put(50, "Laptop");
        CATALOGO.put(30, "Mouse");
        CATALOGO.put(70, "Teclado");
        CATALOGO.put(40, "Monitor");
        CATALOGO.put(20, "Cable USB");
        CATALOGO.put(60, "Impresora");
        CATALOGO.put(80, "Audifonos");
        CATALOGO.put(35, "Webcam");
        CATALOGO.put(90, "Tablet");
        CATALOGO.put(65, "Router");
    }

    static ArbolBST construirArbolProductos() {
        ArbolBST bst = new ArbolBST();
        for (int codigo : ORDEN_INSERCION) {
            bst.insertar(codigo);
        }
        return bst;
    }

    static String conNombres(List<Integer> claves) {
        StringBuilder sb = new StringBuilder();
        for (int c : claves) {
            sb.append(c).append(" ").append(CATALOGO.get(c)).append(" | ");
        }
        String s = sb.toString();
        return s.substring(0, s.length() - 3);
    }

    static void titulo(String texto) {
        System.out.println("==================================================");
        System.out.println(texto);
        System.out.println("==================================================");
    }

    /** Elimina una clave sobre un arbol nuevo y muestra antes / despues. */
    static void demostrarEliminacion(String nombreCaso, int clave) {
        ArbolBST bst = construirArbolProductos();
        System.out.println(nombreCaso);
        System.out.println("Clave a eliminar: " + clave + " (" + CATALOGO.get(clave) + ")  ->  "
                + bst.tipoDeCaso(clave));
        System.out.println();
        System.out.println("ANTES (raiz a la izquierda, derecha arriba):");
        System.out.print(bst.dibujar());
        System.out.println("  inorden: " + Recorridos.formato(Recorridos.inorden(bst.getRaiz())));
        System.out.println();

        boolean eliminado = bst.eliminar(clave);
        System.out.println("eliminar(" + clave + ") = " + eliminado);
        System.out.println();
        System.out.println("DESPUES:");
        System.out.print(bst.dibujar());
        System.out.println("  inorden: " + Recorridos.formato(Recorridos.inorden(bst.getRaiz())));
        System.out.println("  nodos: " + bst.tamano() + " | el arbol sigue siendo un BST valido");
        System.out.println();
    }

    public static void main(String[] args) {

        titulo("PARTE 1: BST de 10 productos construido por codigo");
        ArbolBST bst = construirArbolProductos();
        System.out.println("Orden de insercion: " + Recorridos.formato(ORDEN_INSERCION_LISTA));
        System.out.println("Nodos: " + bst.tamano() + " | Altura: " + bst.altura());
        System.out.println();
        System.out.println("Arbol (raiz a la izquierda, derecha arriba):");
        System.out.print(bst.dibujar());
        System.out.println();

        titulo("PARTE 2: Los cuatro recorridos sobre el mismo BST");
        NodoArbol<Integer> raiz = bst.getRaiz();
        List<Integer> pre = Recorridos.preorden(raiz);
        List<Integer> in = Recorridos.inorden(raiz);
        List<Integer> post = Recorridos.postorden(raiz);
        List<Integer> niv = Recorridos.porNiveles(raiz);
        System.out.println("Preorden   (raiz-izq-der)   : " + Recorridos.formato(pre));
        System.out.println("Inorden    (izq-raiz-der)   : " + Recorridos.formato(in));
        System.out.println("Postorden  (izq-der-raiz)   : " + Recorridos.formato(post));
        System.out.println("Por niveles (cola, BFS)     : " + Recorridos.formato(niv));
        System.out.println();
        System.out.println("Inorden con nombres de producto:");
        System.out.println("  " + conNombres(in));
        System.out.println();

        titulo("PARTE 3: Inorden vs lista ordenada");
        List<Integer> ordenada = new ArrayList<>(ORDEN_INSERCION_LISTA);
        Collections.sort(ordenada);
        System.out.println("Lista ordenada (Collections.sort): " + Recorridos.formato(ordenada));
        System.out.println("Salida del inorden               : " + Recorridos.formato(in));
        System.out.println("Son iguales: " + in.equals(ordenada));
        System.out.println();

        titulo("PARTE 4: Eliminar sin destruir el arbol (tres casos)");
        demostrarEliminacion("--- CASO 1: eliminar un nodo HOJA ---", 20);
        demostrarEliminacion("--- CASO 2: eliminar un nodo con UN HIJO ---", 60);
        demostrarEliminacion("--- CASO 3: eliminar un nodo con DOS HIJOS (la raiz) ---", 50);

        titulo("PARTE 5: Eliminar el arbol completo de forma segura (POSTORDEN)");
        ArbolBST completo = construirArbolProductos();
        System.out.println("Postorden esperado : " + Recorridos.formato(Recorridos.postorden(completo.getRaiz())));
        List<Integer> liberado = completo.vaciar();
        System.out.println("Orden de liberacion: " + Recorridos.formato(liberado));
        System.out.println("Arbol vacio despues de vaciar(): " + completo.estaVacio());
        System.out.println();

        titulo("PARTE 6: Pruebas");
        boolean todoBien = Pruebas.ejecutar();
        System.out.println();
        System.out.println(todoBien ? "Todas las pruebas pasaron." : "Hay pruebas fallidas.");
        System.out.println("Fin del programa.");
    }
}
