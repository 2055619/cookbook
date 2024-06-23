import {useTranslation} from "react-i18next";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faEnvelope} from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

function ContactButton() {
    const {t} = useTranslation();
    const navigate = useNavigate();

    return (
        <div onClick={() => navigate("/contact")}
            className={"border border-cook-orange text-cook hover:bg-cook-orange hover:text-cook rounded transition ease-in duration-200 font-bold py-2 px-4 clickable"}>
            <h1 className={"hidden sm:inline"}>{t('contact')}</h1>
            <FontAwesomeIcon icon={faEnvelope} className={"sm:ml-2"}/>
        </div>
    );
}


export default ContactButton;