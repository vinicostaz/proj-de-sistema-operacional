# 🏫 UNIVERSIDADE DE FORTALEZA

**CENTRO DE CIÊNCIAS TECNOLÓGICAS**  
**CURSO: CIÊNCIA DA COMPUTAÇÃO**

## **SIMULADOR DE ALGORITMOS DE SUBSTITUIÇÃO DE PÁGINAS EM JAVA**

**Autor:** Vinicius Costa Fonseca  
**Professor:** Izequiel Norões

**Palavras-chave:** memória virtual. substituição de páginas. fifo. lru. relógio. ótimo.

---

## 📄 Resumo

Este trabalho apresenta o desenvolvimento de um simulador em Java para avaliar o desempenho de diferentes algoritmos de substituição de páginas utilizados em sistemas operacionais. O simulador permite execução em modo console e também em uma interface gráfica desenvolvida com Swing, que apresenta um gráfico comparativo de desempenho entre os algoritmos FIFO, LRU, Relógio e Ótimo. O objetivo é compreender, de forma prática, o impacto das políticas de substituição de páginas no desempenho da memória virtual.

---

## 🧩 Introdução

O gerenciamento de memória virtual é uma das funções fundamentais de um sistema operacional, pois controla o uso eficiente dos recursos de memória física e secundária. Quando ocorre uma falta de página, o sistema precisa escolher qual página substituir, sendo esse processo governado por algoritmos de substituição. Este trabalho propõe o desenvolvimento de um simulador em Java que implementa os principais algoritmos — FIFO, LRU, Relógio e Ótimo — e compara seus resultados, permitindo visualizar a eficiência de cada método.

---

## ⚙️ Metodologia

O simulador foi desenvolvido em linguagem Java (JDK 11+), com estrutura modular dividida em pacotes:  
**main**, **core**, **algoritmos** e **gui**.

No modo **console**, o programa solicita a quantidade de molduras (frames) e a sequência de páginas a serem acessadas.  
No modo **gráfico**, uma interface em **Swing** apresenta uma tabela e um gráfico de barras com as faltas de página de cada algoritmo.

### 💻 Procedimento para compilar e executar:

```bash
cd src
javac -d out core/*.java algoritmos/*.java gui/*.java main/Main.java
java -cp out main.Main --gui
```

---

## 📊 Resultados e Discussão

A sequência utilizada para os testes foi:

```
7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2
```

Os resultados obtidos demonstraram o seguinte desempenho:

| Algoritmo | Faltas de Página | Taxa de Acerto | Observações |
|------------|------------------|----------------|--------------|
| FIFO | 10 | 23,08% | Simples, mas pode sofrer anomalia de Belady |
| LRU | 9 | 30,77% | Boa eficiência prática |
| Relógio | 9 | 30,77% | Aproximação leve do LRU |
| Ótimo | 7 | 46,15% | Melhor desempenho teórico |

Os resultados confirmam que o algoritmo Ótimo obteve o melhor desempenho teórico, enquanto LRU e Relógio apresentaram resultados próximos, sendo alternativas eficientes para uso prático. O FIFO apresentou maior número de faltas devido à sua simplicidade.

---

## 🧠 Conclusão

O simulador desenvolvido cumpriu seu objetivo de permitir a análise comparativa dos principais algoritmos de substituição de páginas. Foi possível observar o impacto das estratégias de substituição no desempenho e compreender as diferenças entre abordagens teóricas e práticas. A interface gráfica contribuiu para uma visualização mais clara dos resultados e pode ser expandida para incluir outros algoritmos, como NFU e Aging.

---

## 📚 Referências

TANENBAUM, Andrew S.; BOS, Herbert. *Sistemas Operacionais Modernos*. 4. ed. São Paulo: Pearson, 2016.  
SILBERSCHATZ, Abraham; GALVIN, Peter B.; GAGNE, Greg. *Fundamentos de Sistemas Operacionais*. 9. ed. Rio de Janeiro: LTC, 2018.  
STALLINGS, William. *Operating Systems: Internals and Design Principles*. 9th ed. Pearson, 2018.  
DEV MEDIA. *Introdução à Interface GUI no Java (Swing)*. Disponível em: <https://www.devmedia.com.br/introducao-a-interface-gui-no-java/25646>. Acesso em: 30 out. 2025.  
PRIMEFACES. *PrimeFaces – Modern UI for JavaServer Faces*. Disponível em: <https://www.primefaces.org/>. Acesso em: 30 out. 2025.  
SDPM SIMULATOR. *Simulador de Políticas de Gerenciamento de Memória*. Disponível em: <https://sdpm-simulator.netlify.app>. Acesso em: 30 out. 2025.

---

📂 **Repositório GitHub:**  
[https://github.com/vinicostaz/proj-de-sistema-operacional](https://github.com/vinicostaz/proj-de-sistema-operacional)