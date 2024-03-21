import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Recipe";
import pastaImg from "../../assets/image/red-sauce-pasta-recipe.jpg";
import React from "react";
import ReactionFooter from "./ReactionFooter";
import RecipeOptions from "./RecipeOptions";
import {useNavigate} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBowlFood, faGaugeHigh, faHourglass, faLayerGroup} from "@fortawesome/free-solid-svg-icons";

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
        <div className="border rounded-lg px-4 my-2 lg:w-3/5 w-full flex flex-col h-full justify-between">
            <div className={""} onClick={handleViewDetails}>
                <RecipeOptions username={username!} recipe={recipe}/>
                <h1 className="text-3xl font-semibold mt-0 pt-0 clickable">{recipe.title}</h1>
                <p className="text-2xl">{recipe.description}</p>

                <div className="grid grid-cols-4 gap-2 mt-1">
                    <p><FontAwesomeIcon icon={faLayerGroup}/> <span>{t(recipe.category)}</span></p>
                    <p><FontAwesomeIcon icon={faGaugeHigh}/> <span>{t(recipe.difficulty)}</span></p>
                    <p><FontAwesomeIcon icon={faBowlFood}/> <span>{recipe.serving} {t(recipe.portionSize)}</span></p>
                    <p><FontAwesomeIcon icon={faHourglass}/> <span>{recipeEstimatedTimeInHours()}</span></p>
                </div>

                {/*<h1 className="mb-2 text-muted">Diet Types:</h1>*/}
                <div className={`grid grid-cols-${Math.min(recipe.dietTypes.length, 5)} gap-1 mt-1`}>
                    {recipe.dietTypes.map((dietType, index) => (
                        <p key={index} className="">{t(dietType)}</p>
                    ))}
                </div>

                <img className={"w-full md:w-3/5 mx-auto"} src={pastaImg} alt={recipe.title}/>
            </div>

            <footer className={"py-2"}>
                <ReactionFooter recipeTitle={recipe.title}/>
            </footer>
        </div>
    );
}

export default RecipeCard;