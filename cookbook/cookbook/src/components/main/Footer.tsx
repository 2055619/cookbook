import {useTranslation} from "react-i18next";

function Footer() {
    const {t} = useTranslation();
    return (
        <footer className={"bg-light-cook mx-auto my-auto p-1 row"}>
            <div className={"text-center"}>
                <p className="py-4 m-0">{t('copyright')}</p>
            </div>
        </footer>
    );
}

export default Footer;