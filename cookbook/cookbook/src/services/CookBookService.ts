import {cookServerInstance} from "../App";
import {IsignIn, IsignUp, IUser} from "../assets/models/Authentication";

export class CookBookService {
    async getRecipes() {
        const recipes = await fetch('/api/recipes');
        return recipes.json();
    }

    async signIn(user: IsignIn) {
        return cookServerInstance.post<IUser>('/auth/signin', user)
            .then((response) => {
            return response.data;
        });
    }

    async signUp(user: IsignUp) {
        return cookServerInstance.post<IUser>('/auth/signup', user)
            .then((response) => {
            return response.data;
        });
    }
}
