package algoritmos;

import core.PageReplacementAlgorithm;
import core.SimulationResult;
import java.util.*;

/**
 * Algoritmo FIFO (First In, First Out)
 * A página mais antiga na memória é substituída primeiro.
 */
public class FifoAlgorithm implements PageReplacementAlgorithm {

    @Override
    public String getName() {
        return "FIFO";
    }

    @Override
    public SimulationResult simulate(List<Integer> refs, int frames, boolean trace) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> inMemory = new HashSet<>();
        int faults = 0;
        StringBuilder sb = new StringBuilder();

        if (trace) sb.append("\n[FIFO] Trace\n");

        for (int ref : refs) {
            boolean hit = inMemory.contains(ref);
            if (!hit) {
                faults++;
                if (inMemory.size() < frames) {
                    inMemory.add(ref);
                    queue.add(ref);
                } else {
                    int victim = queue.poll();
                    inMemory.remove(victim);
                    inMemory.add(ref);
                    queue.add(ref);
                }
            }

            if (trace) {
                sb.append(String.format("Ref=%d | Mem=%s | %s | Faltas=%d%n",
                        ref, inMemory.toString(), hit ? "HIT" : "FAULT *", faults));
            }
        }

        return new SimulationResult(getName(), faults, refs.size(), sb.toString());
    }
}
