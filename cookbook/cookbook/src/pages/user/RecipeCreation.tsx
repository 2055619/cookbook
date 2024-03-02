import React, {useEffect, useState} from "react";
import {IIngredient, IRecipe} from "../../assets/models/Recipe";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {UtilsService} from "../../services/UtilsService";
import {IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";

interface RecipeCreationProps {
    user: IUser;
}
function RecipeCreation({user}: RecipeCreationProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [instructions, setInstructions] = useState<string[]>([]);
    const [ingredients, setIngredients] = useState<IIngredient[]>([]);
    const [serving, setServing] = useState(0);
    const [prepTime, setPrepTime] = useState(0);
    const [cookTime, setCookTime] = useState(0);

    const [category, setCategory] = useState('');
    const [difficulty, setDifficulty] = useState('');
    const [visibility, setVisibility] = useState('');
    const [portionSize, setPortionSize] = useState('');
    const [dietTypes, setDietTypes] = useState<string[]>([]);

    const [allCategories, setAllCategories] = useState<string[]>([]);
    const [allDifficulties, setAllDifficulties] = useState<string[]>([]);
    const [allVisibility, setAllVisibility] = useState<string[]>([]);
    const [allPortionSizes, setAllPortionSizes] = useState<string[]>([]);
    const [allDietTypes, setAllDietTypes] = useState<string[]>([]);

    useEffect(() => {
        utilsService.getCategories()
            .then((response) => {
                setAllCategories(response);
            })
            .catch((error) => {
                toast.error(t(error.response.data.message));
            });

        utilsService.getDifficultyLevels()
            .then((response) => {
                setAllDifficulties(response);
            })
            .catch((error) => {
                toast.error(t(error.response.data.message));
            });

        utilsService.getVisibility()
            .then((response) => {
                setAllVisibility(response);
            })
            .catch((error) => {
                toast.error(t(error.response.data.message));
            });

        utilsService.getPortionSizes()
            .then((response) => {
                setAllPortionSizes(response);
            })
            .catch((error) => {
                toast.error(t(error.response.data.message));
            });

        utilsService.getDietTypes()
            .then((response) => {
                setAllDietTypes(response);
            })
            .catch((error) => {
                toast.error(t(error.response.data.message));
            });
    }, []);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const cookUsername = user.username;

        const newRecipe: IRecipe = {
            title,
            description,
            cookUsername,
            visibility,
            instructions,
            ingredients,
            category,
            difficulty,
            serving,
            portionSize,
            dietTypes,
            prepTime,
            cookTime,
        };

        // Do something with newRecipe (e.g., send it to an API)
    };

    const handleDietTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.checked) {
            setDietTypes([...dietTypes, event.target.value]);
        } else {
            setDietTypes(dietTypes.filter(dietType => dietType !== event.target.value));
        }
    };

    return (
        <>
            <h1 className="text-3xl font-bold text-center">{t('createRecipe')}</h1>
            <form onSubmit={handleSubmit} className="flex flex-col items-center space-y-4 w-full max-w-md mx-auto p-4">
                <label className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('title')}</span>
                    <input type="text" value={title} onChange={e => setTitle(e.target.value)}
                           className="border-2 border-gray-200 p-2 rounded"/>
                </label>
                <label className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('description')}</span>
                    <textarea value={description} onChange={e => setDescription(e.target.value)}
                              className="border-2 border-gray-200 p-2 rounded"/>
                </label>

                <label className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('instructions')}</span>
                    <textarea value={instructions.join('\n')} onChange={e => setInstructions(e.target.value.split('\n'))}
                              className="border-2 border-gray-200 p-2 rounded"/>
                </label>



                <div className="grid grid-cols-2 gap-4 w-full">
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('category')}</span>
                        <select value={category} onChange={e => setCategory(e.target.value)}
                                className="border-2 border-gray-200 p-2 rounded">
                            {allCategories.map((cat, index) => (
                                <option key={index} value={cat}>{t(cat)}</option>
                            ))}
                        </select>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('difficulty')}</span>
                        <select value={difficulty} onChange={e => setDifficulty(e.target.value)}
                                className="border-2 border-gray-200 p-2 rounded">
                            {allDifficulties.map((diff, index) => (
                                <option key={index} value={diff}>{t(diff)}</option>
                            ))}
                        </select>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('visibility')}</span>
                        <select value={visibility} onChange={e => setVisibility(e.target.value)}
                                className="border-2 border-gray-200 p-2 rounded">
                            {allVisibility.map((vis, index) => (
                                <option key={index} value={vis}>{t(vis)}</option>
                            ))}
                        </select>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('portionSize')}</span>
                        <select value={portionSize} onChange={e => setPortionSize(e.target.value)}
                                className="border-2 border-gray-200 p-2 rounded">
                            {allPortionSizes.map((portion, index) => (
                                <option key={index} value={portion}>{t(portion)}</option>
                            ))}
                        </select>
                    </label>
                </div>
                <div className="grid grid-cols-3 gap-4 gap-x-40">
                    <span className="font-medium col-span-3">{t('dietTypes')}</span>
                    {allDietTypes.map((dietType, index) => (
                        <label key={index} className="inline-flex items-center">
                            <input type="checkbox" value={dietType} onChange={handleDietTypeChange}
                                   className="form-checkbox"/>
                            <span className="ml-2">{t(dietType)}</span>
                        </label>
                    ))}
                </div>


                <button type="submit"
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">Create
                    Recipe
                </button>
            </form>
        </>
    );
};

export default RecipeCreation;