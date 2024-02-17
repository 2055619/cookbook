import {useTranslation} from "react-i18next";
import SignIn from "./SignIn";
import {Route, Routes} from "react-router-dom";
import SignUp from "./SignUp";

interface IAuthenticationProps {
    setUser: (user: any) => void;
}
function Authentication({setUser}: IAuthenticationProps) {
    const {t} = useTranslation();
    const [tab, setTab] = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9];

    return (
        <div className={"container text-center bg-cook vh-min-100"}>
            <h1 className={"display-1 fw-semibold"}>{t('pages.auth.title')}</h1>
            {/*<p>{t('signin')}</p>*/}
            <Routes>
                <Route path={"signin"} element={<SignIn setUser={setUser}/>}/>
                <Route path={"signup"} element={<SignUp setUser={setUser}/>}/>
            </Routes>
        </div>
    );
}

export default Authentication;