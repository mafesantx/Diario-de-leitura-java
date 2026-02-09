import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class LivroDAO {

    public void inserir(Livro livro) {
        String sql = """
            INSERT INTO livros (titulo, autor, genero, total_paginas, paginas_lidas, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setString(3, livro.getGenero());
            stmt.setInt(4, livro.getTotalPaginas());
            stmt.setInt(5, livro.getPaginasLidas());
            stmt.setString(6, livro.getStatus().name());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir livro: " + e.getMessage(), e);
        }
    }

    public ArrayList<Livro> listarTodos() {
        String sql = "SELECT titulo, autor, genero, total_paginas, paginas_lidas, status FROM livros";
        ArrayList<Livro> lista = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro l = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getInt("total_paginas")
                );

                // mantém progresso/status coerente
                l.atualizarProgresso(rs.getInt("paginas_lidas"));

                lista.add(l);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros: " + e.getMessage(), e);
        }

        return lista;
    }

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

    public Optional<Livro> buscarPorTitulo(String titulo) {
        String sql = """
            SELECT titulo, autor, genero, total_paginas, paginas_lidas, status
            FROM livros
            WHERE titulo = ?
            LIMIT 1
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, titulo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Livro l = new Livro(
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("genero"),
                            rs.getInt("total_paginas")
                    );
                    l.atualizarProgresso(rs.getInt("paginas_lidas"));
                    return Optional.of(l);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar livro: " + e.getMessage(), e);
        }

        return Optional.empty();
    }
}
