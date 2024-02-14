
export interface UserDTO {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    preferredUnit: string;
    role: string;
    token: string;
}

export interface signIn {
    username: string;
    password: string;
}

export interface signUp {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    password: string;
    preferredUnit: string;
}