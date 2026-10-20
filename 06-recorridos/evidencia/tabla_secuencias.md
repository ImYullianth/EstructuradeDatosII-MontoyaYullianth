# Tabla de secuencias obtenidas

BST de 10 productos (orden de insercion: 50 30 70 40 20 60 80 35 90 65)

| Recorrido    | Orden de visita   | Secuencia obtenida                |
|--------------|-------------------|-----------------------------------|
| Preorden     | raiz - izq - der  | 50 30 20 40 35 70 60 65 80 90     |
| Inorden      | izq - raiz - der  | 20 30 35 40 50 60 65 70 80 90     |
| Postorden    | izq - der - raiz  | 20 35 40 30 65 60 90 80 70 50     |
| Por niveles  | cola (BFS)        | 50 30 70 20 40 60 80 35 65 90     |

Inorden == lista ordenada con Collections.sort: **true**
