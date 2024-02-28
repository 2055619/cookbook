import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {CookBookService} from "../services/CookBookService";

function Landing() {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const cookbookService = new CookBookService();



    return (
        <div className={"container bg-cook min-vh-100"}>
            <h1>Middle Section</h1>
        </div>
    );
}

export default Landing;