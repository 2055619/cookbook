import {useState, useEffect, useRef} from 'react';
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import {IRecipe} from "../../assets/models/Publication";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";

function SearchBox() {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);
    const [recipesTitle, setRecipesTitle] = useState<IRecipe[]>([]);
    const cookbookService = new CookBookService();
    const popupRef = useRef<HTMLDivElement>(null);

    useEffect(() => {
        const handleClickOutside = (event: MouseEvent) => {
            if (popupRef.current && !popupRef.current.contains(event.target as Node)) {
                setShowPopup(false);
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    function handleInputChange(e: any){
        const searchValue = e.target.value;

        if (searchValue) {
            cookbookService.getRecipeByTitle(searchValue)
                .then((response) => {
                    setRecipesTitle(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                    setRecipesTitle([]);
                });
            setShowPopup(true);
        } else {
            setShowPopup(false);
        }
    }

    function handleRecipeClick(recipeTitle: string){
        navigate('/u/recipeDetail?title=' + recipeTitle);
    }

    return (
        <div className="relative flex lg:w-1/3 lg:ml-52">
            <input
                className="w-full px-8 rounded-lg"
                type="text"
                placeholder={t('input.search')}
                onChange={handleInputChange}
                onClick={() => setShowPopup(true)}
            />
            {showPopup && (
                <div ref={popupRef} className="absolute mt-6 w-11/12 bg-white border border-gray-200 rounded shadow-xl ">
                    {recipesTitle.map((recipe, index) => (
                        <button key={index} className="p-2 cursor-pointer w-full text-start" onClick={() => handleRecipeClick(recipe.title)}>
                            {recipe.title}
                        </button>
                    ))}
                </div>
            )}
        </div>
    )
}

export default SearchBox;