package bellmankallaba;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Noeuds {
    private int x;
    private int y;
    private int id;
    private Color color;
    private List<Noeuds> destinations;
    private List<Double> weights;
    private double cost;

    public Noeuds(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = Color.BLUE; // Couleur par défaut
        this.destinations = new ArrayList<>();
        this.weights = new ArrayList<>();
    }

    // Getters et setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Noeuds> getDestinations() {
        return destinations;
    }

    public void addDestination(Noeuds destination, double weight) {
        destinations.add(destination);
        weights.add(weight);
    }

    public double getWeightToDestination(Noeuds destination) {
        int index = destinations.indexOf(destination);
        if (index != -1) {
            return weights.get(index);
        }
        return Double.POSITIVE_INFINITY;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}

