import java.util.ArrayList;
import java.util.List;

public class Cliente {
	String nome;
	int matricula;
	List<Aluguel> alugueis;

	public Cliente(String nome, int matricula) {
		this.nome = nome;
		this.matricula = matricula;
		this.alugueis = new ArrayList<>();
	}

	public void adicionarAluguel(Aluguel aluguel) {
		alugueis.add(aluguel);
	}

	public String obterInformacoesAlugueis() {
		StringBuilder info = new StringBuilder();
		for (Aluguel aluguel : alugueis) {
			info.append("Livro: ").append(aluguel.nomeLivro).append(", Data de Aluguel: ").append(aluguel.dataAluguel)
					.append(", Data de Devolução: ").append(aluguel.dataDevolucao).append("\n");
		}
		return info.toString();
	}
}
