public class Main {
    public static void main(String[] args) {
        LivroDAO dao = new LivroDAO();

        // exemplo: atualiza "O Pequeno Príncipe" para 50 páginas lidas
        int paginas = 65;
        StatusLivro status = (paginas == 400) ? StatusLivro.CONCLUIDO : StatusLivro.LENDO;

        dao.atualizarProgressoPorTitulo("O Massacre da Familia Hope", paginas, status);

        System.out.println("✅ Progresso atualizado no banco!");
    }
}
