import {cookServerInstance} from "../App";
import {IsignIn, IsignUp, IUser, IUserProfile} from "../assets/models/Authentication";
import {IRecipe} from "../assets/models/Recipe";

export class CookBookService {

    async signIn(user: IsignIn) {
        return cookServerInstance.post<IUser>('/cook/auth/signin', user)
            .then((response) => {
                return response.data;
            })
    }

    async signUp(user: IsignUp) {
        return cookServerInstance.post<IUser>('/cook/auth/signup', user)
            .then((response) => {
                return response.data;
            });
    }

    async getRecipes(page: number) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe[]>('/cook/recipes?page=' + page + '&size=10')
            .then((response) => {
                return response.data;
            });
    }

    async getUser() {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IUser>('/cook/auth/me').then((response) => {
            return response.data;
        });
    }

    async getRecipeByTitle(searchValue: string) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe[]>('/cook/recipes/title?title=' + searchValue)
            .then((response) => {
                return response.data;
            });
    }

    async createRecipe(newRecipe: IRecipe) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.post('/cook/recipe', newRecipe)
            .then((response) => {
                return response.data;
            });
    }

    async getUserRecipes() {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get('/cook/usr/recipes')
            .then((response) => {
                return response.data;
            });
    }

    async getRecipe(title: string) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe>(`/cook/recipe/${title.trim()}`,)
            .then((response) => {
                return response.data;
            });
    }

    async updateRecipe(newRecipe: IRecipe) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: null});

        return cookServerInstance.put('/cook/recipe', newRecipe)
            .then((response) => {
                return response.data;
            });

    }

    async deleteRecipeByTitle(title: string) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.delete(`/cook/recipe/${title.trim()}`)
            .then((response) => {
                return response.data;
            });
    }

    async deleteRecipeById(id: number) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.delete(`/cook/recipe?id=${id}`)
            .then((response) => {
                return response.data;
            });
    }

    async getUserProfile(username: string) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IUser>(`/cook/usr/profile?username=${username}`)
            .then((response) => {
                return response.data;
            });
    }

    async updateProfile(updatedUser: IUserProfile) {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.put('/cook/usr/profile', updatedUser)
            .then((response) => {
                return response.data;
            });
    }

    async getLikedUserRecipes() {
        if (!cookServerInstance.defaults.headers.common['Authorization'])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get('/cook/usr/likedRecipes')
            .then((response) => {
                return response.data;
            });
    }
}
