import React from 'react';
import {useTranslation} from 'react-i18next';

function LanguageSelector() {
    const {i18n} = useTranslation();

    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng).then(r => r);
    };

    const currentLanguage = i18n.language;
    const buttonLabel = currentLanguage === 'en' ? 'Français' : 'English';
    const newLanguage = currentLanguage === 'en' ? 'fr' : 'en';

    return (
        <button
            className="border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 py-2 px-4"
            onClick={() => changeLanguage(newLanguage)}>
            {buttonLabel}
        </button>
    );
}

export default LanguageSelector;