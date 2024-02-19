import javax.swing.JOptionPane;

public class ConversorNotacionCientifica {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Ingrese un número en decimales o notación científica:");

        if (input.toLowerCase().contains("e") || input.toLowerCase().contains("x")) {
            try {
                double decimalNumber = convertirANotacionDecimal(input);
                JOptionPane.showMessageDialog(null, "Número en decimal: " + decimalNumber);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número válido.");
            }
        } else {
            try {
                double decimalNumber = Double.parseDouble(input);
                int exponente = 0;

                while (decimalNumber >= 10.0 || decimalNumber <= -10.0) {
                    decimalNumber /= 10.0;
                    exponente++;
                }

                while (decimalNumber < 1.0 && decimalNumber > -1.0) {
                    decimalNumber *= 10.0;
                    exponente--;
                }

                String scientificNotation = String.format("%.2fx10^%d", decimalNumber, exponente);
                JOptionPane.showMessageDialog(null, "Número en notación científica: " + scientificNotation);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número válido.");
            }
        }
    }
    private static double convertirANotacionDecimal(String input) {
        if (input.toLowerCase().contains("e")) {
          
            String[] parts = input.split("e|E");
            double base = Double.parseDouble(parts[0]);
            int exponente = Integer.parseInt(parts[1]);
            return base * Math.pow(10, exponente);
        } else if (input.toLowerCase().contains("x")) {
            
            String[] parts = input.split("x|X");
            double base = Double.parseDouble(parts[0]);
            int exponente = Integer.parseInt(parts[1]);
            return base * Math.pow(10, exponente);
        } else {
            throw new NumberFormatException();
        }
    }
}


