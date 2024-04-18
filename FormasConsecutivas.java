import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FormasConsecutivas extends JPanel implements KeyListener {

    private int numCirculos = 0; 
    private int numCuadrados = 0;
    private int numTriangulos = 0; 
    private int numPentagonos = 0; 
    
    public void dibujarCirculo(Graphics g, int x, int y, int radio) {
        g.drawOval(x - radio, y - radio, 2 * radio, 2 * radio);
    }

    
    public void dibujarCirculos(Graphics g, int numCirculos) {
        int x = getWidth() / 4; 
        int y = getHeight() / 2; 
        int radio = 1; 

        for (int i = 0; i < numCirculos; i++) {
            dibujarCirculo(g, x, y, radio);
            radio += 3;
        }
    }

    
    public void dibujarCuadrado(Graphics g, int x, int y, int lado) {
        g.drawRect(x - lado / 2, y - lado / 2, lado, lado);
    }

    
    public void dibujarCuadrados(Graphics g, int numCuadrados) {
        int x = 3 * getWidth() / 4; 
        int y = getHeight() / 2; 
        int lado = 1; 

        for (int i = 0; i < numCuadrados; i++) {
            dibujarCuadrado(g, x, y, lado);
            lado += 3;
        }
    }

   
    public void dibujarTriangulo(Graphics g, int x, int y, int lado) {
        int[] xPoints = {x, x - lado / 2, x + lado / 2};
        int[] yPoints = {y - lado / 2, y + lado / 2, y + lado / 2};
        g.drawPolygon(xPoints, yPoints, 3);
    }

    // Método para dibujar múltiples triángulos consecutivos
    public void dibujarTriangulos(Graphics g, int numTriangulos) {
        int x = getWidth() / 2; // Centro horizontal de la ventana para los triángulos
        int y = 3 * getHeight() / 4; // Centro vertical de la ventana
        int lado = 1; // Longitud del lado del primer triángulo

        for (int i = 0; i < numTriangulos; i++) {
            dibujarTriangulo(g, x, y, lado);
            lado += 3; // Aumenta la longitud del lado en 3
        }
    }

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
        int x = 3 * getWidth() / 4; // Centro horizontal de la ventana para los pentágonos
        int y = 3 * getHeight() / 4; // Centro vertical de la ventana
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
        dibujarCirculos(g, numCirculos);
        dibujarCuadrados(g, numCuadrados);
        dibujarTriangulos(g, numTriangulos);
        dibujarPentagonos(g, numPentagonos);
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

            // Agregar 3 al número de cuadrados y repintar
            numCuadrados += 3;
            if (numCuadrados > 99) {
                numCuadrados = 100; // Limitar a 99 cuadrados
            }

            // Agregar 3 al número de triángulos y repintar
            numTriangulos += 3;
            if (numTriangulos > 99) {
                numTriangulos = 99; // Limitar a 99 triángulos
            }

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
        JFrame frame = new JFrame("Formas Consecutivas");
        FormasConsecutivas panel = new FormasConsecutivas();
        frame.add(panel);
        
        // Botón de reinicio
        JButton reiniciarButton = new JButton("Reiniciar");
        reiniciarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.reiniciar();
            }
        });
        frame.add(reiniciarButton, BorderLayout.SOUTH);
        
        // Botón de agregar formas consecutivas
        JButton agregarConsecutivosButton = new JButton("Agregar");
        agregarConsecutivosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.agregarConsecutivos();
            }
        });
        frame.add(agregarConsecutivosButton, BorderLayout.NORTH);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.addKeyListener(panel); // Agregar el panel como KeyListener
    }
    
    // Método para reiniciar el conteo de formas
    public void reiniciar() {
        numCirculos = 0;
        numCuadrados = 0;
        numTriangulos = 0;
        numPentagonos = 0;
        repaint();
    }
    
    // Método para agregar formas consecutivas
    public void agregarConsecutivos() {
        // Agregar 3 al número de círculos y repintar
        numCirculos += 3;
        if (numCirculos > 99) {
            numCirculos = 99; // Limitar a 99 círculos
        }

        // Agregar 3 al número de cuadrados y repintar
        numCuadrados += 3;
        if (numCuadrados > 99) {
            numCuadrados = 99; // Limitar a 99 cuadrados
        }

        // Agregar 3 al número de triángulos y repintar
        numTriangulos += 3;
        if (numTriangulos > 99) {
            numTriangulos = 99; // Limitar a 99 triángulos
        }

        // Agregar 3 al número de pentágonos y repintar
        numPentagonos += 3;
        if (numPentagonos > 99) {
            numPentagonos = 99; // Limitar a 99 pentágonos
        }

        repaint();
    }
}
