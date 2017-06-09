

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Andrés Manuel Gálvez Martín
 */
class TranscomunicacionInstrumental {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int cantidadCaracteres = 1000;
        Scanner s = new Scanner(System.in);
        System.out.println("\u001B[34m--->BIENVENIDOS AL VIAJE ASTRAL<---\u001B[0m\n"
                + "  Como ya sabemos, no podemos estar solos en el universo. Muchos cientificos han alcanzado la locura tratando de encontrar\n"
                + "mensajes ocultos en el infinito (por ahora) número pi.\n"
                + "Pero, ¿Realmente alguna fuente externa nos mandan mensajes cifrados con números?\n"
                + "Para comprobar esta teoría generaremos caracteres basados en números aleatorios y estudiaremos las palabras que se crean\n"
                + "con su combinación.\n\n"
                + "Espero que sus mensajes sean de agrado, suerte.\n");
        System.out.println("Seleccione una opción para generar el documento (opción 6 recomendada): ");
        System.out.println("(1)Todos los caracteres");
        System.out.println("(2)Letras y números");
        System.out.println("(3)Solo letras minúsculas");
        System.out.println("(4)Solo números");
        System.out.println("(5)Solo letras (sin ñ)");
        System.out.println("(6)Texto de calidad");
        System.out.println("(7)Alta frecuencia de espacios (4%)");
        int opcion = Integer.parseInt(s.nextLine());
        System.out.println("Número de lineas: ");
        int nLineas = Integer.parseInt(s.nextLine());
        int lineas = cantidadCaracteres / nLineas;
        int resultado;
        String[] esp = new String[53065];
        //pasar palabra españolas a array
        try {
            String linea = "";
            int contadorLinea = 0;
            BufferedReader br = new BufferedReader(new FileReader("espa~nol.words"));

            while ((linea = br.readLine()) != null) {
                esp[contadorLinea] = linea;
                contadorLinea++;
            }

            br.close();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        //escritura 
        char caracter = ' ';
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("portadora.txt"));
            int contadorLineas = 0;
            for (int i = 1; i < cantidadCaracteres; i++) {
                contadorLineas++;
                switch (opcion) {
                    case 1:
                        resultado = (int) (Math.random() * 223 + 33);
                        break;
                    case 2:
                        resultado = (int) (Math.random() * 118 + 48);
                        break;
                    case 3:
                        resultado = (int) (Math.random() * 26 + 97);
                        break;
                    case 4:
                        resultado = (int) (Math.random() * 10 + 48);
                        break;
                    case 5:
                        resultado = (int) (Math.random() * 26 + 65);
                        break;
                    case 6:
                        caracter = generaLetra((int) (Math.random() * 96 + 1));
                    case 7:
                        caracter = generaLetraEspacios((int) (Math.random() * 100 + 1));
                    default:
                        resultado = (int) (Math.random() * 223 + 33);
                        break;
                }

                if (opcion == 6 || opcion == 7) {
                    if (contadorLineas == lineas) {
                        bw.write(String.valueOf(caracter) + "\r\n");
                        contadorLineas = 0;
                    } else {
                        bw.write(String.valueOf(caracter) + "");
                    }
                } else {
                    char letra = (char) resultado;

                    if (contadorLineas == lineas) {
                        bw.write(String.valueOf(letra) + "\r\n");
                        contadorLineas = 0;
                    } else {
                        bw.write(String.valueOf(letra) + "");
                    }

                }

            }
            bw.close();

        } catch (IOException ioe) {
            System.out.println("Error de escritura. \n" + ioe);
        }
        //lectura
        System.out.print("\n\u001B[34mTEXTO ALEATORIO\u001B[0m\n");
        try {
            BufferedReader br = new BufferedReader(new FileReader("portadora.txt"));

            String linea = "";
            while (linea != null) {
                for (String pal : esp) {
                    if (linea.contains(pal)) {
                        linea = linea.replaceAll(pal, "\033[32m " + pal + "\u001B[0m ");
                    }
                }

                System.out.print(linea + "\n");
                linea = br.readLine();
            }
            System.out.println();
            br.close();

        } catch (IOException e) {
            System.out.println("Error de lectura. \n" + e);
        }

        //contar letras
        System.out.print("\n\u001B[34mSi la sentencia es azul es porque la letra aparece suficientes veces para pertenecer a un texto español\u001B[0m");
        System.out.print("\n\u001B[31mSi la sentencia es roja es porque la letra no aparece suficientes veces para pertenecer a un texto español\u001B[0m\n\n");
        try {
            String[] letras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
            double[] proporcionLetras = {12.53, 1.42, 4.68, 5.86, 13.68, 0.69, 1.01, 0.70, 6.25, 0.44, 0.01, 4.97, 3.15, 6.71, 0.31, 8.68, 2.51, 0.88, 6.87, 7.98, 4.63, 3.93, 0.90, 0.2, 0.22, 0.90, 0.52};
            boolean portadoraCalidad = false;
            String letra = "";
            String linea = "";
            int i = 0;
            int repeticiones = 0;
            int salto = 0;
            for (int e = 0; e <= letras.length - 1; e++) {
                BufferedReader br = new BufferedReader(new FileReader("portadora.txt"));
                letra = letras[e];
                linea = "";
                i = 0;
                repeticiones = 0;
                while ((linea = br.readLine()) != null) {

                    while ((i = linea.indexOf(letra)) != -1) {
                        linea = linea.substring(i + letra.length(), linea.length());
                        repeticiones++;
                    }
                }

                br.close();
                salto++;

                double proporcionApariciones = (proporcionLetras[e] * cantidadCaracteres) / 100;
                if (repeticiones >= proporcionApariciones) {
                    portadoraCalidad = true;
                    System.out.printf("\u001B[31mLa letra %2s aparece %3d\u001B[0m |" , letra , repeticiones);
                    if (salto == 3) {
                        salto = 0;
                        System.out.print("\n");
                    }
                } else {
                    System.out.printf("\u001B[34mLa letra %2s aparece %3d\u001B[0m |" , letra , repeticiones);
                    if (salto == 3) {
                        salto = 0;
                        System.out.print("\n");
                    }
                    portadoraCalidad = false;
                }
            }

            if (portadoraCalidad) {
                System.out.println("--->Portadora de calidad");
            }

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        //palabras encontradas
        System.out.print("\n\u001B[34mPALABRAS ENCONTRADAS\u001B[0m\n");
        
        try {
            BufferedReader br = new BufferedReader(new FileReader("portadora.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("palabrasEncontradas.txt"));

            String lineaOrigen = "";
            boolean palabraEsp = false;
            int salto = 0;

            while ((lineaOrigen = br.readLine()) != null) {
                int l = lineaOrigen.length();
                // Detecta palabra esp
                for (String palabra : esp) {
                    if (lineaOrigen.contains(palabra)) {
                        System.out.print("" + palabra + ", ");
                        palabraEsp = true;
                        salto++;
                        if (salto == 40) {
                            salto = 0;
                            System.out.print("\n");
                        }
                    }
                    if (palabraEsp) {
                        bw.write(palabra + "\r\n");
                        palabraEsp = false;
                    }
                }
            }

            br.close();
            bw.close();

        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

    }

    public static char generaLetra(int op) {
        switch (op) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return 'a';

            case 13:
                return 'b';
            case 14:
            case 15:
            case 16:
            case 17:
                return 'c';
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return 'd';
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                return 'e';
            case 36:
                return 'f';
            case 37:
                return 'g';
            case 38:
                return 'h';
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                return 'i';
            case 45:
                return 'j';
            case 46:
                return 'k';
            case 47:
            case 48:
            case 49:
            case 50:
                return 'l';
            case 51:
            case 52:
            case 53:
                return 'm';
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                return 'n';
            case 60:
                return 'ñ';
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                return 'o';
            case 69:
            case 70:
                return 'p';
            case 71:
                return 'q';
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
                return 'r';
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
                return 's';
            case 85:
            case 86:
            case 87:
            case 88:
                return 't';
            case 89:
            case 90:
            case 91:
                return 'u';
            case 92:
                return 'v';
            case 93:
                return 'w';
            case 94:
                return 'x';
            case 95:
                return 'y';
            case 96:
                return 'z';
            default:
                return ' ';
        }

    }

    public static char generaLetraEspacios(int op) {
        switch (op) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return 'a';

            case 13:
                return 'b';
            case 14:
            case 15:
            case 16:
            case 17:
                return 'c';
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
                return 'd';
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                return 'e';
            case 36:
                return 'f';
            case 37:
                return 'g';
            case 38:
                return 'h';
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
                return 'i';
            case 45:
                return 'j';
            case 46:
                return 'k';
            case 47:
            case 48:
            case 49:
            case 50:
                return 'l';
            case 51:
            case 52:
            case 53:
                return 'm';
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
                return 'n';
            case 60:
                return 'ñ';
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
                return 'o';
            case 69:
            case 70:
                return 'p';
            case 71:
                return 'q';
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
                return 'r';
            case 78:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
                return 's';
            case 85:
            case 86:
            case 87:
            case 88:
                return 't';
            case 89:
            case 90:
            case 91:
                return 'u';
            case 92:
                return 'v';
            case 93:
                return 'w';
            case 94:
                return 'x';
            case 95:
                return 'y';
            case 96:
                return 'z';
            case 97:
            case 98:
            case 99:
            case 100:
                return ' ';
            default:
                return ' ';
        }

    }

}
