package quixotic.projects.cookbook.validation;

public enum ValidationMessage{
	PASSWORD_MESSAGE("exception.passwordMessage"),
	EMAIL_MESSAGE("exception.emailMessage"),

	;
	private final String string;
	ValidationMessage(String string){
		this.string = string;
	}
	@Override
	public String toString(){
		return string;
	}

}