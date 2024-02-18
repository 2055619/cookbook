import React from 'react';
import { useTranslation } from 'react-i18next';

function LanguageSelector() {
    const { i18n } = useTranslation();

    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng).then(r => r);
    };

    const currentLanguage = i18n.language;
    const buttonLabel = currentLanguage === 'en' ? 'Français' : 'English';
    const newLanguage = currentLanguage === 'en' ? 'fr' : 'en';

    return (
        <div className="mx-auto col-1">
            <button className={"btn btn-outline-cook mt-5"} onClick={() => changeLanguage(newLanguage)}>{buttonLabel}</button>
        </div>
    );
}

export default LanguageSelector;