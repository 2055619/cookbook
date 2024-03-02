import {IUser} from "./Authentication";

export interface IRecipe {
    title: string;
    description: string;
    cookUsername: string;
    visibility: string;
    instructions: string[];
    ingredients: IIngredient[];
    category: string;
    difficulty: string;
    serving: number;
    portionSize: string;
    dietTypes: string[];
    prepTime: number;
    cookTime: number;
}

export interface IIngredient {
    name: string;
    quantity: number;
    unit: string;
    state: string;
}