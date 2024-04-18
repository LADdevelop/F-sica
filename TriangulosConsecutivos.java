import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class TriangulosConsecutivos extends JPanel implements KeyListener {

    private int numTriangulos = 0; // Número actual de triángulos dibujados

    // Método para dibujar un triángulo equilátero con un lado específico
    public void dibujarTriangulo(Graphics g, int x, int y, int lado) {
        int[] xPoints = {x, x - lado / 2, x + lado / 2};
        int[] yPoints = {y - lado / 2, y + lado / 2, y + lado / 2};
        g.drawPolygon(xPoints, yPoints, 3);
    }

    // Método para dibujar múltiples triángulos consecutivos
    public void dibujarTriangulos(Graphics g, int numTriangulos) {
        int x = getWidth() / 2; // Centro horizontal de la ventana
        int y = getHeight() / 2; // Centro vertical de la ventana
        int lado = 1; // Longitud del lado del primer triángulo

        for (int i = 0; i < numTriangulos; i++) {
            dibujarTriangulo(g, x, y, lado);
            lado += 3; // Aumenta la longitud del lado en 3
        }
    }

    // Método para dibujar en el lienzo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarTriangulos(g, numTriangulos);
    }

    // Manejar el evento de teclado
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Agregar 3 al número de triángulos y repintar
            numTriangulos += 3;
            if (numTriangulos > 99) {
                numTriangulos = 99; // Limitar a 99 triángulos
            }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    // Método principal
    public static void main(String[] args) {
        JFrame frame = new JFrame("Triángulos Consecutivos");
        TriangulosConsecutivos panel = new TriangulosConsecutivos();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(panel); // Agregar el panel como KeyListener
    }
}
