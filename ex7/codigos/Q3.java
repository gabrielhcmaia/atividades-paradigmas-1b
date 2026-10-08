public class Q3 {
    public static void main(String[] args) {
        int dia = 2; String nome = "";
        switch (dia) {
            case 1: nome = "domingo";
            case 2: nome = "segunda";
            case 3: nome = "terça";
            default: nome = "inválido";
        }
        System.out.println(nome);
    }
}
