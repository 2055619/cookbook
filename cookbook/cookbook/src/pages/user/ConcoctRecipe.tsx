import {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {IRecipe} from "../../assets/models/Recipe";

function ConcoctRecipe() {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const [recipe, setRecipe] = useState<IRecipe>(
        {
            title: "",
            description: "",
            visibility: "",
            cookUsername: "",
            category: "",
            difficulty: "",
            serving: 0,
            portionSize: "",
            prepTime: 0,
            cookTime: 0,
            dietTypes: [],
            ingredients: [],
            instructions: []
        }
    );

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');

        if (title) {
            cookbookService.getRecipe(title)
                .then((response) => {
                    setRecipe(response);
                })
                .catch((error) => {
                    toast.error("error")
                    toast.error(t(error.response?.data.message));
                });
        } else {
            toast.error(t('noRecipeTitle'));
            window.history.back();
        }
    }, []);


    return (
    <div>
      <h1>Concoct Recipe</h1>
    </div>
  );
}

export default ConcoctRecipe;