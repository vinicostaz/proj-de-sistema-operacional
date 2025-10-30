package core;

import java.util.*;

/**
 * Classe responsável por orquestrar a execução de múltiplos algoritmos
 * e gerar o resumo consolidado dos resultados.
 */
public class Simulator {
    private final List<PageReplacementAlgorithm> algorithms;

    public Simulator(List<PageReplacementAlgorithm> algorithms) {
        this.algorithms = algorithms;
    }

    /**
     * Executa todos os algoritmos registrados.
     */
    public List<SimulationResult> runAll(List<Integer> references, int frames, boolean trace) {
        List<SimulationResult> results = new ArrayList<>();
        for (PageReplacementAlgorithm algo : algorithms) {
            SimulationResult result = algo.simulate(references, frames, trace);
            results.add(result);
        }
        return results;
    }

    /**
     * Imprime um resumo simples no console.
     */
    public void printSummary(List<SimulationResult> results) {
        System.out.println("\n========= RESUMO =========");
        for (SimulationResult r : results) {
            System.out.println(r.toString());
        }
    }
}
