import {useTranslation} from "react-i18next";

function PageNotFound() {
    const {t} = useTranslation()

    return (
        <div className={"text-center"}>
            <h1 className={"pt-5 text-9xl text-cook"}>{t('pageNotFound')}</h1>
        </div>
    );
}

export default PageNotFound;