import {useTranslation} from "react-i18next";
import LanguageSelector from "../LanguageSelector";
import {NavLink} from "react-router-dom";
import logo from "../../logo.svg";

function Header() {
    const {t} = useTranslation();
    return (
        <header className={"bg-light-cook mx-auto my-auto p-1 row"}>
            <NavLink className="m-3 col-2 text-decoration-none text-white col-2" to="/">
                <div className="d-flex">
                    <img alt="Logo" className="col-4 d-none d-md-block" src={logo}/>
                    <h1 className={"display-6"}>{t('name')}</h1>
                </div>
            </NavLink>
            <div className={"text-center mx-auto my-auto col-5"}>
                <p>{t('summedDescription')}</p>
            </div>
            <div className={"col-1"}>
                <LanguageSelector/>
            </div>
            <div className={"col-2  my-auto text-center"}>
                <a className="text-light text-decoration-none m-2 display-6" href="/signin">{t('signin')}</a>
            </div>
        </header>
    );
}

export default Header;