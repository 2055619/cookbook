import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {useCallback, useEffect, useRef, useState} from "react";
import {IRecipe} from "../../assets/models/Recipe";
import {toast} from "react-toastify";
import RecipeComponent from "../../components/RecipeComponent";
import Loading from "../../components/Utils/Loading";

function ViewRecipe(){
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [recipes, setRecipes] = useState<IRecipe[]>([]);


    useEffect(() => {
        const loadRecipes = async () => {
            await cookbookService.getUserRecipes()
                .then((response) => {
                    setRecipes(response);
                })
                .catch((error) => {
                    toast.error(t(error.response.data.message));
                    return [];
                });
        };
        loadRecipes();
    }, []);

    return (
        <div className={"container text-center bg-cook-orange min-h-screen"}>

            {recipes.map((recipe, index) => {
                return <div className={`flex justify-center`} key={index}>
                    <RecipeComponent recipe={recipe} key={index}/>
                </div>
            })}

            <Loading/>
        </div>
    );
}

export default ViewRecipe;