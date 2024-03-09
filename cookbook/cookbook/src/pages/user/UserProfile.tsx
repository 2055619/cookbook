import {IUser, IUserProfile} from "../../assets/models/Authentication";
import {useCallback, useEffect, useRef, useState} from "react";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IRecipe} from "../../assets/models/Recipe";
import {toast} from "react-toastify";
import RecipeComponent from "../../components/recipes/RecipeComponent";
import Loading from "../../components/Utils/Loading";
import Landing from "./Landing";

interface IUserProfileProps {
}

function UserProfile() {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [user, setUser] = useState<IUserProfile>(
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
                    setUser(response);
                })
                .catch((error) => {
                    toast.error(t(error.response?.data.message));
                });
        }
    }, []);

    return (
        <div className={"text-start ms-2"}>
            <h1 className={"text-5xl"}>{user.username}</h1>
            <h2 className={"text-3xl"}>{t('email')}: {user.email}</h2>
            <h2 className={"text-3xl"}>{user.firstName} {user.lastName}</h2>

            <h2 className={"text-3xl"}>{t('solidUnit')}: {t(user.solidUnit)}</h2>
            <h2 className={"text-3xl"}>{t('liquidUnit')}: {t(user.liquidUnit)}</h2>
            <h2 className={"text-3xl"}>{t('powderUnit')}: {t(user.powderUnit)}</h2>
            <h2 className={"text-3xl"}>{t('otherUnit')}: {t(user.otherUnit)}</h2>

            <h2 className={"text-7xl text-center mt-4"}>{t('publication')}</h2>

            {/*TODO: Add filter for the user only */}
            <Landing/>
        </div>
    );
}

export default UserProfile;