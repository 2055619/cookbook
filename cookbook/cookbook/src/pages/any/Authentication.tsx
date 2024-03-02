import {useTranslation} from "react-i18next";
import SignIn from "../../components/auth/SignIn";
import {Route, Routes} from "react-router-dom";
import SignUp from "../../components/auth/SignUp";

interface IAuthenticationProps {
    setUser: (user: any) => void;
}
function Authentication({setUser}: IAuthenticationProps) {
    const {t} = useTranslation();

    return (
        <div className="">
            <h1 className="text-5xl font-semibold p-3">{t('pages.auth.title')}</h1>
            <Routes>
                <Route path={"signin"} element={<SignIn setUser={setUser}/>}/>
                <Route path={"signup"} element={<SignUp setUser={setUser}/>}/>
            </Routes>
        </div>
    );
}

export default Authentication;