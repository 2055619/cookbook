
export interface IUser {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    solidUnit: string;
    liquidUnit: string;
    powderUnit: string;
    otherUnit: string;
    role: string;
    token: string;
}

export interface IsignIn {
    username: string;
    password: string;
}

export interface IsignUp {
    username: string;
    email: string;
    firstName: string;
    lastName: string;
    password: string;
    solidUnit: string;
    liquidUnit: string;
    powderUnit: string;
    otherUnit: string;
}