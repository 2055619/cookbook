import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {CookBookService} from "../services/CookBookService";
import Loading from "../components/Utils/Loading";
import { useEffect, useState, useRef, useCallback } from 'react';
import {IRecipe} from "../assets/models/Recipe";
import {toast} from "react-toastify";

function Landing() {
    const {t} = useTranslation();
    const navigate = useNavigate();
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
                    toast.error(t(error.response.data.message));
                    return [];
                });

            const uniqueRecipes: IRecipe[] = Array.from(new Set([...recipes, ...newRecipes].map(recipe => recipe.title)))
                .map(title => {
                    return [...recipes, ...newRecipes].find(recipe => recipe.title === title)!
                })
                .filter(recipe => recipe !== undefined) as IRecipe[];

            setRecipes(uniqueRecipes!);

            // setRecipes(prevRecipes => [...prevRecipes, ...newRecipes]);
        };
        toast.info("Loading Recipes")
        loadRecipes();
    }, [page]);

    return (
        <div className={"container text-center bg-cook-orange min-h-screen"}>
            <h1>Middle Section</h1>

            {recipes.map((recipe, index) => {
                if (recipes.length === index + 1) {
                    return <div ref={lastRecipeElementRef} key={index}>{recipe.title}</div>
                } else {
                    return <div key={index}>{recipe.title}</div>
                }
            })}

            <Loading />
        </div>
    );
}

export default Landing;