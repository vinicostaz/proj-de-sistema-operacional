# 🧩 Simulador de Algoritmos de Substituição de Páginas (Java)

Este projeto implementa um **simulador didático** em Java para avaliar o desempenho de diferentes algoritmos de substituição de páginas em sistemas de memória virtual.  
O sistema pode ser executado em **modo console** ou com **interface gráfica (Swing)**, incluindo um **gráfico comparativo** de desempenho (+1 ponto extra).

---

## 📚 Algoritmos Implementados

- **FIFO (First In, First Out)** — substitui a página mais antiga na memória.  
- **LRU (Least Recently Used)** — substitui a página menos recentemente usada.  
- **Relógio (Second-Chance)** — variação do FIFO com bit de segunda chance.  
- **Ótimo (Optimal)** — substitui a página que será usada mais tarde no futuro (referência teórica).

---

## ⚙️ Como Compilar e Executar

> 💡 Execute os comandos dentro da pasta **`src/`** do projeto.

### 🖥️ Compilação (gera classes em `out/`)
```bash
javac -d out core/*.java algoritmos/*.java gui/*.java main/Main.java
```

▶️ Modo Console

```bash
java -cp out main.Main
```

🧾 Modo Detalhado (trace)
```bash
java -cp out main.Main --trace
```

🎨 Modo Gráfico (GUI)
```bash
java -cp out main.Main --gui
```
