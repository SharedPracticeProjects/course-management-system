package Utilities;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class GuiHelper {
    public static void tableAutoAdjustWidth(JTable table) {
        Printer.printStars();
        System.out.println("tableAutoAdjustWidth method called in GUIHelper");
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            System.out.println("**Entered column width for loop column " + column);
            // First set min width as header width
            Object value = table.getColumnModel().getColumn(column).getHeaderValue();
            Component c = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table, value, false, false, -1, column);
            int width = c.getPreferredSize().width + 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 15, Math.max(width, 50));
            }
            if (width > 300)
                width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        System.out.println("Successfully configured table to auto adjust width");
    }

    public static JPanel createSquareJPanel(int size) {
        JPanel tempPanel = new JPanel();
//        tempPanel.setBorder(BorderFactory.createLineBorder(Color.black));
//        tempPanel.setBackground(Color.RED);
        tempPanel.setMinimumSize(new Dimension(size, 30));
        tempPanel.setMaximumSize(new Dimension(size, 30));
        tempPanel.setPreferredSize(new Dimension(size, 30));
        return tempPanel;
    }

    public static void addBordersToPanel(JPanel panel, int rows) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < 12; i++) {
            JPanel box1;
            box1 = (i == 0 || i == 11) ? GuiHelper.createSquareJPanel(50) : GuiHelper.createSquareJPanel(100);
            gbc.gridx = i;
            gbc.gridy = 0;
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            panel.add(box1, gbc);
        }

        for (int i = 0; i < rows - 1; i++) {
            JPanel box1 = GuiHelper.createSquareJPanel(50);
            gbc.gridx = 0;
            gbc.gridy = i + 1;
            gbc.ipady = 0;
//            gbc.weighty = (i == 11) ? 0.5 : 1;
            gbc.fill = GridBagConstraints.VERTICAL;
            panel.add(box1, gbc);
        }
    }

}
