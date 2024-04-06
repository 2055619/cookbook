import React, {useEffect, useState} from 'react';
import {faComment, faShareSquare, faStar} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IPublication} from "../../assets/models/Publication";

interface IReactionFooterProps {
    publication: IPublication;
    username: string;
}

function ReactionFooter({publication, username}: IReactionFooterProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(-1);

    useEffect(() => {
        cookbookService.getReactionByPublication(publication)
            .then((response) => {
                setRating(response.rating);
            })
            .catch((error) => {
                console.error(error);
            });
    }, []);
    function handleClick(index: number){
        setRating(index + 1);
        console.log(`Clicked star number: ${index + 1}`);
    }

    return (
        <div className={"flex justify-between"}>
            <div className={"flex clickable text-3xl"}>
                {[...Array(5)].map((star, index) => {
                    return (
                        <FontAwesomeIcon
                            key={index}
                            icon={faStar}
                            className={`hover:text-cook-light ${index <= hover || index < rating ? 'text-cook-light' : 'text-cook'}`}
                            onClick={() => handleClick(index)}
                            onMouseEnter={() => setHover(index)} // Add this line
                            onMouseLeave={() => setHover(-1)} // And this line
                        />
                    );
                })}
            </div>

            <button className={"mx-2 text-2xl"}>
                <FontAwesomeIcon className={""} icon={faComment} onClick={() => console.log("comment")}/>
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