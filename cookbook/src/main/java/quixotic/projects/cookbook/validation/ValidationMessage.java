package quixotic.projects.cookbook.validation;

public enum ValidationMessage{
	PASSWORD_MESSAGE("message.invalidPassword"),
	EMAIL_MESSAGE("message.invalidEmail"),
	USERNAME_MESSAGE("message.invalidUsername"),
	NAME_MESSAGE("message.invalidName"),
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