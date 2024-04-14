import {IRecipe} from "../../assets/models/Publication";
import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IUser} from "../../assets/models/Authentication";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {
    faArrowLeft,
    faBowlFood,
    faGaugeHigh,
    faHourglassEnd,
    faHourglassHalf,
    faLayerGroup
} from "@fortawesome/free-solid-svg-icons";
import ImageCard from "../../components/publications/ImageCard";
import Reactions from "../../components/publications/Reactions";

interface IRecipeDetailsProps {
    user: IUser;
}

function RecipeDetails({user}: IRecipeDetailsProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const [recipe, setRecipe] = useState<IRecipe>(
        {
            id: -1,
            title: "",
            description: "",
            visibility: "",
            cookUsername: "",
            creationDate: "",
            publicationType: "RECIPE",
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
                    if (error.response?.data.message !== "NoToken"){
                        toast.error(t(error.response?.data.message));
                    }
                });
        } else {
            toast.error(t('noRecipeTitle'));
            // window.history.back();
        }
    }, []);

    function timeConversion(minutes: number) {
        if (minutes > 60) {
            return Math.floor(minutes / 60) + " " + t('hours') + " " + minutes % 60;
        }
        return minutes;
    }

    return (
        <div className="w-full flex flex-col h-full justify-between pb-5">
            <div className={"text-start ms-1 sticky top-14"}>
                <button onClick={() => window.history.back()}
                        className="clickable hover:bg-cook-red hover:rounded-full px-2 py-1">
                    <FontAwesomeIcon icon={faArrowLeft}/>
                </button>
            </div>

            <div className="text-center">
                <h1 className="text-8xl font-semibold mt-0 pt-0">{recipe?.title}</h1>
                <p className="text-3xl">{recipe?.description}</p>

                <div className="grid mx-auto grid-cols-3 gap-1 mt-1">
                    <p>
                        <FontAwesomeIcon className={"mx-1"} icon={faLayerGroup}/>
                        <span className={"me-1 hidden lg:inline"}>{t('category')} :</span>
                        <span className="text-2xl">{t(recipe?.category)}</span>
                    </p>
                    <p>
                        <FontAwesomeIcon className={"mx-1"} icon={faGaugeHigh}/>
                        <span className={"me-1 hidden lg:inline"}>{t('difficulty')} :</span>
                        <span className="text-2xl">{t(recipe?.difficulty)}</span>
                    </p>
                    <p>
                        <FontAwesomeIcon className={"mx-1"} icon={faBowlFood}/>
                        <span className={"me-1 hidden lg:inline"}>{t('portionSize')} :</span>
                        <span className="text-2xl">{recipe?.serving} {t(recipe?.portionSize)}</span>
                    </p>
                </div>
                <div className="grid grid-cols-2 gap-1 mt-1">
                    <p className={""}>
                        <FontAwesomeIcon className={"mx-1"} icon={faHourglassHalf}/>
                        <span className={"me-1 hidden lg:inline"}>{t('prepTime')} :</span>
                        <span className="text-2xl">{timeConversion(recipe!.prepTime)} <span
                            className={"hidden sm:inline"}>{t('minutes')}</span>
                            <span className={"inline sm:hidden"}>{t('mins')}</span>
                        </span>
                    </p>
                    <h1 className={""}>
                        <FontAwesomeIcon className={"mx-1"} icon={faHourglassEnd}/>
                        <span className={"me-1 hidden lg:inline"}>{t('cookTime')} :</span>
                        <span className="text-2xl">{timeConversion(recipe!.cookTime)}
                            <span className={"hidden sm:inline"}> {t('minutes')}</span>
                            <span className={"inline sm:hidden"}> {t('mins')}</span>
                        </span>
                    </h1>
                </div>

            </div>

            <div className={`grid grid-cols-${Math.min(recipe.dietTypes.length, 5)} gap-1 mt-1`}>
                {recipe.dietTypes.map((dietType, index) => (
                    <p key={index} className="text-2xl">{t(dietType)}</p>
                ))}
            </div>



            <div className="grid grid-cols-2 gap-1">
                <div>
                    <ImageCard byteArray={recipe.image!} alt={recipe.title} />
                    <Reactions publication={recipe} username={user === null ? "" : user.username} />
                </div>

                <div className="text-start ms-10">
                    <h1 className="text-4xl">{t('ingredients')}:</h1>
                    <ul>
                        {recipe?.ingredients.map((ingredient, index) => (
                            <li key={index} className="text-2xl">
                                <span className={""}>{index + 1}.</span>
                                <span className={"mx-1 text-2xl"}>
                                {ingredient.name} {ingredient.quantity} {t(ingredient.unit)}
                            </span>
                            </li>
                        ))}
                    </ul>

                    <h1 className="text-4xl mt-4">{t('instructions')}:</h1>
                    <ol>
                        {recipe?.instructions.map((instruction, index) => (
                            <li key={index} className="text-2xl mt-3">
                                <span className={""}>{index + 1}.</span>
                                <span className={"mx-1"}>{instruction}</span>
                            </li>
                        ))}
                    </ol>
                </div>
            </div>
        </div>
    );
}

export default RecipeDetails;