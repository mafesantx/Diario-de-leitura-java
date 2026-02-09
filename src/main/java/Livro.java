public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private int totalPaginas;
    private int paginasLidas;
    private StatusLivro status;

    public Livro(String titulo, String autor, String genero, int totalPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.totalPaginas = totalPaginas;
        this.paginasLidas = 0;
        this.status = StatusLivro.LENDO;
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
    public StatusLivro getStatus() {
        return status;
    }
    public void atualizarProgresso(int novasPaginasLidas) {
        if (novasPaginasLidas < 0) {
            throw new IllegalArgumentException("Páginas lidas não pode ser negativo.");
        }
        if (novasPaginasLidas > totalPaginas) {
            throw new IllegalArgumentException("Páginas lidas não pode ser maior que o total de páginas.");
        }

        this.paginasLidas = novasPaginasLidas;

        if (this.paginasLidas == this.totalPaginas) {
            this.status = StatusLivro.CONCLUIDO;
        } else {
            this.status = StatusLivro.LENDO;
        }
    }
    public double getPorcentagemLida() {
        return (paginasLidas * 100.0) / totalPaginas;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }
}
