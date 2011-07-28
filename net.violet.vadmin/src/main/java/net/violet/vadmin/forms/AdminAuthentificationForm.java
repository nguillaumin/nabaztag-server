package net.violet.vadmin.forms;



public class AdminAuthentificationForm extends AbstractForm {

	
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private String dispatch;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getDispatch() {
		return dispatch;
	}

	public void setDispatch(String dispatch) {
		this.dispatch = dispatch;
	}
}
