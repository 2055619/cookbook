import {IRecipe} from "../assets/models/Recipe";

interface RecipeCardProps {
    recipe: IRecipe;
}

function RecipeComponent({recipe}: RecipeCardProps) {

    return (
        <div className="card border rounded-lg px-4 py-2 m-2">
            <p className="text-left ">{recipe.cookUsername}</p>
            <h5 className="text-5xl font-semibold">{recipe.title}</h5>
            <p className="text-2xl">{recipe.description}</p>
            <p className="">Difficulty: {recipe.difficulty}</p>
            <h6 className="card-subtitle mb-2 text-muted">Category: {recipe.category}</h6>
            <p className="card-text">Serving: {recipe.serving}</p>
            <p className="card-text">Portion Size: {recipe.portionSize}</p>
            <h6 className="card-subtitle mb-2 text-muted">Diet Types:</h6>
            <ul>
                {recipe.dietTypes.map((dietType, index) => (
                    <li key={index} className=" text-sm">{dietType}</li>
                ))}
            </ul>
            <h6 className="card-subtitle mb-2 text-muted">Ingredients:</h6>
            <ul>
                {recipe.ingredients.map((ingredient, index) => (
                    <li key={index} className=" text-sm">{ingredient.name}</li>
                ))}
            </ul>
            <h6 className="card-subtitle mb-2 text-muted">Instructions:</h6>
            <ol>
                {recipe.instructions.map((instruction, index) => (
                    <li key={index} className=" text-sm">{instruction}</li>
                ))}
            </ol>
            <p className="card-text">Prep Time: {recipe.prepTime} minutes</p>
            <p className="card-text">Cook Time: {recipe.cookTime} minutes</p>
        </div>
    );

    // return (
    //     <div className="border rounded-lg p-4 m-2 max-w-sm">
    //         <h2 className="font-bold text-xl mb-2">{recipe.title}</h2>
    //         <p className="text-gray-700 text-base">{recipe.description}</p>
    //         <div className="mt-2">
    //             <h3 className="font-bold text-lg">Diet Types:</h3>
    //             <ul>
    //                 {recipe.dietTypes.map((dietType, index) => (
    //                     <li key={index} className=" text-sm">{dietType}</li>
    //                 ))}
    //             </ul>
    //         </div>
    //     </div>
    // );
}

export default RecipeComponent;