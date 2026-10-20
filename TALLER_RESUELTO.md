# Taller: Anatomia de un arbol

Asignatura: Estructura de Datos II - Ingenieria de Software / Sistemas - Semestre IV
Guia 2: Anatomia y propiedades de un arbol

## 1. Definiciones y convenciones usadas

| Concepto | Definicion |
|----------|------------|
| Raiz | Nodo sin padre. Es el punto de entrada del arbol. |
| Padre / hijo | Un padre tiene aristas hacia sus hijos. |
| Hermanos | Nodos con el mismo padre. |
| Hoja | Nodo sin hijos. |
| Nodo interno | Nodo con al menos un hijo (la raiz tambien lo es si tiene hijos). |
| Subarbol | Un nodo junto con todos sus descendientes. |
| Camino | Secuencia de nodos conectados por aristas. Su longitud es el numero de aristas (nodos - 1). |
| Grado de un nodo | Numero de hijos del nodo. |
| Grado del arbol | El mayor grado entre todos sus nodos. |
| Profundidad | Aristas desde la raiz hasta el nodo (la raiz tiene profundidad 0). |
| Nivel | Profundidad + 1 (la raiz esta en el nivel 1). |
| Altura de un nodo | Aristas del camino mas largo desde el nodo hasta una hoja (una hoja tiene altura 0). |
| Altura del arbol | Altura de la raiz. El numero de niveles es altura + 1. |

## 2. Arbol A (13 nodos y 4 niveles)

```
Nivel 1:                 A
                    /    |    \
Nivel 2:          B      C      D
                 / \     |     /|\
Nivel 3:        E   F    G    H I J
               / \            |
Nivel 4:      K   L           M
```

El diagrama rotulado esta en `diagramas/diagramas_anatomia_arbol.drawio` (pagina 1).

### Identificacion de conceptos

| Concepto | Respuesta |
|----------|-----------|
| Raiz | A |
| Hojas (7) | F, G, I, J, K, L, M |
| Nodos internos (6) | A, B, C, D, E, H |
| Padre de E | B |
| Hijos de D | H, I, J |
| Hermanos de B | C, D |
| Hermanos de H | I, J |
| Abuelo de K | B |
| Ancestros de M | H, D, A |
| Descendientes de B | E, F, K, L |
| Subarbol de D | D, H, I, J, M (5 nodos) |
| Subarbol de B | B, E, F, K, L (5 nodos) |

### Tabla de metricas calculadas

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

- **Grado del arbol:** 3 (lo tienen A y D).
- **Altura del arbol:** 3 (4 niveles).
- **Profundidad de cinco nodos:** A = 0, C = 1, G = 2, H = 2, M = 3.

## 3. Algoritmo recursivo de la altura

```java
public static int altura(NodoGeneral<?> nodo) {
    if (nodo == null) return -1;
    if (nodo.getHijos().isEmpty()) return 0;     // caso base: hoja
    int max = 0;
    for (NodoGeneral<?> hijo : nodo.getHijos()) {
        max = Math.max(max, altura(hijo));
    }
    return 1 + max;                              // 1 + altura del hijo mas alto
}
```

**Explicacion.** Una hoja tiene altura 0 (caso base). Para cualquier otro nodo, la altura es
1 mas la mayor altura entre sus hijos, porque el camino mas largo hacia una hoja pasa por
el hijo mas alto.

**Ejemplo manual para B:**

```
altura(B) = 1 + max(altura(E), altura(F))
altura(E) = 1 + max(altura(K), altura(L)) = 1 + max(0, 0) = 1
altura(F) = 0
altura(B) = 1 + max(1, 0) = 2
```

**Altura del arbol A:**

```
altura(A) = 1 + max(altura(B), altura(C), altura(D))
          = 1 + max(2, 1, 2) = 3
```

### Comparacion con el calculo manual

El programa (`Main`, parte 3) compara el calculo manual de grado, profundidad y altura de
los 13 nodos contra lo que calcula el codigo. Resultado: **los 13 nodos coinciden**
(ver `evidencia/salida_ejecucion.txt`). Ademas, `Pruebas.java` verifica estos valores
con 36 pruebas, todas correctas.

## 4. Ejercicios

**Ejercicio 1. Profundidad de cada hoja**

| Hoja | Profundidad | Nivel |
|------|-------------|-------|
| F | 2 | 3 |
| G | 2 | 3 |
| I | 2 | 3 |
| J | 2 | 3 |
| K | 3 | 4 |
| L | 3 | 4 |
| M | 3 | 4 |

Las hojas mas profundas (K, L, M) estan en profundidad 3, igual que la altura del arbol.

**Ejercicio 2. Dos caminos distintos y su longitud**

| Camino | Nodos | Longitud (aristas) |
|--------|-------|--------------------|
| Raiz a K | A - B - E - K | 3 |
| Raiz a J | A - D - J | 2 |
| Extra: K a M | K - E - B - A - D - H - M | 6 |

Diagrama en la pagina 2 del archivo `.drawio`.

**Ejercicio 3. Agregar un nuevo nivel y explicar como cambia la altura**

Se agrega el nodo **N** como hijo de K, que es una hoja de la rama mas profunda.
N queda en profundidad 4 (nivel 5), por lo tanto:

- La altura del arbol pasa de **3 a 4** (de 4 a 5 niveles).
- Cambian las alturas de todos los ancestros de N: K 0 a 1, E 1 a 2, B 2 a 3, A 3 a 4.
- No cambian las ramas que no contienen a N: C sigue en 1 y D sigue en 2.

La altura solo crece cuando el nuevo nodo queda mas profundo que la hoja mas profunda actual.
Si N se agregara como hijo de G (profundidad 2), quedaria en profundidad 3 y la altura
seguiria siendo 3 (el programa lo demuestra en la parte 6). Diagrama en la pagina 3 del `.drawio`.

## 5. Arbol de estructura organizacional

Diagrama en la pagina 4 del archivo `.drawio`.

```
Gerencia General
+-- Gerencia Tecnologia
|   +-- Desarrollo
|   |   +-- Equipo Backend
|   |   +-- Equipo Frontend
|   +-- Infraestructura
+-- Gerencia Comercial
|   +-- Ventas
|   |   +-- Ventas Nacionales
|   |   +-- Ventas Internacionales
|   +-- Marketing
+-- Gerencia Administrativa
    +-- Talento Humano
    +-- Contabilidad
```

| Concepto | Respuesta |
|----------|-----------|
| Raiz | Gerencia General |
| Nodos internos (6) | Gerencia General, Gerencia Tecnologia, Desarrollo, Gerencia Comercial, Ventas, Gerencia Administrativa |
| Hojas (8) | Equipo Backend, Equipo Frontend, Infraestructura, Ventas Nacionales, Ventas Internacionales, Marketing, Talento Humano, Contabilidad |
| Grado del arbol | 3 |
| Altura | 3 (4 niveles) |
| Subarbol de Ventas | Ventas, Ventas Nacionales, Ventas Internacionales |
| Hermanos de Ventas | Marketing |
| Cadena de mando hasta Equipo Backend | Gerencia General - Gerencia Tecnologia - Desarrollo - Equipo Backend (3 aristas) |

### Tabla de metricas

| Nodo | Padre | Hijos | Grado | Nivel | Profundidad | Altura | Tipo | Hermanos |
|------|-------|-------|-------|-------|-------------|--------|------|----------|
| Gerencia General | - | Gerencia Tecnologia, Gerencia Comercial, Gerencia Administrativa | 3 | 1 | 0 | 3 | RAIZ | - |
| Gerencia Tecnologia | Gerencia General | Desarrollo, Infraestructura | 2 | 2 | 1 | 2 | INTERNO | Gerencia Comercial, Gerencia Administrativa |
| Desarrollo | Gerencia Tecnologia | Equipo Backend, Equipo Frontend | 2 | 3 | 2 | 1 | INTERNO | Infraestructura |
| Equipo Backend | Desarrollo | - | 0 | 4 | 3 | 0 | HOJA | Equipo Frontend |
| Equipo Frontend | Desarrollo | - | 0 | 4 | 3 | 0 | HOJA | Equipo Backend |
| Infraestructura | Gerencia Tecnologia | - | 0 | 3 | 2 | 0 | HOJA | Desarrollo |
| Gerencia Comercial | Gerencia General | Ventas, Marketing | 2 | 2 | 1 | 2 | INTERNO | Gerencia Tecnologia, Gerencia Administrativa |
| Ventas | Gerencia Comercial | Ventas Nacionales, Ventas Internacionales | 2 | 3 | 2 | 1 | INTERNO | Marketing |
| Ventas Nacionales | Ventas | - | 0 | 4 | 3 | 0 | HOJA | Ventas Internacionales |
| Ventas Internacionales | Ventas | - | 0 | 4 | 3 | 0 | HOJA | Ventas Nacionales |
| Marketing | Gerencia Comercial | - | 0 | 3 | 2 | 0 | HOJA | Ventas |
| Gerencia Administrativa | Gerencia General | Talento Humano, Contabilidad | 2 | 2 | 1 | 1 | INTERNO | Gerencia Tecnologia, Gerencia Comercial |
| Talento Humano | Gerencia Administrativa | - | 0 | 3 | 2 | 0 | HOJA | Contabilidad |
| Contabilidad | Gerencia Administrativa | - | 0 | 3 | 2 | 0 | HOJA | Talento Humano |

### Interpretacion en el contexto organizacional

- El **grado** de un nodo equivale al numero de subordinados directos de ese cargo.
  La Gerencia General tiene grado 3.
- La **profundidad** indica cuantos niveles de mando separan a un area de la Gerencia General.
- La **altura** del arbol (3) es la longitud de la cadena de mando mas larga.
- Una **hoja** es un area sin subordinados; un **nodo interno** es un area que coordina a otras.

## 6. Evidencia

| Evidencia solicitada | Donde esta |
|----------------------|------------|
| Taller resuelto | Este documento |
| Arbol disenado en Draw.io | `diagramas/diagramas_anatomia_arbol.drawio` (4 paginas) |
| Diagrama rotulado del arbol | Pagina 1 del `.drawio` |
| Tabla de metricas calculadas | Secciones 2 y 5 |
| Codigo Java con altura() | `src/MetricasArbol.java` y `src/NodoGeneral.java` |
| Captura de ejecucion | Pantallazo de la consola (referencia: `evidencia/salida_ejecucion.txt`) |
| Enlace GitHub | Repositorio `02-propiedades-arboles` |
