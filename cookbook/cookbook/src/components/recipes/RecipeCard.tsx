import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Publication";
import React from "react";
import RecipeOptions from "./RecipeOptions";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBowlFood, faGaugeHigh, faHourglass, faLayerGroup} from "@fortawesome/free-solid-svg-icons";
import ImageCard from "../publications/ImageCard";

interface RecipeCardProps {
    recipe: IRecipe;
    username?: string;
}

function RecipeCard({recipe, username}: RecipeCardProps) {
    const {t} = useTranslation();
    const navigate = useNavigate()

    const recipeEstimatedTimeInHours = () => {
        const time = recipe.prepTime + recipe.cookTime;
        if (time > 60) {
            return Math.floor(time / 60) + " " + t('hours') + " " + time % 60 + " " + t('minutes');
        }
        return time + " " + t('minutes');
    }

    function handleViewDetails(e: React.MouseEvent<HTMLDivElement>) {
        e.stopPropagation();
        navigate('/u/recipeDetail?title=' + recipe.title)
    }

    return (
        <div className={"clickable"} onClick={handleViewDetails}>
            <RecipeOptions username={username!} publication={recipe}/>
            <h1 className="text-3xl font-semibold mt-0 pt-0 clickable">{recipe.title}</h1>
            <p className="text-2xl">{recipe.description}</p>

            <div className="grid grid-cols-4 gap-2 mt-1">
                <p><FontAwesomeIcon icon={faLayerGroup}/> <span>{t(recipe.category)}</span></p>
                <p><FontAwesomeIcon icon={faGaugeHigh}/> <span>{t(recipe.difficulty)}</span></p>
                <p><FontAwesomeIcon icon={faBowlFood}/> <span>{recipe.serving} {t(recipe.portionSize)}</span></p>
                <p><FontAwesomeIcon icon={faHourglass}/> <span>{recipeEstimatedTimeInHours()}</span></p>
            </div>

            <div className={`grid grid-cols-${Math.min(recipe.dietTypes.length, 5)} gap-1 mt-1`}>
                {recipe.dietTypes.map((dietType, index) => (
                    <p key={index} className="">{t(dietType)}</p>
                ))}
            </div>

            <ImageCard byteArray={recipe.image!} alt={recipe.title + " Image"}/>
        </div>
    );
}

export default RecipeCard;