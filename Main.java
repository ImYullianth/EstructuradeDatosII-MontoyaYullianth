import java.util.List;

/**
 * Guia 2: Anatomia y propiedades de un arbol
 * Taller: "Anatomia de un arbol"
 * Estructura de Datos II - Ingenieria de Software
 */
public class Main {

    /** Nombres del arbol A en preorden y sus valores calculados A MANO (ver TALLER_RESUELTO.md). */
    static final String[] NOMBRES_A = {"A", "B", "E", "K", "L", "F", "C", "G", "D", "H", "M", "I", "J"};
    static final int[] GRADO_MANUAL_A = {3, 2, 2, 0, 0, 0, 1, 0, 3, 1, 0, 0, 0};
    static final int[] PROFUNDIDAD_MANUAL_A = {0, 1, 2, 3, 3, 2, 1, 2, 1, 2, 3, 2, 2};
    static final int[] ALTURA_MANUAL_A = {3, 2, 1, 0, 0, 0, 1, 0, 2, 1, 0, 0, 0};

    // ---------- Arbol A: 13 nodos y 4 niveles ----------

    static NodoGeneral<String> construirArbolA() {
        NodoGeneral<String> a = new NodoGeneral<>("A");
        NodoGeneral<String> b = a.agregarHijo("B");
        NodoGeneral<String> c = a.agregarHijo("C");
        NodoGeneral<String> d = a.agregarHijo("D");

        NodoGeneral<String> e = b.agregarHijo("E");
        b.agregarHijo("F");
        c.agregarHijo("G");
        NodoGeneral<String> h = d.agregarHijo("H");
        d.agregarHijo("I");
        d.agregarHijo("J");

        e.agregarHijo("K");
        e.agregarHijo("L");
        h.agregarHijo("M");
        return a;
    }

    // ---------- Arbol de estructura organizacional: 14 nodos y 4 niveles ----------

    static NodoGeneral<String> construirArbolOrganizacional() {
        NodoGeneral<String> general = new NodoGeneral<>("Gerencia General");

        NodoGeneral<String> tec = general.agregarHijo("Gerencia Tecnologia");
        NodoGeneral<String> com = general.agregarHijo("Gerencia Comercial");
        NodoGeneral<String> adm = general.agregarHijo("Gerencia Administrativa");

        NodoGeneral<String> desarrollo = tec.agregarHijo("Desarrollo");
        tec.agregarHijo("Infraestructura");
        desarrollo.agregarHijo("Equipo Backend");
        desarrollo.agregarHijo("Equipo Frontend");

        NodoGeneral<String> ventas = com.agregarHijo("Ventas");
        com.agregarHijo("Marketing");
        ventas.agregarHijo("Ventas Nacionales");
        ventas.agregarHijo("Ventas Internacionales");

        adm.agregarHijo("Talento Humano");
        adm.agregarHijo("Contabilidad");
        return general;
    }

    static String texto(List<?> lista) {
        StringBuilder sb = new StringBuilder();
        for (Object o : lista) {
            sb.append(o).append(" ");
        }
        return sb.toString().trim();
    }

    static void titulo(String t) {
        System.out.println("==================================================");
        System.out.println(t);
        System.out.println("==================================================");
    }

    static void resumen(NodoGeneral<String> raiz) {
        System.out.println("  Nodos totales    : " + MetricasArbol.contarNodos(raiz));
        System.out.println("  Hojas            : " + MetricasArbol.contarHojas(raiz)
                + "  -> " + texto(MetricasArbol.hojas(raiz)));
        System.out.println("  Nodos internos   : " + MetricasArbol.contarInternos(raiz)
                + "  -> " + texto(MetricasArbol.internos(raiz)));
        System.out.println("  Grado del arbol  : " + MetricasArbol.gradoArbol(raiz));
        System.out.println("  Altura del arbol : " + MetricasArbol.altura(raiz));
        System.out.println("  Numero de niveles: " + (MetricasArbol.altura(raiz) + 1));
    }

    public static void main(String[] args) {

        titulo("PARTE 1: Arbol A (13 nodos, 4 niveles)");
        NodoGeneral<String> raiz = construirArbolA();
        MetricasArbol.imprimirIndentado(raiz);
        System.out.println();
        MetricasArbol.imprimirTabla(raiz);
        System.out.println();
        resumen(raiz);
        System.out.println();

        titulo("PARTE 2: Rotulado y relaciones de parentesco");
        System.out.println("  Raiz                : " + raiz);
        System.out.println("  Padre de E          : " + MetricasArbol.buscar(raiz, "E").getPadre());
        System.out.println("  Hijos de A          : " + texto(MetricasArbol.hijos(raiz)));
        System.out.println("  Hijos de D          : " + texto(MetricasArbol.hijos(MetricasArbol.buscar(raiz, "D"))));
        System.out.println("  Hermanos de B       : " + texto(MetricasArbol.hermanos(MetricasArbol.buscar(raiz, "B"))));
        System.out.println("  Hermanos de H       : " + texto(MetricasArbol.hermanos(MetricasArbol.buscar(raiz, "H"))));
        System.out.println("  Hermanos de A (raiz): " + MetricasArbol.hermanos(raiz) + " (la raiz no tiene)");
        NodoGeneral<String> subD = MetricasArbol.buscar(raiz, "D");
        System.out.println("  Subarbol de D       : " + MetricasArbol.contarNodos(subD) + " nodos -> "
                + texto(MetricasArbol.preorden(subD)));
        NodoGeneral<String> subB = MetricasArbol.buscar(raiz, "B");
        System.out.println("  Subarbol de B       : " + MetricasArbol.contarNodos(subB) + " nodos -> "
                + texto(MetricasArbol.preorden(subB)));
        System.out.println();

        titulo("PARTE 3: Calculo manual vs calculo del programa");
        System.out.printf("  %-5s %-15s %-15s %-15s %s%n", "Nodo", "Grado man/prog", "Prof. man/prog",
                "Altura man/prog", "Coincide");
        boolean todoCoincide = true;
        for (int i = 0; i < NOMBRES_A.length; i++) {
            NodoGeneral<String> n = MetricasArbol.buscar(raiz, NOMBRES_A[i]);
            int g = n.getGrado();
            int p = MetricasArbol.profundidad(n);
            int h = MetricasArbol.altura(n);
            boolean ok = g == GRADO_MANUAL_A[i] && p == PROFUNDIDAD_MANUAL_A[i] && h == ALTURA_MANUAL_A[i];
            todoCoincide = todoCoincide && ok;
            System.out.printf("  %-5s %-15s %-15s %-15s %s%n", NOMBRES_A[i],
                    GRADO_MANUAL_A[i] + " / " + g, PROFUNDIDAD_MANUAL_A[i] + " / " + p,
                    ALTURA_MANUAL_A[i] + " / " + h, ok ? "SI" : "NO");
        }
        System.out.println("  Todos los calculos manuales coinciden con el programa: " + todoCoincide);
        System.out.println();

        titulo("PARTE 4: Ejercicio 1 - Profundidad de cada hoja");
        for (NodoGeneral<String> hoja : MetricasArbol.hojas(raiz)) {
            System.out.println("  Hoja " + hoja + ": profundidad = " + MetricasArbol.profundidad(hoja)
                    + " (nivel " + MetricasArbol.nivel(hoja) + ")");
        }
        System.out.println();

        titulo("PARTE 5: Ejercicio 2 - Caminos y su longitud");
        List<String> camino1 = MetricasArbol.caminoDesdeRaiz(MetricasArbol.buscar(raiz, "K"));
        List<String> camino2 = MetricasArbol.caminoDesdeRaiz(MetricasArbol.buscar(raiz, "J"));
        List<String> camino3 = MetricasArbol.caminoEntre(MetricasArbol.buscar(raiz, "K"),
                MetricasArbol.buscar(raiz, "M"));
        System.out.println("  Camino 1 (raiz a K): " + texto(camino1) + "  longitud = "
                + MetricasArbol.longitudCamino(camino1) + " aristas");
        System.out.println("  Camino 2 (raiz a J): " + texto(camino2) + "  longitud = "
                + MetricasArbol.longitudCamino(camino2) + " aristas");
        System.out.println("  Camino extra (K a M): " + texto(camino3) + "  longitud = "
                + MetricasArbol.longitudCamino(camino3) + " aristas");
        System.out.println();

        titulo("PARTE 6: Ejercicio 3 - Agregar un nuevo nivel");
        System.out.println("  Altura ANTES de modificar: " + MetricasArbol.altura(raiz));
        NodoGeneral<String> k = MetricasArbol.buscar(raiz, "K");
        k.agregarHijo("N");
        System.out.println("  Se agrega el nodo N como hijo de K (la rama mas profunda).");
        System.out.println("  Altura DESPUES de modificar: " + MetricasArbol.altura(raiz)
                + " (niveles: " + (MetricasArbol.altura(raiz) + 1) + ")");
        System.out.println("  Alturas que cambian (ancestros de N):");
        for (String nombre : new String[] {"K", "E", "B", "A"}) {
            System.out.println("    altura(" + nombre + ") = "
                    + MetricasArbol.altura(MetricasArbol.buscar(raiz, nombre)));
        }
        System.out.println("  Alturas que NO cambian: altura(C) = "
                + MetricasArbol.altura(MetricasArbol.buscar(raiz, "C"))
                + ", altura(D) = " + MetricasArbol.altura(MetricasArbol.buscar(raiz, "D")));
        System.out.println();

        NodoGeneral<String> otro = construirArbolA();
        otro.getHijos().get(1).getHijos().get(0).agregarHijo("P");
        System.out.println("  Contraejemplo: se agrega P como hijo de G (rama corta) en un arbol nuevo.");
        System.out.println("  Altura despues: " + MetricasArbol.altura(otro)
                + " (no cambia, porque G esta en profundidad 2 y P queda en profundidad 3)");
        System.out.println("  Conclusion: la altura solo crece si el nodo nuevo queda mas profundo que");
        System.out.println("  la hoja mas profunda actual.");
        System.out.println();

        titulo("PARTE 7: Arbol de estructura organizacional");
        NodoGeneral<String> org = construirArbolOrganizacional();
        MetricasArbol.imprimirIndentado(org);
        System.out.println();
        MetricasArbol.imprimirTabla(org);
        System.out.println();
        resumen(org);
        System.out.println();
        NodoGeneral<String> backend = MetricasArbol.buscar(org, "Equipo Backend");
        List<String> cadena = MetricasArbol.caminoDesdeRaiz(backend);
        System.out.println("  Cadena de mando hasta Equipo Backend: " + String.join(" > ", cadena));
        System.out.println("  Longitud = " + MetricasArbol.longitudCamino(cadena) + " aristas");
        System.out.println("  Hermanos de Ventas      : "
                + texto(MetricasArbol.hermanos(MetricasArbol.buscar(org, "Ventas"))));
        System.out.println();

        titulo("PARTE 8: Pruebas");
        boolean todoBien = Pruebas.ejecutar();
        System.out.println();
        System.out.println(todoBien ? "Todas las pruebas pasaron." : "Hay pruebas fallidas.");
        System.out.println("Fin del programa.");
    }
}
