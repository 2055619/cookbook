import {useTranslation} from "react-i18next";
import SupportDevs from "./SupportDevs";
import React from "react";

function Footer() {
    const {t} = useTranslation();
    return (
        <footer className="bg-cook-light mx-auto p-1 flex items-center justify-center text-center">
            <div>
                <SupportDevs />
                <p className="py-4 m-0 text-cook fw-semibold h6">{t('copyright')}</p>
            </div>
        </footer>
    );
}

export default Footer;