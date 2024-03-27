/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bellmankallaba;

import java.awt.*;

public class Arrow {
    public static void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2, String cost) {
        // Dessiner la ligne entre les deux points
        g2d.setColor(Color.BLACK);
        g2d.drawLine(x1, y1, x2, y2);

        // Calculer les coordonnées du point milieu
        int midX = (x1 + x2) / 2;
        int midY = (y1 + y2) / 2;

        // Calculer l'angle de la flèche
        double angle = Math.atan2(y2 - y1, x2 - x1);

        // Longueur des côtés du triangle
        int triangleSize = 10;
        int triangleHeight = (int) Math.round(triangleSize * Math.tan(Math.toRadians(30)));

        // Coordonnées des sommets du triangle
        int[] xPoints = {
            (int) Math.round(midX + triangleSize * Math.cos(angle - Math.toRadians(150))),
            (int) Math.round(midX + triangleSize * Math.cos(angle + Math.toRadians(150))),
            midX
        };
        int[] yPoints = {
            (int) Math.round(midY + triangleSize * Math.sin(angle - Math.toRadians(150))),
            (int) Math.round(midY + triangleSize * Math.sin(angle + Math.toRadians(150))),
            midY
        };

        // Dessiner le triangle
        g2d.setColor(Color.BLUE);
        g2d.fillPolygon(xPoints, yPoints, 3);

        // Dessiner le coût à côté de la flèche
        g2d.setColor(Color.BLACK);
        g2d.drawString(cost, midX + triangleSize, midY);
    }
}
