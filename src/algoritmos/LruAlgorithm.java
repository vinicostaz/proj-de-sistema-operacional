package algoritmos;

import core.PageReplacementAlgorithm;
import core.SimulationResult;
import java.util.*;

/**
 * Algoritmo LRU (Least Recently Used)
 * Substitui a página menos recentemente utilizada.
 */
public class LruAlgorithm implements PageReplacementAlgorithm {

    @Override
    public String getName() {
        return "LRU";
    }

    @Override
    public SimulationResult simulate(List<Integer> refs, int frames, boolean trace) {
        Set<Integer> inMemory = new HashSet<>();
        Map<Integer, Integer> lastUse = new HashMap<>();
        int faults = 0;
        StringBuilder sb = new StringBuilder();

        if (trace) sb.append("\n[LRU] Trace\n");

        for (int i = 0; i < refs.size(); i++) {
            int ref = refs.get(i);
            boolean hit = inMemory.contains(ref);

            if (!hit) {
                faults++;
                if (inMemory.size() < frames) {
                    inMemory.add(ref);
                } else {
                    // encontra o menos recentemente usado
                    int victim = -1;
                    int oldestUse = Integer.MAX_VALUE;
                    for (int page : inMemory) {
                        int last = lastUse.getOrDefault(page, -1);
                        if (last < oldestUse) {
                            oldestUse = last;
                            victim = page;
                        }
                    }
                    inMemory.remove(victim);
                    inMemory.add(ref);
                }
            }

            lastUse.put(ref, i);

            if (trace) {
                sb.append(String.format("Ref=%d | Mem=%s | %s | Faltas=%d%n",
                        ref, inMemory.toString(), hit ? "HIT" : "FAULT *", faults));
            }
        }

        return new SimulationResult(getName(), faults, refs.size(), sb.toString());
    }
}
