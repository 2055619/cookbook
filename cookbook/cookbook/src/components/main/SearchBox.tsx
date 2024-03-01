import {useState} from 'react';
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import {IRecipe} from "../../assets/models/Recipe";

function SearchBox() {
    const [showPopup, setShowPopup] = useState(false);
    const [recipesTitle, setRecipesTitle] = useState<IRecipe[]>([]);
    const cookbookService = new CookBookService();

    const handleInputChange = async (e: any) => {
        const searchValue = e.target.value;

        if (searchValue) {
            cookbookService.getRecipeByTitle(searchValue)
                .then((response) => {
                    setRecipesTitle(response);
                })
                .catch((error) => {
                    toast.error(error.response.data.message);
                    setRecipesTitle([]);
                });

            setShowPopup(true);
        } else {
            setShowPopup(false);
        }
    };

    // const history = useHistory();

    const handleRecipeClick = (recipeTitle: string) => {
        toast.info("You clicked on " + recipeTitle);
    };


    return (
        <div className="relative flex justify-center">
            <input
                className="w-96 px-8 rounded-lg"
                type="text"
                placeholder="Search recipes..."
                onChange={handleInputChange}
            />
            {showPopup && (
                <div className="absolute mt-6 w-11/12 bg-white border border-gray-200 rounded shadow-xl ">
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