import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class CuadradosConsecutivos extends JPanel implements KeyListener {

    private int numCuadrados = 0; // Número actual de cuadrados dibujados

    // Método para dibujar un cuadrado con un lado específico
    public void dibujarCuadrado(Graphics g, int x, int y, int lado) {
        g.drawRect(x - lado / 2, y - lado / 2, lado, lado);
    }

    // Método para dibujar múltiples cuadrados consecutivos
    public void dibujarCuadrados(Graphics g, int numCuadrados) {
        int x = getWidth() / 2; // Centro horizontal de la ventana
        int y = getHeight() / 2; // Centro vertical de la ventana
        int lado = 1; // Longitud del lado del primer cuadrado

        for (int i = 0; i < numCuadrados; i++) {
            dibujarCuadrado(g, x, y, lado);
            lado += 3; // Aumenta la longitud del lado en 3
        }
    }

    // Método para dibujar en el lienzo
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarCuadrados(g, numCuadrados);
    }

    // Manejar el evento de teclado
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Agregar 3 al número de cuadrados y repintar
            numCuadrados += 3;
            if (numCuadrados > 99) {
                numCuadrados = 99; // Limitar a 99 cuadrados
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
        JFrame frame = new JFrame("Cuadrados Consecutivos");
        CuadradosConsecutivos panel = new CuadradosConsecutivos();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(panel); // Agregar el panel como KeyListener
    }
}
