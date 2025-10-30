package core;

/**
 * Representa o resultado de uma simulação de algoritmo de substituição de páginas.
 */
public class SimulationResult {
    private final String algorithmName;
    private final int faults;
    private final int totalReferences;
    private final String trace;

    public SimulationResult(String algorithmName, int faults, int totalReferences, String trace) {
        this.algorithmName = algorithmName;
        this.faults = faults;
        this.totalReferences = totalReferences;
        this.trace = trace == null ? "" : trace;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public int getFaults() {
        return faults;
    }

    public int getTotalReferences() {
        return totalReferences;
    }

    public String getTrace() {
        return trace;
    }

    public double getHitRate() {
        if (totalReferences == 0) return 0.0;
        return 1.0 - (faults / (double) totalReferences);
    }

    @Override
    public String toString() {
        return String.format(
                "- %-10s: %d faltas de página | taxa de acerto: %.2f%%",
                algorithmName, faults, getHitRate() * 100
        );
    }
}
