import {Route, Routes, useNavigate} from "react-router-dom";
import Home from "../any/Home";
import Authentication from "../any/Authentication";
import Landing from "./Landing";
import PageNotFound from "../any/PageNotFound";
import React, {useEffect} from "react";
import {IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import { useTranslation } from "react-i18next";

interface IUserPage {
    user: IUser | null;
}

function UserPages({user}: IUserPage) {
    const navigate = useNavigate();
    const {t} = useTranslation();

    useEffect(() => {
        const token = sessionStorage.getItem('token');
        if (token === null) {
            toast.error(t('messages.userNotLoggedIn'));
            navigate('/authentication/signin');
        } else {
            navigate('/u/landing');
        }
    }, [user]);

    // TODO: Add other pages
    return (
        <div>
            <Routes>
                <Route path="landing" element={<Landing/>}/>
            </Routes>
        </div>
    )
}

export default UserPages;