import {useTranslation} from "react-i18next";

function Header() {
    const {t} = useTranslation();
    return (
        <header className={"bg-light-cook mx-auto my-auto p-1 row"}>
            <div className={"m-3 col-3"}>
                <h1 className={"display-6"}>{t('name')}</h1>
            </div>
            <div className={"text-center mx-auto my-auto col-5"}>
                <p>{t('summedDescription')}</p>
            </div>
            <div className={"col-2 mx-auto my-auto"}>
                <h1 className="display-6">CONNEXION</h1>
            </div>
        </header>
    );
}

export default Header;