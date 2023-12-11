import java.util.Date;

public class Livro {
	public String nome;
	public String autor;
	public String genero;
	public int codigo;
	public boolean disponivel;
	public String clienteAluguel; // Nome do cliente que alugou
	public Date dataDevolucao; // Data de devolução
	public Date dataAluguel; // Data de aluguel

	Livro(String nome, String autor, String genero, int codigo) {
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

	public int getCodigo() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isDisponivel() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getNome() {
		// TODO Auto-generated method stub
		return null;
	}
}
