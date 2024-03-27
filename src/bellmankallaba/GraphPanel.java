/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellmankallaba;

import java.awt.AWTEventMulticaster;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

/**
 *
 * @author LENOVO
 */
public class GraphPanel extends javax.swing.JPanel {

    private List<Noeuds> nodes;
    private List<Connection> connections;
    private JButton addButton;
    private JButton deleteButton;
    private JButton modifyButton;
    private JButton findPathButton;
    private Noeuds startNode;
    private Noeuds selectedNode;
    private Connection selectedConnection;
    private int offsetX;
    private int offsetY;
    private List<Noeuds> optimalPath;
    private JButton findMinimalPathButton;
    private JButton findMaximalPathButton;
    private JButton moveButton;
    private boolean isNodeMovementEnabled = false;



    public GraphPanel() {
        nodes = new ArrayList<>();
        connections = new ArrayList<>();
        setPreferredSize(new Dimension(400, 400));
        
        addButton = new JButton("Ajouter une flèche");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNode = null;
                addArrow();
            }
        });
        add(addButton);

        deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem();
            }
        });
        add(deleteButton);

        modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifySelectedItem();
            }
        });
        
        add(modifyButton);
        
        moveButton = new JButton("Déplacer");
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableNodeMovement();
            }
        });
        add(moveButton);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    createNode(e.getX(), e.getY());
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    selectedNode = findNode(e.getX(), e.getY());
                    selectedConnection = findConnection(e.getX(), e.getY());
                    repaint();
                }
            }

            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    selectedNode = findNode(e.getX(), e.getY());
                    selectedConnection = findConnection(e.getX(), e.getY());
                    offsetX = e.getX();
                    offsetY = e.getY();
                    repaint();
                } else if (isNodeMovementEnabled) {
                    moveSelectedItem(e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                selectedNode = null;
                selectedConnection = null;
                repaint();
            }
        });
        
        findMinimalPathButton = new JButton("Calculer le chemin minimal");
        findMinimalPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMinimalPath();
            }
        });
        add(findMinimalPathButton);
        findMaximalPathButton = new JButton("Calculer le chemin maximal");
        findMaximalPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateMaximalPath();
            }
        });
        add(findMaximalPathButton);


        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedNode != null) {
                    int newX = selectedNode.getX() + e.getX() - offsetX;
                    int newY = selectedNode.getY() + e.getY() - offsetY;
                    selectedNode.setPosition(newX, newY);
                    offsetX = e.getX();
                    offsetY = e.getY();
                    repaint();
                }
            }
        });
    }
    //pour ectiver/desactiver le mode de déplacement
    void enableNodeMovement() {
        isNodeMovementEnabled = !isNodeMovementEnabled;
        if (isNodeMovementEnabled) {
            moveButton.setText("Terminer le déplacement");
        } else {
            moveButton.setText("Déplacer");
        }
    }

    
     private void handleRightClick(MouseEvent e) {
        Noeuds clickedNode = findNode(e.getX(), e.getY());
        Connection clickedConnection = findConnection(e.getX(), e.getY());

        if (clickedNode != null) {
            showNodePopupMenu(clickedNode, e.getX(), e.getY());
        } else if (clickedConnection != null) {
            showConnectionPopupMenu(clickedConnection, e.getX(), e.getY());
        }
    }
    
    private void showNodePopupMenu(Noeuds node, int x, int y) {
        // Créer un menu contextuel pour le nœud
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Supprimer le nœud");
        JMenuItem modifyMenuItem = new JMenuItem("Modifier l'identifiant");
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteNode(node);
            }
        });
        modifyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyNode(node);
            }
        });
        popupMenu.add(deleteMenuItem);
        popupMenu.add(modifyMenuItem);
        popupMenu.show(this, x, y);
    }
    
    private void showConnectionPopupMenu(Connection connection, int x, int y) {
        // Créer un menu contextuel pour la connexion
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteMenuItem = new JMenuItem("Supprimer la connexion");
        JMenuItem modifyMenuItem = new JMenuItem("Modifier le coût");
        deleteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteConnection(connection);
            }
        });
        modifyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyConnection(connection);
            }
        });
        popupMenu.add(deleteMenuItem);
        popupMenu.add(modifyMenuItem);
        popupMenu.show(this, x, y);
    }
    
    public void addNode(Noeuds node) {
        nodes.add(node);
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    private void createNode(int x, int y) {
        String input = JOptionPane.showInputDialog(this, "Veuillez saisir l'identifiant du nœud :");
        if (input != null && !input.isEmpty()) {
            int nodeId = Integer.parseInt(input);
            Noeuds newNode = new Noeuds(x, y, nodeId);
            nodes.add(newNode);
            repaint();
        }
    }
    
    private void moveSelectedItem(int x, int y) {
            if (selectedNode != null) {
                selectedNode.setPosition(x, y);
                repaint();
            } else if (selectedConnection != null) {
                Noeuds startNode = selectedConnection.getStartNode();
                Noeuds endNode = selectedConnection.getEndNode();
                int deltaX = x - offsetX;
                int deltaY = y - offsetY;
                startNode.setX(startNode.getX() + deltaX);
                startNode.setY(startNode.getY() + deltaY);
                endNode.setX(endNode.getX() + deltaX);
                endNode.setY(endNode.getY() + deltaY);
                repaint();
            }
        }

    
    void addArrow() {
        String[] nodeIds = getNodeIds();
        String startNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud de départ :",
                "Sélection du nœud de départ",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        String endNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud d'arrivée :",
                "Sélection du nœud d'arrivée",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        if (startNodeId != null && endNodeId != null) {
            Noeuds startNode = getNodeById(Integer.parseInt(startNodeId));
            Noeuds endNode = getNodeById(Integer.parseInt(endNodeId));

            if (startNode != null && endNode != null) {
                String cost = JOptionPane.showInputDialog(this, "Veuillez saisir le coût de la connexion :");
                if (cost != null && !cost.isEmpty()) {
                    Connection connection = new Connection(startNode, endNode, cost);
                    connections.add(connection);
                    repaint();
                }
            }
        }
    }

    private String[] getNodeIds() {
        String[] nodeIds = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            nodeIds[i] = Integer.toString(nodes.get(i).getId());
        }
        return nodeIds;
    }

    private Noeuds getNodeById(int nodeId) {
        for (Noeuds node : nodes) {
            if (node.getId() == nodeId) {
                return node;
            }
        }
        return null;
    }

    private Noeuds findNode(int x, int y) {
        for (Noeuds node : nodes) {
            if (isInsideNode(x, y, node)) {
                return node;
            }
        }
        return null;
    }
    
    private boolean isInsideConnection(int x, int y, Connection connection) {
        int startX = connection.getStartNode().getX();
        int startY = connection.getStartNode().getY();
        int endX = connection.getEndNode().getX();
        int endY = connection.getEndNode().getY();

        double distance = Line2D.ptSegDist(startX, startY, endX, endY, x, y);
        return distance <= 5; // Tolerance for selecting the connection
    }
    
    private Connection findConnection(int x, int y) {
        for (Connection connection : connections) {
            Noeuds startNode = connection.getStartNode();
            Noeuds endNode = connection.getEndNode();
            int startX = startNode.getX();
            int startY = startNode.getY();
            int endX = endNode.getX();
            int endY = endNode.getY();
            if (isPointOnLine(x, y, startX, startY, endX, endY)) {
                return connection;
            }
        }
        return null;
    }

    private boolean isInsideNode(int x, int y, Noeuds node) {
        int radius = 20;
        int diameter = 2 * radius;
        int centerX = node.getX();
        int centerY = node.getY();
        int distanceSquared = (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY);
        return distanceSquared <= radius * radius;
    }

    private boolean isPointOnLine(int x, int y, int startX, int startY, int endX, int endY) {
        double distance1 = Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
        double distance2 = Math.sqrt(Math.pow(x - endX, 2) + Math.pow(y - endY, 2));
        double lineLength = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        double buffer = 0.1;
        return distance1 + distance2 >= lineLength - buffer && distance1 + distance2 <= lineLength + buffer;
    }

    void deleteSelectedItem() {
        if (selectedNode != null) {
            deleteNode(selectedNode);
        } else if (selectedConnection != null) {
            deleteConnection(selectedConnection);
        }
    }
    
    private void moveNode(int x, int y) {
        if (selectedNode != null) {
            selectedNode.setX(x - offsetX);
            selectedNode.setY(y - offsetY);
            repaint();
        }
    }
    
    private void deleteNode(Noeuds node) {
        nodes.remove(node);
        Iterator<Connection> iterator = connections.iterator();
        while (iterator.hasNext()) {
            Connection connection = iterator.next();
            if (connection.getStartNode() == node || connection.getEndNode() == node) {
                iterator.remove();
            }
        }
        repaint();
    }

    private void deleteConnection(Connection connection) {
        connections.remove(connection);
        repaint();
    }

    void modifySelectedItem() {
        if (selectedNode != null) {
            modifyNode(selectedNode);
        } else if (selectedConnection != null) {
            modifyConnection(selectedConnection);
        }
    }

    private void modifyNode(Noeuds node) {
        String input = JOptionPane.showInputDialog(this, "Veuillez saisir le nouvel identifiant du nœud :");
        if (input != null && !input.isEmpty()) {
            int newId = Integer.parseInt(input);
            node.setId(newId);
            repaint();
        }
    }

    private void modifyConnection(Connection connection) {
        String input = JOptionPane.showInputDialog(this, "Veuillez saisir le nouveau coût de la connexion :");
        if (input != null && !input.isEmpty()) {
            connection.setCost(input);
            repaint();
        }
    }
    
    private void calculateMinimalPath() {
        String[] nodeIds = getNodeIds();
        String startNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud de départ :",
                "Sélection du nœud de départ",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        String endNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud d'arrivée :",
                "Sélection du nœud d'arrivée",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        if (startNodeId != null && endNodeId != null) {
            Noeuds startNode = getNodeById(Integer.parseInt(startNodeId));
            Noeuds endNode = getNodeById(Integer.parseInt(endNodeId));

            if (startNode != null && endNode != null) {
                // Implementation of Bellman-Kalaba algorithm
                int nodeCount = nodes.size();
                int[] distances = new int[nodeCount];
                Noeuds[] predecessors = new Noeuds[nodeCount];

                // Initialize distances to infinity except for the start node to 0
                for (int i = 0; i < nodeCount; i++) {
                    distances[i] = Integer.MAX_VALUE;
                }
                distances[startNode.getId()] = 0;

                // Relax edges V-1 times to find optimal distances
                for (int i = 0; i < nodeCount - 1; i++) {
                    for (Connection connection : connections) {
                        Noeuds start = connection.getStartNode();
                        Noeuds end = connection.getEndNode();
                        int cost = Integer.parseInt(connection.getCost());

                        if (distances[start.getId()] != Integer.MAX_VALUE && distances[start.getId()] + cost < distances[end.getId()]) {
                            distances[end.getId()] = distances[start.getId()] + cost;
                            predecessors[end.getId()] = start;
                        }
                    }
                }

                // Check for negative weight cycles
                for (Connection connection : connections) {
                    Noeuds start = connection.getStartNode();
                    Noeuds end = connection.getEndNode();
                    int cost = Integer.parseInt(connection.getCost());

                    if (distances[start.getId()] != Integer.MAX_VALUE && distances[start.getId()] + cost < distances[end.getId()]) {
                        JOptionPane.showMessageDialog(this, "Impossible de trouver un chemin minimale.");
                        return;
                    }
                }

                // Construct the optimal path
                optimalPath = new ArrayList<>();
                Noeuds currentNode = endNode;
                while (currentNode != null) {
                    optimalPath.add(0, currentNode);
                    currentNode = predecessors[currentNode.getId()];
                }

                // Color the optimal path in green
                for (Connection connection : connections) {
                    if (optimalPath.contains(connection.getStartNode()) && optimalPath.contains(connection.getEndNode())) {
                        connection.setColor(Color.GREEN);
                    } else {
                        connection.setColor(Color.BLACK);
                    }
                }
                for (Noeuds node : nodes) {
                    if (optimalPath.contains(node)) {
                        node.setColor(Color.GREEN);
                    } else {
                        node.setColor(Color.BLUE);
                    }
                }

                repaint();
            }
        }
    }
    private void calculateMaximalPath() {
        String[] nodeIds = getNodeIds();
        String startNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud de départ :",
                "Sélection du nœud de départ",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        String endNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud d'arrivée :",
                "Sélection du nœud d'arrivée",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        if (startNodeId != null && endNodeId != null) {
            Noeuds startNode = getNodeById(Integer.parseInt(startNodeId));
            Noeuds endNode = getNodeById(Integer.parseInt(endNodeId));

            if (startNode != null && endNode != null) {
                // Implementation of Bellman-Kalaba algorithm for maximum path
                int nodeCount = nodes.size();
                int[] distances = new int[nodeCount];
                Noeuds[] predecessors = new Noeuds[nodeCount];

                // Initialize distances to negative infinity except for the start node to 0
                for (int i = 0; i < nodeCount; i++) {
                    distances[i] = Integer.MIN_VALUE;
                }
                distances[startNode.getId()] = 0;

                // Relax edges V-1 times to find optimal distances
                for (int i = 0; i < nodeCount - 1; i++) {
                    for (Connection connection : connections) {
                        Noeuds start = connection.getStartNode();
                        Noeuds end = connection.getEndNode();
                        int cost = Integer.parseInt(connection.getCost());

                        if (distances[start.getId()] != Integer.MIN_VALUE && distances[start.getId()] + cost > distances[end.getId()]) {
                            distances[end.getId()] = distances[start.getId()] + cost;
                            predecessors[end.getId()] = start;
                        }
                    }
                }

                // Check for positive weight cycles
                for (Connection connection : connections) {
                    Noeuds start = connection.getStartNode();
                    Noeuds end = connection.getEndNode();
                    int cost = Integer.parseInt(connection.getCost());

                    if (distances[start.getId()] != Integer.MIN_VALUE && distances[start.getId()] + cost > distances[end.getId()]) {
                        JOptionPane.showMessageDialog(this, "Impossible de trouver un chemin maximal.");
                        return;
                    }
                }

                // Construct the optimal path
                optimalPath = new ArrayList<>();
                Noeuds currentNode = endNode;
                while (currentNode != null) {
                    optimalPath.add(0, currentNode);
                    currentNode = predecessors[currentNode.getId()];
                }

                // Color the optimal path in DARKGRAY
                for (Connection connection : connections) {
                    if (optimalPath.contains(connection.getStartNode()) && optimalPath.contains(connection.getEndNode())) {
                        connection.setColor(Color.DARK_GRAY);
                    } else {
                        connection.setColor(Color.BLACK);
                    }
                }
                for (Noeuds node : nodes) {
                    if (optimalPath.contains(node)) {
                        node.setColor(Color.DARK_GRAY);
                    } else {
                        node.setColor(Color.BLUE);
                    }
                }

                repaint();
            }
        }
    }
    
        
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Connection connection : connections) {
            
            Noeuds startNode = connection.getStartNode();
            Noeuds endNode = connection.getEndNode();
            String cost = connection.getCost();

            int startX = startNode.getX();
            int startY = startNode.getY();
            int endX = endNode.getX();
            int endY = endNode.getY();
            
            if (connection.getColor() != null) {
                g2d.setColor(connection.getColor());
            } else {
                g2d.setColor(Color.BLACK);
            }
            
            Arrow.drawArrow(g2d, startX, startY, endX, endY, cost);
        }

        for (Noeuds node : nodes) {
            
            int radius = 20;
            int diameter = 2 * radius;
            int x = node.getX() - radius;
            int y = node.getY() - radius;
            
            if (node.getColor() != null) {
                g2d.setColor(node.getColor());
            } else {
                g2d.setColor(Color.BLUE);
            }
            g2d.fillOval(x, y, diameter, diameter);
            g2d.setColor(Color.WHITE);
            g2d.drawString(Integer.toString(node.getId()), node.getX() - 5, node.getY() + 5);
        }

        if (selectedNode != null) {
            g2d.setColor(Color.RED);
            int radius = 20;
            int diameter = 2 * radius;
            int x = selectedNode.getX() - radius;
            int y = selectedNode.getY() - radius;
            g2d.drawOval(x, y, diameter, diameter);
        }

        if (selectedConnection != null) {
            g2d.setColor(Color.RED);
            Noeuds startNode = selectedConnection.getStartNode();
            Noeuds endNode = selectedConnection.getEndNode();
            int startX = startNode.getX();
            int startY = startNode.getY();
            int endX = endNode.getX();
            int endY = endNode.getY();
            g2d.drawLine(startX, startY, endX, endY);
        }
    }
    private void MinimalChemin() {
        String[] nodeIds = getNodeIds();
        String startNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud de départ :",
                "Sélection du nœud de départ",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        String endNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud d'arrivée :",
                "Sélection du nœud d'arrivée",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        if (startNodeId != null && endNodeId != null) {
            Noeuds startNode = getNodeById(Integer.parseInt(startNodeId));
            Noeuds endNode = getNodeById(Integer.parseInt(endNodeId));

            if (startNode != null && endNode != null) {
                // Implementation of Bellman-Kalaba algorithm
                int nodeCount = nodes.size();
                int[] distances = new int[nodeCount];
                Noeuds[] predecessors = new Noeuds[nodeCount];

                // Initialize distances to infinity except for the start node to 0
                for (int i = 0; i < nodeCount; i++) {
                    distances[i] = Integer.MAX_VALUE;
                }
                distances[endNode.getId()] = 0;
        try{
                // Relax edges V-1 times to find optimal distances
                for (int i = 0; i < nodeCount - 1; i++) {
                    for (Connection connection : connections) {
                        Noeuds start = connection.getStartNode();
                        Noeuds end = connection.getEndNode();
                        int cost = Integer.parseInt(connection.getCost());

                        if (distances[start.getId()] != Integer.MAX_VALUE && distances[start.getId()] + cost < distances[end.getId()]) {
                            distances[end.getId()] = distances[start.getId()] + cost;
                            predecessors[end.getId()] = start;
                        }
                    }
                }
            }
        catch(Exception e){
                
                        JOptionPane.showMessageDialog(this, "Impossible de trouver un chemin Minimale.");
                    
               }

                // Construct the optimal path
                optimalPath = new ArrayList<>();
                Noeuds currentNode = endNode;
                while (currentNode != null) {
                    optimalPath.add(0, currentNode);
                    currentNode = predecessors[currentNode.getId()];
                }

                // Color the optimal path in green
                for (Connection connection : connections) {
                    if (optimalPath.contains(connection.getStartNode()) && optimalPath.contains(connection.getEndNode())) {
                        connection.setColor(Color.GREEN);
                    } else {
                        connection.setColor(Color.BLACK);
                    }
                }
                for (Noeuds node : nodes) {
                    if (optimalPath.contains(node)) {
                        node.setColor(Color.GREEN);
                    } else {
                        node.setColor(Color.BLUE);
                    }
                }

                repaint();
            }
        }
    }
    private void maximalChemin() {
        String[] nodeIds = getNodeIds();
        String startNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud de départ :",
                "Sélection du nœud de départ",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        String endNodeId = (String) JOptionPane.showInputDialog(
                this,
                "Sélectionnez le nœud d'arrivée :",
                "Sélection du nœud d'arrivée",
                JOptionPane.PLAIN_MESSAGE,
                null,
                nodeIds,
                nodeIds[0]);

        if (startNodeId != null && endNodeId != null) {
            Noeuds startNode = getNodeById(Integer.parseInt(startNodeId));
            Noeuds endNode = getNodeById(Integer.parseInt(endNodeId));

            if (startNode != null && endNode != null) {
                // Implementation of Bellman-Kalaba algorithm for maximum path
                int nodeCount = nodes.size();
                int[] distances = new int[nodeCount];
                Noeuds[] predecessors = new Noeuds[nodeCount];

                // Initialize distances to negative infinity except for the start node to 0
                for (int i = 0; i < nodeCount; i++) {
                    distances[i] = Integer.MIN_VALUE;
                }
                distances[endNode.getId()] = 0;
           try{
                // Relax edges V-1 times to find optimal distances
                for (int i = 0; i < nodeCount - 1; i++) {
                    for (Connection connection : connections) {
                        Noeuds start = connection.getStartNode();
                        Noeuds end = connection.getEndNode();
                        int cost = Integer.parseInt(connection.getCost());

                        if (distances[start.getId()] != Integer.MIN_VALUE && distances[start.getId()] + cost > distances[end.getId()]) {
                            distances[end.getId()] = distances[start.getId()] + cost;
                            predecessors[end.getId()] = start;
                        }
                    }
                }
             }
             catch(Exception e){
                
                        JOptionPane.showMessageDialog(this, "Impossible de trouver un chemin Maximale.");
                    
               }
                

                // Construct the optimal path
                optimalPath = new ArrayList<>();
                Noeuds currentNode = endNode;
                while (currentNode != null) {
                    optimalPath.add(0, currentNode);
                    currentNode = predecessors[currentNode.getId()];
                }

                // Color the optimal path in DARKGRAY
                for (Connection connection : connections) {
                    if (optimalPath.contains(connection.getStartNode()) && optimalPath.contains(connection.getEndNode())) {
                        connection.setColor(Color.DARK_GRAY);
                    } else {
                        connection.setColor(Color.BLACK);
                    }
                }
                for (Noeuds node : nodes) {
                    if (optimalPath.contains(node)) {
                        node.setColor(Color.DARK_GRAY);
                    } else {
                        node.setColor(Color.BLUE);
                    }
                }

                repaint();
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 836, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 811, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    }
