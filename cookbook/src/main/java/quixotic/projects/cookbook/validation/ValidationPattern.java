package quixotic.projects.cookbook.validation;

public enum ValidationPattern{
	EMAIL_PATTERN("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
	PASSWORD_PATTERN("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"),

	;

	private final String string;
	ValidationPattern(String string){
		this.string = string;
	}
	@Override
	public String toString(){
		return string;
	}
}
