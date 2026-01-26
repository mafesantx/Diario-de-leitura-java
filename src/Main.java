public class Main {
    public static void main(String[] args) {

        Livro livro1 = new Livro(
                "O Massacre da Familia Hope",
                "Riley Sager",
                "Suspense",
                400
        );

        System.out.println("Título: " + livro1.getTitulo());
        System.out.println("Total de páginas: " + livro1.getTotalPaginas());
        System.out.println("Páginas lidas: " + livro1.getPaginasLidas());
    }
}