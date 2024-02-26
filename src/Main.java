import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int option = -1;
        do {
            try {
                gui();
                option = sc.nextInt();
                handleOption(option);
            } catch (InputMismatchException e) {
                System.out.println("ERROR: NO ÉS UNA OPCIÓ VÀLIDA");
                sc.next();
            }
        } while (option != 0);
    }
    public static void gui() {
        System.out.println("=======   ESCULL UNA OPCIÓ  =======");
        System.out.println("===================================");
        System.out.println("1. Veure dades d'una comanda");
        System.out.println("2. Veure dades de la cooperativa");
        System.out.println("3. Exportar dades d'una comanda");
        System.out.println("4. Exportar dades de la cooperativa");
        System.out.println("0. Sortir del programa");
        System.out.println("===================================");
    }
    public static void handleOption(int option) {
        File file1 = new File("src/data/comanda_1.csv");
        File file2 = new File("src/data/comanda_2.csv");
        ArrayList<String[]> arxiu1 = llegirArxiu(file1);
        ArrayList<String[]> arxiu2 = llegirArxiu(file2);
        switch (option) {
            case 1:
                veureDadesDeComandes(arxiu1, arxiu2);
                break;
            case 0:
                System.out.println("EL PROGRAMA ES TANCA...");
                System.exit(0);
                break;
            default:
                System.out.println("ERROR: NO ÉS UNA OPCIÓ VÀLIDA");
        }
    }
    public static ArrayList<String[]> llegirArxiu(File file) {
        ArrayList<String[]> arxiu = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] instancia = linea.split(";");
                arxiu.add(instancia);
            }
        }catch (FileNotFoundException e) {
            System.out.println("ERROR: NO S'HA TROBAT L'ARXIU " + e.getMessage());
            return null;
        }
        return arxiu;
    }
    public static void veureDadesDeComandes(ArrayList<String[]> arxiu1, ArrayList<String[]> arxiu2) {
        System.out.println("ESCULL UNA COMANDA:\n1\n2");
        Scanner sc = new Scanner(System.in);
        int arxiuEscollit = 0;
        try {
            arxiuEscollit = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("ERROR: NO ÉS UNA OPCIÓ VÀLIDA");
        }
        if (arxiuEscollit == 1) {
            assert arxiu1 != null;
            mostrarInformacioArxiu(arxiu1);
        } else if (arxiuEscollit == 2) {
            assert arxiu2 != null;
            mostrarInformacioArxiu(arxiu2);
        }
    }
    public static void veureDadesCooperativa(ArrayList<String[]> arxiu1, ArrayList<String[]> arxiu2) {

    }
    public static void mostrarInformacioArxiu(ArrayList<String[]> arxiu) {
        System.out.println("Data " + arxiu.get(1)[4]);
        int[] quantitatsIva = calcularQuantitatPercentatgeIva(arxiu);
        System.out.println("Total IVA 4% = " + quantitatsIva[0]);
        System.out.println("Total IVA 10% = " + quantitatsIva[1]);
        System.out.println("Total IVA 21% = " + quantitatsIva[2]);
        System.out.println("Total: " + calcularImportTotal(arxiu));
        System.out.println("Llistat de productes:");
        mostrarProductesComprats(arxiu);
    }
    public static int[] calcularQuantitatPercentatgeIva(ArrayList<String[]> arxiu) {
        int[] percentatges = new int[3];
        for (int i = 1; i < arxiu.size(); i++) {
            int quantitat = Integer.parseInt(arxiu.get(i)[7]);
            if (quantitat == 4) {
                percentatges[0]++;
            } else if (quantitat == 10 ) {
                percentatges[1]++;
            } else if (quantitat == 21) {
                percentatges[2]++;
            }
        }
        return percentatges;
    }
    public static double calcularImportTotal(ArrayList<String[]> arxiu) {
        DecimalFormat df = new DecimalFormat("#,00");
        double total = 0;
        for (int i = 1; i < arxiu.size(); i++) {
            total += Double.parseDouble(arxiu.get(i)[8]);
        }
        return Double.parseDouble(df.format(total));
    }
    public static void mostrarProductesComprats(ArrayList<String[]> arxiu) {
        for (int i = 1; i < arxiu.size(); i++) {
            System.out.println("- " + arxiu.get(i)[1].trim() + " quantitat: " + arxiu.get(i)[6] + " IVA: " + arxiu.get(i)[7]);
        }
        System.out.println();
    }
}