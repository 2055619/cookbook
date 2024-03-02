import {Route, Routes, useNavigate} from "react-router-dom";
import Landing from "./Landing";
import PageNotFound from "../any/PageNotFound";
import React, {useEffect} from "react";
import {IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import { useTranslation } from "react-i18next";
import RecipeCreation from "./RecipeCreation";
import ViewRecipe from "./ViewRecipe";

interface IUserPage {
    user: IUser | null;
}

function UserPages({user}: IUserPage) {
    const navigate = useNavigate();
    const {t} = useTranslation();

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (token === null) {
            toast.error(t('message.userNotLoggedIn'));
            navigate('/authentication/signin');
        }
    }, [user]);

    // TODO: Add other pages
    return (
        <div className={"min-h-screen bg-cook-orange text-center"}>
            <Routes>
                <Route path="landing" element={<Landing/>}/>
                <Route path="usrRecipes" element={<ViewRecipe/>}/>
                <Route path="recipesCreation" element={<RecipeCreation user={user!}/>}/>
                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </div>
    )
}

export default UserPages;