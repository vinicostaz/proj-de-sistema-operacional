package gui;

import algoritmos.*;
import core.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Janela Swing para executar os algoritmos e exibir resumo + gráfico comparativo.
 * Integra com core.Simulator e algos/*.
 */
public class SimulatorFrame extends JFrame {
    private final JTextField framesField = new JTextField("3");
    private final JTextField seqField = new JTextField("7,0,1,2,0,3,0,4,2,3,0,3,2");
    private final JCheckBox traceCheck = new JCheckBox("Exibir trace (detalhado)");
    private final JTextArea traceArea = new JTextArea(10, 80);
    private final JTable summaryTable = new JTable();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Algoritmo", "Faltas", "Taxa de acerto (%)"}, 0
    );
    private final BarChartPanel chartPanel = new BarChartPanel();

    public SimulatorFrame() {
        super("Simulador de Substituição de Páginas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 680);
        setLocationRelativeTo(null);

        // Top: inputs
        JPanel inputs = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        int row = 0;
        c.gridx = 0; c.gridy = row; inputs.add(new JLabel("Frames:"), c);
        framesField.setColumns(5);
        c.gridx = 1; c.gridy = row; inputs.add(framesField, c);

        row++;
        c.gridx = 0; c.gridy = row; inputs.add(new JLabel("Sequência:"), c);
        seqField.setColumns(40);
        c.gridx = 1; c.gridy = row; inputs.add(seqField, c);

        row++;
        c.gridx = 1; c.gridy = row; inputs.add(traceCheck, c);

        row++;
        JButton runBtn = new JButton(new RunAction("Executar"));
        JButton exportPngBtn = new JButton(new ExportChartAction("Exportar gráfico (PNG)"));
        JPanel btnBox = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        btnBox.add(runBtn);
        btnBox.add(exportPngBtn);
        c.gridx = 1; c.gridy = row; inputs.add(btnBox, c);

        // Center: split -> left (trace), right (chart + table)
        traceArea.setEditable(false);
        traceArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane traceScroll = new JScrollPane(traceArea);
        traceScroll.setBorder(BorderFactory.createTitledBorder("Trace de execução"));

        JPanel right = new JPanel(new BorderLayout(8, 8));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Gráfico comparativo"));
        right.add(chartPanel, BorderLayout.CENTER);

        summaryTable.setModel(tableModel);
        summaryTable.setFillsViewportHeight(true);
        JScrollPane tableScroll = new JScrollPane(summaryTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Resumo"));
        tableScroll.setPreferredSize(new Dimension(400, 160));
        right.add(tableScroll, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, traceScroll, right);
        split.setResizeWeight(0.55);

        // Layout principal
        JPanel root = new JPanel(new BorderLayout(8, 8));
        root.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        root.add(inputs, BorderLayout.NORTH);
        root.add(split, BorderLayout.CENTER);

        setContentPane(root);
    }

    // Ação: Executar simulação
    private class RunAction extends AbstractAction {
        public RunAction(String name) { super(name); }

        @Override
        public void actionPerformed(ActionEvent e) {
            int frames;
            try {
                frames = Integer.parseInt(framesField.getText().trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(SimulatorFrame.this, "Frames inválidos.");
                return;
            }
            List<Integer> refs = ReferenceParser.parse(seqField.getText());
            if (frames <= 0 || refs.isEmpty()) {
                JOptionPane.showMessageDialog(SimulatorFrame.this, "Informe frames > 0 e uma sequência válida.");
                return;
            }

            boolean trace = traceCheck.isSelected();

            List<PageReplacementAlgorithm> algos = List.of(
                    new FifoAlgorithm(),
                    new LruAlgorithm(),
                    new ClockAlgorithm(),
                    new OptimalAlgorithm()
            );

            Simulator simulator = new Simulator(algos);
            List<SimulationResult> results = simulator.runAll(refs, frames, trace);

            // Trace
            traceArea.setText("");
            if (trace) {
                StringBuilder sb = new StringBuilder();
                for (SimulationResult r : results) {
                    if (!r.getTrace().isEmpty()) {
                        sb.append(r.getTrace()).append("\n");
                    }
                }
                traceArea.setText(sb.toString());
                traceArea.setCaretPosition(0);
            }

            // Tabela resumo
            tableModel.setRowCount(0);
            Map<String, Integer> chartData = new LinkedHashMap<>();
            for (SimulationResult r : results) {
                double hitRatePct = r.getHitRate() * 100.0;
                tableModel.addRow(new Object[]{
                        r.getAlgorithmName(),
                        r.getFaults(),
                        String.format("%.2f", hitRatePct)
                });
                chartData.put(r.getAlgorithmName(), r.getFaults());
            }
            chartPanel.setData(chartData);
        }
    }

    // Ação: exportar gráfico como PNG
    private class ExportChartAction extends AbstractAction {
        public ExportChartAction(String name) { super(name); }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Salvar gráfico como PNG");
            chooser.setSelectedFile(new File("grafico_faltas.png"));
            int res = chooser.showSaveDialog(SimulatorFrame.this);
            if (res != JFileChooser.APPROVE_OPTION) return;

            File file = chooser.getSelectedFile();
            try {
                BufferedImage image = new BufferedImage(
                        chartPanel.getWidth(),
                        chartPanel.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2 = image.createGraphics();
                chartPanel.paint(g2);
                g2.dispose();
                ImageIO.write(image, "png", file);
                JOptionPane.showMessageDialog(SimulatorFrame.this, "Gráfico salvo em: " + file.getAbsolutePath());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(SimulatorFrame.this, "Erro ao salvar PNG: " + ex.getMessage());
            }
        }
    }
}
