
export interface IPublication {
    id: number;
    title: string;
    description: string;
    cookUsername: string;
    creationDate: string;
    visibility: string;
}

export interface ITrick extends IPublication{
    id: number;
    title: string;
    description: string;
    cookUsername: string;
    creationDate: string;
    visibility: string;
}

export interface IRecipe extends IPublication{
    id: number;
    title: string;
    description: string;
    cookUsername: string;
    creationDate: string;
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
    // image: Byte[];
}

export interface IIngredient {
    id?: number;
    name: string;
    quantity: number;
    unit: string;
    ingredientState: string;
}