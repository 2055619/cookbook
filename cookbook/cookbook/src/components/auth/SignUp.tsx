import React, {FormEvent, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {IsignUp} from "../../assets/models/Authentication";
import {CookBookService} from "../../services/CookBookService";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import FormInput from "../../assets/models/Form";
import {UtilsService} from "../../services/UtilsService";
import {cookServerInstance} from "../../App";

interface ISignUpProps {
    setUser: (user: any) => void;
}

function SignUp({setUser}: ISignUpProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();
    const navigate = useNavigate();

    const [lawCheck, setLawCheck] = useState(false);
    const [lawWarning, setLawWarning] = useState("");

    const [emailReg, setEmailReg] = useState(new RegExp(''));
    const [passwordReg, setPasswordReg] = useState(new RegExp(''));
    const [usernameReg, setUsernameReg] = useState(new RegExp(''));
    const [nameReg, setNameReg] = useState(new RegExp(''));

    const [units, setUnits] = useState({solidUnit: '', liquidUnit: '', powderUnit: '', otherUnit: ''});
    const [ing, setIng] = useState({SOLID: [''], LIQUID: [''], POWDER: [''], OTHER: ['']});

    useEffect(() => {
        utilsService.getIngrediantStates().then((response) => {
            setIng(response);
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
        });

        utilsService.getValidationPattern().then((response) => {
            setEmailReg(new RegExp(response.EMAIL_PATTERN));
            setPasswordReg(new RegExp(response.PASSWORD_PATTERN));
            setUsernameReg(new RegExp(response.USERNAME_PATTERN));
            setNameReg(new RegExp(response.NAME_PATTERN));
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
        });
    }, []);

    const [creationForm, setCreationForm] = useState({
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        password: '',
        confirmPassword: ''
    });
    const [createFormInfo, setCreateFromInfo] = useState([
        new FormInput('firstName', 'text', 'pages.auth.firstName', ''),
        new FormInput('lastName', 'text', 'pages.auth.lastName', ''),
        new FormInput('username', 'text', 'pages.auth.username', ''),
        new FormInput('email', 'text', 'pages.auth.email', ''),
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

        if (!nameReg.test(creationForm.firstName.trim())) {
            toast.error(t('message.firstName'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'firstName')
                    formInfo.warning = 'message.firstName';
                return formInfo;
            }))
            isValid = false;
        }
        if (!nameReg.test(creationForm.lastName.trim())) {
            toast.error(t('message.lastName'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'lastName')
                    formInfo.warning = 'message.lastName';
                return formInfo;
            }))
            isValid = false;
        }
        if (!usernameReg.test(creationForm.username.trim())) {
            toast.error(t('message.username'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'username')
                    formInfo.warning = 'message.username';
                return formInfo;
            }))
            isValid = false;
        }
        if (!emailReg.test(creationForm.email.trim())) {
            toast.error(t('message.email'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'email')
                    formInfo.warning = 'message.email';
                return formInfo;
            }))
            isValid = false;
        }
        if (!passwordReg.test(creationForm.password.trim())) {
            toast.error(t('message.password'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'password')
                    formInfo.warning = 'message.password';
                return formInfo;
            }))
            isValid = false;
        }
        if (creationForm.password.trim() !== creationForm.confirmPassword.trim()) {
            toast.error(t('message.passwords'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'confirmPassword')
                    formInfo.warning = 'message.passwords';
                return formInfo;
            }))
            isValid = false;
        }

        if (!lawCheck) {
            toast.error(t('message.law25'));
            setLawWarning('message.law25');
            isValid = false;
        }

        return isValid;
    }

    const handleSubmit = (e: FormEvent) => {
        e.preventDefault();

        if (!isValid())
            return;

        const signUpUser: IsignUp = {
            username: creationForm.username.trim(),
            email: creationForm.email.trim(),
            firstName: creationForm.firstName.trim(),
            lastName: creationForm.lastName.trim(),
            password: creationForm.password.trim(),
            ...units
        }

        cookbookService.signUp(signUpUser).then((response) => {
            setUser(response);
            sessionStorage.setItem('token', response.token);
            cookServerInstance.defaults.headers.common['Authorization'] = response.token;
            toast.success(t("message.signInSuccess"));
            navigate('/u/landing');
        }).catch((error) => {
            if (error.response.data?.value === '418') {
                setCreateFromInfo(createFormInfo.map((formInfo) => {
                    if (formInfo.name === 'username')
                        formInfo.warning = 'message.usernameTaken';
                    return formInfo;
                }))
            }
            if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
        });
    };

    return (
        <form onSubmit={handleSubmit} className="h-screen">
            <div className="flex flex-col justify-center items-center mb-3">
                {
                    createFormInfo.map((formInfo, index) => (
                        <div key={index} className="my-2 xl:w-1/3 lg:w-1/2 md:w-3/4 w-11/12" id={formInfo.name}>
                            <input
                                className={`${formInfo.warning !== '' ? "border-cook-red" : "border-cook-light"} form-input border rounded-md p-2 w-full`}
                                id={formInfo.name}
                                onChange={handleCreationChange} type={formInfo.type}
                                placeholder={t(formInfo.placeholder)}/>
                            <h5 className="text-red-500">{t(formInfo.warning)}</h5>
                        </div>
                    ))
                }
            </div>
            <div className="row mb-3">
                <h1 className={"text-3xl"}>{t('pages.auth.preference')}</h1>
                <div className="grid lg:grid-cols-2 grid-cols-1 lg:gap-4 gap-1 mb-3 ">
                    <div className="my-1 lg:mr-0 mx-auto w-2/3" id="solidUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, solidUnit: event.target.value});
                                }}
                                className="form-select border border-cook-light rounded-md p-2 mx-auto w-11/12">
                            <option>{t('pages.auth.solidUnit')}</option>
                            {ing["SOLID"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                    <div className="my-1 mx-auto lg:mx-0 w-2/3" id="liquidUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, liquidUnit: event.target.value});
                                }}
                                className="form-select border border-cook-light rounded-md p-2 w-11/12">
                            <option>{t('pages.auth.liquidUnit')}</option>
                            {ing["LIQUID"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                    <div className="my-1 lg:mr-0 mx-auto w-2/3" id="powderUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, powderUnit: event.target.value});
                                }}
                                className="form-select border border-cook-light rounded-md p-2 w-11/12">
                            <option>{t('pages.auth.powderUnit')}</option>
                            {ing["POWDER"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                    <div className="my-1 mx-auto lg:mx-0 w-2/3" id="otherUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, otherUnit: event.target.value});
                                }}
                                className="form-select border border-cook-light rounded-md p-2 w-11/12">
                            <option>{t('pages.auth.otherUnit')}</option>
                            {ing["OTHER"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                </div>
                <div className="clickable" onClick={() => {
                    setLawCheck(!lawCheck)
                    lawCheck ? setLawWarning("message.law25") : setLawWarning("")
                }}>
                    <input type={"checkbox"} checked={lawCheck} className={"mx-2 clickable"}/>
                    <label className={"mx-2 clickable"}>{t('pages.auth.law25')}</label>
                    <h5 className="text-red-500">{t(lawWarning)}</h5>
                </div>
                <span className={"clickable "} onClick={() => navigate("/policies")}>{t('seePolicy')}</span>
            </div>

            <button type="submit"
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 mx-5">
                {t('signup')}
            </button>
        </form>
    );
}

export default SignUp;