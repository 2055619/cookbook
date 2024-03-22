import {Route, Routes, useNavigate} from "react-router-dom";
import Landing from "./Landing";
import PageNotFound from "../any/PageNotFound";
import React, {useEffect} from "react";
import {IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import RecipeModification from "./RecipeModification";
import RecipesList from "./RecipesList";
import RecipeDetails from "./RecipeDetails";
import {cookServerInstance} from "../../App";
import {CookBookService} from "../../services/CookBookService";
import UserProfile from "./UserProfile";
import ConcoctRecipe from "./ConcoctRecipe";
import ProfileModification from "./ProfileModification";

interface IUserPage {
    user: IUser | null;
    setUser: (user: IUser) => void;
}

function UserPages({user, setUser}: IUserPage) {
    const cookbookService = new CookBookService();
    const navigate = useNavigate();

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
                    toast.error(error.response?.data.message);
                    navigate('/authentication/signin');
                });
        } else {
            toast.error("message.userNotLoggedIn");
            navigate('/authentication/signin');
        }
    }, [user]);

    // TODO: Add other pages
    return (
        <div className={"min-h-screen bg-cook-orange text-center"}>
            <Routes>
                <Route path="landing" element={<Landing user={user!}/>}/>
                <Route path="recipes" element={<RecipesList user={user!}/>}/>
                <Route path="recipeDetail" element={<RecipeDetails user={user!}/>}/>
                <Route path="concoct" element={<ConcoctRecipe user={user!} />}/>
                <Route path="recipesModification" element={<RecipeModification user={user!}/>}/>
                <Route path="profile" element={<UserProfile user={user!}/>}/>
                <Route path="profileModify" element={<ProfileModification user={user!} setUser={setUser}/>}/>
                <Route path="*" element={<PageNotFound/>}/>
            </Routes>
        </div>
    )
}

export default UserPages;