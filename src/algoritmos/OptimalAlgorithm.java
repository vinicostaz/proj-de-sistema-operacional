package algoritmos;

import core.PageReplacementAlgorithm;
import core.SimulationResult;
import java.util.*;

/**
 * Algoritmo Ótimo (Optimal)
 * Substitui a página que será usada mais tarde no futuro.
 */
public class OptimalAlgorithm implements PageReplacementAlgorithm {

    @Override
    public String getName() {
        return "Ótimo";
    }

    @Override
    public SimulationResult simulate(List<Integer> refs, int frames, boolean trace) {
        List<Integer> memory = new ArrayList<>();
        int faults = 0;
        StringBuilder sb = new StringBuilder();

        if (trace) sb.append("\n[Ótimo] Trace\n");

        for (int i = 0; i < refs.size(); i++) {
            int ref = refs.get(i);
            boolean hit = memory.contains(ref);

            if (!hit) {
                faults++;
                if (memory.size() < frames) {
                    memory.add(ref);
                } else {
                    int victimIndex = findVictim(memory, refs, i + 1);
                    memory.set(victimIndex, ref);
                }
            }

            if (trace) {
                sb.append(String.format("Ref=%d | Mem=%s | %s | Faltas=%d%n",
                        ref, memory.toString(), hit ? "HIT" : "FAULT *", faults));
            }
        }

        return new SimulationResult(getName(), faults, refs.size(), sb.toString());
    }

    private int findVictim(List<Integer> memory, List<Integer> refs, int fromIndex) {
        int farthest = -1;
        int victim = 0;
        for (int i = 0; i < memory.size(); i++) {
            int page = memory.get(i);
            int nextUse = Integer.MAX_VALUE;

            for (int j = fromIndex; j < refs.size(); j++) {
                if (refs.get(j) == page) {
                    nextUse = j;
                    break;
                }
            }

            if (nextUse == Integer.MAX_VALUE) {
                return i; // nunca mais usada
            }

            if (nextUse > farthest) {
                farthest = nextUse;
                victim = i;
            }
        }
        return victim;
    }
}
