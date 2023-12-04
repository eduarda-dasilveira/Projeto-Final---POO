import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class BibliotecaGUI extends JFrame {

	private List<Livro> livros = new ArrayList<>();
	private List<Cliente> clientes = new ArrayList<>();
	private List<Aluguel> alugueis = new ArrayList<>(); // Adicionado essa linha

	private JTextArea outputArea;

	public BibliotecaGUI() {
		super("Biblioteca");

		outputArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(outputArea);
		add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2));

		JButton cadastrarLivroButton = new JButton("Cadastrar Livro");
		cadastrarLivroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarLivro();
			}
		});
		panel.add(cadastrarLivroButton);

		JButton cadastrarClienteButton = new JButton("Cadastrar Cliente");
		cadastrarClienteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarCliente();
			}
		});
		panel.add(cadastrarClienteButton);

		JButton buscarLivroButton = new JButton("Buscar Livro");
		buscarLivroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarLivro();
			}
		});
		panel.add(buscarLivroButton);

		JButton buscarClienteButton = new JButton("Buscar Cliente");
		buscarClienteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarCliente();
			}
		});
		panel.add(buscarClienteButton);

		JButton alugarLivroButton = new JButton("Alugar Livro");
		alugarLivroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				alugarLivro();
			}
		});
		panel.add(alugarLivroButton);

		JButton devolverLivroButton = new JButton("Devolver Livro");
		devolverLivroButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				devolverLivro();
			}
		});
		panel.add(devolverLivroButton);

		JButton listarLivrosOrdenadosButton = new JButton("Listar Livros (Ord. Alfabética)");
		listarLivrosOrdenadosButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listarLivrosOrdenados();
			}
		});
		panel.add(listarLivrosOrdenadosButton);

		JButton listarClientesOrdenadosButton = new JButton("Listar Clientes (Ord. Alfabética)");
		listarClientesOrdenadosButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listarClientesOrdenados();
			}
		});
		panel.add(listarClientesOrdenadosButton);

		add(panel, BorderLayout.SOUTH);

		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	private void cadastrarLivro() {
		String nome = JOptionPane.showInputDialog("Nome do livro:");
		String autor = JOptionPane.showInputDialog("Autor do livro:");
		String genero = JOptionPane.showInputDialog("Gênero do livro:");
		int codigo = Integer.parseInt(JOptionPane.showInputDialog("Código do livro:"));

		Livro livro = new Livro(nome, autor, genero, codigo);
		livros.add(livro);
		atualizarOutput();
	}

	private void cadastrarCliente() {
		String nome = JOptionPane.showInputDialog("Nome do cliente:");
		int matricula = Integer.parseInt(JOptionPane.showInputDialog("Matrícula do cliente:"));

		Cliente cliente = new Cliente(nome, matricula);
		clientes.add(cliente);
		atualizarOutput();
	}

	private void buscarLivro() {
		String opcoesBusca[] = { "Por Nome", "Por Código" };
		int escolha = JOptionPane.showOptionDialog(null, "Escolha o tipo de busca:", "Buscar Livro",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesBusca, opcoesBusca[0]);

		if (escolha == 0) {
			buscarLivroPorNome();
		} else if (escolha == 1) {
			buscarLivroPorCodigo();
		}
	}

	private void buscarCliente() {
		String opcoesBusca[] = { "Por Nome", "Por Matrícula" };
		int escolha = JOptionPane.showOptionDialog(null, "Escolha o tipo de busca:", "Buscar Cliente",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesBusca, opcoesBusca[0]);

		if (escolha == 0) {
			buscarClientePorNome();
		} else if (escolha == 1) {
			buscarClientePorMatricula();
		}
	}

	private void alugarLivro() {
		int matriculaCliente = Integer.parseInt(JOptionPane.showInputDialog("Digite a matrícula do cliente:"));

		// Verificar se o cliente existe
		Cliente clienteEncontrado = null;
		for (Cliente cliente : clientes) {
			if (cliente.matricula == matriculaCliente) {
				clienteEncontrado = cliente;
				break;
			}
		}

		if (clienteEncontrado != null) {
			// Solicitar o gênero para filtrar a lista de livros
			String generoEscolhido = JOptionPane.showInputDialog("Digite o gênero desejado:");

			// Listar os livros disponíveis do gênero escolhido
			List<Livro> livrosDisponiveis = new ArrayList<>();
			for (Livro livro : livros) {
				if (livro.disponivel && livro.genero.equalsIgnoreCase(generoEscolhido)) {
					livrosDisponiveis.add(livro);
				}
			}

			if (!livrosDisponiveis.isEmpty()) {
				// Mostrar os livros disponíveis do gênero escolhido
				StringBuilder livrosStr = new StringBuilder("Livros disponíveis no gênero " + generoEscolhido + ":\n");
				for (Livro livro : livrosDisponiveis) {
					livrosStr.append("Código: ").append(livro.codigo).append(", Nome: ").append(livro.nome)
							.append(", Autor: ").append(livro.autor).append(", Status: ")
							.append(livro.disponivel ? "Disponível" : "Indisponível");

					if (!livro.disponivel) {
						livrosStr.append(", Cliente: ").append(livro.clienteAluguel).append(", Aluguel: ")
								.append(new SimpleDateFormat("dd/MM/yyyy").format(livro.getDataAluguel()))
								.append(", Devolução: ")
								.append(new SimpleDateFormat("dd/MM/yyyy").format(livro.getDataDevolucao()));
					}

					livrosStr.append("\n");
				}

				// Adicionar a solicitação do código do livro na mesma tela
				livrosStr.append("\nDigite o código do livro que você deseja alugar:");

				// Solicitar o código do livro
				int codigoLivro;
				try {
					codigoLivro = Integer.parseInt(JOptionPane.showInputDialog(livrosStr.toString()));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Código do livro inválido!");
					return;
				}

				// Encontrar o livro escolhido
				Livro livroSelecionado = null;
				for (Livro livro : livrosDisponiveis) {
					if (livro.codigo == codigoLivro) {
						livroSelecionado = livro;
						break;
					}
				}

				if (livroSelecionado != null) {
					// Solicitar as datas de aluguel e devolução
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					Date dataAluguel = null;
					Date dataDevolucao = null;

					try {
						String strDataAluguel = JOptionPane.showInputDialog("Digite a data de aluguel (dd/MM/yyyy):");
						String strDataDevolucao = JOptionPane
								.showInputDialog("Digite a data de devolução (dd/MM/yyyy):");

						// Garantir que as datas sejam inicializadas corretamente
						dataAluguel = dateFormat.parse(strDataAluguel);
						dataDevolucao = dateFormat.parse(strDataDevolucao);

						// Definir as datas no livro
						livroSelecionado.setDataAluguel(dataAluguel);
						livroSelecionado.setDataDevolucao(dataDevolucao);

					} catch (ParseException e) {
						JOptionPane.showMessageDialog(null, "Formato de data inválido!");
						return;
					}

					// Criar o aluguel e associar ao cliente
					Aluguel aluguel = new Aluguel(livroSelecionado.codigo, dataAluguel, dataDevolucao,
							livroSelecionado.nome, matriculaCliente);
					clienteEncontrado.adicionarAluguel(aluguel);

					// Atualizar o livro com informações de aluguel
					livroSelecionado.disponivel = false;
					livroSelecionado.clienteAluguel = clienteEncontrado.nome;
					livroSelecionado.dataDevolucao = dataDevolucao;

					// Adicionar o aluguel à lista geral de aluguéis
					alugueis.add(aluguel);

					JOptionPane.showMessageDialog(null, "Livro alugado com sucesso!");
				} else {
					JOptionPane.showMessageDialog(null, "Livro não encontrado.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Não há livros disponíveis no gênero escolhido.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
		}

		atualizarOutput();
	}

	private void devolverLivro() {
		int codigoLivro = Integer
				.parseInt(JOptionPane.showInputDialog("Digite o código do livro que você deseja devolver:"));

		// Encontrar o aluguel correspondente ao livro
		Aluguel aluguelEncontrado = null;
		for (Aluguel aluguel : alugueis) {
			if (aluguel.codigoLivro == codigoLivro) {
				aluguelEncontrado = aluguel;
				break;
			}
		}

		if (aluguelEncontrado != null) {
			// Marcar o livro como disponível novamente
			for (Livro livro : livros) {
				if (livro.codigo == aluguelEncontrado.codigoLivro) {
					livro.disponivel = true;
					break;
				}
			}

			// Remover o aluguel da lista de aluguéis
			alugueis.remove(aluguelEncontrado);

			JOptionPane.showMessageDialog(null, "Livro devolvido com sucesso!");
		} else {
			JOptionPane.showMessageDialog(null, "Aluguel não encontrado para o livro informado.");
		}

		atualizarOutput();
	}

	private void buscarLivroPorNome() {
		String nomeBusca = JOptionPane.showInputDialog("Digite o nome do livro:");
		for (Livro livro : livros) {
			if (livro.nome.equalsIgnoreCase(nomeBusca)) {
				Aluguel aluguel = obterAluguelPorCodigoLivro(livro.codigo);
				mostrarLivroInfo(livro, aluguel);
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "Livro não encontrado.");
	}

	private void appendLivroInfo(StringBuilder sb, Livro livro) {
		sb.append("Código: ").append(livro.codigo).append(", Nome: ").append(livro.nome).append(", Autor: ")
				.append(livro.autor).append(", Gênero: ").append(livro.genero).append(", Status: ")
				.append(livro.disponivel ? "Disponível" : "Indisponível");

		if (!livro.disponivel && livro.clienteAluguel != null && livro.dataAluguel != null
				&& livro.dataDevolucao != null) {
			sb.append(", Cliente: ").append(livro.clienteAluguel).append(", Aluguel: ")
					.append(new SimpleDateFormat("dd/MM/yyyy").format(livro.getDataAluguel())).append(", Devolução: ")
					.append(new SimpleDateFormat("dd/MM/yyyy").format(livro.getDataDevolucao()));
		}

		sb.append("\n");
	}

	private void buscarLivroPorCodigo() {
		int codigoBusca = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do livro:"));
		Livro livroEncontrado = obterLivroPorCodigo(codigoBusca);
		if (livroEncontrado != null) {
			Aluguel aluguel = obterAluguelPorCodigoLivro(livroEncontrado.codigo);
			mostrarLivroInfo(livroEncontrado, aluguel);
		} else {
			JOptionPane.showMessageDialog(null, "Livro não encontrado.");
		}
	}

	private Aluguel obterAluguelPorCodigoLivro(int codigoLivro) {
		for (Aluguel aluguel : alugueis) {
			if (aluguel.codigoLivro == codigoLivro) {
				return aluguel;
			}
		}
		return null;
	}

	private Livro obterLivroPorCodigo(int codigoLivro) {
		for (Livro livro : livros) {
			if (livro.codigo == codigoLivro) {
				return livro;
			}
		}
		return null;
	}

	private void mostrarLivroInfo(Livro livro, Aluguel aluguel) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder info = new StringBuilder("Livro Encontrado:\n");
		info.append(livro.nome).append(" - ").append(livro.autor).append(" - ").append(livro.genero).append(" - ")
				.append(livro.codigo).append(" - ").append(livro.disponivel ? "Disponível" : "Indisponível");

		if (aluguel != null) {
			info.append(", Data de Devolução: ").append(dateFormat.format(aluguel.dataDevolucao));
		}

		JOptionPane.showMessageDialog(null, info.toString());
	}

	private void buscarClientePorNome() {
		String nomeBusca = JOptionPane.showInputDialog("Digite o nome do cliente:");
		for (Cliente cliente : clientes) {
			if (cliente.nome.equalsIgnoreCase(nomeBusca)) {
				JOptionPane.showMessageDialog(null, "Cliente Encontrado:\n" + cliente.nome + " - " + cliente.matricula);
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
	}

	private void buscarClientePorMatricula() {
		int matriculaBusca = Integer.parseInt(JOptionPane.showInputDialog("Digite a matrícula do cliente:"));
		for (Cliente cliente : clientes) {
			if (cliente.matricula == matriculaBusca) {
				JOptionPane.showMessageDialog(null, "Cliente Encontrado:\n" + cliente.nome + " - " + cliente.matricula);
				return;
			}
		}
		JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
	}

	private void listarLivrosOrdenados() {
		List<Livro> livrosOrdenados = new ArrayList<>(livros);
		Collections.sort(livrosOrdenados, Comparator.comparing(livro -> livro.nome.toLowerCase()));

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder("Livros (Ordenados Alfabeticamente):\n");
		for (Livro livro : livrosOrdenados) {
			sb.append(livro.nome).append(" - ").append(livro.autor).append(" - ").append(livro.genero).append(" - ")
					.append(livro.codigo).append(" - ").append(livro.disponivel ? "Disponível" : "Indisponível");

			// Adicionar data de devolução, se estiver indisponível
			if (!livro.disponivel) {
				Aluguel aluguel = obterAluguelPorCodigoLivro(livro.codigo);
				if (aluguel != null) {
					sb.append(", Data de Devolução: ").append(dateFormat.format(aluguel.dataDevolucao));
				}
			}

			sb.append("\n");
		}

		outputArea.setText(sb.toString());
	}

	private void listarClientesOrdenados() {
		List<Cliente> clientesOrdenados = new ArrayList<>(clientes);
		Collections.sort(clientesOrdenados, Comparator.comparing(cliente -> cliente.nome.toLowerCase()));

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder("Clientes (Ordenados Alfabeticamente):\n");
		for (Cliente cliente : clientesOrdenados) {
			sb.append(cliente.nome).append(" - ").append(cliente.matricula).append("\n");

			// Exibir informações dos livros alugados pelo cliente
			if (!cliente.alugueis.isEmpty()) {
				sb.append("  Livros Alugados:\n");
				for (Aluguel aluguel : cliente.alugueis) {
					sb.append("    Livro: ").append(aluguel.nomeLivro).append(", Data de Aluguel: ")
							.append(dateFormat.format(aluguel.dataAluguel)).append(", Data de Devolução: ")
							.append(dateFormat.format(aluguel.dataDevolucao)).append("\n");
				}
			}
		}

		outputArea.setText(sb.toString());
	}

	private void atualizarOutput() {
		StringBuilder sb = new StringBuilder();
		sb.append("Livros:\n");
		for (Livro livro : livros) {
			sb.append(livro.nome).append(" - ").append(livro.autor).append(" - ").append(livro.genero).append(" - ")
					.append(livro.codigo).append(" - ").append(livro.disponivel ? "Disponível" : "Indisponível")
					.append("\n");
		}

		sb.append("\nClientes:\n");
		for (Cliente cliente : clientes) {
			sb.append(cliente.nome).append(" - ").append(cliente.matricula).append("\n");
		}

		outputArea.setText(sb.toString());
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BibliotecaGUI();
			}
		});
	}
}
