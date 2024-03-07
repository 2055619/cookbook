import {useTranslation} from "react-i18next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEllipsisV} from "@fortawesome/free-solid-svg-icons/faEllipsisV";
import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {IRecipe} from "../../assets/models/Recipe";

interface IRecipeOptionsProps {
    username: string;
    recipe: IRecipe;
}

function RecipeOptions({username, recipe}: IRecipeOptionsProps) {
    const {t} = useTranslation();
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

    function handleEdit() {
        toast.info("Edit");
    }

    function handleDelete() {
        toast.info("Delete");
    }

    function handleSave() {
        toast.info("Save");
    }

    function handleReport() {
        toast.info("Report");
    }

    return (
        <div className={"relative text-end pb-0"}>
            <div className={"flex justify-between items-center"}>
                <p className="mb-0 pb-0">{recipe.cookUsername}</p>
                <FontAwesomeIcon className={"mt-2 px-2 clickable hover:bg-cook-red hover:rounded-full p-2"}
                                 onClick={togglePopup} icon={faEllipsisV}/>
            </div>
            {showPopup && (
                <div
                    onClick={handlePopupClick}
                    className="absolute right-0 w-1/6 border-2 border-cook rounded shadow-xl bg-cook-light">
                    {username === recipe.cookUsername && (
                        <>
                            <button
                                onClick={handleEdit}
                                className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook transition ease-in duration-200 py-1 w-full">
                                {t('input.edit')}
                            </button>
                            <button
                                onClick={handleDelete}
                                className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook transition ease-in duration-200 py-1 w-full">
                                {t('input.delete')}
                            </button>

                        </>
                    )}
                    <button
                        onClick={handleSave}
                        className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook transition ease-in duration-200 py-1 w-full">
                        {t('input.save')}
                    </button>
                    <button
                        onClick={handleReport}
                        className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook transition ease-in duration-200 py-1 w-full">
                        {t('input.report')}
                    </button>
                </div>
            )}
        </div>
    );
}

export default RecipeOptions;