import React, {useState} from "react";
import {CookBookService} from "../../services/CookBookService";
import {IsignIn, IUser} from "../../assets/models/Authentication";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {cookServerInstance} from "../../App";
import {faEye, faEyeSlash} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


interface SignInProps {
    setUser: (user: IUser) => void;
}

function SignIn({setUser}: SignInProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);

    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();

        const user: IsignIn = {
            username: username,
            password: password
        };
        await cookbookService.signIn(user)
            .then((response) => {
                setUser(response);
                sessionStorage.setItem('token', response.token);
                cookServerInstance.defaults.headers.common['Authorization'] = response.token;
                toast.success(t("message.signInSuccess"));
                navigate('/u/landing');
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
            });
    }

    function toggleShowPassword() {
        setShowPassword(!showPassword);
    }

    return (
        <form autoComplete="false" onSubmit={handleSubmit} className="">
            <div className="flex flex-col justify-center items-center mb-3">
                <div className="mb-3 lg:w-1/2 md:w-1/2 w-11/12" id="formBasicEmail">
                    <label className="text-lg font-bold text-right my-auto w-full">{t('pages.auth.username')}</label>
                    <input
                        type="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        className="form-input border border-gray-300 rounded-md p-2 w-full"
                        placeholder={t('pages.auth.username')}
                    />
                </div>
                <div className="mb-3 lg:w-1/3 md:w-1/2 w-11/12 relative" id="formBasicPassword">
                    <label className="text-lg font-bold text-right my-auto w-full">{t('pages.auth.password')}</label>
                    <input
                        type={showPassword ? "text" : "password"}
                        autoComplete={"current-password"}
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="form-input border border-gray-300 rounded-md p-2 w-full"
                        placeholder={t('pages.auth.password')}
                    />
                    <button type="button" onClick={toggleShowPassword}
                            className="absolute right-3 mt-3 top-1/2 transform -translate-y-1/2">
                        <FontAwesomeIcon icon={showPassword ? faEyeSlash : faEye}/>
                    </button>
                </div>
            </div>
            <button type="submit"
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 m-5 p-2">
                {t('signin')}
            </button>
            <button type="button" onClick={() => {toast.info(t('message.notImplemented'))}}
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 mb-5 p-2">
                {t('pages.auth.forgotPassword')}
            </button>
            <button type="button"
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 mx-5 p-2"
                    onClick={() => {
                        navigate("/authentication/signup")
                    }}>
                {t('pages.auth.createAccount')}
            </button>
        </form>
    );
}

export default SignIn;
