import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SumaDiagonal extends JFrame {
    private JLabel labelSumaDiagonalPrincipal;
    private JLabel labelRestaDiagonalPrincipal;
    private JTextField[][] matrizInput;
    private int size;

    public SumaDiagonal(int size) {
        super("Suma y Resta de Diagonales");
        this.size = size;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(size + 3, size + 2));

        matrizInput = new JTextField[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrizInput[i][j] = new JTextField(2);
                panel.add(matrizInput[i][j]);
            }
        }

        JButton botonSumaDiagonalPrincipal = new JButton("Suma Diagonal Principal");
        botonSumaDiagonalPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[][] matriz = leerMatriz();
                if (matriz != null) {
                    int suma = sumarDiagonalPrincipal(matriz);
                    labelSumaDiagonalPrincipal.setText("Suma Diagonal Principal: " + suma);
                }
            }
        });

        JButton botonCerrar = new JButton("Cerrar");
        botonCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JButton botonRestaDiagonalPrincipal = new JButton("Restar Diagonal Principal");
        botonRestaDiagonalPrincipal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[][] matriz = leerMatriz();
                if (matriz != null) {
                    int resta = restarDiagonalPrincipal(matriz);
                    labelRestaDiagonalPrincipal.setText("Resta Diagonal Principal: " + resta);
                }
            }
        });

        labelSumaDiagonalPrincipal = new JLabel();
        labelRestaDiagonalPrincipal = new JLabel();

        panel.add(botonSumaDiagonalPrincipal);
        panel.add(botonCerrar);
        panel.add(botonRestaDiagonalPrincipal);
        panel.add(labelSumaDiagonalPrincipal);
        panel.add(labelRestaDiagonalPrincipal);

        getContentPane().add(panel);
        setSize(400, 250);
        setVisible(true);
    }

    private int[][] leerMatriz() {
        int[][] matriz = new int[size][size];
        try {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matriz[i][j] = Integer.parseInt(matrizInput[i][j].getText());
                }
            }
            return matriz;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese solo números enteros.");
            return null;
        }
    }

    private int sumarDiagonalPrincipal(int[][] matriz) {
        int suma = 0;
        for (int i = 0; i < size; i++) {
            suma += matriz[i][i];
        }
        return suma;
    }

    private int restarDiagonalPrincipal(int[][] matriz) {
        int resta = matriz[0][0]; // Inicializamos con el primer elemento de la diagonal principal
        for (int i = 1; i < size; i++) { // Empezamos desde la segunda celda de la diagonal principal
            resta -= matriz[i][i];
        }
        return resta;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int size = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz cuadrada:"));
                new SumaDiagonal(size);
            }
        });
    }
}
