import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {IFilters} from "../../assets/models/Form";
import StarDropdown from "../reaction/StarDropdown";
import {IUser} from "../../assets/models/Authentication";
import {UtilsService} from "../../services/UtilsService";
import MultiSelectCheckboxDropdown from "../Utils/MulltiSelectCheckboxDropdown";
import FilterComponent from "../Utils/FilterComponent";

interface ILeftAside {
    setFilters: (filters: IFilters) => void;
    user: IUser | null;
}

function LeftAside({setFilters, user}: ILeftAside) {
    // const {t} = useTranslation();
    // const utilsService = new UtilsService();
    //
    // const [publicationType, setPublicationType] = useState<string>("");
    // const [title, setTitle] = useState("");
    // const [cookUsername, setCookUsername] = useState("");
    // const [creationDate, setCreationDate] = useState("");
    // const [stars, setStars] = useState(0);
    // const [difficulty, setDifficulty] = useState<string[]>([]);
    // const [recipeType, setRecipeType] = useState<string[]>([]);
    // const [ingredientName, setIngredientName] = useState("");
    // const [dietType, setDietType] = useState<string[]>([]);
    // const [cookingTime, setCookingTime] = useState<number>(0);
    // const [prepTime, setPrepTime] = useState<number>(0);
    // const [cookTime, setCookTime] = useState<number>(0);
    //
    // const [difficulties, setDifficulties] = useState<string[]>([]);
    // const [recipeTypes, setRecipeTypes] = useState<string[]>([]);
    // const [dietTypes, setDietTypes] = useState<string[]>([]);
    //
    // const [publicationTypes, setPublicationTypes] = useState<string[]>([]);
    //
    // useEffect(() => {
    //     utilsService.getPublicationTypes().then((response) => {
    //         setPublicationTypes(response);
    //     });
    //     utilsService.getDifficultyLevels().then((response) => {
    //         setDifficulties(response);
    //     });
    //     utilsService.getCategories().then((response) => {
    //         setRecipeTypes(response);
    //     });
    //     utilsService.getDietTypes().then((response) => {
    //         setDietTypes(response);
    //     });
    //
    // }, []);
    //
    //
    // function handleSubmit(event: React.FormEvent) {
    //     event.preventDefault();
    //
    //     setFilters({
    //         publicationType: publicationType,
    //         title: title,
    //         cookUsername: cookUsername,
    //         creationDate: creationDate,
    //         averageRating: stars,
    //         difficulty: difficulty,
    //         category: recipeType,
    //         ingredientName: ingredientName,
    //         dietTypes: dietType,
    //         cookingTime: cookingTime,
    //         prepTime: prepTime,
    //         cookTime: cookTime
    //     });
    // }
    //
    // function handleReset() {
    //     setFilters({
    //         publicationType: "",
    //         title: "",
    //         cookUsername: "",
    //         creationDate: "",
    //         averageRating: 0,
    //         difficulty: [],
    //         category: [],
    //         ingredientName: "",
    //         dietTypes: [],
    //         cookingTime: 0,
    //         prepTime: 0,
    //         cookTime: 0
    //     });
    //     setPublicationType("")
    //     setTitle("");
    //     setCookUsername("");
    //     setCreationDate("");
    //     setStars(0);
    //     setDifficulty([]);
    //     setRecipeType([]);
    //     setIngredientName("");
    //     setDietType([]);
    //     setCookingTime(0);
    //     setPrepTime(0);
    //     setCookTime(0);
    //
    // }
    //
    // const recipeFilters = (
    //     <>
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('difficulty')}</label>
    //             <MultiSelectCheckboxDropdown
    //                 options={difficulties}
    //                 selectedOptions={difficulty}
    //                 setSelectedOptions={setDifficulty}
    //                 label={t('difficulty')}
    //             />
    //         </div>
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('recipeType')}</label>
    //             <MultiSelectCheckboxDropdown
    //                 options={recipeTypes}
    //                 selectedOptions={recipeType}
    //                 setSelectedOptions={setRecipeType}
    //                 label={t('recipeType')}
    //             />
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('dietTypes')}</label>
    //             <MultiSelectCheckboxDropdown
    //                 options={dietTypes}
    //                 selectedOptions={dietType}
    //                 setSelectedOptions={setDietType}
    //                 label={t('dietTypes')}
    //             />
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('maxCookingTime')}</label>
    //             <input type="number" value={cookingTime} onChange={(e) => setCookingTime(parseInt(e.target.value))}
    //                    min={0}
    //                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('maxPrepTime')}</label>
    //             <input type="number" value={prepTime} onChange={(e) => setPrepTime(parseInt(e.target.value))}
    //                    min={0}
    //                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('maxCookTime')}</label>
    //             <input type="number" value={cookTime} onChange={(e) => setCookTime(parseInt(e.target.value))}
    //                    min={0}
    //                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
    //         </div>
    //     </>
    // );
    //
    // const publicationFilters = (
    //     <form className={"sticky top-14"} onSubmit={handleSubmit}>
    //         <h1 className={"text-4xl"}>{t('filter')}</h1>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('publicationType')}</label>
    //             <select value={publicationType} onChange={(e) => setPublicationType(e.target.value)}
    //                     className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm">
    //                 <option value="">{t('all')}</option>
    //                 {publicationTypes.map((type, index) => (
    //                     <option key={index} value={type}>{t(type)}</option>
    //                 ))}
    //             </select>
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('title')}</label>
    //             <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}
    //                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('pages.auth.username')}</label>
    //             <input type="text" value={cookUsername} onChange={(e) => setCookUsername(e.target.value)}
    //                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('stars')}</label>
    //             <StarDropdown selectedStars={stars} setSelectedStars={setStars}/>
    //         </div>
    //
    //         <div>
    //             <label className="block text-sm font-medium text-cook-light">{t('creationDate')}</label>
    //             <input type="date" value={creationDate} onChange={(e) => setCreationDate(e.target.value)}
    //                    className="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-cook-light focus:border-cook-light sm:text-sm"/>
    //         </div>
    //
    //         {
    //             publicationType === "RECIPE" || publicationType === "" ? recipeFilters : ""
    //         }
    //
    //         <div className="flex justify-between mt-3">
    //             <button type={"button"} onClick={handleReset}
    //                     className="border border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-2 mx-2">
    //                 {t('input.reset')}
    //             </button>
    //
    //             <button type="submit"
    //                     className="border border-cook-light text-cook-light hover:bg-cook-light hover:text-cook rounded transition ease-in duration-200 p-2 mx-2">
    //                 {t('input.apply')}
    //             </button>
    //         </div>
    //     </form>
    // );

    return (
        <div className="md:w-1/4 text-cook-light px-4 hidden md:block">
            <FilterComponent user={user} setFilters={setFilters} />
        </div>
    );
}

// @ts-ignore
export default LeftAside;