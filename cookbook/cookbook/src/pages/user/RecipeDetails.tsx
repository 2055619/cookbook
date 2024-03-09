import {IRecipe} from "../../assets/models/Recipe";
import {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IUser} from "../../assets/models/Authentication";

interface IRecipeDetailsProps {
    user: IUser;
}

function RecipeDetails({user}: IRecipeDetailsProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const [recipe, setRecipe] = useState<IRecipe>();

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');

        if (title !== null) {
            cookbookService.getRecipe(title)
                .then((response) => {
                    setRecipe(response);
                })
                .catch((error) => {
                    toast.error("error")
                    toast.error(t(error.response?.data.message));
                });
        }
    }, []);


    return (
        <div>
            <h1>Recipe Details</h1>
        </div>
    );
}

export default RecipeDetails;