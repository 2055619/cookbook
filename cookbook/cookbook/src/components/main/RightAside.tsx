import {IUser} from "../../assets/models/Authentication";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";

interface IRightAside {
    user: IUser;
}

function RightAside({user}: IRightAside) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const navigate = useNavigate();
    const [followed, setFollowed] = useState<IUser[]>([]);

    useEffect(() => {
        if (user === null || user === undefined){
            return;
        }
        cookbookService.getFollowing(user.username)
            .then((response) => {
                setFollowed(response);
            }).catch((error) => {
                if (error.response?.data.message !== "NoToken")
                    toast.error(t(error.response?.data.message));
        });
    }, [user]);


    function handleUserClick(username: string) {
        navigate(`/u/profile?username=${username}`);
    }

    return (
        <div className="w-1/4 text-center me-2 hidden md:block text-cook-light sticky top-12 h-screen overflow-auto">
            <h1 className="text-2xl">{t('followed')}</h1>
            <ul>
                {followed.map((follower, index) => (
                    <li className="mb-0 p-1 mx-auto w-1/2 clickable hover:bg-cook-red hover:text-cook hover:rounded-full"
                        key={index} onClick={() => handleUserClick(follower.username)}>
                        {follower.username}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default RightAside;