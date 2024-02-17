import React from 'react';
import { useTranslation } from 'react-i18next';

function LanguageSelector() {
    const { i18n } = useTranslation();

    const changeLanguage = (lng: string) => {
        i18n.changeLanguage(lng).then(r => r);
    };

    return (
        <div className="mt-1">
            <button className={"btn btn-outline-cook my-1"} onClick={() => changeLanguage("fr")}>Français</button>
            <button className={"btn btn-outline-cook"} onClick={() => changeLanguage("en")}>English</button>
            {/*<select className={"form-select clickable"} defaultValue={"fr"} onChange={(e) => changeLanguage(e.target.value)}>*/}
            {/*    <option value="fr">Français</option>*/}
            {/*    <option value="en">English</option>*/}
            {/*</select>*/}
        </div>
    );
}

export default LanguageSelector;
