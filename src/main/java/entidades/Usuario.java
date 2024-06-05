package entidades;

public abstract class Usuario {
	private String nome;
	private String email;
	private String senha;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		if (nome == null) {
			throw new IllegalArgumentException();
			}
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException();
	}
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		if (senha == null) {
			throw new IllegalArgumentException();
	}
		this.senha = senha;
	}
	
	

}
