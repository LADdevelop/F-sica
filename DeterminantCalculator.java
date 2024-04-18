import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeterminantCalculator extends JFrame {
    private JTextField[][] matrixFields;
    private JTextField sizeField;
    private JButton createButton;
    private JButton calculateButton;
    private JButton closeButton;
    private JLabel resultLabel;

    public DeterminantCalculator() {
        setTitle("Determinant Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Campo de texto para especificar el tamaño de la matriz
        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Size:"));
        sizeField = new JTextField(5);
        sizePanel.add(sizeField);

        // Botón para crear la matriz
        createButton = new JButton("Create Matrix");
        createButton.addActionListener(e -> createMatrix());

        sizePanel.add(createButton);
        panel.add(sizePanel);

        resultLabel = new JLabel();
        panel.add(resultLabel);

        // Panel para botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        
        // Botón para calcular el determinante
        calculateButton = new JButton("Calculate Determinant");
        calculateButton.addActionListener(e -> calculateDeterminant());
        buttonPanel.add(calculateButton);

        // Botón para cerrar el programa
        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> closeProgram());
        buttonPanel.add(closeButton);

        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    private void createMatrix() {
        int size;
        try {
            size = Integer.parseInt(sizeField.getText());
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, "Size must be a positive integer.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid size input.");
            return;
        }

        JPanel matrixPanel = new JPanel(new GridLayout(size, size));
        matrixFields = new JTextField[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                JTextField textField = new JTextField(5);
                textField.setInputVerifier(new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        JTextField field = (JTextField) input;
                        String text = field.getText();
                        if (text.equals("-0")) {
                            field.setText("0");
                        }
                        return true;
                    }
                });
                matrixPanel.add(textField);
                matrixFields[i][j] = textField;
            }
        }

        getContentPane().removeAll(); // Eliminar todos los componentes
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new JScrollPane(matrixPanel), BorderLayout.CENTER);

        // Agregar botón para calcular el determinante
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(calculateButton);
        buttonPanel.add(closeButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(resultLabel, BorderLayout.NORTH); // Añadir espacio para el resultado

        pack();
    }

    private void calculateDeterminant() {
        int size = matrixFields.length;
        double[][] matrix = new double[size][size];

        // Obtener los valores de los campos de texto y convertirlos a números
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                try {
                    double value = Double.parseDouble(matrixFields[i][j].getText());
                    if (value == -0.0) // Eliminar el valor -0
                        value = 0.0;
                    matrix[i][j] = value;
                } catch (NumberFormatException e) {
                    // Si hay un error al convertir, establecer el valor a cero
                    matrix[i][j] = 0;
                }
            }
        }

        // Calcular el determinante
        double determinant = calculateDeterminant(matrix);
        resultLabel.setText("Determinant: " + determinant);
    }

    private double calculateDeterminant(double[][] matrix) {
        int size = matrix.length;
        if (size == 1) {
            return matrix[0][0]; // Caso base para una matriz 1x1
        }

        double determinant = 0;
        int sign = 1;

        for (int i = 0; i < size; i++) {
            double[][] submatrix = new double[size - 1][size - 1];
            for (int j = 1; j < size; j++) {
                for (int k = 0, l = 0; k < size; k++) {
                    if (k != i) {
                        submatrix[j - 1][l] = matrix[j][k];
                        l++;
                    }
                }
            }
            determinant += sign * matrix[0][i] * calculateDeterminant(submatrix);
            sign *= -1;
        }

        return determinant;
    }

    private void closeProgram() {
        dispose(); // Cerrar la ventana
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DeterminantCalculator::new);
    }
}

