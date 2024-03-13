import {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {IIngredient, IRecipe} from "../../assets/models/Recipe";

function ConcoctRecipe() {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const [recipe, setRecipe] = useState<IRecipe>(
        {
            title: "",
            description: "",
            visibility: "",
            cookUsername: "",
            category: "",
            difficulty: "",
            serving: 0,
            portionSize: "",
            prepTime: 0,
            cookTime: 0,
            dietTypes: [],
            ingredients: [],
            instructions: []
        }
    );

    const [ingredientUnits, setIngredientUnits] = useState<{[key: string]: string}>({});
    const [checkedIngredients, setCheckedIngredients] = useState<string[]>([]);

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');

        if (title) {
            cookbookService.getRecipe(title)
                .then((response) => {
                    setRecipe(response);
                })
                .catch((error) => {
                    toast.error("error")
                    toast.error(t(error.response?.data.message));
                });
        } else {
            toast.error(t('noRecipeTitle'));
            window.history.back();
        }
    }, []);

    const handleUnitChange = (ingredient: IIngredient, unit: string) => {
        // Here you can add the logic to calculate the equivalent amount in the selected unit
        // For simplicity, let's assume that the equivalent amount is the same as the original amount
        const equivalentAmount = ingredient.quantity;

        setIngredientUnits(prevState => ({
            ...prevState,
            [ingredient.name]: `${equivalentAmount} ${unit}`
        }));
    };

    const handleCheck = (ingredient: IIngredient) => {
        if (checkedIngredients.includes(ingredient.name)) {
            setCheckedIngredients(checkedIngredients.filter(i => i !== ingredient.name));
        } else {
            setCheckedIngredients([...checkedIngredients, ingredient.name]);
        }
    };

    return (
        <div>
            <h1 className={"text-9xl"}>{recipe.title}</h1>

            <ul>
                {recipe.ingredients.filter(ingredient => !checkedIngredients.includes(ingredient.name)).map((ingredient, index) => (
                    <li key={index}>
                        <input type="checkbox" id={`ingredient-${index}`} onChange={() => handleCheck(ingredient)}/>
                        <label htmlFor={`ingredient-${index}`}>{ingredient.name}</label>
                        {/* Rest of the ingredient item */}
                    </li>
                ))}
            </ul>
            <div className="mt-4">
                <h2>Checked Ingredients</h2>
                <ul>
                    {recipe.ingredients.filter(ingredient => checkedIngredients.includes(ingredient.name)).map((ingredient, index) => (
                        <li key={index} className="text-gray-500">
                            <input type="checkbox" id={`ingredient-${index}`} checked
                                   onChange={() => handleCheck(ingredient)}/>
                            <label htmlFor={`ingredient-${index}`}>{ingredient.name}</label>
                            {/* Rest of the ingredient item */}
                        </li>
                    ))}
                </ul>
            </div>

        </div>
    );
}

export default ConcoctRecipe;