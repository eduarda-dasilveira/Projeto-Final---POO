import java.util.Date;

public class Livro {
	String nome;
	String autor;
	String genero;
	int codigo;
	boolean disponivel;
	String clienteAluguel; // Nome do cliente que alugou
	Date dataDevolucao; // Data de devolução
	Date dataAluguel; // Data de aluguel

	public Livro(String nome, String autor, String genero, int codigo) {
		this.nome = nome;
		this.autor = autor;
		this.genero = genero;
		this.codigo = codigo;
		this.disponivel = true;
		this.clienteAluguel = null;
		this.dataDevolucao = null;
		this.dataAluguel = null;
	}

	public void setDataAluguel(Date dataAluguel) {
		this.dataAluguel = dataAluguel;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Date getDataAluguel() {
		return this.dataAluguel;
	}

	public Date getDataDevolucao() {
		return this.dataDevolucao;
	}
}
