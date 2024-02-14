import {cookServerInstance} from "../App";

export class CookBookService {
    async getRecipes() {
        const recipes = await fetch('/api/recipes');
        return recipes.json();
    }

    async authenticate(username: string, password: string) {
        return cookServerInstance.post('/authenticate', {
            username: username,
            password: password
        }).then((response) => {
            return response.data;
        }).catch((error) => {
            return {error: error};
        });
    }
}
