package bellmankallaba;

import java.awt.Color;

public class Arc extends javafx.scene.shape.Arc {
    private Noeuds destination;
    private String weight;
    private Color color;

    public Arc(Noeuds destination, String weight) {
        this.destination = destination;
        this.weight = weight;
        this.color = Color.BLACK; // Couleur par défaut
    }

    public Noeuds getDestination() {
        return destination;
    }

    public void setDestination(Noeuds destination) {
        this.destination = destination;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
