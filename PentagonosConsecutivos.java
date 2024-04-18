import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PentagonosConsecutivos extends JPanel implements KeyListener {

    private int numPentagonos = 0; // Número actual de pentágonos dibujados

    // Método para dibujar un pentágono regular con un lado específico
    public void dibujarPentagono(Graphics g, int x, int y, int lado) {
        int radio = lado / 2;
        int[] xPoints = new int[5];
        int[] yPoints = new int[5];
        for (int i = 0; i < 5; i++) {
            double angulo = Math.toRadians(90 + i * 72);
            xPoints[i] = (int) (x + radio * Math.cos(angulo));
            yPoints[i] = (int) (y - radio * Math.sin(angulo)); // Negativo para ajustar la dirección del eje y
        }
        g.drawPolygon(xPoints, yPoints, 5);
    }

    // Método para dibujar múltiples pentágonos consecutivos
    public void dibujarPentagonos(Graphics g, int numPentagonos) {
        int x = getWidth() / 2; // Centro horizontal de la ventana
        int y = getHeight() / 2; // Centro vertical de la ventana
        int lado = 1; // Longitud del lado del primer pentágono

        for (int i = 0; i < numPentagonos; i++) {
            dibujarPentagono(g, x, y, lado);
            lado += 3; // Aumenta la longitud del lado en 3
        }
    }

    // Método para dibujar en el lienzo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarPentagonos(g, numPentagonos);
    }

    // Manejar el evento de teclado
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Agregar 3 al número de pentágonos y repintar
            numPentagonos += 3;
            if (numPentagonos > 99) {
                numPentagonos = 99; // Limitar a 99 pentágonos
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
        JFrame frame = new JFrame("Pentágonos Consecutivos");
        PentagonosConsecutivos panel = new PentagonosConsecutivos();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(panel); // Agregar el panel como KeyListener
    }
}
