import React from "react";
import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Recipe";

interface IOtherInfoProps {
    recipe: IRecipe;
}
function OtherInfo({recipe}: IOtherInfoProps) {
    const {t} = useTranslation();

    return (
        <div>
            <h1 className={"text-5xl my-3"}>{t('otherInfo')}</h1>
            <h3 className={"text-2xl mb-2"}>{recipe.description}</h3>
            <div className={"grid grid-cols-5 gap-y-3"}>
                <p>{t('category') + ": " + t(recipe.category)}</p>
                <p>{t('difficulty') + ": " + t(recipe.difficulty)}</p>
                <p>{t('portionSize') + ": " + t(recipe.portionSize)}</p>
                <p>{t('serving') + ": " + recipe.serving}</p>
                <p>{t('visibility') + ": " + t(recipe.visibility)}</p>
                <p className={"col-span-5"}>{t('dietTypes') + ": " + recipe.dietTypes.map(dietType => t(dietType)).join(", ")}</p>
            </div>

        </div>
    )
}

export default OtherInfo;