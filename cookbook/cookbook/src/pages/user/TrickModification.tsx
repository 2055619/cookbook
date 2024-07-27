import React, {useState, useEffect} from 'react';
import {useTranslation} from 'react-i18next';
import {toast} from 'react-toastify';
import {CookBookService} from '../../services/CookBookService';
import {UtilsService} from '../../services/UtilsService';
import {IUser} from '../../assets/models/Authentication';
import {IRecipe, ITrick} from "../../assets/models/Publication";

interface TrickModificationProps {
    user: IUser,
    setPubType: (value: (((prevState: string) => string) | string)) => void
}

function TrickModification({user, setPubType}: TrickModificationProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();
    const utilsService = new UtilsService();

    const [trick, setTrick] = useState<ITrick>();

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [visibility, setVisibility] = useState('PUBLIC');

    const [titleWarning, setTitleWarning] = useState('');
    const [descriptionWarning, setDescriptionWarning] = useState('');

    const [allVisibility, setAllVisibility] = useState<string[]>([]);

    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');
        if (title !== null) {
            cookbookService.getTrick(title)
                .then((response) => {
                    setPubType('trick')
                    setTrick(response);

                    setTitle(response.title);
                    setDescription(response.description);
                    setVisibility(response.visibility);
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }

        utilsService.getVisibility()
            .then((response) => {
                setAllVisibility(response);
            })
            .catch((error) => {
                if (error.response?.data.message !== "NoToken")
                    toast.error(t(error.response?.data.message));
            });
    }, []);

    function isValid() {
        let isValid = true;

        if (title === '') {
            setTitleWarning('titleEmpty');
            isValid = false;
        }
        if (description === '') {
            toast.error(t('descriptionEmpty'));
            isValid = false;
        }

        return isValid;
    }

    function handleSubmit(e: React.FormEvent) {
        e.preventDefault();

        const newTrick: ITrick = {
            id: trick?.id !== undefined ? trick.id : -1,
            title,
            description,
            cookUsername: user.username,
            creationDate: new Date().toISOString(),
            visibility,
            averageRating: 0,
            publicationType: 'TRICK',
        };

        if (!isValid()) {
            toast.error(t('invalidForm'));
            return;
        }

        if (trick !== undefined) {
            cookbookService.updateTrick(newTrick)
                .then(() => {
                    toast.success(t('trickUpdated'));
                    window.history.back();
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });

        } else {
            cookbookService.createTrick(newTrick)
                .then(() => {
                    toast.success(t('trickCreated'));
                    window.history.back();
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken")
                        toast.error(t(error.response?.data.message));
                });
        }
    }


    return (
        <div className={""}>
            <h1 className="text-3xl font-bold text-center">{t('createTrick')}</h1>
            <form onSubmit={handleSubmit}
                  className="flex flex-col items-center space-y-4 md:w-full lg:w-3/4 max-w-full mx-auto p-4">
                <label
                    className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('title')}</span>
                    <input type="text" value={title} onChange={e => setTitle(e.target.value)}
                           placeholder={trick?.title || t('input.title')}
                           className={`border-2 p-2 rounded ${titleWarning !== '' ? "border-cook-red" : "border-cook-light"}`}/>
                    <h5 className="text-red-500">{t(titleWarning)}</h5>
                </label>
                <label className="flex flex-col space-y-1 w-full">
                    <span className="font-medium">{t('description')}</span>
                    <textarea value={description} onChange={e => setDescription(e.target.value)}
                              placeholder={trick?.description || t('input.description')}
                              className={`border-2 p-2 rounded ${descriptionWarning !== '' ? "border-cook-red" : "border-cook-light"}`}/>
                    <h5 className="text-red-500">{t(descriptionWarning)}</h5>
                </label>

                <label className="flex flex-col space-y-1">
                    <span className="font-medium">{t('visibility')}</span>
                    <select value={visibility} onChange={e => setVisibility(e.target.value)}
                            className="border-2 border-cook-light p-2 rounded">
                        {allVisibility.map((vis, index) => (
                            <option key={index} value={vis}>{t(vis)}</option>
                        ))}
                    </select>
                </label>
                <button type="submit" onClick={handleSubmit}
                        className="border border-cook text-cook hover:bg-cook hover:text-cook-orange rounded transition ease-in duration-200 p-2">
                    {trick !== undefined ? t('updateTrick') : t('createTrick')}
                </button>
            </form>
        </div>
    );
}

export default TrickModification;