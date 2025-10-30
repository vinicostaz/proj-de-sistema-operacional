package main;

import core.*;
import algoritmos.*;

import javax.swing.*;
import java.util.*;

/**
 * Ponto de entrada do Simulador de Substituição de Páginas.
 * Executa os algoritmos via console ou abre a interface gráfica (--gui).
 */
public class Main {

    public static void main(String[] args) {
        boolean trace = false;
        boolean useGui = false;

        // Flags opcionais
        for (String arg : args) {
            if ("--trace".equalsIgnoreCase(arg)) trace = true;
            if ("--gui".equalsIgnoreCase(arg)) useGui = true;
        }

        if (useGui) {
            // GUI opcional (+1 ponto)
            try {
                Class<?> guiClass = Class.forName("gui.SimulatorFrame");
                JFrame frame = (JFrame) guiClass.getDeclaredConstructor().newInstance();
                frame.setVisible(true);
            } catch (Exception e) {
                System.err.println("Erro ao iniciar GUI: " + e.getMessage());
            }
            return;
        }

        // === MODO CONSOLE ===
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Simulador de Substituição de Páginas ===");
        System.out.print("Informe a quantidade de frames: ");
        int frames = readIntSafe(sc);

        System.out.print("Informe a sequência de páginas (ex: 7,0,1,2,0,3,0,4,2,3,0,3,2): ");
        String input = sc.nextLine().trim();
        List<Integer> refs = ReferenceParser.parse(input);

        if (frames <= 0 || refs.isEmpty()) {
            System.out.println("❌ Entrada inválida. Encerrando programa.");
            return;
        }

        List<PageReplacementAlgorithm> algos = List.of(
                new FifoAlgorithm(),
                new LruAlgorithm(),
                new ClockAlgorithm(),
                new OptimalAlgorithm()
        );

        Simulator simulator = new Simulator(algos);
        List<SimulationResult> results = simulator.runAll(refs, frames, trace);

        // Se o modo trace estiver ligado, imprime logs
        if (trace) {
            for (SimulationResult r : results) {
                if (!r.getTrace().isEmpty()) {
                    System.out.println(r.getTrace());
                }
            }
        }

        simulator.printSummary(results);
    }

    private static int readIntSafe(Scanner sc) {
        int val = -1;
        String s = sc.nextLine();
        try {
            val = Integer.parseInt(s.trim());
        } catch (Exception ignored) {
        }
        return val;
    }
}
