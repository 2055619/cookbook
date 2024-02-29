import {useTranslation} from "react-i18next";
import React from "react";

function Home() {
    const {t} = useTranslation();

    return (
        <div className={"container"}>
            <div id="intro">
                <div className="flex h-screen bg-cook-orange">
                    <div className="my-5 text-center">
                        <h1 className="text-4xl text-capitalize fw-bold">{t('pages.home.title')}</h1>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;