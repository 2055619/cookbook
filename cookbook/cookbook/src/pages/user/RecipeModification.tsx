import React, {useEffect, useState} from "react";
import {IIngredient, IRecipe} from "../../assets/models/Recipe";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {UtilsService} from "../../services/UtilsService";
import {IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faTrash} from "@fortawesome/free-solid-svg-icons";

interface RecipeModificationProps {
    user: IUser;
}

function RecipeModification({user}: RecipeModificationProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();
    const [recipe, setRecipe] = useState<IRecipe>();


    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [instructions, setInstructions] = useState<string[]>([]);
    const [ingredients, setIngredients] = useState<IIngredient[]>([]);
    const [serving, setServing] = useState(1);
    const [prepTime, setPrepTime] = useState(1);
    const [cookTime, setCookTime] = useState(0);

    const [category, setCategory] = useState('MAIN');
    const [difficulty, setDifficulty] = useState('EASY');
    const [visibility, setVisibility] = useState('PUBLIC');
    const [portionSize, setPortionSize] = useState('MEDIUM');
    const [dietTypes, setDietTypes] = useState<string[]>([]);

    const [allCategories, setAllCategories] = useState<string[]>([]);
    const [allDifficulties, setAllDifficulties] = useState<string[]>([]);
    const [allVisibility, setAllVisibility] = useState<string[]>([]);
    const [allPortionSizes, setAllPortionSizes] = useState<string[]>([]);
    const [allDietTypes, setAllDietTypes] = useState<string[]>([]);

    const [units, setUnits] = useState({SOLID: [''], LIQUID: [''], POWDER: [''], OTHER: ['']});

    const [titleWarning, setTitleWarning] = useState('');
    const [descriptionWarning, setDescriptionWarning] = useState('');
    const [instructionsWarning, setInstructionsWarning] = useState('');
    const [ingredientsWarning, setIngredientsWarning] = useState('');
    const [dietTypeWarning, setDietTypeWarning] = useState('');

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');
        if (title !== null) {
            cookbookService.getRecipe(title)
                .then((response) => {
                    setRecipe(response);

                    setTitle(response.title);
                    setDescription(response.description);
                    setInstructions(response.instructions);
                    setIngredients(response.ingredients);
                    setServing(response.serving);
                    setPrepTime(response.prepTime);
                    setCookTime(response.cookTime);
                    setCategory(response.category);
                    setDifficulty(response.difficulty);
                    setVisibility(response.visibility);
                    setPortionSize(response.portionSize);
                    setDietTypes(response.dietTypes);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }
    }, []);

    useEffect(() => {
        utilsService.getCategories()
            .then((response) => {
                setAllCategories(response);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
            });

        utilsService.getDifficultyLevels()
            .then((response) => {
                setAllDifficulties(response);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
            });

        utilsService.getVisibility()
            .then((response) => {
                setAllVisibility(response);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
            });

        utilsService.getPortionSizes()
            .then((response) => {
                setAllPortionSizes(response);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
            });

        utilsService.getDietTypes()
            .then((response) => {
                setAllDietTypes(response);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
            });

        utilsService.getIngrediantStates().then((response) => {
            setUnits(response);
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
        });
    }, []);

    const isValid = () => {
        let isValid = true;

        if (title.length < 3) {
            setTitleWarning('message.titleLength');
            isValid = false;
        } else {
            setTitleWarning('');
        }

        if (description.length < 10) {
            setDescriptionWarning('message.descriptionLength');
            isValid = false;
        } else {
            setDescriptionWarning('');
        }

        if (instructions.length < 1) {
            setInstructionsWarning('message.instructionsLength');
            isValid = false;
        } else {
            setInstructionsWarning('');
        }

        if (ingredients.length < 1) {
            setIngredientsWarning('message.ingredientsLength');
            isValid = false;
        } else {
            setIngredientsWarning('');
        }

        if (dietTypes.length < 1) {
            setDietTypeWarning('message.dietTypesLength');
            isValid = false;
        } else {
            setDietTypeWarning('');
        }

        return isValid;
    }

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        const newRecipe: IRecipe = {
            id: recipe?.id,
            title,
            description,
            cookUsername: user.username,
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

        if (!isValid()) {
            return;
        }

        if (recipe !== undefined) {
            cookbookService.updateRecipe(newRecipe)
                .then(() => {
                    toast.success(t('recipeUpdated'));
                    window.history.back();
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });

        } else {
            cookbookService.createRecipe(newRecipe)
                .then(() => {
                    toast.success(t('recipeCreated'));
                    window.history.back();
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }
    };

    // Diet
    const handleDietTypeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.checked) {
            setDietTypes([...dietTypes, event.target.value]);
        } else {
            setDietTypes(dietTypes.filter(dietType => dietType !== event.target.value));
        }
    };

    // Instructions
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

    // Ingredients
    const handleIngredientChange = (index: number, newIngredient: string) => {
        const newIngredients = [...ingredients];
        newIngredients[index] = {...newIngredients[index], name: newIngredient};
        setIngredients(newIngredients);
    };

    const addIngredient = () => {
        setIngredients([...ingredients, {name: '', quantity: 0, unit: 'CUP', ingredientState: 'OTHER'}]);
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
        newIngredients[index] = {
            ...newIngredients[index],
            ingredientState: value,
            unit: units[value as 'SOLID' | 'LIQUID' | 'POWDER' | 'OTHER'][0]
        };
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
                  className="flex flex-col items-center space-y-4 md:w-full lg:w-3/4 max-w-full mx-auto p-4">
                <label
                    className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('title')}</span>
                    <input type="text" value={title} onChange={e => setTitle(e.target.value)}
                           placeholder={recipe?.title || t('input.title')}
                           className={`border-2 p-2 rounded ${titleWarning !== '' ? "border-cook-red" : "border-cook-light"}`}/>
                    <h5 className="text-red-500">{t(titleWarning)}</h5>
                </label>
                <label className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('description')}</span>
                    <textarea value={description} onChange={e => setDescription(e.target.value)}
                              placeholder={recipe?.description || t('input.description')}
                              className={`border-2 p-2 rounded ${descriptionWarning !== '' ? "border-cook-red" : "border-cook-light"}`}/>
                    <h5 className="text-red-500">{t(descriptionWarning)}</h5>
                </label>

                <div className="grid grid-cols-2 gap-4 w-full">
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('prepTime')}</span>
                        <input type="number" value={prepTime} onChange={e => setPrepTime(Number(e.target.value))}
                               className="border-2 border-cook-light p-2 rounded" min="1"/>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('cookTime')}</span>
                        <input type="number" value={cookTime} onChange={e => setCookTime(Number(e.target.value))}
                               className="border-2 border-cook-light p-2 rounded" min={`0`}/>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('serving')}</span>
                        <input type="number" value={serving} onChange={e => setServing(Number(e.target.value))}
                               className="border-2 border-cook-light p-2 rounded" min={`1`}/>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('portionSize')}</span>
                        <select value={portionSize} onChange={e => setPortionSize(e.target.value)}
                                className="border-2 border-cook-light p-2 rounded">
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
                                   placeholder={t('input.instruction')}
                                   className="border-2 border-cook-light p-2 rounded flex-grow"/>
                            <button type="button" onClick={() => removeInstruction(index)}
                                    className="border-2 border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-1">
                                <span className={"hidden lg:inline-block"}>{t('input.delete')}</span>
                                <FontAwesomeIcon icon={faTrash} className={"mx-1"}/>
                            </button>
                        </div>
                    ))}
                    <h5 className="text-red-500">{t(instructionsWarning)}</h5>
                    <button type="button" onClick={addInstruction}
                            className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                        {t('addInstruction')}
                    </button>
                </div>

                <div className="flex flex-col space-y-1 md:w-full w-full max-w-full mx-auto">
                    <span className="font-medium">{t('ingredients')}</span>
                    {ingredients.map((ingredient, index) => (
                        <div key={index} className="grid grid-cols-2 gap-4 w-full py-1">
                            <input type="text" value={ingredient.name}
                                   onChange={e => handleIngredientChange(index, e.target.value)}
                                   placeholder={t('input.ingredient')}
                                   className="border-2 border-cook-light p-2 rounded "/>
                            <input type="number" value={ingredient.quantity}
                                   onChange={e => handleIngredientQuantityChange(index, Number(e.target.value))}
                                   className="border-2 border-cook-light p-2 rounded"
                                   min="0" step="0.01"/>
                            <select value={ingredient.ingredientState}
                                    onChange={e => handleIngredientStateChange(index, e.target.value)}
                                    className="border-2 border-cook-light p-2 rounded">
                                {Object.keys(units).map((ingredientState, stateIndex) => (
                                    <option key={stateIndex} value={ingredientState}>{t(ingredientState)}</option>
                                ))}
                            </select>
                            <select value={ingredient.unit}
                                    onChange={e => handleIngredientUnitChange(index, e.target.value)}
                                    className="border-2 border-cook-light p-2 rounded ">
                                {units[ingredient.ingredientState as "SOLID" | "LIQUID" | "POWDER" | "OTHER"] &&
                                    units[ingredient.ingredientState as 'SOLID' | 'LIQUID' | 'POWDER' | 'OTHER'].map((unit, unitIndex) => (
                                        <option key={unitIndex} value={unit}>{t(unit)}</option>
                                    ))}
                            </select>
                            <button type="button" onClick={() => removeIngredient(index)}
                                    className="col-span-2 border-2 border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-1">
                                <span className={""}>{t('input.delete')}</span>
                                <FontAwesomeIcon icon={faTrash} className={"mx-1"}/>
                            </button>
                        </div>
                    ))}
                    <h5 className="text-red-500">{t(ingredientsWarning)}</h5>
                    <button type="button" onClick={addIngredient}
                            className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                        {t('addIngredient')}
                    </button>
                </div>

                <div className="grid grid-cols-3 gap-4 w-full">
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('category')}</span>
                        <select value={category} onChange={e => setCategory(e.target.value)}
                                className="border-2 border-cook-light p-2 rounded">
                            {allCategories.map((cat, index) => (
                                <option key={index} value={cat}>{t(cat)}</option>
                            ))}
                        </select>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('difficulty')}</span>
                        <select value={difficulty} onChange={e => setDifficulty(e.target.value)}
                                className="border-2 border-cook-light p-2 rounded">
                            {allDifficulties.map((diff, index) => (
                                <option key={index} value={diff}>{t(diff)}</option>
                            ))}
                        </select>
                    </label>
                    <label className="flex flex-col space-y-1">
                        <span className="font-medium">{t('visibility')}</span>
                        <select value={visibility} onChange={e => setVisibility(e.target.value)}
                                className="border-2 border-cook-light p-2 rounded">
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
                                   checked={dietTypes.includes(dietType)}
                                   className="form-checkbox sr-only"/>
                            <div className="w-4 h-4 border-2 border-gray-300 rounded-md mr-2"></div>
                            <span className="ml-2">{t(dietType)}</span>
                        </label>
                    ))}
                </div>
                <h5 className="text-red-500">{t(dietTypeWarning)}</h5>

                <button type="submit" onClick={handleSubmit}
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                    {recipe !== undefined ? t('updateRecipe') : t('createRecipe')}
                </button>
            </form>
        </div>
    );
}

export default RecipeModification;