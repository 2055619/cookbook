import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import React, {useEffect, useState} from "react";
import {IRecipe} from "../../assets/models/Publication";
import {toast} from "react-toastify";
import RecipeCard from "../../components/recipes/RecipeCard";
import {IUser} from "../../assets/models/Authentication";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";
import {useNavigate} from "react-router-dom";
import Loading from "../../components/Utils/Loading";

interface IUserRecipesProps {
    user: IUser;
}

function RecipesList({user}: IUserRecipesProps){
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const navigate = useNavigate();

    const [recipes, setRecipes] = useState<IRecipe[]>([]);

    useEffect(() => {
        const loadRecipes = async () => {
            await cookbookService.getSavedUserRecipes()
                .then((response) => {
                    setRecipes(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                    return [];
                });
        };
        loadRecipes();
    }, []);

    return (
        <div className={"pb-10"}>
            <div className={"text-start ms-1 sticky top-14"}>
                <button onClick={() => navigate('/u/landing')}
                        className="clickable hover:bg-cook-red hover:rounded-full px-2 py-1">
                    <FontAwesomeIcon icon={faArrowLeft}/>
                </button>
            </div>

            <h1 className={"text-5xl mb-5"}>{t('savedRecipesList')}</h1>
            {
                recipes.length === 0 ?
                    <h1 className={"text-4xl mt-10"}>{t('noSavedRecipes')}</h1> :
                    <div className={"grid grid-cols-1 gap-4"}>
                        {
                            recipes.map((recipe, index) => {
                                // return <div className={`flex justify-center `} key={index}>
                                return <div className={`border rounded-lg px-4 mx-auto my-2 lg:w-3/5 w-full flex flex-col h-full justify-between `} key={index}>
                                    <RecipeCard recipe={recipe} username={user?.username} key={index}/>
                                </div>
                            })
                        }
                    </div>
            }
            <Loading/>
        </div>
    );
}

export default RecipesList;