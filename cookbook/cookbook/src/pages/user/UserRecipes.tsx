import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {useEffect, useState} from "react";
import {IRecipe} from "../../assets/models/Recipe";
import {toast} from "react-toastify";
import RecipeCard from "../../components/recipes/RecipeCard";
import {IUser} from "../../assets/models/Authentication";

interface IUserRecipesProps {
    user: IUser;
}
// TODO: Maybe delete it???
function UserRecipes({user}: IUserRecipesProps){
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
                    // toast.error(error.response);
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
                    <RecipeCard recipe={recipe} username={user?.username} key={index}/>
                </div>
            })}

        </div>
    );
}

export default UserRecipes;