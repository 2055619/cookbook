import React from 'react';
import {faComment, faGrinStars, faShareSquare, faTired} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useTranslation} from "react-i18next";

interface IReactionFooterProps {
    recipeTitle: string;
}

function ReactionFooter({recipeTitle}: IReactionFooterProps) {
    const {t} = useTranslation();
    return (
        <div className={"flex justify-between"}>
            <div className={"flex"}>
                <button className={"mx-2 text-2xl text-cook-light"}>
                    <FontAwesomeIcon className={"fa fa-1x"} icon={faGrinStars}/>
                    <span className={"mx-1"}>{t('miam')}</span>
                </button>
                <button className={"mx-2 text-2xl text-cook-red"}>
                    <FontAwesomeIcon className={""} icon={faTired}/>
                    <span className={"mx-1"}>{t('eww')}</span>
                </button>
            </div>
            <button className={"mx-2 text-2xl"}>
                <FontAwesomeIcon className={""} icon={faComment}/>
                <span className={"mx-1"}>{t('comment')}</span>
            </button>
            <button className={"mx-2 text-2xl"}>
                <FontAwesomeIcon className={""} icon={faShareSquare}/>
                <span className={"mx-1"}>{t('share')}</span>
            </button>
        </div>
    );
}

export default ReactionFooter;