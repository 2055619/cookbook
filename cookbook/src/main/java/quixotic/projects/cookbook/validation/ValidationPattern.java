package quixotic.projects.cookbook.validation;

public enum ValidationPattern{
	EMAIL_PATTERN("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
	PASSWORD_PATTERN("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"),
	USERNAME_PATTERN("^[A-Za-z0-9._%+-]{3,}$"),
	NAME_PATTERN("^.{3,}$"),
	PHONE_NUMBER_PATTERN("^[0-9]{9,}$"),
    DESCRIPTION_PATTERN("^.{10,}$"),
    COMMENT_PATTERN("^.{1,}$"),;

	private final String string;
	ValidationPattern(String string){
		this.string = string;
	}
	@Override
	public String toString(){
		return string;
	}
}
