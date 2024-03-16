import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {IIngredient, IRecipe} from "../../assets/models/Recipe";
import {UtilsService} from "../../services/UtilsService";
import InstructionCard from "../../components/recipes/InstructionCard";
import {IUser} from "../../assets/models/Authentication";
import OtherInfo from "../../components/recipes/OtherInfo";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";

interface IConcoctRecipeProps {
    user: IUser;
}

function ConcoctRecipe({user}: IConcoctRecipeProps) {
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();
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

    const [checkedIngredients, setCheckedIngredients] = useState<string[]>([""]);
    const [ing, setIng] = useState({SOLID: [''], LIQUID: [''], POWDER: [''], OTHER: ['']});
    const [portionSizes, setPortionSizes] = useState<string[]>([]);
    const [portion, setPortion] = useState(1);
    const [ingredientStates, setIngredientStates] = useState<IIngredient[]>([]);

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');

        if (title) {
            cookbookService.getRecipe(title)
                .then((response) => {
                    setRecipe(response);

                    setPortion(response.serving)
                    setIngredientStates(response.ingredients);
                })
                .catch((error) => {
                    // toast.error("error")
                    toast.error(t(error.response?.data.message));
                });
        } else {
            toast.error(t('noRecipeTitle'));
            window.history.back();
        }

        utilsService.getIngrediantStates().then((response) => {
            setIng(response);
        }).catch((error) => {
            toast.error(t(error.response?.data.message));
        });

        utilsService.getPortionSizes().then((response) => {
            setPortionSizes(response);
        }).catch((error) => {
            toast.error(t(error.response?.data.message));
        });
    }, []);

    async function handleUnitChange(ingredient: IIngredient, unit: string) {
        let qty = ingredient.quantity;
        await utilsService.convert(ingredient.quantity, ingredient.unit, unit)
            .then((response) => {
                qty = parseFloat(response.toFixed(2));
            })
            .catch((error) => {
                toast.error(t(error.response?.data.message));
            });


        setRecipe({
            ...recipe,
            ingredients: recipe.ingredients.map(i => i.name === ingredient.name ? {...i, unit: unit, quantity: qty} : i)
        });
    }

    function handleCheck(ingredient: IIngredient) {
        if (checkedIngredients.includes(ingredient.name)) {
            setCheckedIngredients(checkedIngredients.filter(i => i !== ingredient.name));
        } else {
            setCheckedIngredients([...checkedIngredients, ingredient.name]);
        }
    }

    function handleServingsChange(e: React.ChangeEvent<HTMLInputElement>) {
        const value = e.target.value;
        setPortion(parseInt(value));
        setRecipe({
            ...recipe,
            ingredients: recipe.ingredients.map(i => {
                const newQty = parseFloat((parseInt(value) / recipe.serving *
                    ingredientStates.find(ing => ing.name === i.name)!.quantity).toFixed(2));
                return {...i, quantity: newQty}
            })
        });
    }

    function handleSizeChange(e: React.ChangeEvent<HTMLSelectElement>) {
        const value = e.target.value;
        const mod = (portionSizes.indexOf(value) + 3) / (portionSizes.indexOf(recipe.portionSize) + 3);
        setRecipe({
            ...recipe,
            ingredients: recipe.ingredients.map(i => {
                const newQty = parseFloat((mod * i.quantity).toFixed(2));
                return {...i, quantity: newQty}
            }),
            portionSize: value
        });
    }

    function getUnit(ingredient: IIngredient) {
        switch (ingredient.ingredientState) {
            case "SOLID":
                return user.solidUnit;
            case "LIQUID":
                return user.liquidUnit;
            case "POWDER":
                return user.powderUnit
            case "OTHER":
                return user.otherUnit;
            default:
                return ingredient.unit;
        }
    }

    function revisedQuantity(quantity: number) {
        return Math.round(quantity * 4) / 4;
    }

    return (
        <div className={"min-h-screen bg-cook-orange"}>
            <div className="grid grid-cols-4">
                <div className={"text-start ms-1"}>
                    <button onClick={() => window.history.back()}
                            className="clickable hover:bg-cook-red hover:rounded-full px-2 py-1">
                        <FontAwesomeIcon icon={faArrowLeft}/>
                    </button>
                </div>

                <h1 className={"text-9xl col-span-2"}>{recipe.title}</h1>
            </div>

            <p className={"text-2xl my-2"}>{t('prepTime') + ": " + recipe.prepTime}, {t('cookTime') + ": " + recipe.cookTime}</p>

            <h1 className={"text-3xl"}>{t('choosePortion')}</h1>
            <form onSubmit={(e) => e.preventDefault()}>
                <label className={"mx-2"} htmlFor="serving">{t('serving')}</label>
                <input
                    className="border rounded py-2 px-2 mb-3 leading-tight w-1/12"
                    type="number" id="serving"
                    min={1}
                    onChange={handleServingsChange}
                    name="serving" value={portion}/>

                <label className={"mx-2"} htmlFor="portion">{t('portion')}</label>
                <select
                    className={"mx-1 p-1 bg-white border rounded-md shadow-sm focus:border-cook-light text-base"}
                    value={recipe.portionSize}
                    onChange={handleSizeChange}>
                    {
                        portionSizes.map((size, index) => (
                            <option key={index} value={size}>{t(size)}</option>
                        ))
                    }
                </select>
            </form>

            <h1 className={"text-4xl"}>{t('ingredients')}</h1>
            <div className={"grid grid-cols-2 text-center"}>
                <h1 className="text-2xl">Non utilisé</h1>
                <h1 className="text-2xl">Utilisé</h1>

                <ul>
                    {recipe.ingredients.filter(ingredient => !checkedIngredients.includes(ingredient.name)).map((ingredient, index) => (
                        <li className={"grid grid-cols-2"} key={index}>
                            <div className={"clickable grid grid-cols-3 gap-0"} onClick={() => handleCheck(ingredient)}>
                                <input className={"clickable"} type="checkbox" checked={false} readOnly={true}
                                       id={`ingredient-${index}`}/>
                                <span className={"mx-1 text-2xl"}>{ingredient.name}</span>
                                <span className={"mx-1 text-3xl"}>{revisedQuantity(ingredient.quantity)}</span>
                            </div>
                            <select
                                className={"mx-1 bg-white border border-gray-300 rounded-md shadow-sm focus:border-cook-light focus:ring-cook-light text-base"}
                                defaultValue={ingredient.unit}
                                onChange={(e) => handleUnitChange(ingredient, e.target.value)}>
                                { ing[ingredient.ingredientState as "SOLID" | "LIQUID" | "POWDER" | "OTHER"] &&
                                    ing[ingredient.ingredientState as "SOLID" | "LIQUID" | "POWDER" | "OTHER"].map((unit, index) => (
                                        <option key={index} value={unit}>{t(unit)}</option>
                                    ))
                                }
                            </select>
                        </li>
                    ))}
                </ul>
                <ul>
                    {recipe.ingredients.filter(ingredient => checkedIngredients.includes(ingredient.name)).map((ingredient, index) => (
                        <li key={index} className="text-cook opacity-80 grid grid-cols-2 clickable"
                            onClick={() => handleCheck(ingredient)}>
                            <div className={"clickable grid grid-cols-3 gap-0"}>
                                <input className={"clickable"} type="checkbox" checked={true} readOnly={true}
                                       id={`ingredient-${index}`}/>
                                <span className={"mx-1 text-2xl"}>{ingredient.name}</span>
                                <span className={"mx-1 text-3xl"}>{revisedQuantity(ingredient.quantity)}</span>
                            </div>
                            <select
                                className={"mx-1 bg-white border border-gray-300 rounded-md shadow-sm focus:border-cook-light focus:ring-cook-light text-base"}
                                value={getUnit(ingredient)}
                                disabled={true}>
                                {
                                    ing[ingredient.ingredientState as "SOLID" | "LIQUID" | "POWDER" | "OTHER"].map((unit, index) => (
                                        <option key={index} value={unit}>{t(unit)}</option>
                                    ))
                                }
                            </select>
                        </li>

                    ))}
                </ul>
            </div>

            <InstructionCard recipe={recipe}/>

            <OtherInfo recipe={recipe}/>
        </div>
    );
}

export default ConcoctRecipe;