import {useTranslation} from "react-i18next";

function Home() {
    const {t} = useTranslation();

    return (
        <div className={"container"}>
            <div id="intro">
                <div className="row vh-100 text-center">
                    <div className="my-5">
                        <h1 className="display-1 text-capitalize">{t('pages.home.title')}</h1>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;