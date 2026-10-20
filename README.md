# 02-propiedades-arboles

Guia 2: **Anatomia y propiedades de un arbol**
Taller de Moodle: **"Anatomia de un arbol"**
Asignatura: Estructura de Datos II - Ingenieria de Software / Sistemas - Semestre IV
Ecosistema: Java / GitHub

## Objetivo

Identificar y calcular raiz, hojas, nodos internos, grado, nivel, profundidad, altura y
caminos en un arbol, e implementar un metodo recursivo `altura()` en Java sobre una
estructura `NodoGeneral<T>`. Como cierre del taller, se disena un arbol de estructura
organizacional en draw.io.

## Estructura del proyecto

```
02-propiedades-arboles/
|-- src/
|   |-- NodoGeneral.java     (nodo con dato, padre y lista de hijos)
|   |-- MetricasArbol.java   (altura, profundidad, nivel, grado, caminos, hermanos)
|   |-- Pruebas.java         (36 pruebas verificables)
|   `-- Main.java            (demostracion: arbol A, ejercicios y arbol organizacional)
|-- diagramas/
|   `-- diagramas_anatomia_arbol.drawio   (4 paginas, abrir en draw.io)
|-- evidencia/
|   `-- salida_ejecucion.txt
|-- TALLER_RESUELTO.md       (taller resuelto, version GitHub)
|-- TALLER_RESUELTO.docx     (taller resuelto, version Word para Moodle)
|-- .gitignore
`-- README.md
```

## Como ejecutar

### En Visual Studio Code
1. Instalar el "Extension Pack for Java".
2. Abrir la carpeta `02-propiedades-arboles`.
3. Abrir `src/Main.java` y pulsar **Run** sobre `main`.
4. Tomar captura de la consola (captura de ejecucion).

### Desde terminal
```
javac -d out src/*.java
java -cp out Main
```

## Diagramas (draw.io)

Abrir `diagramas/diagramas_anatomia_arbol.drawio` en https://app.diagrams.net. Paginas:

1. Arbol A rotulado (raiz, hojas, internos, hermanos, subarbol, grado, profundidad y altura)
2. Dos caminos y su longitud
3. Arbol con un nuevo nivel (altura 3 a 4)
4. Arbol de estructura organizacional

## El arbol A

```
Nivel 1:                 A
                    /    |    \
Nivel 2:          B      C      D
                 / \     |     /|\
Nivel 3:        E   F    G    H I J
               / \            |
Nivel 4:      K   L           M
```

Resumen: 13 nodos, 7 hojas, 6 nodos internos, grado del arbol 3, altura 3 (4 niveles).

Tabla de metricas del arbol A:

| Nodo | Padre | Hijos | Grado | Nivel | Profundidad | Altura | Tipo | Hermanos |
|------|-------|-------|-------|-------|-------------|--------|------|----------|
| A | - | B, C, D | 3 | 1 | 0 | 3 | RAIZ | - |
| B | A | E, F | 2 | 2 | 1 | 2 | INTERNO | C, D |
| E | B | K, L | 2 | 3 | 2 | 1 | INTERNO | F |
| K | E | - | 0 | 4 | 3 | 0 | HOJA | L |
| L | E | - | 0 | 4 | 3 | 0 | HOJA | K |
| F | B | - | 0 | 3 | 2 | 0 | HOJA | E |
| C | A | G | 1 | 2 | 1 | 1 | INTERNO | B, D |
| G | C | - | 0 | 3 | 2 | 0 | HOJA | - |
| D | A | H, I, J | 3 | 2 | 1 | 2 | INTERNO | B, C |
| H | D | M | 1 | 3 | 2 | 1 | INTERNO | I, J |
| M | H | - | 0 | 4 | 3 | 0 | HOJA | - |
| I | D | - | 0 | 3 | 2 | 0 | HOJA | H, J |
| J | D | - | 0 | 3 | 2 | 0 | HOJA | H, I |

## Convenciones

- **Profundidad:** aristas desde la raiz (raiz = 0). **Nivel:** profundidad + 1.
- **Altura de un nodo:** aristas hasta la hoja mas lejana (hoja = 0).
- **Longitud de un camino:** numero de aristas.
- Un arbol vacio (`null`) tiene altura -1.

## Metodo altura()

```java
public static int altura(NodoGeneral<?> nodo) {
    if (nodo == null) return -1;
    if (nodo.getHijos().isEmpty()) return 0;
    int max = 0;
    for (NodoGeneral<?> hijo : nodo.getHijos()) {
        max = Math.max(max, altura(hijo));
    }
    return 1 + max;
}
```

Una hoja devuelve 0 (caso base). Cualquier otro nodo devuelve 1 + la mayor altura de sus
hijos. La explicacion completa con el calculo manual esta en `TALLER_RESUELTO.md`.

## Pruebas

`Pruebas.java` ejecuta 36 verificaciones (`[OK]` / `[FALLO]`): conteos globales, la altura,
el grado y la profundidad de los 13 nodos contra el calculo manual, relaciones de parentesco,
caminos, casos borde (nodo unico, arbol vacio, nodo inexistente, lista de hijos de solo
lectura), la modificacion del arbol del ejercicio 3 y el arbol organizacional.

Resultado: **36 correctas, 0 fallidas.**

## Ejercicios

Las respuestas completas estan en `TALLER_RESUELTO.md`. Resumen:

1. **Profundidad de las hojas:** F, G, I, J = 2; K, L, M = 3.
2. **Dos caminos:** raiz a K = A-B-E-K (longitud 3); raiz a J = A-D-J (longitud 2).
3. **Nuevo nivel:** al agregar N bajo K, la altura pasa de 3 a 4. Cambian solo las alturas
   de los ancestros de N (K, E, B, A).

## Subir a GitHub

```
git init
git add .
git commit -m "Guia 2: anatomia y propiedades de un arbol, altura recursiva"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/02-propiedades-arboles.git
git push -u origin main
```
