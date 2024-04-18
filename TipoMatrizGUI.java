import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TipoMatrizGUI extends JFrame implements ActionListener {
    private JTextField filasTextField, columnasTextField;
    private JButton crearMatrizButton;
    private JTextArea resultadoTextArea;
    private JRadioButton radioBoton1, radioBoton2, radioBoton3;

    public TipoMatrizGUI() {
        super("Identificar Tipo de Matriz");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        inputPanel.add(new JLabel("Número de columnas:"));
        filasTextField = new JTextField();
        inputPanel.add(filasTextField);
        inputPanel.add(new JLabel("Número de filas:"));
        columnasTextField = new JTextField();
        inputPanel.add(columnasTextField);
        crearMatrizButton = new JButton("Crear Matriz");
        crearMatrizButton.addActionListener(this);
        inputPanel.add(crearMatrizButton);

        // Agregar radio botones
        radioBoton1 = new JRadioButton("Determinate");
        radioBoton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeterminantCalculator calculator = new DeterminantCalculator();
                calculator.setVisible(true);
            }
        });

        radioBoton2 = new JRadioButton("Sarrus");
        radioBoton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReglaDeSarrus sarrusCalculator = new ReglaDeSarrus();
            }
        });

        radioBoton3 = new JRadioButton("Suma y Resta");
        radioBoton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int size = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tamaño de la matriz cuadrada:"));
                SumaDiagonal sumaDiagonal = new SumaDiagonal(size);
            }
        });

        ButtonGroup grupoRadioBotones = new ButtonGroup();
        grupoRadioBotones.add(radioBoton1);
        grupoRadioBotones.add(radioBoton2);
        grupoRadioBotones.add(radioBoton3);

        JPanel radioPanel = new JPanel(new GridLayout(2, 3));
        radioPanel.add(radioBoton1);
        radioPanel.add(radioBoton2);
        radioPanel.add(radioBoton3);

        inputPanel.add(new JLabel("Seleccione una opción:"));
        inputPanel.add(radioPanel);

        resultadoTextArea = new JTextArea();
        resultadoTextArea.setEditable(false);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(resultadoTextArea), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TipoMatrizGUI gui = new TipoMatrizGUI();
            gui.setVisible(true);
        });
    }

@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == crearMatrizButton) {
        int filas, columnas;
        try {
            filas = Integer.parseInt(filasTextField.getText());
            columnas = Integer.parseInt(columnasTextField.getText());
            // Ajustar los valores si uno de los campos es cero
            if (filas == 0 && columnas == 1) {
                filasTextField.setText("0");
                columnasTextField.setText("1");
            }
            if (filas == 1 && columnas == 0) {
                filasTextField.setText("1");
                columnasTextField.setText("0");
            }
            if (filas == 0 && columnas == 0) {
                JOptionPane.showMessageDialog(this, "Ingrese al menos un número válido para filas o columnas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (filas == 0) {
                filas = 1;
            }
            if (columnas == 0) {
                columnas = 1;
            }
            if (filas < 0 || columnas < 0) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido para filas y columnas (no puede ser negativo).", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese valores válidos para filas y columnas.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Fraccion[][] matriz = new Fraccion[filas][columnas];
            
// Lanzar el diálogo para ingresar los elementos de la matriz
for (int i = 0; i < filas; i++) {
    for (int j = 0; j < columnas; j++) {
        // Determinar el número a mostrar en el mensaje según el valor de filas y columnas
        int indice = (filas == 0) ? (j + 1) : (i + 1);
        String mensaje = "Ingrese el elemento [" + indice + (filas == 0 ? "" : "][" + (j + 1) + "]") + ":";
        String entrada;
        boolean esValido = false;
        while (!esValido) {
            entrada = JOptionPane.showInputDialog(this, mensaje);
            // Verificar si la entrada es -0, 0 o está vacía
            if (entrada.equals("-0")) {
                JOptionPane.showMessageDialog(this, "Ingrese un número o fracción válida.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (entrada.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un valor.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                esValido = true;
                try {
                    String[] partes = entrada.split("/");
                    int numerador = Integer.parseInt(partes[0]);
                    int denominador = partes.length > 1 ? Integer.parseInt(partes[1]) : 1; // Si no hay denominador, consideramos 1 por defecto
                    matriz[i][j] = new Fraccion(numerador, denominador);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(this, "Ingrese un número entero o una fracción válida en el formato 'numerador/denominador'.", "Error", JOptionPane.ERROR_MESSAGE);
                    esValido = false;
                }
            }
        }
    }
}

        // Después de crear la matriz
        if (esMatrizCuadrada(matriz) && matriz.length > 2) {
            // Calcular la traza
            Fraccion sumaTraza = new Fraccion(0, 1);
            for (int i = 0; i < matriz.length; i++) {
                sumaTraza = new Fraccion(sumaTraza.getNumerador() + matriz[i][i].getNumerador(), 1); // Sumar solo los numeradores
            }
            
            // Construir el texto completo con la matriz y los resultados
            StringBuilder resultadoCompleto = new StringBuilder();
            resultadoCompleto.append("Matriz Ingresada:\n");
            for (Fraccion[] fila : matriz) {
                for (Fraccion elemento : fila) {
                    resultadoCompleto.append(elemento).append("\t");
                }
                resultadoCompleto.append("\n");
            }
            resultadoCompleto.append("\n");
            resultadoCompleto.append("Diagonal Principal:\n");
            for (int i = 0; i < matriz.length; i++) {
                resultadoCompleto.append(matriz[i][i]).append("\t");
            }
            resultadoCompleto.append("\n");
            resultadoCompleto.append("\n");
            resultadoCompleto.append("Diagonal Secundaria:\n");
            for (int i = 0; i < matriz.length; i++) {
                resultadoCompleto.append(matriz[i][matriz.length - i - 1]).append("\t");
            }
            resultadoCompleto.append("\n");
            resultadoCompleto.append("\n");
            resultadoCompleto.append("Traza:\n");
            resultadoCompleto.append(sumaTraza).append("\n");

            // Crear un nuevo JTextArea para mostrar los resultados
            JTextArea resultadosTextArea = new JTextArea();
            resultadosTextArea.setEditable(false);
            resultadosTextArea.setText(resultadoCompleto.toString());

            // Crear un JScrollPane con el JTextArea de los resultados
            JScrollPane scrollPane = new JScrollPane(resultadosTextArea);

            // Ajustar el tamaño preferido del JScrollPane
            scrollPane.setPreferredSize(new Dimension(200, 200));

            // Agregar el JScrollPane con los resultados en la parte inferior
            getContentPane().add(scrollPane, BorderLayout.SOUTH);

            // Redibujar la ventana para mostrar los cambios
            revalidate();
            repaint();
        } 



            StringBuilder resultado = new StringBuilder();
            if (esMatrizEscalar(matriz)) {
                resultado.append("La matriz ingresada es una matriz escalar.\n");
                System.out.println("Matriz Escalar");
            }


            if (esMatrizIdentidad(matriz)) {
                resultado.append("La matriz ingresada es una matriz identidad.\n");
                System.out.println("Matriz Identidad");
            }

            if (esMatrizRectangular(matriz)) {
                resultado.append("La matriz ingresada es una matriz rectangular.\n");
                System.out.println("Matriz Rectangular");
            }
            

            if (esMatrizDeFila(matriz)) {
                resultado.append("La matriz ingresada es una matriz de fila.\n");
                System.out.println("Matriz de Fila");
            }  
            

            if (esMatrizDeColumna(matriz)) {
                resultado.append("La matriz ingresada es una matriz de columna.\n");
                System.out.println("Matriz de Columna");
            }
            

            if (esMatrizNula(matriz)) {
                resultado.append("La matriz ingresada es una matriz nula.\n");
                System.out.println("Matriz Nula");
            }            
    

            if (esMatrizCuadrada(matriz)) {
                resultado.append("La matriz ingresada es una matriz cuadrada de orden " + matriz.length + ".\n");
                System.out.println("Matriz Cuadrada de Orden " + matriz.length);
            }
            

            if (esMatrizDiagonal(matriz)) {
            resultado.append("La matriz ingresada es una matriz diagonal.\n");
            System.out.println("Matriz Diagonal");
            }


            if (esMatrizOpuesta(matriz)) {
            resultado.append("La matriz ingresada es una matriz opuesta.\n");
            System.out.println("Matriz Opuesta");
            }

            //if (esMatrizTranspuesta(matriz, matrizOriginal)) {
            //resultado.append("La matriz ingresada es la transpuesta de la matriz original.\n");
            //System.out.println("Matriz Transpuesta");
            //}

            if (esMatrizTriangularSuperior(matriz)) {
            resultado.append("La matriz ingresada es triangular superior.\n");
            System.out.println("Matriz Triangular Superior");
            }

            if (esMatrizTriangularInferior(matriz)) {
            resultado.append("La matriz ingresada es triangular inferior.\n");
            System.out.println("Matriz Triangular Inferior");
            }



             if (resultado.length() == 0) {
                resultado.append("La matriz ingresada no coincide con ningún tipo conocido.\n");
            }

            resultado.append("\nMatriz ingresada:\n");
            for (Fraccion[] fila : matriz) {
                for (Fraccion elemento : fila) {
                    resultado.append(elemento).append("\t");
                }
                resultado.append("\n");
            }
    
            resultadoTextArea.setText(resultado.toString());
        }
    }
    
    
    // Métodos para verificar tipos de matrices...

    private boolean esMatrizEscalar(Fraccion[][] matriz) {
        Fraccion elementoDiagonal = matriz[0][0]; // Tomamos el primer elemento de la diagonal principal como referencia
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                // Si no estamos en la diagonal principal y el elemento no es cero, no es una matriz escalar
                if (i != j && !matriz[i][j].esCero()) {
                    return false;
                }
                // Si estamos en la diagonal principal y el elemento es diferente al primer elemento de la diagonal, no es una matriz escalar
                if (i == j && !matriz[i][j].equals(elementoDiagonal)) {
                    return false;
                }
            }
        }
        return true; // Si todos los elementos de la diagonal principal son iguales, es una matriz escalar
    }
    
    private boolean esMatrizIdentidad(Fraccion[][] matriz) {
        if (matriz.length != matriz[0].length) {
            return false;
        }
    
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if ((i == j && !matriz[i][j].esUnoSimplificado()) || (i != j && !matriz[i][j].esCero())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean esMatrizRectangular(Fraccion[][] matriz) {
        return matriz.length != matriz[0].length; // La matriz es rectangular si el número de filas es diferente al número de columnas
    }
    
    
    private boolean esMatrizDeFila(Fraccion[][] matriz) {
        return matriz.length == 1; // Si la matriz tiene solo una fila, es una matriz de fila
    }
    
    private boolean esMatrizDeColumna(Fraccion[][] matriz) {
        // Si la matriz tiene solo una columna y más de una fila, es una matriz de columna
        return matriz.length > 1 && matriz[0].length == 1;
    }
    
    private boolean esMatrizNula(Fraccion[][] matriz) {
        for (Fraccion[] fila : matriz) {
            for (Fraccion elemento : fila) {
                if (!elemento.esCero()) {
                    return false; // Si encontramos un elemento diferente de cero, la matriz no es nula
                }
            }
        }
        return true; // Si todos los elementos son cero, la matriz es nula
    }

    private boolean esMatrizCuadrada(Fraccion[][] matriz) {
        int filas = matriz.length;
        // Verificar si todas las filas tienen el mismo número de columnas
        for (int i = 0; i < filas; i++) {
            if (matriz[i].length != filas) {
                return false;
            }
        }
        return true; // Si el número de filas es igual al número de columnas, la matriz es cuadrada
    }
    
    private boolean esMatrizDiagonal(Fraccion[][] matriz) {
    int filas = matriz.length;
    int columnas = matriz[0].length;
    // Verificar si todos los elementos fuera de la diagonal principal son cero
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            if (i != j && !matriz[i][j].esCero()) {
                return false;
            }
        }
    }
    return true; // Si todos los elementos fuera de la diagonal principal son cero, la matriz es diagonal
}

private boolean esMatrizOpuesta(Fraccion[][] matriz) {
    int filas = matriz.length;
    int columnas = matriz[0].length;
    
    // Crear la matriz opuesta
    Fraccion[][] opuesta = new Fraccion[filas][columnas];
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            opuesta[i][j] = new Fraccion(-matriz[i][j].getNumerador(), matriz[i][j].getDenominador());
        }
    }
    
    // Verificar si la matriz es igual a su matriz opuesta
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            if (!matriz[i][j].equals(opuesta[i][j])) {
                return false;
            }
        }
    }
    
    return true;
}


//private boolean esMatrizTranspuesta(Fraccion[][] matriz, Fraccion[][] matrizOriginal) {
    //int filas = matriz.length;
    //int columnas = matriz[0].length;

    // Verificar si los elementos son iguales a la matriz original transpuesta
    //for (int i = 0; i < filas; i++) {
        //for (int j = 0; j < columnas; j++) {
            //if (!matriz[i][j].equals(matrizOriginal[j][i])) {
                //return false;
            //}
        //}
    //}

    //return true;
//}

private boolean esMatrizTriangularSuperior(Fraccion[][] matriz) {
    int filas = matriz.length;
    int columnas = matriz[0].length;

    // Verificar si todos los elementos por debajo de la diagonal principal son cero
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            if (i > j && !matriz[i][j].esCero()) {
                return false; // Si hay algún elemento no cero por debajo de la diagonal principal, no es triangular superior
            }
        }
    }

    return true; // Si todos los elementos por debajo de la diagonal principal son cero, es triangular superior
}


private boolean esMatrizTriangularInferior(Fraccion[][] matriz) {
    int filas = matriz.length;
    int columnas = matriz[0].length;

    // Verificar si todos los elementos por encima de la diagonal principal son cero
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            if (i < j && !matriz[i][j].esCero()) {
                return false; // Si hay algún elemento no cero por encima de la diagonal principal, no es triangular inferior
            }
        }
    }

    return true; // Si todos los elementos por encima de la diagonal principal son cero, es triangular inferior
}

    // Otros métodos para verificar tipos de matrices...

    // Clase Fraccion y métodos relacionados...

    private static class Fraccion {
        private int numerador;
        private int denominador;

        public Fraccion(int numerador, int denominador) {
            this.numerador = numerador;
            this.denominador = denominador;
            simplificar();
        }

        public boolean esUnoSimplificado() {
            return numerador == denominador && numerador == 1;
        }        

        public int getNumerador() {
            return numerador;
        }

        public int getDenominador() {
            return denominador;
        }

        public boolean esCero() {
            return numerador == 0;
        }

        public boolean esUno() {
            return numerador == denominador;
        }

        public int getValorEntero() {
            return numerador / denominador;
        }

        @Override
        public String toString() {
            if (denominador == 1) {
                return Integer.toString(numerador);
            } else {
                return numerador + "/" + denominador;
            }
        }

        private void simplificar() {
            int mcd = mcd(numerador, denominador);
            numerador /= mcd;
            denominador /= mcd;
        }

        private int mcd(int a, int b) {
            while (b != 0) {
                int t = b;
                b = a % b;
                a = t;
            }
            return Math.abs(a);
        }
    }
}
