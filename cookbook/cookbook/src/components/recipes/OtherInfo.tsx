import React from "react";
import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Recipe";

interface IOtherInfoProps {
    recipe: IRecipe;
}

function OtherInfo({recipe}: IOtherInfoProps) {
    const {t} = useTranslation();

    return (
        <div className={"mt-20 pb-10"}>
            <h1 className={"text-7xl my-3"}>{t('otherInfo')}</h1>
            <h3 className={"text-2xl mb-2"}>{recipe.description}</h3>
            <div className={"grid grid-cols-5 gap-y-3"}>
                <p>{t('category') + ": " + t(recipe.category)}</p>
                <p>{t('difficulty') + ": " + t(recipe.difficulty)}</p>
                <p>{t('portionSize') + ": " + t(recipe.portionSize)}</p>
                <p>{t('serving') + ": " + recipe.serving}</p>
                <p>{t('visibility') + ": " + t(recipe.visibility)}</p>
                <p className={"col-span-5"}>{t('dietTypes') + ": " + recipe.dietTypes.map(dietType => t(dietType)).join(", ")}</p>
            </div>

            <div className={"grid grid-cols-2 text-start m-3"}>
                <div>
                    <h3 className={"text-4xl "}>{t('ingredients')}</h3>
                    <ol>
                        {recipe.ingredients.map((ingredient, index) => (
                            <li className={""} key={index}>
                                <span className={""}>{index + 1}.</span>
                                <span className={"mx-1 text-2xl"}>{ingredient.name} {ingredient.quantity} {t(ingredient.unit)}</span>
                            </li>
                        ))}
                    </ol>
                </div>
                <div>
                    <h3 className={"text-4xl my-3"}>{t('instructions')}</h3>
                    <ol>
                        {recipe.instructions.map((instruction, index) => (
                            <li className={""} key={index}>
                                    <span className={""}>{index + 1}.</span>
                                    <span className={"mx-1 text-2xl"}>{instruction}</span>
                            </li>
                        ))}
                    </ol>
                </div>


            </div>

        </div>
    )
}

export default OtherInfo;