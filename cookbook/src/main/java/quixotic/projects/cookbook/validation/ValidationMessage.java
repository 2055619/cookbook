package quixotic.projects.cookbook.validation;

public enum ValidationMessage{
	PASSWORD_MESSAGE("message.invalidPassword"),
	EMAIL_MESSAGE("message.invalidEmail"),
	USERNAME_MESSAGE("message.invalidUsername"),
	NAME_MESSAGE("message.invalidName"),
	SERVING_MESSAGE("message.invalidServing"),
	COOKTIME_MESSAGE("message.invalidCookTime"),
	PREPTIME_MESSAGE("message.invalidPrepTime"),
	QUANTITY_MESSAGE("message.invalidQuantity"),



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