import { IUser } from "../../assets/models/Authentication";
import React, {useState} from "react";
import TrickModification from "./TrickModification";
import RecipeModification from "./RecipeModification";
import { useTranslation } from "react-i18next";


interface PublicationCreationProps {
    user: IUser;
}

function PublicationCreation({user}: PublicationCreationProps){
    const {t} = useTranslation();

    const [publicationType, setPublicationType] = useState('recipe');

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

            {publicationType === 'recipe' && <RecipeModification user={user}/>}
            {publicationType === 'trick' && <TrickModification user={user}/>}
        </div>
    )
}

export default PublicationCreation;