import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class CirculosConcentricos extends JPanel implements KeyListener {

    private int numCirculos = 0; // Número actual de círculos dibujados

    // Método para dibujar un círculo con un radio específico
    public void dibujarCirculo(Graphics g, int x, int y, int radio) {
        g.drawOval(x - radio, y - radio, 2 * radio, 2 * radio);
    }

    // Método para dibujar múltiples círculos concéntricos
    public void dibujarCirculos(Graphics g, int numCirculos) {
        int x = getWidth() / 2; // Centro horizontal de la ventana
        int y = getHeight() / 2; // Centro vertical de la ventana
        int radio = 1; // Radio del primer círculo

        for (int i = 0; i < numCirculos; i++) {
            dibujarCirculo(g, x, y, radio);
            radio += 3; // Aumenta el radio en 3
        }
    }

    // Método para dibujar en el lienzo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCirculos(g, numCirculos);
    }

    // Manejar el evento de teclado
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Agregar 3 al número de círculos y repintar
            numCirculos += 3;
            if (numCirculos > 99) {
                numCirculos = 99; // Limitar a 99 círculos
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
        JFrame frame = new JFrame("Círculos Concéntricos");
        CirculosConcentricos panel = new CirculosConcentricos();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(panel); // Agregar el panel como KeyListener
    }
}
