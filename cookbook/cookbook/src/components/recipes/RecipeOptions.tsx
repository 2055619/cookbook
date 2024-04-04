import {useTranslation} from "react-i18next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEllipsisV} from "@fortawesome/free-solid-svg-icons/faEllipsisV";
import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {IRecipe} from "../../assets/models/Publication";
import {useNavigate} from "react-router-dom";
import {CookBookService} from "../../services/CookBookService";
import {faPlay} from "@fortawesome/free-solid-svg-icons";

interface IRecipeOptionsProps {
    username: string;
    recipe: IRecipe;
}

function RecipeOptions({username, recipe}: IRecipeOptionsProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const cookbookService = new CookBookService();
    const [showPopup, setShowPopup] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);


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
        navigate(`/u/recipesModification?title=${recipe.title}`);
    }

    function handleDelete() {
        setShowDeleteModal(true); // Open the delete confirmation modal
    }

    function handleSave() {
        cookbookService.saveRecipe(recipe.id!)
            .then(r => {
                toast.success(t('input.saved'))
            })
            .catch(e => {
                toast.error(t(e.response?.data.message));
            })
    }

    function handleReport() {
        toast.info("Report");
    }

    function handleViewProfile(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        event.stopPropagation();
        navigate('/u/profile?username=' + recipe.cookUsername)
    }

    function handleConfirmDelete() {
        cookbookService.deleteRecipeById(recipe.id!)
            .then(r => {
                toast.success("Delete")
                setShowDeleteModal(false);
                window.location.reload();
            })
            .catch(e => {
                toast.error(t(e.response?.data.message));
                setShowDeleteModal(false);
            });

    }

    function handleOptionClick(e: React.MouseEvent) {
        e.stopPropagation();
    }

    function handleConcoctionClick() {
        navigate(`/u/concoct?title=${recipe.title}`);
    }

    return (
        <div className={"relative text-end pb-0"} onClick={handleOptionClick}>
            <div className={"flex justify-between items-center"}>
                <button className="mb-0 p-1 clickable hover:bg-cook-red hover:rounded-full"
                        onClick={handleViewProfile}>{recipe.cookUsername}
                </button>

                <button className={"px-1 hover:bg-cook-red hover:rounded-full text-2xl"}
                        onClick={handleConcoctionClick}>
                    <span className={"hidden lg:inline-block"}>{t('concoct')}</span>
                    <FontAwesomeIcon className={"ms-1"} icon={faPlay}/>
                </button>

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
                        {t('input.favorite')}
                    </button>
                    <button
                        onClick={handleReport}
                        className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook transition ease-in duration-200 py-1 w-full">
                        {t('input.report')}
                    </button>
                </div>
            )}

            {showDeleteModal && (
                <div className="fixed z-10 inset-0 overflow-y-auto flex items-center justify-center text-cook">
                    <div className="fixed inset-0 bg-cook opacity-50" onClick={() => setShowDeleteModal(false)}></div>
                    {/* This line adds the grayed out background */}
                    <div
                        className="rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:max-w-lg sm:w-full">
                        <div
                            className="inline-block align-bottom bg-cook rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                            <div className="bg-cook-orange px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                                <div className="sm:flex sm:items-start">
                                    <div className="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                        <h3 className="text-lg leading-6 font-medium">
                                            {t('confirmDelete')}
                                        </h3>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-500">
                                                {t('confirmDeleteDescription')}
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="bg-cook-orange opacity-90 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                                <button type="button"
                                        onClick={handleConfirmDelete}
                                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 ms-5">
                                    {t('input.confirm')}
                                </button>
                                <button type="button"
                                        onClick={() => setShowDeleteModal(false)}
                                        className="border-2 border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-2 ms-5">
                                    {t('input.cancel')}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            )}

        </div>
    );
}

export default RecipeOptions;