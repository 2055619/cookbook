import {useTranslation} from "react-i18next";
import React from "react";

function Home() {
    const {t} = useTranslation();

    return (
        <div className={"container"}>
            <div id="intro">
                <div className="row vh-100 text-center bg-cook">
                    <div className="my-5">
                        <h1 className="display-1 text-capitalize fw-bold">{t('pages.home.title')}</h1>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;