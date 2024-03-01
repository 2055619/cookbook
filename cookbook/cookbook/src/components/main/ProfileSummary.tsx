import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import avatar from "../../assets/image/avatar.jpg";
import LanguageSelector from "../Utils/LanguageSelector";

interface IProfileSummaryProps {
    setUser: (user: any) => void;
    user: any;
}
function ProfileSummary({setUser, user}: IProfileSummaryProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);

    function SignOut() {
        sessionStorage.clear();
        setUser(null);
        navigate('/authentication/signin');
    }

    useEffect(() => {
        document.addEventListener('click', closePopup);
        return () => {
            document.removeEventListener('click', closePopup);
        };
    }, []);

    function togglePopup(e: React.MouseEvent) {
        e.stopPropagation();
        setShowPopup(!showPopup);
    }

    function closePopup() {
        setShowPopup(false);
    }
    function handlePopupClick(e: React.MouseEvent) {
        e.stopPropagation();
    }

    return (
        <div className="relative">
            <img src={avatar} alt="Avatar" className="h-10 w-10 me-1 rounded-full cursor-pointer"
                 onClick={togglePopup}/>
            {showPopup && (
                <div
                    onClick={handlePopupClick}
                    className="absolute right-0 mt-2 w-48 bg-white border border-gray-200 rounded shadow-xl bg-cook-light">
                    <div className="p-2 m-0 bg-cook-light border-2 border-cook-orange">
                        <h1 className="text-center text-sm mb-1">{t('welcome') + " " + user.username}</h1>
                        <LanguageSelector/>
                        <button
                            className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 font-bold py-2 px-4 my-2 w-full"
                            onClick={() => {}}>
                            {t('parameters')}
                        </button>

                        <button
                            className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 font-bold py-2 px-4 w-full"
                            onClick={SignOut}>
                            {t('signout')}
                        </button>
                    </div>
                </div>
            )}
        </div>
    );
}

export default ProfileSummary;