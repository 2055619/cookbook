import {Button, Form} from "react-bootstrap";
import React, {FormEvent, useState} from "react";
import {useTranslation} from "react-i18next";
import {IsignUp, IUser} from "../assets/models/Authentication";
import {CookBookService} from "../services/CookBookService";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import FormInput from "../assets/models/Form";

interface ISignUpProps {
    setUser: (user: any) => void;
}

function SignUp({setUser}: ISignUpProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const navigate = useNavigate();
    const [solidUnit, setSolidUnit] = useState('');
    const [liquidUnit, setLiquidUnit] = useState('');
    const [powderUnit, setPowderUnit] = useState('');
    const [otherUnit, setOtherUnit] = useState('');

    const regEmail = new RegExp('^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$');
    const regPassword = new RegExp('^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$');

    const [creationForm, setCreationForm] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        confirmPassword: ''
    });
    const [createFormInfo, setCreateFromInfo] = useState([
        new FormInput('firstName',  'text', 'pages.auth.firstName', ''),
        new FormInput('lastName', 'text', 'pages.auth.lastName', ''),
        new FormInput('username',  'text', 'pages.auth.username', ''),
        new FormInput('email',  'text', 'pages.auth.email', ''),
        new FormInput('password', 'password', 'pages.auth.password', ''),
        new FormInput('confirmPassword', 'password', 'pages.auth.confirmPassword', '')
    ])

    function handleCreationChange(e: any) {
        setCreateFromInfo(createFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setCreationForm({...creationForm, [e.target.id]: e.target.value});
    }

    function isValid() {
        let isValid = true;

        if (creationForm.firstName.trim().length < 2) {
            toast.error(t('messages.firstName'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'firstName')
                    formInfo.warning = 'messages.firstName';
                return formInfo;
            }))
            isValid = false;
        }
        if (creationForm.lastName.trim().length < 2) {
            toast.error(t('messages.lastName'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'lastName')
                    formInfo.warning = 'messages.lastName';
                return formInfo;
            }))
            isValid = false;
        }
        if (creationForm.username.trim().length < 4) {
            toast.error(t('messages.username'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'username')
                    formInfo.warning = 'messages.username';
                return formInfo;
            }))
            isValid = false;
        }
        if (!regEmail.test(creationForm.email)) {
            toast.error(t('messages.email'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'email')
                    formInfo.warning = 'messages.email';
                return formInfo;
            }))
            isValid = false;
        }
        if (!regPassword.test(creationForm.password)) {
            toast.error(t('messages.password'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'password')
                    formInfo.warning = 'messages.password';
                return formInfo;
            }))
            isValid = false;
        }
        if (creationForm.password !== creationForm.confirmPassword) {
            toast.error(t('messages.passwords'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'confirmPassword')
                    formInfo.warning = 'messages.passwords';
                return formInfo;
            }))
            isValid = false;
        }
        return isValid;
    }

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();

        if (!isValid())
            return;

        const signUpUser: IsignUp = {
            username: creationForm.username,
            email: creationForm.email,
            firstName: creationForm.firstName,
            lastName: creationForm.lastName,
            password: creationForm.password,
            solidUnit: solidUnit,
            liquidUnit: liquidUnit,
            powderUnit: powderUnit,
            otherUnit: otherUnit
        }

        cookbookService.signUp(signUpUser).then((response) => {
            setUser(response);
            sessionStorage.setItem('token', response.token);
            toast.success(t("messages.signInSuccess"));
            navigate('/landing');
        }).catch((error) => {
            if (error.response.data.message === 'usernameTaken'){
                setCreateFromInfo(createFormInfo.map((formInfo) => {
                    if (formInfo.name === 'username')
                        formInfo.warning = 'messages.usernameTaken';
                    return formInfo;
                }))
            }
            toast.error(t(error.response.data.message));
        });
    };

    return (
        <Form onSubmit={handleSubmit} className={"pb-5"}>
            <div className={"d-flex flex-column justify-content-center align-items-center mb-3"}>
                {
                    createFormInfo.map((formInfo, index) => (
                        <Form.Group key={index} className="my-2 col-4" controlId={formInfo.name}>
                            <Form.Control className={`${formInfo.warning !== '' ? "is-invalid" : ""}`}
                                          onChange={handleCreationChange} type={formInfo.type}
                                          placeholder={t(formInfo.placeholder)}/>
                            <h5 className="text-danger">{t(formInfo.warning)}</h5>
                        </Form.Group>
                    ))
                }
            </div>
            <div className="row mb-3">
                <h1>{t('pages.auth.preference')}</h1>
                <div className={"col-2"}></div>
                <Form.Group controlId="solidUnit" className={"my-2 col-4"}>
                    <Form.Control type="text" value={solidUnit} placeholder={t('pages.auth.solidUnit')}
                                  onChange={(e) => setSolidUnit(e.target.value)}/>
                </Form.Group>
                <Form.Group controlId="liquidUnit" className={"my-2 col-4"}>
                    <Form.Control type="text" value={liquidUnit} placeholder={t('pages.auth.liquidUnit')}
                                  onChange={(e) => setLiquidUnit(e.target.value)}/>
                </Form.Group>
                <div className={"col-2"}></div>
                <div className={"col-2"}></div>
                <Form.Group controlId="powderUnit" className={"my-2 col-4"}>
                    <Form.Control type="text" value={powderUnit} placeholder={t('pages.auth.powderUnit')}
                                  onChange={(e) => setPowderUnit(e.target.value)}/>
                </Form.Group>
                <Form.Group controlId="otherUnit" className={"my-2 col-4"}>
                    <Form.Control type="text" value={otherUnit} placeholder={t('pages.auth.otherUnit')}
                                  onChange={(e) => setOtherUnit(e.target.value)}/>
                </Form.Group>
            </div>

            <Button type="submit" variant={"btn-outline-cook"} className={"btn btn-outline-cook"}>{t('signup')}</Button>
        </Form>
    );
}

export default SignUp;