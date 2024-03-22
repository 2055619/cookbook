import {IUser, IUserProfile} from "../../assets/models/Authentication";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import Landing from "./Landing";
import {useNavigate} from "react-router-dom";

interface IUserProfileProps {
    user: IUser;
}

function UserProfile({user}: IUserProfileProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const navigate = useNavigate();

    const [userProfile, setUserProfile] = useState<IUserProfile>(
        {
            username: "UserName",
            email: "Email",
            firstName: "FirstName",
            lastName: "LastName",
            solidUnit: "SolidUnit",
            liquidUnit: "LiquidUnit",
            powderUnit: "PowderUnit",
            otherUnit: "OtherUnit"
        }
    );

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const username = urlParams.get('username');

        if (username) {
            cookbookService.getUserProfile(username)
                .then((response) => {
                    setUserProfile(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }
    }, []);

    return (
        <div className={"text-start ms-2"}>

            <div className="grid grid-cols-3 text-center mb-2">
                <span className={"text-3xl"}>{userProfile.firstName} {userProfile.lastName}</span>
                <span className={"text-3xl"}>{userProfile.username}</span>
                <span className={"text-3xl"}>{t('email')}: {userProfile.email}</span>
            </div>

            <h2 className={"text-3xl"}>{t('solidUnit')}: {t(userProfile.solidUnit)}</h2>
            <h2 className={"text-3xl"}>{t('liquidUnit')}: {t(userProfile.liquidUnit)}</h2>
            <h2 className={"text-3xl"}>{t('powderUnit')}: {t(userProfile.powderUnit)}</h2>
            <h2 className={"text-3xl"}>{t('otherUnit')}: {t(userProfile.otherUnit)}</h2>

            <div className={"text-center"}>
                <button
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 w-1/6"
                    onClick={() => {
                        navigate("/u/profileModify?username=" + user.username)
                    }}>
                    {t('profileModify')}
                </button>
            </div>

            <h2 className={"text-7xl text-center mt-4"}>{t('publication')}</h2>

            <Landing username={userProfile.username} user={user!}/>
        </div>
    );
}

export default UserProfile;