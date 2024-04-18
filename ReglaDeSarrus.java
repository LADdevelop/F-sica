import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ReglaDeSarrus {
    private JFrame frame;
    private JPanel panel;
    private JTextField[][] matrixFields;
    private JButton calculateButton;
    private JButton closeButton;
    private JLabel determinantLabel;

    public ReglaDeSarrus() {
        frame = new JFrame("Determinante por Regla de Sarrus");
        panel = new JPanel(new GridLayout(5, 4));
        matrixFields = new JTextField[3][3];
        calculateButton = new JButton("Calcular");
        closeButton = new JButton("Cerrar");
        determinantLabel = new JLabel("");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrixFields[i][j] = new JTextField();
                panel.add(matrixFields[i][j]);
            }
        }
        
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double[][] matrix = new double[3][3];               
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            String value = matrixFields[i][j].getText();
                            if (value.equals("-0")) {
                                throw new NumberFormatException("Ingrese un número válido");
                            }
                            matrix[i][j] = Double.parseDouble(value);
                        }
                    }
                    // Calculo de la determinate 
                    double determinant = sarrusDeterminant(matrix);
                    determinantLabel.setText("Determinante: " + determinant);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(calculateButton);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        panel.add(closeButton);

        panel.add(determinantLabel);

        frame.add(panel);
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        frame.setVisible(true);
    }

    public static double sarrusDeterminant(double[][] matrix) {
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("La matriz debe ser de 3x3");
        }

        double positiveSum = (matrix[0][0] * matrix[1][1] * matrix[2][2] +
                            matrix[0][1] * matrix[1][2] * matrix[2][0] +
                            matrix[0][2] * matrix[1][0] * matrix[2][1]);
        double negativeSum = (matrix[0][2] * matrix[1][1] * matrix[2][0] +
                            matrix[0][0] * matrix[1][2] * matrix[2][1] +
                            matrix[0][1] * matrix[1][0] * matrix[2][2]);
        double determinant = positiveSum - negativeSum;

        return determinant;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ReglaDeSarrus();
            }
        });
    }
}

