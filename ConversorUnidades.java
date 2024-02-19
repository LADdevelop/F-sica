import javax.swing.*;

public class ConversorUnidades {

    public static void main(String[] args) {
        
        String input = JOptionPane.showInputDialog("Ingrese el valor a convertir:");

        
        try {
            double valor = Double.parseDouble(input);

         
            String[] opciones = {"Metro", "Kilogramo", "Segundo", "Kelvin", "Amperio", "Mole", "Candela",
                    "Pulgada", "Pie", "Yarda", "Milla", "Onza", "Libras", "Centímetro"};
            String unidadOrigen = (String) JOptionPane.showInputDialog(null, "Seleccione la unidad de medida:",
                    "Selección de unidad", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            
            String unidadDestino = (String) JOptionPane.showInputDialog(null, "Seleccione la unidad a la que desea convertir:",
                    "Selección de unidad", JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

            
            if (!esConversionPermitida(unidadOrigen, unidadDestino)) {
                JOptionPane.showMessageDialog(null, "No es posible convertir de " + unidadOrigen + " a " + unidadDestino);
            } else {
         
                double resultado = convertirUnidades(valor, unidadOrigen, unidadDestino);

               
                JButton verTodoButton = new JButton("Ver Todo");
                verTodoButton.addActionListener(e -> mostrarConversionesPosibles(unidadOrigen, unidadDestino, valor, resultado));

                
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel(valor + " " + unidadOrigen + " es equivalente a " + resultado + " " + unidadDestino));
                panel.add(verTodoButton);

             
                JOptionPane.showOptionDialog(null, panel, "Resultado de la conversión",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número válido.");
        }
    }

    private static boolean esConversionPermitida(String unidadOrigen, String unidadDestino) {
        String[] conversionesPermitidas = {"Metro->Pulgada", "Metro->Centímetro", "Pulgada->Metro", "Pulgada->Centímetro",
                "Centímetro->Metro", "Centímetro->Pulgada"};
    
        return java.util.Arrays.asList(conversionesPermitidas).contains(unidadOrigen + "->" + unidadDestino);
    }


    private static void mostrarConversionesPosibles(String unidadOrigen, String unidadDestino, double valorOriginal, double resultado) {

        String[] conversionesPosibles = {"Metro->Pulgada", "Metro->Centímetro", "Pulgada->Metro", "Pulgada->Centímetro",
                "Centímetro->Metro", "Centímetro->Pulgada"};
       
  
        StringBuilder mensaje = new StringBuilder("Conversiones posibles con " + unidadOrigen + ":\n");
        for (String conversion : conversionesPosibles) {
            String[] partes = conversion.split("->");
            String unidadDesde = partes[0];
            String unidadHacia = partes[1];

            double resultadoConversion = convertirUnidades(valorOriginal, unidadOrigen, unidadHacia);

            mensaje.append(valorOriginal).append(" ").append(unidadOrigen)
                    .append(" es equivalente a ").append(resultadoConversion).append(" ").append(unidadHacia)
                    .append(" (").append(resultadoConversion - resultado).append(" ").append(unidadDestino).append(")\n");
        }

    
        JOptionPane.showMessageDialog(null, mensaje.toString());
    }

    
    private static double convertirUnidades(double valor, String unidadOrigen, String unidadDestino) {
       
        double metroAPulgada = 39.3701;
        double pulgadaAMetro = 0.0254;
        double centimetroAMetro = 0.01;
        double metroACentimetro = 100;
     

   
        switch (unidadOrigen) {
            case "Metro":
                switch (unidadDestino) {
                    case "Pulgada":
                        return valor * metroAPulgada;
                    case "Centímetro":
                        return valor * metroACentimetro;
                  
                    default:
                        return valor; 
                }
            case "Pulgada":
                switch (unidadDestino) {
                    case "Metro":
                        return valor * pulgadaAMetro;
                    case "Centímetro":
                        return valor * pulgadaAMetro * metroACentimetro;
                    
                    default:
                        return valor; 
                }
            case "Centímetro":
                switch (unidadDestino) {
                    case "Metro":
                        return valor * centimetroAMetro;
                    case "Pulgada":
                        return valor * centimetroAMetro * metroAPulgada;
    
                    default:
                        return valor; 
                }
                        default:
                return valor; 
        }
    }
}

