import React, {useEffect, useState} from "react";
import {CookBookService} from "../../services/CookBookService";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import {IUser, IUserProfile} from "../../assets/models/Authentication";
import {UtilsService} from "../../services/UtilsService";
import FormInput from "../../assets/models/Form";

interface IProfileModificationProps {
    user: IUser;
    setUser: (user: any) => void;
}

function ProfileModification({user, setUser}: IProfileModificationProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();
    const navigate = useNavigate();


    const [emailReg, setEmailReg] = useState(new RegExp(''));
    const [nameReg, setNameReg] = useState(new RegExp(''));

    const [units, setUnits] = useState({solidUnit: '', liquidUnit: '', powderUnit: '', otherUnit: ''});
    const [ing, setIng] = useState({SOLID: [''], LIQUID: [''], POWDER: [''], OTHER: ['']});

    const [creationForm, setCreationForm] = useState({
        email: '',
        firstName: '',
        lastName: '',
    });
    const [createFormInfo, setCreateFromInfo] = useState([
        new FormInput('email', 'text', 'pages.auth.email', ''),
        new FormInput('firstName', 'text', 'pages.auth.firstName', ''),
        new FormInput('lastName', 'text', 'pages.auth.lastName', ''),
    ])

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const username = urlParams.get('username');
        if (username && user === null) {
            cookbookService.getUserProfile(username)
                .then((response) => {
                    setUser(response);

                    setDefaultValues(response);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        } else if (user !== null) {
            setDefaultValues(user);
        }


        utilsService.getIngrediantStates().then((response) => {
            setIng(response);
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                toast.error(t(error.response?.data.message));
        });

        utilsService.getValidationPattern().then((response) => {
            setEmailReg(new RegExp(response.EMAIL_PATTERN));
            setNameReg(new RegExp(response.NAME_PATTERN));
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                toast.error(t(error.response?.data.message));
        });
    }, []);

    function handleCreationChange(e: any) {
        setCreateFromInfo(createFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setCreationForm({...creationForm, [e.target.id]: e.target.value});
    }

    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();

        const updatedUser: IUserProfile = {
            username: user.username,
            email: creationForm.email.trim(),
            firstName: creationForm.firstName.trim(),
            lastName: creationForm.lastName.trim(),
            ...units
        };

        if (!emailReg.test(updatedUser.email)) {
            setCreateFromInfo(createFormInfo.map((formInfo) => {
                if (formInfo.name === 'email')
                    formInfo.warning = 'message.emailInvalid';
                return formInfo;
            }))
            return;
        }

        await cookbookService.updateProfile(updatedUser)
            .then((response) => {
                setUser(response);
                toast.success(t("message.profileUpdateSuccess"));
                navigate('/u/landing');
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                    toast.error(t(error.response?.data.message));
            });
    }

    function setDefaultValues(passedUser: IUser | null = null) {
        setCreationForm({
            email: passedUser?.email || user.email || '',
            firstName: passedUser?.firstName || user.firstName || '',
            lastName: passedUser?.lastName || user.lastName || ''
        });
        setUnits({
            solidUnit: passedUser?.solidUnit || user.solidUnit || '',
            liquidUnit: passedUser?.liquidUnit || user.liquidUnit || '',
            powderUnit: passedUser?.powderUnit || user.powderUnit || '',
            otherUnit: passedUser?.otherUnit || user.otherUnit || ''
        });
    }

    return (
        <div className={""}>
            <h1 className={"text-3xl"}>{t('profileModify')}</h1>
            <form onSubmit={handleSubmit} className="h-screen">
                <div className="flex flex-col justify-center items-center mb-3">
                    {
                        createFormInfo.map((formInfo, index) => (
                            <div key={index} className="my-2 xl:w-1/3 lg:w-1/2 md:w-3/4 w-11/12" id={formInfo.name}>
                                <input
                                    className={`${formInfo.warning !== '' ? "border-cook-red" : "border-cook-light"} form-input border rounded-md p-2 w-full`}
                                    id={formInfo.name}
                                    onChange={handleCreationChange} type={formInfo.type}
                                    value={creationForm[formInfo.name as "email" | "firstName" | "lastName"]}
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
                            <label >{t('pages.auth.solidUnit')}</label>
                            <select aria-label="Default select example"
                                    onChange={(event) => {
                                        setUnits({...units, solidUnit: event.target.value});
                                    }}
                                    value={units.solidUnit}
                                    className="form-select border border-cook-light rounded-md p-2 mx-auto w-11/12">
                                {ing["SOLID"].map((unit, index) => (
                                    <option key={index} value={unit}>{t(unit)}</option>
                                ))}
                            </select>
                        </div>
                        <div className="my-1 mx-auto lg:mx-0 w-2/3" id="liquidUnitSelect">
                            <label>{t('pages.auth.liquidUnit')}</label>

                            <select aria-label="Default select example"
                                    onChange={(event) => {
                                        setUnits({...units, liquidUnit: event.target.value});
                                    }}
                                    value={units.liquidUnit}
                                    className="form-select border border-cook-light rounded-md p-2 w-11/12">
                                {ing["LIQUID"].map((unit, index) => (
                                    <option key={index} value={unit}>{t(unit)}</option>
                                ))}
                            </select>
                        </div>
                        <div className="my-1 lg:mr-0 mx-auto w-2/3" id="powderUnitSelect">
                            <label>{t('pages.auth.powderUnit')}</label>
                            <select aria-label="Default select example"
                                    onChange={(event) => {
                                        setUnits({...units, powderUnit: event.target.value});
                                    }}
                                    value={units.powderUnit}
                                    className="form-select border border-cook-light rounded-md p-2 w-11/12">
                                {ing["POWDER"].map((unit, index) => (
                                    <option key={index} value={unit}>{t(unit)}</option>
                                ))}
                            </select>
                        </div>
                        <div className="my-1 mx-auto lg:mx-0 w-2/3" id="otherUnitSelect">
                            <label>{t('pages.auth.otherUnit')}</label>
                            <select aria-label="Default select example"
                                    onChange={(event) => {
                                        setUnits({...units, otherUnit: event.target.value});
                                    }}
                                    value={units.otherUnit}
                                    className="form-select border border-cook-light rounded-md p-2 w-11/12">
                                {ing["OTHER"].map((unit, index) => (
                                    <option key={index} value={unit}>{t(unit)}</option>
                                ))}
                            </select>
                        </div>
                    </div>
                </div>

                <div className="mb-3">
                    <h1 className={"text-3xl mb-2"}>{t('pages.auth.password')}</h1>
                    <button type="button"
                            onClick={() => toast.info(t('message.notImplemented'))}
                            className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 mx-2">
                        {t('modifyPassword')}
                    </button>
                </div>
                <button type="reset" onClick={() => navigate('/u/profile?username=' + user.username)}
                        className="border border-cook-red text-cook-red hover:bg-cook-red hover:text-cook rounded transition ease-in duration-200 p-2 mx-2">
                    {t('input.cancel')}
                </button>

                <button type={"button"} onClick={() => setDefaultValues(user)}
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2 mx-2">
                    {t('input.reset')}
                </button>

                <button type="submit"
                        className="border border-cook-light text-cook-light hover:bg-cook-light hover:text-cook rounded transition ease-in duration-200 p-2 mx-2">
                    {t('input.modify')}
                </button>
            </form>
        </div>
    );
}

export default ProfileModification;