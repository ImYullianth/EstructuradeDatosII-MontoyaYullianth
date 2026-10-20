# 03-arbol-binario-java

Laboratorio: **"Mi primer arbol binario"**
Asignatura: Estructura de Datos II - Ingenieria de Software - Semestre IV
Ecosistema: Java / GitHub

## Objetivo

Disenar e implementar de forma nativa un arbol binario en Java usando
referencias izquierda y derecha, con una clase generica `NodoArbol<T>`.

## Estructura del proyecto

```
03-arbol-binario-java/
|-- src/
|   |-- NodoArbol.java          (clase generica del nodo)
|   |-- ClasificadorArbol.java  (lleno, completo, perfecto, degenerado)
|   `-- Main.java               (construccion, pruebas y arbol de expresiones)
|-- diagramas/
|   `-- diagramas_arbol_binario.drawio   (4 paginas, abrir en draw.io)
|-- evidencia/
|   `-- salida_ejecucion.txt    (salida de la ejecucion)
|-- .gitignore
`-- README.md
```

## Como ejecutar

### En Visual Studio Code
1. Instalar el "Extension Pack for Java".
2. Abrir la carpeta `03-arbol-binario-java` (Archivo > Abrir carpeta).
3. Abrir `src/Main.java` y pulsar **Run** sobre el metodo `main`.

### Desde terminal
```
cd 03-arbol-binario-java
javac -d out src/*.java
java -cp out Main
```

## Diagramas (draw.io)

Abrir `diagramas/diagramas_arbol_binario.drawio` en https://app.diagrams.net
(Archivo > Abrir desde > Dispositivo). Contiene 4 paginas:

1. Clasificacion de arboles (lleno, completo, perfecto, degenerado)
2. Arbol de 7 nodos
3. Arbol de expresiones `(3 + 5) * (8 - 2)`
4. Modelo en memoria de los objetos y sus referencias

## Modelo en memoria

Cada `NodoArbol<T>` es un **objeto en el heap** con tres campos:

| Campo       | Tipo           | Que guarda                              |
|-------------|----------------|-----------------------------------------|
| `dato`      | `T`            | el valor del nodo                       |
| `izquierdo` | `NodoArbol<T>` | referencia al hijo izquierdo (o `null`) |
| `derecho`   | `NodoArbol<T>` | referencia al hijo derecho (o `null`)   |

Una variable `raiz` guarda la **referencia** al primer nodo. Desde ella se
llega a todos los demas siguiendo las referencias izquierda y derecha. Los
nodos no estan guardados juntos en memoria como un arreglo: estan dispersos
en el heap y conectados solo por referencias (estructura dinamica).

Encapsulamiento: los campos son `private`; solo se accede con getters y setters.

## Clasificacion de arboles binarios

| Tipo       | Definicion                                                        |
|------------|-------------------------------------------------------------------|
| Lleno      | Cada nodo tiene 0 o 2 hijos.                                      |
| Completo   | Todos los niveles se llenan de izquierda a derecha.               |
| Perfecto   | Todos los nodos internos tienen 2 hijos y las hojas estan al mismo nivel (nodos = 2^h - 1). |
| Degenerado | Cada nodo tiene un solo hijo; se parece a una lista enlazada.     |

Las clasificaciones **no son excluyentes**: todo arbol perfecto es tambien
lleno y completo.

## Respuestas a los ejercicios

**1. Arbol de expresiones simple.**
Se representa `(3 + 5) * (8 - 2)`: la raiz es `*`, sus hijos son `+` y `-`, y las
hojas son los operandos `3, 5, 8, 2`. Los operadores son nodos internos y los
operandos son hojas. Resultado: 48 (ver `Main.java`, parte 4).

**2. Clasificacion del arbol de 7 nodos.**
El arbol A..G (A con hijos B y C; B con D y E; C con F y G) es **lleno, completo y
perfecto**: todos los nodos internos tienen 2 hijos, los niveles se llenan de
izquierda a derecha y las 4 hojas estan en el mismo nivel (7 = 2^3 - 1).

**3. Diferencia entre una referencia null y un nodo hoja.**
- Una **referencia `null`** no apunta a ningun objeto: indica que ese hijo no existe.
  No tiene dato ni se le pueden llamar metodos (da `NullPointerException`).
- Un **nodo hoja** si es un objeto real, con su dato, cuyas dos referencias
  (`izquierdo` y `derecho`) valen `null`.

En resumen: la hoja existe y tiene dato; el `null` es la ausencia de nodo.

## Manejo de null

- Antes de leer `getDato()` de un hijo se valida `if (hijo != null)`.
- `imprimirRaizYHijos()` informa cuando un hijo no existe en vez de fallar.
- Los metodos de `ClasificadorArbol` y `evaluar` tratan el caso `null`
  (arbol vacio devuelve 0 nodos, altura 0, o lanza una excepcion clara).
- `Main` incluye una prueba que provoca y captura un `NullPointerException`
  para demostrar por que la validacion es necesaria.

## Evidencias

- Diagramas de clasificacion: `diagramas/diagramas_arbol_binario.drawio`
- Proyecto Java con `NodoArbol<T>`: carpeta `src/`
- Captura de ejecucion: tomar pantallazo de la consola de VS Code (referencia en
  `evidencia/salida_ejecucion.txt`)
- README con el modelo en memoria: este archivo

## Subir a GitHub

```
git init
git add .
git commit -m "Laboratorio 3: arbol binario con NodoArbol generico"
git branch -M main
git remote add origin https://github.com/TU_USUARIO/03-arbol-binario-java.git
git push -u origin main
```
