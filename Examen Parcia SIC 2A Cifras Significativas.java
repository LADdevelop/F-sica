import javax.swing.JOptionPane;

public class CifrasSignificativas {
    public static void main(String[] args) {
        boolean continuar = true;

        while (continuar) {
            
            String numeroIngresado = JOptionPane.showInputDialog("Ingrese un número en notación científica o número normal:");

            
            ResultadoCifras resultado = contarCifrasSignificativas(numeroIngresado);

            
            JOptionPane.showMessageDialog(null, "Número ingresado: " + numeroIngresado +
                    "\nCifras significativas: " + resultado.cifrasSignificativas +
                    "\nCifras encontradas: " + resultado.cifrasEncontradas);

        
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea ingresar otro número?", "Continuar", JOptionPane.YES_NO_OPTION);

           
            continuar = (respuesta == JOptionPane.YES_OPTION);
        }
    }

    private static class ResultadoCifras {
        int cifrasSignificativas;
        String cifrasEncontradas;

        ResultadoCifras(int cifrasSignificativas, String cifrasEncontradas) {
            this.cifrasSignificativas = cifrasSignificativas;
            this.cifrasEncontradas = cifrasEncontradas;
        }
    }

    private static ResultadoCifras contarCifrasSignificativas(String numero) {
      
        String numeroLimpio = numero.replaceAll("[^0-9\\.eE-]", "");

        
        int conteo = 0;
        boolean empiezaConteo = false;
        boolean puntoDecimalContado = false;
        boolean notacionCientificaEncontrada = false;
        String cifrasEncontradas = "";

        for (int i = 0; i < numeroLimpio.length(); i++) {
            char currentChar = numeroLimpio.charAt(i);

            if (Character.isDigit(currentChar)) {
                if (currentChar != '0' || empiezaConteo) {
                    empiezaConteo = true;
                    cifrasEncontradas += currentChar;
                    conteo++;
                }
                if (puntoDecimalContado) {
                    
                    empiezaConteo = true;
                }
                if (notacionCientificaEncontrada) {
                    
                    break;
                }
            } else if (currentChar == '.') {
                if (!puntoDecimalContado && empiezaConteo) {
                    puntoDecimalContado = true;
                    cifrasEncontradas += currentChar;
                }
            } else if (currentChar == 'e' || currentChar == 'E') {
                
                break;
            } else if (currentChar == '-' || currentChar == '+') {
                if (empiezaConteo || i + 1 < numeroLimpio.length() && Character.isDigit(numeroLimpio.charAt(i + 1))) {
                    empiezaConteo = true;
                    cifrasEncontradas += currentChar;
                }
            }
        }

        return new ResultadoCifras(conteo, cifrasEncontradas);
    }
}
