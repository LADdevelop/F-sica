import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;


public class SumaVectores extends JFrame {
    private JComboBox<String> medidaComboBox;
    private JComboBox<String> direccionComboBox1;
    private JComboBox<String> direccionComboBox2;
    private JTextField magnitudField1;
    private JTextField magnitudField2;
    private JButton calcularButton;
    private VectorPanel vectorPanel;

    private String[] medidas = {"Metro", "Kilogramo", "Segundo", "Kilometro", "Centimetro", "Milimetro", "Mol", "Pulgadas", "Pies", "Yardas", "Millas", "Libras", "Onzas"};
    private String[] direcciones = {"Norte", "Sur", "Este", "Oeste"};

    public SumaVectores() {
        setTitle("Suma de Vectores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(0, 2));

        JLabel medidaLabel = new JLabel("Seleccione sistema de medida:");
        medidaComboBox = new JComboBox<>(medidas);
        controlPanel.add(medidaLabel);
        controlPanel.add(medidaComboBox);

        JLabel vector1Label = new JLabel("Vector 1:");
        controlPanel.add(vector1Label);
        magnitudField1 = new JTextField(10);
        controlPanel.add(magnitudField1);
        direccionComboBox1 = new JComboBox<>(direcciones);
        controlPanel.add(direccionComboBox1);

        JLabel vector2Label = new JLabel("Vector 2:");
        controlPanel.add(vector2Label);
        magnitudField2 = new JTextField(10);
        controlPanel.add(magnitudField2);
        direccionComboBox2 = new JComboBox<>(direcciones);
        controlPanel.add(direccionComboBox2);

        calcularButton = new JButton("Calcular");
        calcularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarResultado();
            }
        });
        controlPanel.add(calcularButton);

        add(controlPanel, BorderLayout.NORTH);

        vectorPanel = new VectorPanel();
        add(vectorPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void mostrarResultado() {
        
        double magnitudVector1 = Double.parseDouble(magnitudField1.getText());
        String direccionVector1 = (String) direccionComboBox1.getSelectedItem();
        double magnitudVector2 = Double.parseDouble(magnitudField2.getText());
        String direccionVector2 = (String) direccionComboBox2.getSelectedItem();

        
        double magnitudResultante = calcularMagnitudResultante(magnitudVector1, magnitudVector2, direccionVector1, direccionVector2);
        double anguloResultante = calcularAnguloResultante(magnitudVector1, magnitudVector2, direccionVector1, direccionVector2);

        
        String resultado = String.format("El resultado de la suma de vectores es: %.2f %s\nÁngulo resultante: %.2f grados", magnitudResultante, medidaComboBox.getSelectedItem(), anguloResultante);
        JOptionPane.showMessageDialog(this, resultado, "Resultado", JOptionPane.INFORMATION_MESSAGE);

        
        vectorPanel.setVectors(magnitudVector1, direccionVector1, magnitudVector2, direccionVector2, magnitudResultante, anguloResultante);
        vectorPanel.repaint();
    }

    
    private double calcularMagnitudResultante(double magnitudVector1, double magnitudVector2, String direccionVector1, String direccionVector2) {
        return magnitudVector1 + magnitudVector2;
    }

    
    private double calcularAnguloResultante(double magnitudVector1, double magnitudVector2, String direccionVector1, String direccionVector2) {
        int angulo1 = direccionToAngulo(direccionVector1);
        int angulo2 = direccionToAngulo(direccionVector2);

        int anguloResultante = Math.abs(angulo2 - angulo1);

        if (anguloResultante > 180) {
            anguloResultante = 360 - anguloResultante;
        }

        return anguloResultante;
    }

    
    private int direccionToAngulo(String direccion) {
        switch (direccion.toUpperCase()) {
            case "NORTE":
                return 0;
            case "SUR":
                return 180;
            case "ESTE":
                return 90;
            case "OESTE":
                return 270;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SumaVectores();
            }
        });
    }

    private class VectorPanel extends JPanel {
        private double magnitudVector1;
        private String direccionVector1;
        private double magnitudVector2;
        private String direccionVector2;
        private double magnitudResultante;
        private double anguloResultante;

        public void setVectors(double magnitudVector1, String direccionVector1, double magnitudVector2, String direccionVector2, double magnitudResultante, double anguloResultante) {
            this.magnitudVector1 = magnitudVector1;
            this.direccionVector1 = direccionVector1;
            this.magnitudVector2 = magnitudVector2;
            this.direccionVector2 = direccionVector2;
            this.magnitudResultante = magnitudResultante;
            this.anguloResultante = anguloResultante;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            int origenX = getWidth() / 2;
            int origenY = getHeight() / 2;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.setColor(Color.BLACK);

            g2d.drawLine(origenX, 0, origenX, getHeight()); 
            g2d.drawLine(0, origenY, getWidth(), origenY); 

            
            g2d.setStroke(new BasicStroke(3));

           
            drawVector(g2d, origenX, origenY, magnitudVector1, direccionVector1, Color.RED);

            
            drawVector(g2d, origenX, origenY, magnitudVector2, direccionVector2, Color.BLUE);

            
            drawResultantVector(g2d, magnitudResultante, anguloResultante);
        }

        private void drawVector(Graphics2D g2d, int origenX, int origenY, double magnitud, String direccion, Color color) {
            if (direccion != null) {
                int x = origenX;
                int y = origenY;

                switch (direccion.toUpperCase()) {
                    case "NORTE":
                        y -= magnitud;
                        break;
                    case "SUR":
                        y += magnitud;
                        break;
                    case "ESTE":
                        x += magnitud;
                        break;
                    case "OESTE":
                        x -= magnitud;
                        break;
                }

                g2d.setColor(color);
                g2d.drawLine(origenX, origenY, x, y);
            }
        }

        private void drawResultantVector(Graphics2D g2d, double magnitud, double angulo) {
            int origenX = getWidth() / 2;
            int origenY = getHeight() / 2;

            double rad = Math.toRadians(angulo);

            int x1 = origenX + (int) (magnitud * Math.cos(rad));
            int y1 = origenY - (int) (magnitud * Math.sin(rad));

            g2d.setColor(Color.GREEN);
            g2d.drawLine(origenX, origenY, x1, y1);
        }
    }
}
