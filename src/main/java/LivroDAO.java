import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LivroDAO {

    public void inserir(Livro livro) {
        // ...
    }

    public ArrayList<Livro> listarTodos() {
        // ...
        return new ArrayList<>();
    }

    // ✅ ESTE MÉTODO TEM QUE FICAR AQUI DENTRO
    public void atualizarProgressoPorTitulo(String titulo, int paginasLidas, StatusLivro status) {
        String sql = """
            UPDATE livros
            SET paginas_lidas = ?, status = ?
            WHERE titulo = ?
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paginasLidas);
            stmt.setString(2, status.name());
            stmt.setString(3, titulo);

            int linhas = stmt.executeUpdate();
            if (linhas == 0) {
                throw new RuntimeException("Nenhum livro encontrado com esse título.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar progresso: " + e.getMessage(), e);
        }
    }
}
