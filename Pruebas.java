import java.util.Arrays;
import java.util.List;

/**
 * Pruebas verificables de las metricas del arbol.
 * Cada prueba compara el resultado del programa contra el calculo manual.
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

    private static List<String> lista(String... valores) {
        return Arrays.asList(valores);
    }

    private static NodoGeneral<String> n(NodoGeneral<String> raiz, String dato) {
        return MetricasArbol.buscar(raiz, dato);
    }

    public static boolean ejecutar() {
        correctas = 0;
        fallidas = 0;
        NodoGeneral<String> a = Main.construirArbolA();

        System.out.println("-- Arbol A: conteos y propiedades globales --");
        verificar("el arbol A tiene 13 nodos", MetricasArbol.contarNodos(a) == 13);
        verificar("el arbol A tiene 7 hojas", MetricasArbol.contarHojas(a) == 7);
        verificar("el arbol A tiene 6 nodos internos", MetricasArbol.contarInternos(a) == 6);
        verificar("el grado del arbol A es 3", MetricasArbol.gradoArbol(a) == 3);
        verificar("la altura del arbol A es 3 (4 niveles)", MetricasArbol.altura(a) == 3);
        verificar("hojas = F G I J K L M",
                MetricasArbol.hojas(a).stream().map(NodoGeneral::getDato).sorted().toList()
                        .equals(lista("F", "G", "I", "J", "K", "L", "M")));

        System.out.println("-- Arbol A: altura, grado y profundidad de cada nodo (vs calculo manual) --");
        boolean alturas = true;
        boolean grados = true;
        boolean profundidades = true;
        for (int i = 0; i < Main.NOMBRES_A.length; i++) {
            NodoGeneral<String> nodo = n(a, Main.NOMBRES_A[i]);
            alturas = alturas && MetricasArbol.altura(nodo) == Main.ALTURA_MANUAL_A[i];
            grados = grados && nodo.getGrado() == Main.GRADO_MANUAL_A[i];
            profundidades = profundidades && MetricasArbol.profundidad(nodo) == Main.PROFUNDIDAD_MANUAL_A[i];
        }
        verificar("las 13 alturas coinciden con el calculo manual", alturas);
        verificar("los 13 grados coinciden con el calculo manual", grados);
        verificar("las 13 profundidades coinciden con el calculo manual", profundidades);
        verificar("nivel de la raiz = 1 y nivel de K = 4",
                MetricasArbol.nivel(a) == 1 && MetricasArbol.nivel(n(a, "K")) == 4);

        System.out.println("-- Relaciones de parentesco --");
        verificar("la raiz A no tiene padre", a.getPadre() == null && a.esRaiz());
        verificar("el padre de E es B", n(a, "E").getPadre() == n(a, "B"));
        verificar("hermanos de B = C D", MetricasArbol.hermanos(n(a, "B")).equals(lista("C", "D")));
        verificar("hermanos de H = I J", MetricasArbol.hermanos(n(a, "H")).equals(lista("I", "J")));
        verificar("la raiz no tiene hermanos", MetricasArbol.hermanos(a).isEmpty());
        verificar("el subarbol de D tiene 5 nodos (D H I J M)",
                MetricasArbol.contarNodos(n(a, "D")) == 5);
        verificar("una hoja tiene altura 0 y grado 0",
                MetricasArbol.altura(n(a, "K")) == 0 && n(a, "K").getGrado() == 0 && n(a, "K").esHoja());

        System.out.println("-- Caminos --");
        List<String> c1 = MetricasArbol.caminoDesdeRaiz(n(a, "K"));
        List<String> c2 = MetricasArbol.caminoDesdeRaiz(n(a, "J"));
        List<String> c3 = MetricasArbol.caminoEntre(n(a, "K"), n(a, "M"));
        verificar("camino raiz a K = A B E K (longitud 3)",
                c1.equals(lista("A", "B", "E", "K")) && MetricasArbol.longitudCamino(c1) == 3);
        verificar("camino raiz a J = A D J (longitud 2)",
                c2.equals(lista("A", "D", "J")) && MetricasArbol.longitudCamino(c2) == 2);
        verificar("camino K a M = K E B A D H M (longitud 6)",
                c3.equals(lista("K", "E", "B", "A", "D", "H", "M")) && MetricasArbol.longitudCamino(c3) == 6);
        verificar("camino de un nodo a si mismo tiene longitud 0",
                MetricasArbol.longitudCamino(MetricasArbol.caminoEntre(n(a, "B"), n(a, "B"))) == 0);
        verificar("camino de la raiz a si misma = A", MetricasArbol.caminoDesdeRaiz(a).equals(lista("A")));

        System.out.println("-- Casos borde --");
        verificar("altura de un arbol con un solo nodo = 0", MetricasArbol.altura(new NodoGeneral<>("X")) == 0);
        verificar("altura de un arbol vacio (null) = -1", MetricasArbol.altura(null) == -1);
        verificar("contarNodos(null) = 0", MetricasArbol.contarNodos(null) == 0);
        verificar("buscar un dato que no existe devuelve null", n(a, "Z") == null);
        boolean lanza = false;
        try {
            new NodoGeneral<>("Q").getHijos().add(new NodoGeneral<>("R"));
        } catch (UnsupportedOperationException ex) {
            lanza = true;
        }
        verificar("getHijos() es de solo lectura (encapsulamiento)", lanza);

        System.out.println("-- Modificar el arbol (ejercicio 3) --");
        NodoGeneral<String> m = Main.construirArbolA();
        n(m, "K").agregarHijo("N");
        verificar("agregar N bajo K sube la altura del arbol de 3 a 4", MetricasArbol.altura(m) == 4);
        verificar("alturas nuevas: K=1, E=2, B=3, A=4",
                MetricasArbol.altura(n(m, "K")) == 1 && MetricasArbol.altura(n(m, "E")) == 2
                        && MetricasArbol.altura(n(m, "B")) == 3 && MetricasArbol.altura(n(m, "A")) == 4);
        verificar("las ramas C y D no cambian de altura",
                MetricasArbol.altura(n(m, "C")) == 1 && MetricasArbol.altura(n(m, "D")) == 2);
        verificar("la profundidad de N es 4", MetricasArbol.profundidad(n(m, "N")) == 4);
        NodoGeneral<String> m2 = Main.construirArbolA();
        n(m2, "G").agregarHijo("P");
        verificar("agregar P bajo G (rama corta) NO cambia la altura del arbol", MetricasArbol.altura(m2) == 3);

        System.out.println("-- Arbol organizacional --");
        NodoGeneral<String> org = Main.construirArbolOrganizacional();
        verificar("organizacion: 14 nodos, 8 hojas, 6 internos",
                MetricasArbol.contarNodos(org) == 14 && MetricasArbol.contarHojas(org) == 8
                        && MetricasArbol.contarInternos(org) == 6);
        verificar("organizacion: altura 3 (4 niveles) y grado del arbol 3",
                MetricasArbol.altura(org) == 3 && MetricasArbol.gradoArbol(org) == 3);
        verificar("organizacion: hermano de Ventas = Marketing",
                MetricasArbol.hermanos(n(org, "Ventas")).equals(lista("Marketing")));
        verificar("organizacion: profundidad de Equipo Backend = 3",
                MetricasArbol.profundidad(n(org, "Equipo Backend")) == 3);

        System.out.println();
        System.out.println("RESULTADO DE PRUEBAS: " + correctas + " correctas, " + fallidas + " fallidas");
        return fallidas == 0;
    }
}
