import React, {useState} from "react";
import {CookBookService} from "../services/CookBookService";
import {IsignIn, IUser} from "../assets/models/Authentication";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";
import {Button, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";


interface SignInProps {
    setUser: (user: IUser) => void;
}
function SignIn({setUser}: SignInProps) {
    const cookbookService = new CookBookService();
    const {t} = useTranslation();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

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
                toast.success(t("messages.signInSuccess"));
                navigate('/landing');
            })
            .catch((error) => {
                console.log(error);
                toast.error(t(error.response.data.message));
            });
    }

    return (
        <Form autoComplete="false" onSubmit={handleSubmit} className={"vh-100"}>
            <div className={"d-flex flex-column justify-content-center align-items-center mb-3"}>
                <Form.Group className="mb-3 w-50" controlId="formBasicEmail">
                    <Form.Label>{t('pages.auth.username')}</Form.Label>
                    <Form.Control
                        type="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </Form.Group>
                <Form.Group className="mb-3 w-50" controlId="formBasicPassword">
                    <Form.Label>{t('pages.auth.password')}</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
            </div>
            <Button type="button" variant={"btn-outline-cook"} className="btn btn-outline-cook" onClick={() => { navigate("/authentication/signup")}}>
                {t('pages.auth.createAccount')}
            </Button>
            <Button type="submit" variant={"btn-outline-cook"} className="btn btn-outline-cook mx-5">
                {t('signin')}
            </Button>
            <Button type="button" variant={"btn-outline-cook"} className="btn btn-outline-cook">
                {t('pages.auth.forgotPassword')}
            </Button>
        </Form>
    );
}

export default SignIn;
