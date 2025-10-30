package core;

import java.util.List;

/**
 * Interface para qualquer algoritmo de substituição de páginas.
 * Cada algoritmo deve implementar o método simulate() e retornar
 * um SimulationResult com as estatísticas e (opcionalmente) o trace.
 */
public interface PageReplacementAlgorithm {
    /**
     * Executa o algoritmo de substituição de páginas.
     *
     * @param references lista de páginas referenciadas
     * @param frames quantidade de molduras de página disponíveis
     * @param trace se true, gera o log detalhado do passo a passo
     * @return resultado da simulação (faltas, taxa de acerto, trace)
     */
    SimulationResult simulate(List<Integer> references, int frames, boolean trace);

    /**
     * Nome legível do algoritmo (ex: "FIFO", "LRU", etc).
     */
    String getName();
}
