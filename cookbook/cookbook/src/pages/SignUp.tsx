import React, {FormEvent, useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import {IsignUp} from "../assets/models/Authentication";
import {CookBookService} from "../services/CookBookService";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import FormInput from "../assets/models/Form";
import {UtilsService} from "../services/UtilsService";

interface ISignUpProps {
    setUser: (user: any) => void;
}

function SignUp({setUser}: ISignUpProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();
    const navigate = useNavigate();

    const [emailReg, setEmailReg] = useState(new RegExp(''));
    const [passwordReg, setPasswordReg] = useState(new RegExp(''));

    const [units, setUnits] = useState({solidUnit: '', liquidUnit: '', powderUnit: '', otherUnit: ''});
    const [ing, setIng] = useState({SOLID: [''], LIQUID: [''], POWDER: [''], OTHER: ['']});

    useEffect(() => {
        utilsService.getIngrediantStates().then((response) => {
            setIng(response.data);
        }).catch((error) => {
            toast.error(t(error.response.data.message));
        });

        utilsService.getValidationPattern().then((response) => {
            setEmailReg(new RegExp(response.data.EMAIL_PATTERN));
            setPasswordReg(new RegExp(response.data.PASSWORD_PATTERN));
        }).catch((error) => {
            toast.error(t(error.response.data.message));
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
        if (!emailReg.test(creationForm.email)) {
            toast.error(t('messages.email'));
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'email')
                    formInfo.warning = 'messages.email';
                return formInfo;
            }))
            isValid = false;
        }
        if (!passwordReg.test(creationForm.password)) {
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
            ...units
        }

        cookbookService.signUp(signUpUser).then((response) => {
            setUser(response);
            sessionStorage.setItem('token', response.token);
            toast.success(t("messages.signInSuccess"));
            navigate('/landing');
        }).catch((error) => {
            if (error.response.data.value === '418') {
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
        <form onSubmit={handleSubmit} className="h-screen">
            <div className="flex flex-col justify-center items-center mb-3">
                {
                    createFormInfo.map((formInfo, index) => (
                        <div key={index} className="my-2 w-1/3" id={formInfo.name}>
                            <input
                                className={`${formInfo.warning !== '' ? "border-red-500" : ""} form-input border border-gray-300 rounded-md p-2 w-full`}
                                onChange={handleCreationChange} type={formInfo.type}
                                placeholder={t(formInfo.placeholder)}/>
                            <h5 className="text-red-500">{t(formInfo.warning)}</h5>
                        </div>
                    ))
                }
            </div>
            <div className="row mb-3">
                <h1>{t('pages.auth.preference')}</h1>
                <div className="grid grid-cols-2 gap-4 mb-3">
                    <div className="my-1 ml-auto w-2/3" id="solidUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, solidUnit: event.target.value});
                                }}
                                className="form-select border border-gray-300 rounded-md p-2 w-full">
                            <option>{t('pages.auth.solidUnit')}</option>
                            {ing["SOLID"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                    <div className="my-1 mr-auto w-2/3" id="liquidUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, liquidUnit: event.target.value});
                                }}
                                className="form-select border border-gray-300 rounded-md p-2 w-full">
                            <option>{t('pages.auth.liquidUnit')}</option>
                            {ing["LIQUID"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                    <div className="my-1 ml-auto w-2/3" id="powderUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, powderUnit: event.target.value});
                                }}
                                className="form-select border border-gray-300 rounded-md p-2 w-full">
                            <option>{t('pages.auth.powderUnit')}</option>
                            {ing["POWDER"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                    <div className="my-1 mr-auto w-2/3" id="otherUnitSelect">
                        <select aria-label="Default select example"
                                onChange={(event) => {
                                    setUnits({...units, otherUnit: event.target.value});
                                }}
                                className="form-select border border-gray-300 rounded-md p-2 w-full">
                            <option>{t('pages.auth.otherUnit')}</option>
                            {ing["OTHER"].map((unit, index) => (
                                <option key={index} value={unit}>{t(unit)}</option>
                            ))}
                        </select>
                    </div>
                </div>
                {/*<div className="flex flex-wrap justify-between items-center mb-3">*/}
                {/*    <div className="my-2 w-1/3" id="solidUnitSelect">*/}
                {/*        <select aria-label="Default select example"*/}
                {/*                onChange={(event) => {*/}
                {/*                    setUnits({...units, solidUnit: event.target.value});*/}
                {/*                }}*/}
                {/*                className="form-select border border-gray-300 rounded-md p-2 w-full">*/}
                {/*            <option>{t('pages.auth.solidUnit')}</option>*/}
                {/*            {ing["SOLID"].map((unit, index) => (*/}
                {/*                <option key={index} value={unit}>{t(unit)}</option>*/}
                {/*            ))}*/}
                {/*        </select>*/}
                {/*    </div>*/}
                {/*    <div className="my-2 w-1/3" id="liquidUnitSelect">*/}
                {/*        <select aria-label="Default select example"*/}
                {/*                onChange={(event) => {*/}
                {/*                    setUnits({...units, liquidUnit: event.target.value});*/}
                {/*                }}*/}
                {/*                className="form-select border border-gray-300 rounded-md p-2 w-full">*/}
                {/*            <option>{t('pages.auth.liquidUnit')}</option>*/}
                {/*            {ing["LIQUID"].map((unit, index) => (*/}
                {/*                <option key={index} value={unit}>{t(unit)}</option>*/}
                {/*            ))}*/}
                {/*        </select>*/}
                {/*    </div>*/}
                {/*    <div className="my-2 w-1/3" id="powderUnitSelect">*/}
                {/*        <select aria-label="Default select example"*/}
                {/*                onChange={(event) => {*/}
                {/*                    setUnits({...units, powderUnit: event.target.value});*/}
                {/*                }}*/}
                {/*                className="form-select border border-gray-300 rounded-md p-2 w-full">*/}
                {/*            <option>{t('pages.auth.powderUnit')}</option>*/}
                {/*            {ing["POWDER"].map((unit, index) => (*/}
                {/*                <option key={index} value={unit}>{t(unit)}</option>*/}
                {/*            ))}*/}
                {/*        </select>*/}
                {/*    </div>*/}
                {/*    <div className="my-2 w-1/3" id="otherUnitSelect">*/}
                {/*        <select aria-label="Default select example"*/}
                {/*                onChange={(event) => {*/}
                {/*                    setUnits({...units, otherUnit: event.target.value});*/}
                {/*                }}*/}
                {/*                className="form-select border border-gray-300 rounded-md p-2 w-full">*/}
                {/*            <option>{t('pages.auth.otherUnit')}</option>*/}
                {/*            {ing["OTHER"].map((unit, index) => (*/}
                {/*                <option key={index} value={unit}>{t(unit)}</option>*/}
                {/*            ))}*/}
                {/*        </select>*/}
                {/*    </div>*/}
                {/*</div>*/}
            </div>

            <button type="submit"
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 mx-5">
                {t('signup')}
            </button>
        </form>
    );
}

export default SignUp;