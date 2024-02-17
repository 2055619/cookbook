import {cookServerInstance} from "../App";
import {IsignIn, IsignUp, IUser} from "../assets/models/Authentication";

export class CookBookService {
    async signIn(user: IsignIn) {
        return cookServerInstance.post<IUser>('/cook/auth/signin', user)
            .then((response) => {
            return response.data;
        });
    }

    async signUp(user: IsignUp) {
        return cookServerInstance.post<IUser>('/cook/auth/signup', user)
            .then((response) => {
            return response.data;
        });
    }
}
