import {IRecipe} from "../../assets/models/Recipe";
import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IUser} from "../../assets/models/Authentication";
import pastaImg from "../../assets/image/red-sauce-pasta-recipe.jpg";

interface IRecipeDetailsProps {
    user: IUser;
}

function RecipeDetails({user}: IRecipeDetailsProps) {
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

    function timeConversion(minutes: number) {
        if (minutes > 60) {
            return Math.floor(minutes / 60) + " " + t('hours') + " " + minutes % 60 + " " + t('minutes');
        }
        return minutes + " " + t('minutes');
    }


    return (
        <div className="w-full flex flex-col h-full justify-between pb-5">

            <div className={"w-11/12 mx-auto"}>
                <h1 className="text-8xl font-semibold mt-0 pt-0">{recipe?.title}</h1>
                <p className="text-3xl">{recipe?.description}</p>

                <img className={"w-full md:w-3/5 mx-auto"} src={pastaImg} alt={recipe?.title}/>

                <div className="grid mx-auto grid-cols-3 gap-1 mt-1">
                    <p>
                        <span className={"me-1"}>{t('category')} :</span>
                        <span className="text-2xl">{t(recipe?.category)}</span>
                    </p>
                    <p>
                        <span className={"me-1"}>{t('difficulty')} :</span>
                        <span className="text-2xl">{t(recipe?.difficulty)}</span>
                    </p>
                    <p>
                        <span className={"me-1"}>{t('portionSize')} :</span>
                        <span className="text-2xl">{recipe?.serving} {t(recipe?.portionSize)}</span>
                    </p>
                </div>
                <div className="grid grid-cols-2 gap-1 mt-1">
                    <p className={""}>
                        <span className={"me-1"}>{t('prepTime')} :</span>
                        <span className="text-2xl">{timeConversion(recipe!.prepTime)}</span>
                    </p>
                    <h1 className={""}>
                        <span className={"me-1"}>{t('cookTime')} :</span>
                        <span className="text-2xl">{timeConversion(recipe!.cookTime)}</span>
                    </h1>
                </div>

                <h1 className="">{t('dietTypes')}:</h1>
                {recipe?.dietTypes.map((dietType, index) => (
                    <p key={index} className="inline-block mx-1 text-2xl">{t(dietType)}, </p>
                ))}
                <h1 className="">{t('ingredients')}:</h1>
                <ul>
                    {recipe?.ingredients.map((ingredient, index) => (
                        <li key={index}
                            className="text-2xl">{ingredient.name} : {ingredient.quantity} {ingredient.unit}</li>
                    ))}
                </ul>

                <h1 className="">{t('instructions')}:</h1>
                <ol>
                    {recipe?.instructions.map((instruction, index) => (
                        <li key={index} className="text-2xl">{instruction}</li>
                    ))}
                </ol>

            </div>


            {/*<footer className={"py-2"}>*/}
            {/*    <ReactionFooter recipeTitle={recipe?.title}/>*/}
            {/*</footer>*/}
        </div>
    );
}

export default RecipeDetails;