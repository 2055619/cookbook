import {cookServerInstance} from "../App";
import {IsignIn, IsignUp, IUser, IUserProfile} from "../assets/models/Authentication";
import {IPublication, IReaction, IRecipe, ITrick} from "../assets/models/Publication";

export class CookBookService {

    // Auth / User
    async signIn(user: IsignIn) {
        return cookServerInstance.post<IUser>(`/cook/auth/signin`, user)
            .then((response) => {
                return response.data;
            })
    }

    async signUp(user: IsignUp) {
        return cookServerInstance.post<IUser>(`/cook/auth/signup`, user)
            .then((response) => {
                return response.data;
            });
    }

    async getUser() {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IUser>(`/cook/auth/me`).then((response) => {
            return response.data;
        });
    }

    async getUserProfile(username: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IUser>(`/cook/usr/profile?username=${username}`)
            .then((response) => {
                return response.data;
            });
    }

    async updateProfile(updatedUser: IUserProfile) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.put(`/cook/usr/profile`, updatedUser)
            .then((response) => {
                return response.data;
            });
    }

    // Recipe
    async getRecipes(page: number) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe[]>(`/pubs/recipes?page=${page}&size=10`)
            .then((response) => {
                return response.data;
            });
    }

    async getRecipeByTitle(searchValue: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe[]>(`/pubs/recipes/title?title=${searchValue}`)
            .then((response) => {
                return response.data;
            });
    }

    async createRecipe(newRecipe: IRecipe) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.post(`/pubs/recipe`, newRecipe)
            .then((response) => {
                return response.data;
            });
    }

    async getUserRecipes() {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get(`/pubs/usr/recipes`)
            .then((response) => {
                return response.data;
            });
    }

    async getRecipe(title: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe>(`/pubs/recipe/${title.trim()}`,)
            .then((response) => {
                return response.data;
            });
    }

    async updateRecipe(newRecipe: IRecipe) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: null});

        return cookServerInstance.put(`/pubs/recipe`, newRecipe)
            .then((response) => {
                return response.data;
            });

    }

    async deleteRecipeByTitle(title: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.delete(`/pubs/recipe/${title.trim()}`)
            .then((response) => {
                return response.data;
            });
    }

    async deleteRecipeById(id: number) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.delete(`/pubs/recipe?id=${id}`)
            .then((response) => {
                return response.data;
            });
    }

    async getSavedUserRecipes() {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get(`/pubs/usr/SavedRecipes`)
            .then((response) => {
                return response.data;
            });
    }

    async saveRecipe(id: number) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.post(`/pubs/usr/save?id=${id}`)
            .then((response) => {
                return response.data;
            });
    }

    // Trick
    async createTrick(trick: ITrick) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.post(`/pubs/trick`, trick)
            .then((response) => {
                return response.data;
            });
    }

    async getTrick(title: string) {
        return cookServerInstance.get<ITrick>(`/pubs/trick/${title}`)
            .then((response) => {
                return response.data;
            });
    }

    async updateTrick(trick: ITrick) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: null});

        return cookServerInstance.put(`/pubs/trick`, trick)
            .then((response) => {
                return response.data;
            });
    }

    async deleteTrickById(id: number) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: null});

        return cookServerInstance.delete(`/pubs/trick?id=${id}`)
            .then((response) => {
                return response.data;
            });
    }

    // Publications
    async getPublications(page: number) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IRecipe[]>(`/pubs/publications?page=${page}&size=10`)
            .then((response) => {
                return response.data;
            });
    }

    async getPublication(title: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IPublication>(`/pubs/publication/${title}`)
            .then((response) => {
                return response.data;
            });
    }

    async deletePublicationById(id: number) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.delete(`/pubs/publication?id=${id}`).then((response) => {
            return response.data;
        });
    }

    // Reactions / Comments / Follows
    async getReactionsByPublication(publication: IPublication) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});
        if (publication === null || publication.id === null || publication.id === -1)
            return Promise.reject({message: "No Publication ID"});

        return cookServerInstance.get<IReaction[]>(`/cook/reactions/${publication.id}`)
            .then((response) => {
                return response.data;
            });
    }

    async reactPublication(reaction: IReaction) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.post(`/cook/react`, reaction)
            .then((response) => {
                return response.data;
            });
    }

    async unfollow(username: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.delete(`/cook/usr/unfollow?username=${username}`)
            .then((response) => {
                return response.data;
            });
    }

    async follow(username: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.put(`/cook/usr/follow?username=${username}`)
            .then((response) => {
                return response.data;
            });
    }

    async getFollowers(username: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IUser[]>(`/cook/usr/followers?username=${username}`)
            .then((response) => {
                return response.data;
            });
    }

    async getFollowing(username: string) {
        if (!cookServerInstance.defaults.headers.common[`Authorization`])
            return Promise.reject({response: {data: {message: "NoToken"}}});

        return cookServerInstance.get<IUser[]>(`/cook/usr/following?username=${username}`)
            .then((response) => {
                return response.data;
            });
    }
}
