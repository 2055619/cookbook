import {IRecipe} from "../assets/models/Recipe";

interface RecipeCardProps {
    recipe: IRecipe;
}

function RecipeComponent({recipe}: RecipeCardProps) {
    return (
        <div className="border rounded-lg p-4 m-2 max-w-sm">
            <h2 className="font-bold text-xl mb-2">{recipe.title}</h2>
            <p className="text-gray-700 text-base">{recipe.description}</p>
            <div className="mt-2">
                <h3 className="font-bold text-lg">Diet Types:</h3>
                <ul>
                    {recipe.dietTypes.map((dietType, index) => (
                        <li key={index} className="text-gray-700 text-sm">{dietType}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default RecipeComponent;