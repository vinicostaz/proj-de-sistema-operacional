package gui;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Painel simples de gráfico de barras usando Java2D (sem bibliotecas externas).
 * Use setData(...) e chame repaint().
 */
public class BarChartPanel extends JPanel {
    private Map<String, Integer> data = new LinkedHashMap<>();
    private String title = "Faltas de Página (menor é melhor)";

    public BarChartPanel() {
        setPreferredSize(new Dimension(600, 260));
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    public void setData(Map<String, Integer> data) {
        if (data == null) data = new LinkedHashMap<>();
        this.data = new LinkedHashMap<>(data);
        repaint();
    }

    public Map<String, Integer> getData() {
        return data;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics gOld) {
        super.paintComponent(gOld);
        Graphics2D g = (Graphics2D) gOld.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int pad = 40;

        // título
        g.setColor(Color.DARK_GRAY);
        g.setFont(getFont().deriveFont(Font.BOLD, 14f));
        g.drawString(title, pad, pad - 12);

        if (data == null || data.isEmpty()) {
            g.dispose();
            return;
        }

        // eixos
        g.setColor(Color.GRAY);
        g.drawLine(pad, pad, pad, h - pad);
        g.drawLine(pad, h - pad, w - pad, h - pad);

        int max = data.values().stream().mapToInt(Integer::intValue).max().orElse(1);
        int n = data.size();

        int availableW = w - 2 * pad;
        int availableH = h - 2 * pad;

        int gap = Math.max(8, availableW / (n * 6));
        int barW = Math.max(24, (availableW - (n + 1) * gap) / n);

        int x = pad + gap;
        g.setFont(getFont().deriveFont(12f));
        FontMetrics fm = g.getFontMetrics();

        for (Map.Entry<String, Integer> e : data.entrySet()) {
            int val = e.getValue();
            int barH = (int) ((val / (double) max) * (availableH - 16));
            int y = h - pad - barH;

            // barra
            g.setColor(new Color(80, 120, 200));
            g.fillRect(x, y, barW, barH);
            g.setColor(new Color(40, 40, 40));
            g.drawRect(x, y, barW, barH);

            // valor acima
            String vStr = String.valueOf(val);
            int vw = fm.stringWidth(vStr);
            g.drawString(vStr, x + (barW - vw) / 2, y - 4);

            // rótulo X
            String lbl = e.getKey();
            int tw = fm.stringWidth(lbl);
            g.drawString(lbl, x + (barW - tw) / 2, h - pad + fm.getAscent() + 2);

            x += barW + gap;
        }

        g.dispose();
    }
}
