import {Route, Routes, useNavigate} from "react-router-dom";
import Landing from "./Landing";
import PageNotFound from "../any/PageNotFound";
import React, {useEffect} from "react";
import {IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import RecipesList from "./RecipesList";
import RecipeDetails from "./RecipeDetails";
import {cookServerInstance} from "../../App";
import {CookBookService} from "../../services/CookBookService";
import UserProfile from "./UserProfile";
import ConcoctRecipe from "./ConcoctRecipe";
import ProfileModification from "./ProfileModification";
import {IFilters} from "../../assets/models/Form";
import ReactionsPage from "./ReactionsPage";
import {useTranslation} from "react-i18next";
import PublicationCreation from "./PublicationCreation";
import Loading from "../../components/Utils/Loading";

interface IUserPage {
    user: IUser | null;
    setUser: (user: IUser | null) => void;
    filters: IFilters;
}

function UserPages({user, setUser, filters}: IUserPage) {
    const cookbookService = new CookBookService();
    const navigate = useNavigate();
    const {t} = useTranslation()

    const interval = setInterval(checkToken, 100000);

    function checkToken() {
        cookbookService.getUser()
            .then((response) => {
                // setUser(response);
            })
            .catch((error) => {
                toast.error(t(error.response?.data.message));
                setUser(null);
                navigate('/authentication/signin');
                interval && clearInterval(interval);
            });
    }

    useEffect(() => {
        const token = sessionStorage.getItem('token');

        if (token) {
            if (user !== null)
                return;
            cookServerInstance.defaults.headers.common['Authorization'] = token;

            cookbookService.getUser()
                .then((response) => {
                    setUser(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                    navigate('/authentication/signin');
                });
        } else {
            toast.error(t('message.userNotLoggedIn'));
            navigate('/authentication/signin');
        }
    }, [user]);

    return (
        <div className={"min-h-screen bg-cook-orange text-center"}>
            {
                user === null ?
                <div>
                    <h1>{t('loading')}</h1>
                    <Loading/>
                </div> : (
                        <Routes>
                            <Route path="landing" element={<Landing user={user!} filters={filters} />}/>
                            <Route path="recipes" element={<RecipesList user={user!}/>}/>
                            <Route path="recipeDetail" element={<RecipeDetails user={user!}/>}/>
                            <Route path="concoct" element={<ConcoctRecipe user={user!} />}/>
                            <Route path="publicationModification" element={<PublicationCreation user={user!}/>}/>
                            <Route path="profile" element={<UserProfile user={user!}/>}/>
                            <Route path="profileModify" element={<ProfileModification user={user!} setUser={setUser}/>}/>
                            <Route path="reactions" element={<ReactionsPage username={user!.username}/>}/>
                            <Route path="*" element={<PageNotFound/>}/>
                        </Routes>
                    )
            }
        </div>
    )
}

export default UserPages;