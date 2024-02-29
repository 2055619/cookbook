import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {CookBookService} from "../services/CookBookService";
import Loading from "../components/Utils/Loading";

function Landing() {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const cookbookService = new CookBookService();



    return (
        <div className={"container text-center bg-cook-orange min-h-screen"}>
            <h1>Middle Section</h1>
            <Loading />
        </div>
    );
}

export default Landing;