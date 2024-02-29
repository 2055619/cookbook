import {IUser} from "./Authentication";

export interface IRecipe {
    id: number;
    title: string;
    description: string;
    cook: IUser;
    visibility: string;
    instructions: string[];
    ingredients: string[];
    category: string;
    difficulty: string;
    serving: number;
    portionSize: string;
    dietTypes: string[];
    prepTime: number;
    cookTime: number;
}