import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Pruebas verificables de recorridos y eliminacion.
 * Cada prueba compara el resultado real contra el esperado e imprime OK o FALLO.
 */
public class Pruebas {

    private static int correctas = 0;
    private static int fallidas = 0;

    private Pruebas() {
    }

    private static void verificar(String nombre, boolean condicion) {
        if (condicion) {
            correctas++;
            System.out.println("  [OK]    " + nombre);
        } else {
            fallidas++;
            System.out.println("  [FALLO] " + nombre);
        }
    }

    static List<Integer> lista(Integer... valores) {
        return new ArrayList<>(Arrays.asList(valores));
    }

    static boolean estaOrdenada(List<Integer> l) {
        for (int i = 1; i < l.size(); i++) {
            if (l.get(i - 1) >= l.get(i)) {
                return false;
            }
        }
        return true;
    }

    static ArbolBST arbolBase() {
        ArbolBST bst = new ArbolBST();
        for (int c : Main.ORDEN_INSERCION) {
            bst.insertar(c);
        }
        return bst;
    }

    /** Ejecuta todas las pruebas. Devuelve true si todas pasaron. */
    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;

        System.out.println("-- Recorridos sobre el BST de 10 productos --");
        ArbolBST bst = arbolBase();
        NodoArbol<Integer> r = bst.getRaiz();
        verificar("preorden  = 50 30 20 40 35 70 60 65 80 90",
                Recorridos.preorden(r).equals(lista(50, 30, 20, 40, 35, 70, 60, 65, 80, 90)));
        verificar("inorden   = 20 30 35 40 50 60 65 70 80 90",
                Recorridos.inorden(r).equals(lista(20, 30, 35, 40, 50, 60, 65, 70, 80, 90)));
        verificar("postorden = 20 35 40 30 65 60 90 80 70 50",
                Recorridos.postorden(r).equals(lista(20, 35, 40, 30, 65, 60, 90, 80, 70, 50)));
        verificar("porNiveles = 50 30 70 20 40 60 80 35 65 90",
                Recorridos.porNiveles(r).equals(lista(50, 30, 70, 20, 40, 60, 80, 35, 65, 90)));

        List<Integer> ordenada = new ArrayList<>(Main.ORDEN_INSERCION_LISTA);
        Collections.sort(ordenada);
        verificar("inorden coincide con la lista ordenada con Collections.sort",
                Recorridos.inorden(r).equals(ordenada));
        verificar("el BST tiene 10 nodos y altura 4", bst.tamano() == 10 && bst.altura() == 4);

        System.out.println("-- Casos borde de los recorridos --");
        ArbolBST vacio = new ArbolBST();
        verificar("arbol vacio: los 4 recorridos devuelven lista vacia",
                Recorridos.preorden(vacio.getRaiz()).isEmpty()
                        && Recorridos.inorden(vacio.getRaiz()).isEmpty()
                        && Recorridos.postorden(vacio.getRaiz()).isEmpty()
                        && Recorridos.porNiveles(vacio.getRaiz()).isEmpty());

        ArbolBST uno = new ArbolBST();
        uno.insertar(7);
        verificar("un solo nodo: los 4 recorridos devuelven [7]",
                Recorridos.preorden(uno.getRaiz()).equals(lista(7))
                        && Recorridos.inorden(uno.getRaiz()).equals(lista(7))
                        && Recorridos.postorden(uno.getRaiz()).equals(lista(7))
                        && Recorridos.porNiveles(uno.getRaiz()).equals(lista(7)));

        ArbolBST lineal = new ArbolBST();
        for (int i = 1; i <= 5; i++) {
            lineal.insertar(i);
        }
        verificar("arbol degenerado 1..5: preorden = inorden = niveles = 1 2 3 4 5",
                Recorridos.preorden(lineal.getRaiz()).equals(lista(1, 2, 3, 4, 5))
                        && Recorridos.inorden(lineal.getRaiz()).equals(lista(1, 2, 3, 4, 5))
                        && Recorridos.porNiveles(lineal.getRaiz()).equals(lista(1, 2, 3, 4, 5)));
        verificar("arbol degenerado 1..5: postorden = 5 4 3 2 1",
                Recorridos.postorden(lineal.getRaiz()).equals(lista(5, 4, 3, 2, 1)));

        ArbolBST repetidos = new ArbolBST();
        repetidos.insertar(5);
        boolean segundo = repetidos.insertar(5);
        verificar("insertar una clave repetida es rechazado", !segundo && repetidos.tamano() == 1);

        System.out.println("-- Eliminacion: caso 1 (hoja) --");
        bst = arbolBase();
        verificar("tipoDeCaso(20) es hoja", bst.tipoDeCaso(20).startsWith("CASO 1"));
        verificar("eliminar(20) devuelve true", bst.eliminar(20));
        verificar("inorden sin el 20 y sigue ordenado",
                Recorridos.inorden(bst.getRaiz()).equals(lista(30, 35, 40, 50, 60, 65, 70, 80, 90)));
        verificar("el nodo 30 ya no tiene hijo izquierdo y quedan 9 nodos",
                bst.getRaiz().getIzquierdo().getIzquierdo() == null && bst.tamano() == 9);

        System.out.println("-- Eliminacion: caso 2 (un hijo) --");
        bst = arbolBase();
        verificar("tipoDeCaso(60) es un hijo", bst.tipoDeCaso(60).startsWith("CASO 2"));
        verificar("eliminar(60) devuelve true", bst.eliminar(60));
        verificar("el 65 ocupa el lugar del 60 (hijo izquierdo de 70)",
                bst.getRaiz().getDerecho().getIzquierdo().getDato() == 65);
        verificar("inorden sin el 60 y sigue ordenado",
                Recorridos.inorden(bst.getRaiz()).equals(lista(20, 30, 35, 40, 50, 65, 70, 80, 90)));

        System.out.println("-- Eliminacion: caso 3 (dos hijos) --");
        bst = arbolBase();
        verificar("tipoDeCaso(50) es dos hijos", bst.tipoDeCaso(50).startsWith("CASO 3"));
        verificar("eliminar(50) devuelve true", bst.eliminar(50));
        verificar("la nueva raiz es 60 (sucesor inorden)", bst.getRaiz().getDato() == 60);
        verificar("el 65 subio y quedo como hijo izquierdo de 70",
                bst.getRaiz().getDerecho().getIzquierdo().getDato() == 65);
        verificar("inorden sin el 50 y sigue ordenado",
                Recorridos.inorden(bst.getRaiz()).equals(lista(20, 30, 35, 40, 60, 65, 70, 80, 90)));

        bst = arbolBase();
        bst.eliminar(30);
        verificar("eliminar(30) (dos hijos): su lugar lo toma el 35",
                bst.getRaiz().getIzquierdo().getDato() == 35
                        && bst.getRaiz().getIzquierdo().getDerecho().getIzquierdo() == null);

        System.out.println("-- Eliminacion: casos borde --");
        bst = arbolBase();
        verificar("eliminar(999) (no existe) devuelve false y no cambia el arbol",
                !bst.eliminar(999) && bst.tamano() == 10);
        verificar("eliminar en arbol vacio devuelve false", !new ArbolBST().eliminar(1));

        ArbolBST solo = new ArbolBST();
        solo.insertar(3);
        verificar("eliminar la unica raiz deja el arbol vacio",
                solo.eliminar(3) && solo.estaVacio() && solo.tamano() == 0);

        bst = arbolBase();
        int[] secuencia = {20, 60, 50, 90, 30};
        boolean siempreOrdenado = true;
        for (int c : secuencia) {
            bst.eliminar(c);
            if (!estaOrdenada(Recorridos.inorden(bst.getRaiz()))) {
                siempreOrdenado = false;
            }
        }
        verificar("tras eliminar 20,60,50,90,30 el inorden sigue ordenado y quedan 5 nodos",
                siempreOrdenado && bst.tamano() == 5);

        System.out.println("-- Eliminar el arbol completo (postorden) --");
        bst = arbolBase();
        List<Integer> esperado = Recorridos.postorden(bst.getRaiz());
        NodoArbol<Integer> raizVieja = bst.getRaiz();
        List<Integer> liberado = bst.vaciar();
        verificar("vaciar() libera en orden postorden (hijos antes que el padre)",
                liberado.equals(esperado));
        verificar("tras vaciar() el arbol queda vacio y la raiz vieja sin hijos",
                bst.estaVacio() && raizVieja.getIzquierdo() == null && raizVieja.getDerecho() == null);

        System.out.println();
        System.out.println("RESULTADO DE PRUEBAS: " + correctas + " correctas, " + fallidas + " fallidas");
        return fallidas == 0;
    }
}
