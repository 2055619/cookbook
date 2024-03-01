import {useTranslation} from "react-i18next";
import LanguageSelector from "../Utils/LanguageSelector";
import {NavLink, useNavigate} from "react-router-dom";
import logo from "../../logo.svg";
import {IUser} from "../../assets/models/Authentication";

interface IHeaderProps {
    setUser: (user: IUser | null) => void;
    user: IUser | null;
}

function Header({user, setUser}: IHeaderProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();

    function SignOut() {
        setUser(null);
        localStorage.clear();
        navigate('/');
    }

    return (
        <header className="bg-cook-light mx-auto flex items-center justify-between font-bold p-2 text-cook sticky top-0 z-50">
            <NavLink className="flex items-center" to="/">
                <img alt="Logo" className="h-8 w-8 mr-2" src={logo}/>
                <h1 className="text-xl font-semibold">{t('name')}</h1>
            </NavLink>
            <div className="text-center">
                <p className="text-sm">{t('summedDescription')}</p>
            </div>
            <div className="flex items-center space-x-4">
                <LanguageSelector/>
                {user === null ?
                    <button className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 font-bold py-2 px-4"
                            onClick={() => {
                                navigate('/authentication/signin')
                            }}>
                        {t('signin')}
                    </button> :
                    <div className="flex flex-col items-center">
                        <p className="text-sm mx-0 p-0">{t('welcome') + " " + user.username}</p>
                        <button className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 font-bold py-2 px-4"
                                onClick={SignOut}>
                            {t('signout')}
                        </button>
                    </div>
                }
            </div>
        </header>
    );
}

export default Header;