import {IRecipe} from "../../assets/models/Recipe";
import pastaImg from "../../assets/image/red-sauce-pasta-recipe.jpg";
import ReactionFooter from "./ReactionFooter";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEllipsisV} from "@fortawesome/free-solid-svg-icons/faEllipsisV";
import {toast} from "react-toastify";
import {Modal} from "react-bootstrap";
import LanguageSelector from "../Utils/LanguageSelector";
import {useNavigate} from "react-router-dom";

interface IUserRecipeComponentProps {
    recipe: IRecipe;
}

function UserRecipeComponent({recipe}: IUserRecipeComponentProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);

    useEffect(() => {
        document.addEventListener('click', closePopup);
        return () => {
            document.removeEventListener('click', closePopup);
        };
    }, []);

    function togglePopup(e: React.MouseEvent) {
        e.stopPropagation();
        setShowPopup(!showPopup);
    }

    function closePopup() {
        setShowPopup(false);
    }
    function handlePopupClick(e: React.MouseEvent) {
        e.stopPropagation();
    }

    const recipeEstimatedTimeInHours = () => {
        const time = recipe.prepTime + recipe.cookTime;
        if (time > 60) {
            return Math.floor(time / 60) + " " + t('hours') + " " + time % 60 + " " + t('minutes');
        }
        return time + " " + t('minutes');
    }

    return (
        <div className="border rounded-lg px-4 my-2 lg:w-3/5 w-full flex flex-col h-full justify-between">
            <div className={""}>
                <div className={"relative text-end pb-0"}>
                    <FontAwesomeIcon className={"mt-2 px-2 clickable hover:bg-cook-red hover:rounded-full p-2"}
                                     onClick={togglePopup} icon={faEllipsisV}/>
                    {showPopup && (
                        <div
                            onClick={handlePopupClick}
                            className="absolute right-0 w-1/5 border rounded shadow-xl bg-cook-light">
                            <div className="text-center p-2 m-0 bg-cook-light border-2 border-cook-orange">
                                <button className={"w-full"}>TEST</button>
                                <button className={"w-full"}>TEST</button>
                                <button className={"w-full"}>TEST</button>
                                <button className={"w-full"}>TEST</button>
                            </div>
                        </div>
                    )}
                </div>
                <h1 className="text-5xl font-semibold mt-0 pt-0">{recipe.title}</h1>
                <p className="text-2xl">{recipe.description}</p>

                <div className="grid grid-cols-4 gap-1 mt-1">
                    <p>{t(recipe.category)}</p>
                    <p>{t(recipe.difficulty)}</p>
                    <p>{recipe.serving} {t(recipe.portionSize)}</p>
                    <p>{recipeEstimatedTimeInHours()}</p>
                </div>

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

export default UserRecipeComponent;