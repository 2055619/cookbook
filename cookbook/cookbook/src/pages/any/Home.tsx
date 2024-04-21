import {useTranslation} from "react-i18next";
import React from "react";
import {toast} from "react-toastify";
import SupportDevs from "../../components/main/SupportDevs";

function Home() {
    const {t} = useTranslation();


    return (
        <div className={"container"}>
            <div id="intro">
                <div className="flex h-screen bg-cook-orange justify-center">
                    <div className="my-5 text-center">
                        <h1 className="text-4xl font-bold">{t('cookWelcome')}</h1>

                        <div className="w-1/2 mx-auto">
                            <h1 className="text-2xl mt-3">{t('intro')}</h1>

                            <SupportDevs />
                        </div>
                    </div>
                </div>
                <h1 className="text-2xl py-3 font-bold">{t('reachOut')}</h1>
            </div>
        </div>
    );
}

export default Home;