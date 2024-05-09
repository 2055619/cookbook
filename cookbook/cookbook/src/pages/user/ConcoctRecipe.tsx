import React, {useEffect, useState} from "react";
import {toast} from "react-toastify";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {IIngredient, IRecipe} from "../../assets/models/Publication";
import {UtilsService} from "../../services/UtilsService";
import InstructionCard from "../../components/recipes/InstructionCard";
import {IUser} from "../../assets/models/Authentication";
import OtherInfo from "../../components/recipes/OtherInfo";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from "@fortawesome/free-solid-svg-icons";
import Fraction from "fraction.js";

interface IConcoctRecipeProps {
    user: IUser;
}

function ConcoctRecipe({user}: IConcoctRecipeProps) {
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();
    const {t} = useTranslation();
    const [recipe, setRecipe] = useState<IRecipe>(
        {
            id: -1,
            title: "",
            description: "",
            cookUsername: "",
            creationDate: "",
            visibility: "",
            averageRating: 0,
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
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        } else {
            toast.error(t('noRecipeTitle'));
            window.history.back();
        }

        utilsService.getIngrediantStates().then((response) => {
            setIng(response);
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
        });

        utilsService.getPortionSizes().then((response) => {
            setPortionSizes(response);
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
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
                if (error.response?.data.message !== "NoToken")
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
                return "";
        }
    }

    // function revisedQuantity(quantity: number) {
    //     let fraction = new Fraction(quantity).simplify(0.1);
    //     return fraction.toFraction(true);
    // }

    function revisedQuantity(quantity: number) {
        const fractions = [0, 1/4, 1/3, 1/2, 2/3, 3/4, 1];

        let wholeNumber = Math.floor(quantity);
        let decimalPart = quantity - wholeNumber;

        let closestFraction = fractions.reduce((prev, curr) => {
            return (Math.abs(curr - decimalPart) < Math.abs(prev - decimalPart) ? curr : prev);
        }, Number.MAX_SAFE_INTEGER);

        let fraction = new Fraction(wholeNumber + closestFraction).simplify(0.01);
        return fraction.toFraction(true);
    }

    return (
        <div className={"min-h-screen bg-cook-orange"}>
            <div className="grid md:grid-cols-4 grid-cols-2">
                <div className={"text-start ms-1"}>
                    <button onClick={() => window.history.back()}
                            className="clickable hover:bg-cook-red hover:rounded-full px-2 py-1">
                        <FontAwesomeIcon icon={faArrowLeft}/>
                    </button>
                </div>

                <h1 className={"text-7xl col-span-4"}>{recipe.title}</h1>
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

            <div className={"text-center"}>
                <h1 className={"text-4xl"}>{t('ingredients')}</h1>
                <ul>
                    {recipe.ingredients.map((ingredient, index) => (
                        <li className={`w-11/12 mx-auto ${checkedIngredients.includes(ingredient.name) ? "text-cook opacity-80" : ""}`}
                            key={index}>
                            <div className={"clickable grid grid-cols-12 gap-0"}
                                 onClick={() => handleCheck(ingredient)}>
                                <input className={"clickable col-span-1"} type="checkbox"
                                       checked={checkedIngredients.includes(ingredient.name)} readOnly={true}
                                       id={`ingredient-${index}`}/>
                                <span
                                    className={`mx-1 col-span-6 ${checkedIngredients.includes(ingredient.name) ? "text-sm" : "text-2xl"}`}>{ingredient.name}</span>
                                <span
                                    className={`mx-1 col-span-2 ${checkedIngredients.includes(ingredient.name) ? "text-sm" : "text-3xl"}`}>{revisedQuantity(ingredient.quantity)}</span>
                                <select onClick={(e) => e.stopPropagation()}
                                    className={"mx-1 my-1 bg-white border border-gray-300 rounded-md shadow-sm focus:border-cook-light text-base col-span-3"}
                                    defaultValue={ingredient.unit}
                                    onChange={(e) => handleUnitChange(ingredient, e.target.value)}>
                                    {ing[ingredient.ingredientState as "SOLID" | "LIQUID" | "POWDER" | "OTHER"] &&
                                        ing[ingredient.ingredientState as "SOLID" | "LIQUID" | "POWDER" | "OTHER"].map((unit, index) => (
                                            <option key={index} value={unit}>{t(unit)}</option>
                                        ))
                                    }
                                </select>
                            </div>
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