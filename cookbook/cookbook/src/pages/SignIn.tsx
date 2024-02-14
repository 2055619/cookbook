import React, {useState} from "react";
import {CookBookService} from "../services/CookBookService";
import {IsignIn} from "../assets/models/Authentication";
import {toast} from "react-toastify";
import {useTranslation} from "react-i18next";

function SignIn() {
    const cookbookService = new CookBookService();
    const { t } = useTranslation();
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
                console.log(response);
                toast.success(t("pages.authentication.signInSuccess"));
            })
            .catch((error) => {
                console.log(error);
                toast.error(t(error));
            });
    }

    return (
        <form onSubmit={handleSubmit}>
            <input
                type="email"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <button type="submit">Log In</button>
        </form>
    );
}

export default SignIn;
