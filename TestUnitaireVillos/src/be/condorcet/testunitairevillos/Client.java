package be.condorcet.testunitairevillos;

public class Client {
	protected int idClient;
	protected String pseudo;
	protected String password;
	protected String email;
	protected String nom;
	protected String prenom;
	
	public Client() {
		
	}

	public Client(int idClient, String pseudo, String password, String email, String nom, String prenom) {
		super();
		this.idClient = idClient;
		this.pseudo = pseudo;
		this.password = password;
		this.email = email;
		this.nom = nom;
		this.prenom = prenom;
	}

	public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int idClient) {
		this.idClient = idClient;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "Client [idClient=" + idClient + ", pseudo=" + pseudo
				+ ", password=" + password + ", email=" + email + ", nom="
				+ nom + ", prenom=" + prenom + "]";
	}
		

}
