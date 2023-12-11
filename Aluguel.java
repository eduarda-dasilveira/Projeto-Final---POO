import java.util.Date;

public class Aluguel {
	public int codigoLivro;
	public Date dataAluguel;
	public Date dataDevolucao;
	public String nomeLivro;
	public int matriculaCliente;

	public Aluguel(int codigoLivro, Date dataAluguel, Date dataDevolucao, String nomeLivro, int matriculaCliente) {
		this.codigoLivro = codigoLivro;
		this.dataAluguel = dataAluguel;
		this.dataDevolucao = dataDevolucao;
		this.nomeLivro = nomeLivro;
		this.matriculaCliente = matriculaCliente;
	}

	public Date getDataAluguel() {
		return this.dataAluguel;
	}

	public Date getDataDevolucao() {
		return this.dataDevolucao;
	}
}
