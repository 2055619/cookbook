import {cookServerInstance} from "../App";
import {IsignIn, IsignUp} from "../assets/models/Authentication";

export class CookBookService {
    async getRecipes() {
        const recipes = await fetch('/api/recipes');
        return recipes.json();
    }

    async signIn(user: IsignIn) {
        return cookServerInstance.post<IsignIn>('/signin', user)
            .then((response) => {
            return response.data;
        });
    }

    async signUp(user: IsignUp) {
        return cookServerInstance.post<IsignUp>('/signup', user)
            .then((response) => {
            return response.data;
        });
    }
}
