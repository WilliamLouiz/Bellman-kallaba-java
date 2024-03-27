
package bellmankallaba;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author LENOVO
 */
public class GraphFrame extends javax.swing.JFrame {

    private GraphPanel graphPanel;

    public GraphFrame() {
        graphPanel = new GraphPanel();
            

        // Création des nœuds initiaux
                 Noeuds node1 = new Noeuds(100, 400, 0);
                 Noeuds node2 = new Noeuds(200, 400, 1);
                 Noeuds node3 = new Noeuds(300, 200, 2);
                 Noeuds node4 = new Noeuds(600, 200, 3);
                 Noeuds node5 = new Noeuds(450, 400, 4);
                 Noeuds node6 = new Noeuds(300, 600, 5);
                 Noeuds node7 = new Noeuds(600, 600, 6);
                 Noeuds node8 = new Noeuds(700, 400, 7);
                 Noeuds node9 = new Noeuds(800, 400, 8);
                 
                 Connection connection1 = new Connection(node1, node2, "5");
                 Connection connection2 = new Connection(node2, node3, "3");
                 Connection connection3 = new Connection(node3, node4, "4");
                 Connection connection4 = new Connection(node3, node5, "2");
                 Connection connection5 = new Connection(node2, node5, "8");
                 Connection connection6 = new Connection(node2, node6, "6");
                 Connection connection7 = new Connection(node6, node7, "2");
                 Connection connection8 = new Connection(node5, node8, "3");
                 Connection connection9 = new Connection(node4, node8, "7");
                 Connection connection10 = new Connection(node7, node8, "4");
                 Connection connection11 = new Connection(node8, node9, "5");
                 
                 graphPanel.addNode(node1);
                 graphPanel.addNode(node2);
                 graphPanel.addNode(node3);
                 graphPanel.addNode(node4);
                 graphPanel.addNode(node5);
                 graphPanel.addNode(node6);
                 graphPanel.addNode(node7);
                 graphPanel.addNode(node8);
                 graphPanel.addNode(node9);
                 
                 graphPanel.addConnection(connection1);
                 graphPanel.addConnection(connection2);
                 graphPanel.addConnection(connection3);
                 graphPanel.addConnection(connection4);
                 graphPanel.addConnection(connection5);
                 graphPanel.addConnection(connection6);
                 graphPanel.addConnection(connection7);
                 graphPanel.addConnection(connection8);
                 graphPanel.addConnection(connection9);
                 graphPanel.addConnection(connection10);
                 graphPanel.addConnection(connection11);

        // Configuration du bouton "Ajouter un nœud"
        

        // Configuration de la fenêtre
        setTitle("Graphe de Bellman-Kalaba");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 900);
        setLayout(new BorderLayout());
        add(graphPanel, BorderLayout.CENTER);
        setVisible(true);
        setResizable(false);
            // Création du panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        // Création du panneau de boutons
        ButtonPanel buttonPanel = new ButtonPanel();

        // Ajout du panneau de boutons au panneau principal (en haut)
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        // Ajout du panneau de graphiques au panneau principal (au centre)
        mainPanel.add(graphPanel, BorderLayout.CENTER);

        // Réglage de la couleur de fond du panneau de graphiques
        graphPanel.setBackground(Color.LIGHT_GRAY);

        // Affichage de la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GraphFrame::new);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 916, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 734, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
