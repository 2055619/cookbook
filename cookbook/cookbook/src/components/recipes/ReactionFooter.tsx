import React, {useEffect, useState} from 'react';
import {faComment, faShareSquare, faStar} from '@fortawesome/free-solid-svg-icons';
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {useTranslation} from "react-i18next";
import {CookBookService} from "../../services/CookBookService";
import {IPublication, IReaction} from "../../assets/models/Publication";
import {toast} from "react-toastify";

interface IReactionFooterProps {
    publication: IPublication;
    username: string;
}

function ReactionFooter({publication, username}: IReactionFooterProps) {
    const {t} = useTranslation();
    const cookbookService = new CookBookService();

    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(-1);
    const [reaction, setReaction] = useState<IReaction>({
        id: null,
        rating: null,
        comment: null,
        publicationId: publication.id,
        cookUsername: username
    });

    useEffect(() => {
        cookbookService.getReactionsByPublication(publication)
            .then((response) => {
                // setReaction(response);
                response.map((reaction) => {
                    if (reaction.cookUsername === username) {
                        setReaction(reaction);
                        setRating(reaction.rating!);
                    }
                });
            })
            .catch((error) => {
                toast.error(t(error.response?.data.message));
            });
    }, []);

    function handleClick(index: number) {
        setRating(index + 1);
        setReaction({...reaction, rating: index + 1});

        cookbookService.ratePublication(reaction)
            .then((response) => {
                setReaction(response);
            })
            .catch((error) => {
                toast.error(t(error.response?.data.message));
            });
        console.log("react: ", reaction)
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