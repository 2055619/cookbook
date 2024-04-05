import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import Loading from "../../components/Utils/Loading";
import {useCallback, useEffect, useRef, useState} from 'react';
import {IPublication, IRecipe} from "../../assets/models/Publication";
import {toast} from "react-toastify";
import RecipeCard from "../../components/recipes/RecipeCard";
import {IUser} from "../../assets/models/Authentication";
import PublicationCard from "../../components/PublicationCard";

interface ILandingProps {
    user: IUser;
    username?: string;
}

function Landing({username, user}: ILandingProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [publications, setPublications] = useState<IPublication[]>([]);
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
        async function loadPublications() {
            const pub = await cookbookService.getPublications(page)
                .then((response) => {
                    return response;
                    // setPublications(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                    return [];
                });

            const uniquePublication: IRecipe[] = Array.from(new Set([...publications, ...pub].map(publication => publication.title)))
                .map(title => {
                    return [...publications, ...pub].find(publication => publication.title === title)!
                })
                .filter(publication => !username || username === publication.cookUsername) as IRecipe[];

            setPublications(uniquePublication!);
        }

        loadPublications();
    }, [page, username]);

    return (
        <div className={"text-center"}>

            {
                publications.map((publication, index) => {
                    if (publications.length === index + 1) {
                        return <div className={`flex justify-center`} ref={lastRecipeElementRef} key={index}>
                            <PublicationCard publication={publication} username={user.username} key={index}/>
                        </div>
                    } else {
                        return <div className={`flex justify-center`} key={index}>
                            <PublicationCard publication={publication} username={user.username} key={index}/>
                        </div>
                    }
                })
            }

            <Loading/>
        </div>
    );
}

export default Landing;