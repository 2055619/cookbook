import {cookServerInstance} from "../App";
import {IsignIn, IsignUp, IUser} from "../assets/models/Authentication";
import {IRecipe} from "../assets/models/Recipe";

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

    async getRecipes(page: number) {
        return cookServerInstance.get<IRecipe[]>('/cook/recipes?page=' + page + '&size=10')
            .then((response) => {
                return response.data;
            });
    }

    async getUser() {
        return cookServerInstance.get<IUser>('/cook/auth/me').then((response) => {
            return response.data;
        });
    }

    async getRecipeByTitle(searchValue: string) {
        return cookServerInstance.get<IRecipe[]>('/cook/recipes/title?title=' + searchValue)
            .then((response) => {
                return response.data;
            });
    }

    async createRecipe(newRecipe: IRecipe) {
        return cookServerInstance.post('/cook/recipe', newRecipe)
            .then((response) => {
                return response.data;
            });
    }

    async getUserRecipes() {
        return cookServerInstance.get('/cook/usr/recipes')
            .then((response) => {
                return response.data;
            });
    }

    async getRecipe(title: string) {
        return cookServerInstance.get<IRecipe>(`/cook/recipe/${title.trim()}`,)
            .then((response) => {
                return response.data;
            });
    }

    async updateRecipe(newRecipe: IRecipe) {
        return cookServerInstance.put('/cook/recipe', newRecipe)
            .then((response) => {
                return response.data;
            });

    }
}
