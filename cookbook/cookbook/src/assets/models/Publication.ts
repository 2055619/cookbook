
export interface IPublication {
    id: number;
    title: string;
    description: string;
    cookUsername: string;
    creationDate: string;
    visibility: string;
    averageRating: number;
    publicationType: string;
}
export interface ITrick extends IPublication{
    id: number;
    title: string;
    description: string;
    cookUsername: string;
    creationDate: string;
    visibility: string;
    averageRating: number;
    publicationType: string;
}
export interface IRecipe extends IPublication{
    id: number;
    title: string;
    description: string;
    cookUsername: string;
    creationDate: string;
    visibility: string;
    averageRating: number;
    publicationType: string;
    instructions: string[];
    ingredients: IIngredient[];
    category: string;
    difficulty: string;
    serving: number;
    portionSize: string;
    dietTypes: string[];
    prepTime: number;
    cookTime: number;
    image?: number[];
}

export interface IIngredient {
    id?: number;
    name: string;
    quantity: number;
    unit: string;
    ingredientState: string;
}

export interface IReaction {
    id: number | null;
    rating: number | null;
    comment: IComment | null;
    cookUsername: string;
    publicationId: number;
}
export interface IComment {
    id: number | null;
    content: string;
    creationDate: string;
}