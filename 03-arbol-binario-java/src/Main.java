/**
 * Laboratorio: "Mi primer arbol binario"
 * Estructura de Datos II - Ingenieria de Software
 */
public class Main {

    // ---------- Construccion de los 4 arboles de ejemplo ----------

    static NodoArbol<String> construirLleno() {
        NodoArbol<String> a = new NodoArbol<>("A");
        NodoArbol<String> b = new NodoArbol<>("B");
        NodoArbol<String> c = new NodoArbol<>("C");
        a.setIzquierdo(b);
        a.setDerecho(c);
        b.setIzquierdo(new NodoArbol<>("D"));
        b.setDerecho(new NodoArbol<>("E"));
        return a;
    }

    static NodoArbol<String> construirCompleto() {
        NodoArbol<String> a = construirLleno();
        a.getDerecho().setIzquierdo(new NodoArbol<>("F"));
        return a;
    }

    static NodoArbol<String> construirPerfecto() {
        NodoArbol<String> a = construirCompleto();
        a.getDerecho().setDerecho(new NodoArbol<>("G"));
        return a;
    }

    static NodoArbol<String> construirDegenerado() {
        NodoArbol<String> a = new NodoArbol<>("A");
        NodoArbol<String> b = new NodoArbol<>("B");
        NodoArbol<String> c = new NodoArbol<>("C");
        NodoArbol<String> d = new NodoArbol<>("D");
        NodoArbol<String> e = new NodoArbol<>("E");
        a.setDerecho(b);
        b.setDerecho(c);
        c.setDerecho(d);
        d.setDerecho(e);
        return a;
    }

    // ---------- Arbol manual de 7 nodos (enlazando referencias a mano) ----------

    static NodoArbol<String> construirArbolSieteNodos() {
        NodoArbol<String> raiz = new NodoArbol<>("A");
        NodoArbol<String> b = new NodoArbol<>("B");
        NodoArbol<String> c = new NodoArbol<>("C");
        NodoArbol<String> d = new NodoArbol<>("D");
        NodoArbol<String> e = new NodoArbol<>("E");
        NodoArbol<String> f = new NodoArbol<>("F");
        NodoArbol<String> g = new NodoArbol<>("G");

        raiz.setIzquierdo(b);
        raiz.setDerecho(c);
        b.setIzquierdo(d);
        b.setDerecho(e);
        c.setIzquierdo(f);
        c.setDerecho(g);
        return raiz;
    }

    // ---------- Arbol de expresion: (3 + 5) * (8 - 2) ----------

    static NodoArbol<String> construirArbolExpresion() {
        NodoArbol<String> mult = new NodoArbol<>("*");
        NodoArbol<String> suma = new NodoArbol<>("+");
        NodoArbol<String> resta = new NodoArbol<>("-");

        suma.setIzquierdo(new NodoArbol<>("3"));
        suma.setDerecho(new NodoArbol<>("5"));
        resta.setIzquierdo(new NodoArbol<>("8"));
        resta.setDerecho(new NodoArbol<>("2"));

        mult.setIzquierdo(suma);
        mult.setDerecho(resta);
        return mult;
    }

    static String aInfijo(NodoArbol<String> n) {
        if (n == null) {
            return "";
        }
        if (n.esHoja()) {
            return n.getDato();
        }
        return "(" + aInfijo(n.getIzquierdo()) + " " + n.getDato() + " "
                + aInfijo(n.getDerecho()) + ")";
    }

    static int evaluar(NodoArbol<String> n) {
        if (n == null) {
            throw new IllegalArgumentException("Expresion invalida: nodo nulo");
        }
        if (n.esHoja()) {
            return Integer.parseInt(n.getDato());
        }
        int izq = evaluar(n.getIzquierdo());
        int der = evaluar(n.getDerecho());
        switch (n.getDato()) {
            case "+":
                return izq + der;
            case "-":
                return izq - der;
            case "*":
                return izq * der;
            case "/":
                if (der == 0) {
                    throw new ArithmeticException("Division por cero");
                }
                return izq / der;
            default:
                throw new IllegalArgumentException("Operador desconocido: " + n.getDato());
        }
    }

    // ---------- Utilidades de impresion ----------

    static void mostrar(String titulo, NodoArbol<String> raiz) {
        System.out.println(titulo);
        System.out.println("  Nodos: " + ClasificadorArbol.contarNodos(raiz)
                + " | Altura: " + ClasificadorArbol.altura(raiz));
        System.out.println("  Clasificacion: " + ClasificadorArbol.clasificar(raiz));
        System.out.println();
    }

    static void linea(String texto) {
        System.out.println("==================================================");
        System.out.println(texto);
        System.out.println("==================================================");
    }

    // ---------- Programa principal ----------

    public static void main(String[] args) {

        linea("PARTE 1: Clasificacion de cuatro arboles pequenos");
        mostrar("Arbol 1 (forma del arbol lleno)", construirLleno());
        mostrar("Arbol 2 (forma del arbol completo)", construirCompleto());
        mostrar("Arbol 3 (forma del arbol perfecto)", construirPerfecto());
        mostrar("Arbol 4 (forma del arbol degenerado)", construirDegenerado());
        System.out.println("Nota: las clasificaciones no son excluyentes. Un arbol perfecto");
        System.out.println("es tambien lleno y completo.");
        System.out.println();

        linea("PARTE 2: Arbol manual de 7 nodos (A..G)");
        NodoArbol<String> raiz = construirArbolSieteNodos();
        raiz.imprimirRaizYHijos();
        System.out.println();
        System.out.println("Hijos de B:");
        raiz.getIzquierdo().imprimirRaizYHijos();
        System.out.println("Hijos de C:");
        raiz.getDerecho().imprimirRaizYHijos();
        System.out.println();
        mostrar("Clasificacion del arbol de 7 nodos", raiz);

        linea("PARTE 3: Pruebas de manejo de null");
        NodoArbol<String> hoja = raiz.getIzquierdo().getIzquierdo(); // nodo D
        System.out.println("Prueba 1: imprimir una hoja (sus hijos son null, sin error)");
        hoja.imprimirRaizYHijos();
        System.out.println("  esHoja() de D = " + hoja.esHoja());
        System.out.println();

        System.out.println("Prueba 2: acceso seguro con validacion");
        NodoArbol<String> hijoDeHoja = hoja.getIzquierdo();
        if (hijoDeHoja != null) {
            System.out.println("  Dato: " + hijoDeHoja.getDato());
        } else {
            System.out.println("  D no tiene hijo izquierdo: se evita el error con if != null");
        }
        System.out.println();

        System.out.println("Prueba 3: que pasaria SIN validar (se captura la excepcion)");
        try {
            System.out.println(hoja.getIzquierdo().getDato());
        } catch (NullPointerException ex) {
            System.out.println("  NullPointerException capturada: no se puede leer el dato de null");
        }
        System.out.println();

        System.out.println("Prueba 4: metodos utilitarios con arbol nulo");
        NodoArbol<String> vacio = null;
        System.out.println("  contarNodos(null) = " + ClasificadorArbol.contarNodos(vacio));
        System.out.println("  altura(null)      = " + ClasificadorArbol.altura(vacio));
        System.out.println("  clasificar(null)  = " + ClasificadorArbol.clasificar(vacio));
        System.out.println();

        linea("PARTE 4: Arbol de expresiones (3 + 5) * (8 - 2)");
        NodoArbol<String> expr = construirArbolExpresion();
        System.out.println("Raiz y operandos directos:");
        expr.imprimirRaizYHijos();
        System.out.println();
        System.out.println("Expresion infija : " + aInfijo(expr));
        System.out.println("Resultado        : " + evaluar(expr));
        System.out.println();
        mostrar("Clasificacion del arbol de expresion", expr);

        System.out.println("Fin del programa.");
    }
}
