import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {useEffect, useState} from "react";
import {IRecipe} from "../../assets/models/Recipe";
import {toast} from "react-toastify";
import UserRecipeComponent from "../../components/recipes/UserRecipeComponent";

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
                    toast.error(t(error.response?.data.message));
                    return [];
                });
        };
        loadRecipes();
    }, []);

    return (
        <div className={""}>
            <h1 className={"text-4xl"}>{t('modifyRecipes')}</h1>
            {recipes.map((recipe, index) => {
                return <div className={`flex justify-center`} key={index}>
                    <UserRecipeComponent recipe={recipe} key={index}/>
                </div>
            })}

        </div>
    );
}

export default ViewRecipe;