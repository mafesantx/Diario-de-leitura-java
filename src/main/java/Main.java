import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LivroDAO dao = new LivroDAO();

        int opcao;

        do {
            System.out.println("\n=== DIÁRIO DE LEITURA 📚 ===");
            System.out.println("1 - Cadastrar livro");
            System.out.println("2 - Listar livros");
            System.out.println("3 - Atualizar progresso");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (opcao) {
                case 1 -> cadastrarLivro(scanner, dao);
                case 2 -> listarLivros(dao);
                case 3 -> atualizarProgresso(scanner, dao);
                case 0 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    // ================= MÉTODOS DO MENU =================

    private static void cadastrarLivro(Scanner scanner, LivroDAO dao) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("Gênero: ");
        String genero = scanner.nextLine();

        System.out.print("Total de páginas: ");
        int totalPaginas = scanner.nextInt();
        scanner.nextLine();

        Livro livro = new Livro(titulo, autor, genero, totalPaginas);
        dao.inserir(livro);

        System.out.println("✅ Livro cadastrado com sucesso!");
    }

    private static void listarLivros(LivroDAO dao) {
        ArrayList<Livro> livros = dao.listarTodos();

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }

        System.out.println("\n--- Seus livros ---");
        for (Livro l : livros) {
            System.out.printf(
                    "📖 %s | %d/%d páginas | %s | %.2f%%%n",
                    l.getTitulo(),
                    l.getPaginasLidas(),
                    l.getTotalPaginas(),
                    l.getStatus(),
                    l.getPorcentagemLida()
            );
        }
    }

    private static void atualizarProgresso(Scanner scanner, LivroDAO dao) {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        var livroOpt = dao.buscarPorTitulo(titulo);
        if (livroOpt.isEmpty()) {
            System.out.println("❌ Livro não encontrado com esse título.");
            return;
        }

        Livro livro = livroOpt.get();

        System.out.print("Páginas lidas (0 até " + livro.getTotalPaginas() + "): ");
        int paginasLidas = scanner.nextInt();
        scanner.nextLine();

        // ✅ Status automático
        StatusLivro status = (paginasLidas >= livro.getTotalPaginas())
                ? StatusLivro.CONCLUIDO
                : StatusLivro.LENDO;

        dao.atualizarProgressoPorTitulo(titulo, paginasLidas, status);

        System.out.println("✅ Progresso atualizado no banco!");

        // ✅ Melhoria 2: listar depois do update
        System.out.println("\n📚 Situação atual:");
        listarLivros(dao);
    }
}
