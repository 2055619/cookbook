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
    const [visibility, setVisibility] = useState('SECRET');
    const [portionSize, setPortionSize] = useState('');
    const [dietTypes, setDietTypes] = useState<string[]>([]);

    const [allCategories, setAllCategories] = useState<string[]>([]);
    const [allDifficulties, setAllDifficulties] = useState<string[]>([]);
    const [allVisibility, setAllVisibility] = useState<string[]>([]);
    const [allPortionSizes, setAllPortionSizes] = useState<string[]>([]);
    const [allDietTypes, setAllDietTypes] = useState<string[]>([]);

    const [units, setUnits] = useState({SOLID: [''], LIQUID: [''], POWDER: [''], OTHER: ['']});

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

        utilsService.getIngrediantStates().then((response) => {
            setUnits(response);
        }).catch((error) => {
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

        cookbookService.createRecipe(newRecipe)
            .then(() => {
                toast.success(t('recipeCreated'));
            })
            .catch((error) => {
                toast.error(t(error.response.data.message));
            });
    };

    const handleDietTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.checked) {
            setDietTypes([...dietTypes, event.target.value]);
        } else {
            setDietTypes(dietTypes.filter(dietType => dietType !== event.target.value));
        }
    };


    const handleInstructionChange = (index: number, newInstruction: string) => {
        const newInstructions = [...instructions];
        newInstructions[index] = newInstruction;
        setInstructions(newInstructions);
    };

    const addInstruction = () => {
        setInstructions([...instructions, '']);
    };

    const removeInstruction = (index: number) => {
        const newInstructions = [...instructions];
        newInstructions.splice(index, 1);
        setInstructions(newInstructions);
    };


    const handleIngredientChange = (index: number, newIngredient: string) => {
        const newIngredients = [...ingredients];
        newIngredients[index] = {...newIngredients[index], name: newIngredient};
        setIngredients(newIngredients);
    };

    const addIngredient = () => {
        setIngredients([...ingredients, {name: '', quantity: 0, unit: 'CUP', state: 'OTHER'}]);
    };

    const removeIngredient = (index: number) => {
        const newIngredients = [...ingredients];
        newIngredients.splice(index, 1);
        setIngredients(newIngredients);
    };


    function handleIngredientQuantityChange(index: number, number: number) {
        const newIngredients = [...ingredients];
        newIngredients[index] = {...newIngredients[index], quantity: number};
        setIngredients(newIngredients);
    }


    function handleIngredientStateChange(index: number, value: string) {
        const newIngredients = [...ingredients];
        newIngredients[index] = {...newIngredients[index], state: value};
        setIngredients(newIngredients);
    }

    function handleIngredientUnitChange(index: number, value: string) {
        const newIngredients = [...ingredients];
        newIngredients[index] = {...newIngredients[index], unit: value};
        setIngredients(newIngredients);
    }

    return (
        <div className={""}>
            <h1 className="text-3xl font-bold text-center">{t('createRecipe')}</h1>
            <form onSubmit={handleSubmit}
                  className="flex flex-col items-center space-y-4 w-2/3 max-w-full mx-auto p-4">
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

                <div className="grid grid-cols-2 gap-4 w-full">
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('prepTime')}</span>
                        <input type="number" value={prepTime} onChange={e => setPrepTime(Number(e.target.value))}
                               className="border-2 border-gray-200 p-2 rounded"/>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('cookTime')}</span>
                        <input type="number" value={cookTime} onChange={e => setCookTime(Number(e.target.value))}
                               className="border-2 border-gray-200 p-2 rounded"/>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('serving')}</span>
                        <input type="number" value={serving} onChange={e => setServing(Number(e.target.value))}
                               className="border-2 border-gray-200 p-2 rounded"/>
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

                <div className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('instructions')}</span>
                    {instructions.map((instruction, index) => (
                        <div key={index} className="flex space-x-2 items-center">
                            <span>{index + 1}.</span>
                            <input type="text" value={instruction}
                                   onChange={e => handleInstructionChange(index, e.target.value)}
                                   placeholder={t('input.ingredient')}
                                   className="border-2 border-gray-200 p-2 rounded flex-grow"/>
                            <button onClick={() => removeInstruction(index)}
                                    className="border-2 border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-1">
                                {t('input.delete')}
                            </button>
                        </div>
                    ))}
                    <button onClick={addInstruction}
                            className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                        {t('addInstruction')}
                    </button>
                </div>

                <div className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('ingredients')}</span>
                    {ingredients.map((ingredient, index) => (
                        <div key={index} className="flex space-x-2 items-center">
                            <input type="text" value={ingredient.name}
                                   onChange={e => handleIngredientChange(index, e.target.value)}
                                   placeholder={t('input.instruction')}
                                   className="border-2 border-gray-200 p-2 rounded flex-grow"/>
                            <input type="number" value={ingredient.quantity}
                                   onChange={e => handleIngredientQuantityChange(index, Number(e.target.value))}
                                   className="border-2 border-gray-200 p-2 rounded w-20"
                                   min="0" step="0.01"/>
                            <select value={ingredient.state}
                                    onChange={e => handleIngredientStateChange(index, e.target.value)}
                                    className="border-2 border-gray-200 p-2 rounded">
                                {Object.keys(units).map((state, stateIndex) => (
                                    <option key={stateIndex} value={state}>{t(state)}</option>
                                ))}
                            </select>
                            <select value={ingredient.unit}
                                    onChange={e => handleIngredientUnitChange(index, e.target.value)}
                                    className="border-2 border-gray-200 p-2 rounded">
                                {units[ingredient.state as 'SOLID' | 'LIQUID' | 'POWDER' | 'OTHER'].map((unit, unitIndex) => (
                                    <option key={unitIndex} value={unit}>{t(unit)}</option>
                                ))}
                            </select>
                            <button onClick={() => removeIngredient(index)}
                                    className="border-2 border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-1">{t('input.delete')}
                            </button>
                        </div>
                    ))}
                    <button onClick={addIngredient} className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                        {t('addIngredient')}
                    </button>
                </div>

                <div className="grid grid-cols-3 gap-4 w-full">
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
                </div>
                <div className="grid grid-cols-3 gap-4 gap-x-5">
                    <span className="font-medium col-span-3">{t('dietTypes')}</span>
                    {allDietTypes.map((dietType, index) => (
                        <label key={index} className="inline-flex items-center">
                            <input type="checkbox" value={dietType} onChange={handleDietTypeChange}
                                   className="form-checkbox sr-only"/>
                            <div className="w-4 h-4 border-2 border-gray-300 rounded-md mr-2"></div>
                            <span className="ml-2">{t(dietType)}</span>
                        </label>
                    ))}
                </div>


                <button type="submit"
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                    {t('createRecipe')}
                </button>
            </form>
        </div>
    );
};

export default RecipeCreation;