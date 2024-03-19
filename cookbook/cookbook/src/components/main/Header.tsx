import {useTranslation} from "react-i18next";
import LanguageSelector from "../Utils/LanguageSelector";
import {NavLink, useNavigate} from "react-router-dom";
import logo from "../../assets/image/cookbookIcon.jpg";
import {IUser} from "../../assets/models/Authentication";
import ProfileSummary from "../ProfileSummary";
import SearchBox from "./SearchBox";
import {faPlus, faScroll} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";



interface IHeaderProps {
    setUser: (user: IUser | null) => void;
    user: IUser | null;
}

function Header({user, setUser}: IHeaderProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();

    const AllHeader = () => {
        return (
            <>
                <NavLink className="flex items-center" to="/">
                    <img alt="Logo" className="h-8 w-8 mr-2" src={logo}/>
                    <h1 className="text-xl font-semibold">{t('name')}</h1>
                </NavLink>
                <div className="text-center lg:block hidden">
                    <p className="text-sm">{t('summedDescription')}</p>
                </div>
                <div className="flex items-center space-x-4">
                    <LanguageSelector/>
                    <NavLink
                        className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 font-bold py-2 px-4"
                        to="/authentication/signin">
                        {t('signin')}
                    </NavLink>
                </div>
            </>
        );
    }

    const UserHeader = () => {
        return (
            <>
                <NavLink className="flex items-center" to="/u/landing">
                    <img alt="Logo" className="h-10 w-10 mr-2" src={logo}/>
                    <h1 className="text-xl font-semibold hidden md:block">{t('name')}</h1>
                </NavLink>
                <SearchBox/>
                <div className="flex justify-end items-center space-x-3">
                    {/*TODO: Changer pour aller à la liste des recette*/}
                    <div className={"flex items-center clickable"}
                         onClick={() => navigate('/u/recipes')}>
                        <h1 className={"lg:block hidden"}>{t('usrRecipes')}</h1>
                        <FontAwesomeIcon className={"clickable ml-2 md:w-8 md:h-8"} icon={faScroll}
                                         onClick={() => navigate('/u/recipes')}/>
                    </div>
                    <div className="flex items-center clickable"
                         onClick={() => navigate('/u/recipesModification')}>
                        <h1 className={"lg:block hidden"}>{t('createRecipe')}</h1>
                        <FontAwesomeIcon className={"clickable ml-2 md:w-8 md:h-8"} icon={faPlus}
                                         onClick={() => navigate('/u/recipesModification')}/>
                    </div>
                    <ProfileSummary setUser={setUser} user={user}/>
                </div>
            </>
        );
    }

    return (
        <header
            className="bg-cook-light mx-auto flex items-center justify-between font-bold p-1 text-cook sticky top-0 z-50">
            {user === null ? (
                    <>
                        {AllHeader()}
                    </>

                ) :
                (
                    <>
                        {UserHeader()}
                    </>
                )}
        </header>
    );
}

export default Header;