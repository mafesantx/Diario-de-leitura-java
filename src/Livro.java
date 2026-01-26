public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private int totalPaginas;
    private int paginasLidas;
    private String status;

    public Livro(String titulo, String autor, String genero, int totalPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.totalPaginas = totalPaginas;
        this.paginasLidas = 0;
        this.status = "LENDO";
    }
    public String getTitulo() {
        return titulo;
    }
    public int getTotalPaginas() {
        return totalPaginas;
    }
    public int getPaginasLidas() {
        return paginasLidas;
    }
}
