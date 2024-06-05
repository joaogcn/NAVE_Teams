package persistencia;

import entidades.Aluno;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class AlunoRepositorio {
	private List<Aluno> alunos;
	
	public AlunoRepositorio() {
		alunos = new ArrayList<Aluno>();
	}
	
	public void salvar(Aluno aluno) {
		if (aluno == null) {
			throw new IllegalArgumentException();
		}
		
		alunos.add(aluno);
	}
	
	public List<Aluno> pesquisar() {
		var copia = new ArrayList<Aluno>();
		copia.addAll(alunos);
		copia.sort(new Comparator<Aluno>() {
			@Override
			public int compare(Aluno aluno1, Aluno aluno2) {
				return aluno1.getNome().compareTo(aluno2.getNome());
			}
		} );
		return copia;
		
	}

	public Aluno obter(String email) {
		for (var aluno : alunos) {
			if (aluno.getEmail() == email) {
				return aluno;
			}
		}
		return null;
	}

	
	public void excluir(String email) {
		var i = alunos.iterator();
		while (i.hasNext()) {
			var aluno = i.next();
			if (aluno.getEmail() == email) {
				i.remove();
				break;
				
			}
				
		}
		
	}
	

}
