# 06-recorridos

Guia 6: **Recorridos en arboles binarios**
Laboratorio de Moodle: **"Eliminar sin destruir el arbol"**
Asignatura: Estructura de Datos II - Ingenieria de Software / Sistemas - Semestre IV
Ecosistema: Java / GitHub

## Objetivo

Implementar y comparar los recorridos Preorden, Inorden, Postorden y por niveles
sobre un BST, y ademas implementar `eliminar()` con sus tres casos
(hoja, un hijo y dos hijos) sin destruir el resto del arbol.

## Estructura del proyecto

```
06-recorridos/
|-- src/
|   |-- NodoArbol.java      (nodo generico con referencias izquierda y derecha)
|   |-- ArbolBST.java       (insertar, buscar, eliminar, vaciar, dibujar)
|   |-- Recorridos.java     (preorden, inorden, postorden, porNiveles)
|   |-- Pruebas.java        (31 pruebas verificables)
|   `-- Main.java           (demostracion completa)
|-- diagramas/
|   `-- diagramas_recorridos_y_eliminacion.drawio   (5 paginas, abrir en draw.io)
|-- evidencia/
|   |-- salida_ejecucion.txt
|   `-- tabla_secuencias.md
|-- .gitignore
`-- README.md
```

## Como ejecutar

### En Visual Studio Code
1. Instalar el "Extension Pack for Java".
2. Abrir la carpeta `06-recorridos`.
3. Abrir `src/Main.java` y pulsar **Run** sobre `main`.
4. Tomar captura de la consola (esa es la captura de ejecucion).

### Desde terminal
```
javac -d out src/*.java
java -cp out Main
```

## Diagramas (draw.io)

Abrir `diagramas/diagramas_recorridos_y_eliminacion.drawio` en https://app.diagrams.net.
Paginas:

1. BST de 10 productos
2. Los cuatro recorridos (con el numero de orden de visita en cada nodo)
3. Caso 1: eliminar hoja (antes / despues)
4. Caso 2: eliminar nodo con un hijo (antes / despues)
5. Caso 3: eliminar nodo con dos hijos (antes / despues)

## El BST de 10 productos

La clave de cada nodo es el codigo del producto.

| Codigo | Producto   | Codigo | Producto   |
|--------|------------|--------|------------|
| 20     | Cable USB  | 60     | Impresora  |
| 30     | Mouse      | 65     | Router     |
| 35     | Webcam     | 70     | Teclado    |
| 40     | Monitor    | 80     | Audifonos  |
| 50     | Laptop     | 90     | Tablet     |

Orden de insercion: `50 30 70 40 20 60 80 35 90 65` (10 nodos, altura 4).

```
                    90
              80
        70
                    65
              60
  50                          (raiz a la izquierda, derecha arriba)
              40
                    35
        30
              20
```

## Tabla de secuencias obtenidas

| Recorrido   | Orden de visita  | Secuencia                     |
|-------------|------------------|-------------------------------|
| Preorden    | raiz - izq - der | 50 30 20 40 35 70 60 65 80 90 |
| Inorden     | izq - raiz - der | 20 30 35 40 50 60 65 70 80 90 |
| Postorden   | izq - der - raiz | 20 35 40 30 65 60 90 80 70 50 |
| Por niveles | cola (BFS)       | 50 30 70 20 40 60 80 35 65 90 |

## Casos de uso de cada recorrido

| Recorrido   | Caso de uso apropiado |
|-------------|-----------------------|
| Preorden    | **Copiar o serializar** un arbol: al visitar primero la raiz, se puede reconstruir el mismo arbol insertando las claves en ese orden. Tambien sirve para imprimir una estructura jerarquica (carpetas, menus). |
| Inorden     | **Listar los datos ordenados**. En un BST produce las claves de menor a mayor, por ejemplo un catalogo de productos por codigo, o validar que un arbol es un BST. |
| Postorden   | **Liberar o eliminar el arbol completo** y evaluar arboles de expresion: los hijos se procesan antes que el padre. |
| Por niveles | **Procesar por cercania a la raiz**: nivel de jerarquia, camino mas corto en numero de aristas, imprimir el arbol por pisos. Usa una cola. |

## Interpretacion de las salidas

- **Inorden = lista ordenada.** La salida del inorden coincide con la lista de
  codigos ordenada con `Collections.sort` (el programa lo verifica). Esto ocurre
  porque en un BST todo lo de la izquierda es menor y todo lo de la derecha es
  mayor: visitar izquierda - raiz - derecha recorre las claves en orden.
- **Preorden empieza siempre en la raiz** (50) y **postorden termina siempre en la
  raiz** (50).
- **Por niveles** muestra la forma del arbol: 50 | 30 70 | 20 40 60 80 | 35 65 90.
  Es el unico recorrido que usa una cola en lugar de recursion.
- **Arbol degenerado:** si se insertan 1,2,3,4,5 en orden, preorden, inorden y
  niveles dan lo mismo (1 2 3 4 5) y postorden da 5 4 3 2 1. Esto confirma que un
  BST degenerado se comporta como una lista.

## Laboratorio: eliminar sin destruir el arbol

`ArbolBST.eliminar(int clave)` busca la clave y aplica uno de tres casos. El metodo
recursivo devuelve la nueva raiz del subarbol y el padre re-enlaza su referencia,
asi el resto del arbol queda intacto y sigue siendo un BST valido.

| Caso | Situacion | Que se hace | Ejemplo |
|------|-----------|-------------|---------|
| 1 | Nodo hoja | El padre apunta a `null`. | Eliminar 20: el nodo 30 queda sin hijo izquierdo. |
| 2 | Un hijo | El hijo ocupa el lugar del nodo eliminado. | Eliminar 60: el 65 pasa a ser hijo izquierdo de 70. |
| 3 | Dos hijos | Se copia el **sucesor inorden** (minimo del subarbol derecho) en el nodo y se elimina el sucesor original. | Eliminar 50 (raiz): el sucesor es 60, la nueva raiz es 60 y el 65 sube. |

Los diagramas antes/despues de los tres casos estan en las paginas 3, 4 y 5 del
archivo `.drawio`. La salida del programa tambien muestra el arbol antes y
despues de cada eliminacion.

## Respuestas a los ejercicios

**1. Arbol de 10 productos por codigo.** Construido en `Main.construirArbolProductos()`
insertando los codigos `50 30 70 40 20 60 80 35 90 65`.

**2. Comparar el inorden con una lista ordenada.** Son identicas
(`20 30 35 40 50 60 65 70 80 90`). El programa lo comprueba con `equals` y lo imprime
en la parte 3.

**3. Que recorrido usar para eliminar un arbol completo de forma segura.**
**Postorden** (izquierda - derecha - raiz). Se liberan primero los dos hijos y al final
el padre. Si se eliminara primero el padre (preorden), se perderia la referencia a
sus hijos antes de poder liberarlos y quedarian subarboles sin alcanzar. Con postorden
cada nodo se libera cuando todo su subarbol ya fue liberado. Esta implementado en
`ArbolBST.vaciar()`, que ademas pone las referencias en `null`; la prueba verifica
que el orden de liberacion es `20 35 40 30 65 60 90 80 70 50` (el postorden).

## Pruebas

`Pruebas.java` ejecuta 31 verificaciones que se imprimen como `[OK]` o `[FALLO]`:

- Las cuatro secuencias de recorrido contra los valores esperados.
- Inorden contra `Collections.sort`.
- Casos borde: arbol vacio, un solo nodo, arbol degenerado y claves repetidas.
- Los tres casos de eliminacion, mas clave inexistente, arbol vacio y raiz unica.
- Una secuencia de eliminaciones donde el inorden debe seguir ordenado.
- `vaciar()` en orden postorden.

Resultado: **31 correctas, 0 fallidas.**

## Evidencia para Moodle / GitHub

- [x] Codigo de los cuatro recorridos: `src/Recorridos.java`
- [x] Codigo del metodo `eliminar()`: `src/ArbolBST.java`
- [x] Tres diagramas antes/despues: `diagramas/` (paginas 3, 4 y 5)
- [x] Tabla con secuencias: este README y `evidencia/tabla_secuencias.md`
- [x] Capturas de ejecucion: pantallazo de la consola (referencia: `evidencia/salida_ejecucion.txt`)
- [x] README con casos de uso: este archivo
- [ ] Commit en GitHub (ver abajo)

## Subir a GitHub

```
git init
git add .
git commit -m "Guia 6: recorridos en arbol binario y eliminacion en BST"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/06-recorridos.git
git push -u origin main
```
