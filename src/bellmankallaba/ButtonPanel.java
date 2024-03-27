
package bellmankallaba;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ButtonPanel extends javax.swing.JPanel {
    private JLabel titre;

    public ButtonPanel() {
        // Configuration du layout du panneau de boutons
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBackground(Color.DARK_GRAY);
        titre = new JLabel("BELLMAN KALLABA");
        titre.setForeground(Color.white);
        add(titre);
    }

    public void addButton(JButton button) {
        add(button);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 895, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
