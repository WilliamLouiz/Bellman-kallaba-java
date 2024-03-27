
package bellmankallaba;

import java.awt.Color;


public class Connection {
    private Noeuds startNode;
    private Noeuds endNode;
    private String cost;
    private Color color;

    public Connection(Noeuds startNode, Noeuds endNode, String cost) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.cost = cost;
        this.color = Color.BLACK; // Couleur par défaut
    }

    // Getters et setters

    public Noeuds getStartNode() {
        return startNode;
    }

    public void setStartNode(Noeuds startNode) {
        this.startNode = startNode;
    }

    public Noeuds getEndNode() {
        return endNode;
    }

    public void setEndNode(Noeuds endNode) {
        this.endNode = endNode;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

