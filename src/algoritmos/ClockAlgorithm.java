package algoritmos;

import core.PageReplacementAlgorithm;
import core.SimulationResult;
import java.util.*;

/**
 * Algoritmo do Relógio (Second-Chance)
 * Variação do FIFO com bit de segunda chance.
 */
public class ClockAlgorithm implements PageReplacementAlgorithm {

    @Override
    public String getName() {
        return "Relógio";
    }

    @Override
    public SimulationResult simulate(List<Integer> refs, int frames, boolean trace) {
        int[] memory = new int[frames];
        boolean[] useBit = new boolean[frames];
        Arrays.fill(memory, -1);
        int pointer = 0;
        int faults = 0;
        StringBuilder sb = new StringBuilder();

        if (trace) sb.append("\n[Relógio] Trace\n");

        for (int ref : refs) {
            boolean hit = false;

            // verifica se a página já está na memória
            for (int i = 0; i < frames; i++) {
                if (memory[i] == ref) {
                    hit = true;
                    useBit[i] = true;
                    break;
                }
            }

            if (!hit) {
                faults++;

                // substituição com segunda chance
                while (true) {
                    if (memory[pointer] == -1) {
                        memory[pointer] = ref;
                        useBit[pointer] = true;
                        pointer = (pointer + 1) % frames;
                        break;
                    }

                    if (!useBit[pointer]) {
                        memory[pointer] = ref;
                        useBit[pointer] = true;
                        pointer = (pointer + 1) % frames;
                        break;
                    } else {
                        useBit[pointer] = false;
                        pointer = (pointer + 1) % frames;
                    }
                }
            }

            if (trace) {
                sb.append(String.format("Ref=%d | Mem=%s | %s | Faltas=%d%n",
                        ref, Arrays.toString(memory), hit ? "HIT" : "FAULT *", faults));
            }
        }

        return new SimulationResult(getName(), faults, refs.size(), sb.toString());
    }
}
