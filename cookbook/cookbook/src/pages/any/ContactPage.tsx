import {useTranslation} from "react-i18next";
import React, {useEffect, useState} from "react";
import {UtilsService} from "../../services/UtilsService";
import FormInput, {IContact} from "../../assets/models/Form";
import {toast} from "react-toastify";

function ContactPage() {
    const {t} = useTranslation();
    const utilsService = new UtilsService();

    const [emailReg, setEmailReg] = useState(new RegExp(''));
    const [numberReg, setNumberReg] = useState(new RegExp(''));

    useEffect(() => {

        utilsService.getValidationPattern().then((response) => {
            setEmailReg(new RegExp(response.EMAIL_PATTERN));
            setNumberReg(new RegExp(response.PHONE_NUMBER_PATTERN));
        }).catch((error) => {
            if (error.response?.data.message !== "NoToken")
                toast.error(t(error.response?.data.message));
        });
    }, []);

    const [messageForm, setMessageForm] = useState<{ [key: string]: string }>({
        name: '',
        email: '',
        phoneNumber: '',
        message: ''
    });
    const [messageFormInfo, setMessageFormInfo] = useState([
        new FormInput('name', 'text', 'fullName', ''),
        new FormInput('email', 'text', 'pages.auth.email', ''),
        new FormInput('phoneNumber', 'text', 'phoneNumber', ''),
    ])

    const [message, setMessage] = useState('');

    function isValid() {
        let valid = true;
        if (messageForm.name === '') {
            setMessageFormInfo(messageFormInfo.map((formInfo) => {
                if (formInfo.name === 'name')
                    formInfo.warning = 'message.firstName';

                return formInfo;
            }))

            valid = false;
        }
        if (messageForm.email === '' || !emailReg.test(messageForm.email)) {
            toast.error(t('message.email'));
            setMessageFormInfo(messageFormInfo.map((formInfo) => {
                if (formInfo.name === 'email')
                    formInfo.warning = 'message.email';
                return formInfo;
            }))

            valid = false;
        }
        if (messageForm.phoneNumber === '' || !numberReg.test(messageForm.phoneNumber)) {
            setMessageFormInfo(messageFormInfo.map((formInfo) => {
                if (formInfo.name === 'phoneNumber')
                    formInfo.warning = 'message.phoneNumber';
                return formInfo;
            }))
            valid = false;
        }
        return valid;
    }

    function handleSubmit(event: any) {
        event.preventDefault();
        setMessageForm({...messageForm, message: message});

        if (!isValid()){
            return;
        }

        const finalMessage: IContact = {
            name: messageForm.name,
            email: messageForm.email,
            phoneNumber: messageForm.phoneNumber,
            message: message
        };

        utilsService.sendMessage(finalMessage)
            .then(r => {
                setMessage('');
                setMessageForm({
                    name: '',
                    email: '',
                    phoneNumber: '',
                    message: ''
                });
                toast.success(r)
            })
            .catch(e => toast.error(e));
    }

    function handleMessageChange(e: any) {
        setMessageFormInfo(messageFormInfo.map((formInfo) => {
            if (formInfo.name === e.target.id)
                formInfo.warning = '';
            return formInfo;
        }))
        setMessageForm({...messageForm, [e.target.id]: e.target.value});
    }

        return (
        <div className="">
            <h1 className={"text-7xl"}>{t('contact')}</h1>
            <p className={"text-4xl"}>{t('contactDesc')}</p>

            <form className="w-full sm:w-10/12 mx-auto text-left">
                {
                    messageFormInfo.map((formInfo, index) => (
                        <div key={index} className="my-4 mx-auto" id={formInfo.name}>
                            <input
                                className={`${formInfo.warning !== '' ? "border-cook-red" : "border-cook-light"} form-input border rounded-md p-2 w-full`}
                                id={formInfo.name}
                                onChange={handleMessageChange} type={formInfo.type}
                                value={messageForm[formInfo.name]}
                                placeholder={t(formInfo.placeholder)}/>
                            <h5 className="text-red-500">{t(formInfo.warning)}</h5>
                        </div>
                    ))
                }

                <div className="mb-4">
                    <textarea
                        placeholder={t('comment')}
                        className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                        id="message" value={message} onChange={e => setMessage(e.target.value)}/>
                </div>
            </form>
            <button type="button" onClick={handleSubmit}
                    className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 mx-5 p-2">
                {t('sendMessage')}
            </button>
        </div>
    );
}

export default ContactPage;