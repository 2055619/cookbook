import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import Loading from "../../components/Utils/Loading";
import {useCallback, useEffect, useRef, useState} from 'react';
import {IRecipe} from "../../assets/models/Publication";
import {toast} from "react-toastify";
import RecipeCard from "../../components/recipes/RecipeCard";
import {IUser} from "../../assets/models/Authentication";

interface ILandingProps {
    user: IUser;
    username?: string;
}

function Landing({username, user}: ILandingProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [recipes, setRecipes] = useState<IRecipe[]>([]);
    const [page, setPage] = useState(0);
    const observer = useRef<IntersectionObserver | null>(null);

    const lastRecipeElementRef = useCallback((node: HTMLDivElement | null) => {
        if (observer.current) observer.current.disconnect();
        observer.current = new IntersectionObserver(entries => {
            if (entries[0].isIntersecting) {
                setPage(prevPageNumber => prevPageNumber + 1);
            }
        })
        if (node) observer.current.observe(node);
    }, []);

    useEffect(() => {
        const loadRecipes = async () => {
            const newRecipes = await cookbookService.getRecipes(page)
                .then((response) => {
                    return response;
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                    return [];
                });

            const uniqueRecipes: IRecipe[] = Array.from(new Set([...recipes, ...newRecipes].map(recipe => recipe.title)))
                .map(title => {
                    return [...recipes, ...newRecipes].find(recipe => recipe.title === title)!
                })
                .filter(recipe => !username || username === recipe.cookUsername) as IRecipe[];

            setRecipes(uniqueRecipes!);
        };
        loadRecipes();
    }, [page, username]);

    return (
        <div className={"text-center"}>

            {
                recipes.map((recipe, index) => {
                    if (recipes.length === index + 1) {
                        return <div className={`flex justify-center`} ref={lastRecipeElementRef} key={index}>
                            <RecipeCard recipe={recipe} username={user.username} key={index}/>
                        </div>
                    } else {
                        return <div className={`flex justify-center`} key={index}>
                            <RecipeCard recipe={recipe} username={user.username} key={index}/>
                        </div>
                    }
                })
            }

            <Loading/>
        </div>
    );
}

export default Landing;