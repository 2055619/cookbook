import { IUser } from "../../assets/models/Authentication";
import React, {useEffect, useState} from "react";
import TrickModification from "./TrickModification";
import RecipeModification from "./RecipeModification";
import { useTranslation } from "react-i18next";
import {toast} from "react-toastify";
import { CookBookService } from "../../services/CookBookService";


interface PublicationCreationProps {
    user: IUser;
}

function PublicationCreation({user}: PublicationCreationProps){
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [publicationType, setPublicationType] = useState('recipe');


    useEffect(() => {
        const urlParams = new URLSearchParams(window.location.search);
        const title = urlParams.get('title');
        if (title !== null) {
            cookbookService.getPublication(title)
                .then((response) => {
                    if (response.publicationType === "RECIPE"){
                        setPublicationType('recipe');
                    } else {
                        setPublicationType('trick');
                    }
                })
                .catch((error) => {
                    if (error.response?.data.message !== "NoToken"){
                        toast.error(t(error.response?.data.message));
                    }
                });
        }
    }, []);

    function handlePublicationTypeChange(event: React.ChangeEvent<HTMLSelectElement>){
        setPublicationType(event.target.value);
    }

    return (
        <div>
            <h1 className={"text-5xl"}>{t('publicationCreation')}</h1>
            <select
                className={"mt-2 bg-white border border-gray-300 rounded-md shadow-sm focus:border-cook focus:ring-cook text-base"}
                value={publicationType} onChange={handlePublicationTypeChange}>
                <option value="recipe">{t('recipe')}</option>
                <option value="trick">{t('trick')}</option>
            </select>

            {publicationType === 'recipe' && <RecipeModification user={user} setPubType={setPublicationType}/>}
            {publicationType === 'trick' && <TrickModification user={user} setPubType={setPublicationType}/>}
        </div>
    )
}

export default PublicationCreation;