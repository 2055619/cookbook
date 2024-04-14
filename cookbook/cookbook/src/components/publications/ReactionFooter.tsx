import React, {useEffect, useState} from 'react';
import {faComment, faShareSquare, faStar} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IPublication, IRecipe} from "../../assets/models/Publication";
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

interface IReactionFooterProps {
    publication: IPublication;
    username: string;
}

function ReactionFooter({publication, username}: IReactionFooterProps) {
    const {t} = useTranslation();
    const navigate = useNavigate();
    const cookbookService = new CookBookService();

    const [avgRating, setAvgRating] = useState(0);

    useEffect(() => {
        cookbookService.getReactionsByPublication(publication)
            .then((response) => {
                let sum = 0;
                response.map((reaction) => {
                    sum += reaction.rating!;
                    return reaction;
                });
                setAvgRating(sum / response.length);

            })
            .catch((error) => {
                toast.error(t(error.response?.data.message));
            });
    }, []);

    function isRecipe(publication: IPublication): publication is IRecipe {
        return (publication as IRecipe).instructions !== undefined;
    }

    function isTrick(publication: IPublication): publication is IRecipe {
        return (publication as IRecipe).instructions === undefined;
    }

    return (
        <div className={"flex justify-between"}>
            <div className={"flex text-3xl"}>
                {[...Array(5)].map((star, index) => {
                    return (
                        <FontAwesomeIcon
                            key={index}
                            icon={faStar}
                            className={` ${index < Math.floor(avgRating) ? 'text-cook-light' : 'text-cook'} `}
                        />
                    );
                })}
            </div>

            <button className={"mx-2 text-2xl"} onClick={() => navigate(`/u/reactions?title=${publication.title}`)}>
                <FontAwesomeIcon className={""} icon={faComment} />
                <span className={"mx-1 hidden md:inline"}>{t('comment')}</span>
            </button>
            <button className={"mx-2 text-2xl"}>
                <FontAwesomeIcon className={""} icon={faShareSquare} onClick={() => console.log("share")}/>
                <span className={"mx-1 hidden md:inline"}>{t('share')}</span>
            </button>
        </div>
    );
}

export default ReactionFooter;