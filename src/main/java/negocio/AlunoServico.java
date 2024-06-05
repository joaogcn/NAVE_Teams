package negocio;

import entidades.Aluno;
import persistencia.AlunoRepositorio;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service

public class AlunoServico {
	private AlunoRepositorio repositorio;
	private final List<String> alunos = new ArrayList<>();

	public void adicionarAluno(String aluno) {
		alunos.add(aluno);
	}

	public List<String> getAlunos() {
		return new ArrayList<>(alunos);
	}

	public AlunoServico(AlunoRepositorio repositorio) {
		if (repositorio == null) {
			throw new IllegalArgumentException();
		}
		this.repositorio = repositorio;
	}

	public void salvar(Aluno aluno) {
		if (aluno == null) {
			throw new IllegalArgumentException();

		}
		// TODO Implementar servico
		repositorio.excluir(aluno.getEmail());
		repositorio.salvar(aluno);
	}



	public List<Aluno> pesquisar() {
		return repositorio.pesquisar();
	}

	public Aluno obter(String email) {
		return repositorio.obter(email);

	}

	public void excluir (String email) {
		repositorio.excluir(email);
	}



	}


