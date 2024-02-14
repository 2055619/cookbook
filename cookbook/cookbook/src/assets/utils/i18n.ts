import i18n from 'i18next';

import enTranslations from '../../language/en.json';
import frTranslations from '../../language/fr.json';
import {initReactI18next} from "react-i18next";

i18n
    .use(initReactI18next)
    .init({
        resources: {
            en: {
                translation: enTranslations,
            },
            fr: {
                translation: frTranslations,
            }
        },
        lng: 'fr',
        fallbackLng: 'fr',
        interpolation: {
            escapeValue: false,
        },
    }).then(r => r);

export default i18n;
